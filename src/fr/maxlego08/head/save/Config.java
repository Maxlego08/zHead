package fr.maxlego08.head.save;

import fr.maxlego08.head.HeadPlugin;
import fr.maxlego08.head.api.enums.HeadCategory;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class Config {

    public static boolean enableDebug = true;
    public static boolean enableDebugTime = false;

    public static Map<HeadCategory, String> categoryNames = new HashMap<>();

    public static String headInventoryName;
    public static String paginationInventoryName;
    public static ItemConfiguration headItem;
    public static ItemConfiguration paginateItem;
    public static ItemConfiguration refreshItem;
    public static String backItemName;
    public static String pageItemName;

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
        headItem = new ItemConfiguration(configuration.getString("inventory.heads.item.name"), configuration.getStringList("inventory.heads.item.lore"));
        refreshItem = new ItemConfiguration(configuration.getString("inventory.heads.refresh.name"), configuration.getStringList("inventory.heads.refresh.lore"));

        paginationInventoryName = configuration.getString("inventory.pagination.name");
        paginateItem = new ItemConfiguration(configuration.getString("inventory.pagination.item.name"), configuration.getStringList("inventory.pagination.item.lore"));

        backItemName = configuration.getString("inventory.back");
        pageItemName = configuration.getString("inventory.page");
    }
}
