package fr.maxlego08.head;

import com.google.gson.reflect.TypeToken;
import fr.maxlego08.head.api.Head;
import fr.maxlego08.head.api.HeadManager;
import fr.maxlego08.head.api.enums.HeadCategory;
import fr.maxlego08.head.placeholder.LocalPlaceholder;
import fr.maxlego08.head.save.Config;
import fr.maxlego08.head.zcore.enums.EnumInventory;
import fr.maxlego08.head.zcore.enums.Message;
import fr.maxlego08.head.zcore.utils.ZUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ZHeadManager extends ZUtils implements HeadManager {

    private final HeadPlugin plugin;
    private final Map<HeadCategory, List<Head>> heads = new HashMap<>();
    private Date updatedAt = new Date();

    public ZHeadManager(HeadPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerPlaceholders() {

        LocalPlaceholder placeholder = LocalPlaceholder.getInstance();
        placeholder.register("count", (player) -> String.valueOf(count()));
        placeholder.register("count_format", (player) -> simplifyNumber(count()));

        placeholder.register("category_count_format_", (player, args) -> {
            try {
                return simplifyNumber(count(HeadCategory.valueOf(args.toUpperCase())));
            } catch (Exception exception) {
                return args + " category was not found";
            }
        });
        placeholder.register("category_count_", (player, args) -> {
            try {
                return String.valueOf(count(HeadCategory.valueOf(args.toUpperCase())));
            } catch (Exception exception) {
                return args + " category was not found";
            }
        });
    }

    @Override
    public void downloadHead(boolean force) {

        File file = new File(plugin.getDataFolder(), "heads.json");
        if (file.exists() && !force) {
            this.loadDate();
            this.loadHeads();
            return;
        }

        long ms = System.currentTimeMillis();

        this.plugin.getLogger().info("Download starts");

        try {
            String API_URL = "https://minecraft-inventory-builder.com/api/v1/heads";
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();

            JSONParser parser = new JSONParser();
            JSONArray json = (JSONArray) parser.parse(content.toString());

            if (file.exists()) {
                this.plugin.getLogger().info("Delete old json file");
                file.delete();
            }

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(json.toJSONString());
            fileWriter.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        this.updatedAt = new Date();
        long duration = System.currentTimeMillis() - ms;
        this.plugin.getLogger().info("Download done in " + duration + "ms");

        this.loadHeads();
        this.saveDate();
    }

    private void saveDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String formattedDate = dateFormat.format(this.updatedAt);

        File dateFile = new File(plugin.getDataFolder(), "date.txt");
        if (dateFile.exists()) dateFile.delete();

        try {
            if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdirs();

            try (FileWriter writer = new FileWriter(dateFile, false)) {
                writer.write(formattedDate);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDate() {
        File dateFile = new File(plugin.getDataFolder(), "date.txt");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        try (BufferedReader reader = new BufferedReader(new FileReader(dateFile))) {
            String dateString = reader.readLine();

            if (dateString != null) this.updatedAt = dateFormat.parse(dateString);
            else this.updatedAt = new Date();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void loadHeads() {

        File file = new File(plugin.getDataFolder(), "heads.json");
        if (!file.exists()) {
            downloadHead(true);
            return;
        }

        this.heads.clear();

        try {
            FileReader fileReader = new FileReader(file);
            List<Head> headList = plugin.getGson().fromJson(fileReader, new TypeToken<List<Head>>() {
            }.getType());

            headList.forEach(head -> this.heads.computeIfAbsent(head.getHeadCategory(), k -> new ArrayList<>()).add(head));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public long count() {
        return this.heads.values().stream().mapToLong(List::size).sum();
    }

    @Override
    public long count(HeadCategory headCategory) {
        return this.heads.get(headCategory).size();
    }

    @Override
    public void openCategory(Player player, HeadCategory headCategory, int page) {
        createInventory(this.plugin, player, EnumInventory.HEADS_PAGINATION, page, headCategory);
    }

    @Override
    public List<Head> getHeads(HeadCategory headCategory) {
        return this.heads.get(headCategory);
    }

    @Override
    public void give(CommandSender sender, Player player, Head head, int amount) {
        ItemStack itemStack = createSkull(head.getValue());
        itemStack.setAmount(amount);
        Config.paginateItem.applyName(itemStack, "%name%", head.getName());
        give(player, itemStack);

        message(sender, Message.GIVE, "%name%", head.getName(), "%id%", head.getId());
    }

    @Override
    public void give(CommandSender sender, Player player, String headId, int amount) {
        Optional<Head> optional = getHead(headId);
        if (!optional.isPresent()) {
            message(sender, Message.NOT_FOUND, "%id%", headId);
            return;
        }

        give(sender, player, optional.get(), amount);
    }

    @Override
    public Optional<Head> getHead(String id) {
        return this.heads.values().stream().flatMap(List::stream).filter(e -> e.getId().equalsIgnoreCase(id)).findFirst();
    }

    @Override
    public ItemStack createItemStack(String id) {
        return getHead(id).map(head -> createSkull(head.getValue())).orElse(null);
    }

    @Override
    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    @Override
    public void search(Player player, String value) {
        List<Head> lists = search(value);
        createInventory(this.plugin, player, EnumInventory.SEARCH, 1, lists);
    }

    @Override
    public List<Head> search(String value) {
        return this.heads.values().stream().flatMap(List::stream).filter(e -> e.getId().toLowerCase().contains(value.toLowerCase()) || e.getName().toLowerCase().contains(value.toLowerCase()) || e.getTags().toLowerCase().contains(value.toLowerCase())).collect(Collectors.toList());
    }
}
