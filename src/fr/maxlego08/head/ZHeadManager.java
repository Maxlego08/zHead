package fr.maxlego08.head;

import com.google.gson.reflect.TypeToken;
import fr.maxlego08.head.api.Head;
import fr.maxlego08.head.api.HeadManager;
import fr.maxlego08.head.api.enums.HeadCategory;
import fr.maxlego08.head.zcore.enums.EnumInventory;
import fr.maxlego08.head.zcore.utils.ZUtils;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZHeadManager extends ZUtils implements HeadManager {

    private final String API_URL = "https://minecraft-heads.com/scripts/api.php?cat=%s&tags=true";
    private final HeadPlugin plugin;
    private final Map<HeadCategory, List<Head>> heads = new HashMap<>();

    public ZHeadManager(HeadPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void downloadHead(boolean force) {
        for (HeadCategory headCategory : HeadCategory.values()) {
            File file = headCategory.getFile(this.plugin);
            if (file.exists() && !force) this.loadHead(headCategory);
            else this.downloadHead(headCategory);
        }
    }

    @Override
    public void downloadHead(HeadCategory headCategory) {
        long ms = System.currentTimeMillis();

        String name = headCategory.getName();
        String urlString = String.format(API_URL, name);
        try {
            URL url = new URL(urlString);
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

            FileWriter file = new FileWriter(headCategory.getFile(this.plugin));
            file.write(json.toJSONString());
            file.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        long duration = System.currentTimeMillis() - ms;
        this.plugin.getLogger().info("Download done in " + duration + "ms (" + name + ")");

        this.loadHead(headCategory);
    }

    @Override
    public void loadHead(HeadCategory headCategory) {

        try {
            FileReader fileReader = new FileReader(headCategory.getFile(plugin));
            List<Head> headList = plugin.getGson().fromJson(fileReader, new TypeToken<List<Head>>() {
            }.getType());

            this.heads.put(headCategory, headList);
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
}
