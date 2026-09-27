package fr.maxlego08.head.save;

import fr.maxlego08.head.HeadPlugin;
import fr.maxlego08.head.api.enums.HeadCategory;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class Config {

    public static volatile boolean enableDebug = true;
    public static volatile boolean enableDebugTime = false;

    public static Map<HeadCategory, String> categoryNames = new ConcurrentHashMap<>();

    public static volatile String headInventoryName;
    public static volatile String searchInventoryName;
    public static volatile String paginationInventoryName;
    public static volatile ItemConfiguration headItem;
    public static volatile ItemConfiguration paginateItem;
    public static volatile ItemConfiguration refreshItem;
    public static volatile ItemConfiguration informationItem;
    public static volatile ItemConfiguration searchItem;
    public static volatile String backItemName;
    public static volatile String pageItemName;

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
        informationItem = new ItemConfiguration(configuration.getString("inventory.heads.informations.name"), configuration.getStringList("inventory.heads.informations.lore"));

        paginationInventoryName = configuration.getString("inventory.pagination.name");
        paginateItem = new ItemConfiguration(configuration.getString("inventory.pagination.item.name"), configuration.getStringList("inventory.pagination.item.lore"));
        searchItem = new ItemConfiguration(configuration.getString("inventory.pagination.search.name"), configuration.getStringList("inventory.pagination.search.lore"));

        searchInventoryName = configuration.getString("inventory.search.name");

        backItemName = configuration.getString("inventory.back");
        pageItemName = configuration.getString("inventory.page");
    }
}
