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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZHeadManager extends ZUtils implements HeadManager {

    private final String API_URL = "http://mib.test/api/v1/heads";
    private final HeadPlugin plugin;
    private final Map<HeadCategory, List<Head>> heads = new HashMap<>();

    public ZHeadManager(HeadPlugin plugin) {
        this.plugin = plugin;
        for (HeadCategory headCategory : HeadCategory.values()) heads.put(headCategory, new ArrayList<>());
    }

    @Override
    public void downloadHead(boolean force) {

        File file = new File(plugin.getDataFolder(), "heads.json");
        if (file.exists()) {
            this.loadHeads();
            return;
        }

        long ms = System.currentTimeMillis();

        try {
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
}
