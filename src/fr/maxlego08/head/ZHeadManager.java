package fr.maxlego08.head;

import com.google.gson.reflect.TypeToken;
import fr.maxlego08.head.api.Head;
import fr.maxlego08.head.api.HeadManager;
import fr.maxlego08.head.api.enums.HeadCategory;
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
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ZHeadManager extends ZUtils implements HeadManager {

    private final HeadPlugin plugin;
    private final Map<HeadCategory, List<Head>> heads = new HashMap<>();

    public ZHeadManager(HeadPlugin plugin) {
        this.plugin = plugin;
        for (HeadCategory headCategory : HeadCategory.values()) heads.put(headCategory, new ArrayList<>());
    }

    @Override
    public void downloadHead(boolean force) {

        File file = new File(plugin.getDataFolder(), "heads.json");
        if (file.exists() && !force) {
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

        long duration = System.currentTimeMillis() - ms;
        this.plugin.getLogger().info("Download done in " + duration + "ms");

        this.loadHeads();
    }

    public void loadHeads() {

        File file = new File(plugin.getDataFolder(), "heads.json");
        if (!file.exists()) {
            downloadHead(true);
            return;
        }

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
        Config.headItem.applyName(itemStack, "%name%", head.getName());
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
}
