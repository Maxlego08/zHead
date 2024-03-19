package fr.maxlego08.head.save;

import fr.maxlego08.head.HeadPlugin;
import fr.maxlego08.head.api.enums.HeadCategory;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {

    public static boolean enableDebug = true;
    public static boolean enableDebugTime = false;

    public static Map<HeadCategory, String> categoryNames = new HashMap<>();

    public static String headInventoryName;
    public static String paginationInventoryName;
    public static String headItemName;
    public static List<String> headItemLore;
    public static String paginateItemName;
    public static List<String> paginateItemLore;

    /**
     * static Singleton instance.
     */
    private static volatile Config instance;


    /**
     * Private constructor for singleton.
     */
    private Config() {
    }

    /**
     * Return a singleton instance of Config.
     */
    public static Config getInstance() {
        // Double lock for thread safety.
        if (instance == null) {
            synchronized (Config.class) {
                if (instance == null) {
                    instance = new Config();
                }
            }
        }
        return instance;
    }

    public void loadConfiguration(HeadPlugin plugin) {
        FileConfiguration configuration = plugin.getConfig();
        for (HeadCategory headCategory : HeadCategory.values()) {
            categoryNames.put(headCategory, configuration.getString("category." + headCategory.name().toLowerCase(), headCategory.getName()));
        }

        headInventoryName = configuration.getString("inventory.heads.name");
        headItemName = configuration.getString("inventory.heads.item.name");
        headItemLore = configuration.getStringList("inventory.heads.item.lore");

        paginationInventoryName = configuration.getString("inventory.pagination.name");
        paginateItemName = configuration.getString("inventory.pagination.item.name");
        paginateItemLore = configuration.getStringList("inventory.pagination.item.lore");
    }

}
