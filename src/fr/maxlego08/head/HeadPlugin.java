package fr.maxlego08.head;

import fr.maxlego08.head.api.HeadManager;
import fr.maxlego08.head.command.commands.CommandHead;
import fr.maxlego08.head.command.commands.CommandPlayerHead;
import fr.maxlego08.head.inventory.inventories.InventoryHeadPagination;
import fr.maxlego08.head.inventory.inventories.InventoryHeadSearch;
import fr.maxlego08.head.inventory.inventories.InventoryHeads;
import fr.maxlego08.head.placeholder.LocalPlaceholder;
import fr.maxlego08.head.save.Config;
import fr.maxlego08.head.save.MessageLoader;
import fr.maxlego08.head.zcore.ZPlugin;
import fr.maxlego08.head.zcore.enums.EnumInventory;
import fr.maxlego08.head.zcore.utils.plugins.Metrics;
import fr.maxlego08.head.zcore.utils.plugins.VersionChecker;
import org.bukkit.plugin.ServicePriority;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * System to create your plugins very simply Projet:
 * <a href="https://github.com/Maxlego08/TemplatePlugin">https://github.com/Maxlego08/TemplatePlugin</a>
 *
 * @author Maxlego08
 */
public class HeadPlugin extends ZPlugin {

    private HeadManager headManager;

    @Override
    public void onEnable() {

        LocalPlaceholder placeholder = LocalPlaceholder.getInstance();
        placeholder.setPrefix("zhead");

        this.preEnable();

        this.saveDefaultConfig();
        this.headManager = new ZHeadManager(this);

        this.registerCommand("zhead", new CommandHead(this), "head", "zhd");
        this.registerCommand("playerhead", new CommandPlayerHead(this), "ph");
        this.registerInventory(EnumInventory.HEADS, new InventoryHeads());
        this.registerInventory(EnumInventory.HEADS_PAGINATION, new InventoryHeadPagination());
        this.registerInventory(EnumInventory.SEARCH, new InventoryHeadSearch());
        this.getServer().getServicesManager().register(HeadManager.class, this.headManager, this, ServicePriority.Highest);

        this.addSave(new MessageLoader(this));

        this.headManager.downloadHead(isDateBeforeLimit(new File(getDataFolder(), "date.txt"), LocalDateTime.of(2025, 2, 12, 0, 0, 0)));

        Config.getInstance().loadConfiguration(this);
        this.loadFiles();

        this.headManager.registerPlaceholders();

        new Metrics(this, 21364);
        VersionChecker versionChecker = new VersionChecker(this, 317);
        versionChecker.useLastVersion();

        this.postEnable();
    }

    @Override
    public void onDisable() {

        this.preDisable();

        this.saveFiles();

        this.postDisable();
    }

    public HeadManager getHeadManager() {
        return headManager;
    }

    public boolean isDateBeforeLimit(File file, LocalDateTime localDateTime) {

        if (!file.exists()) return false;

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String dateContent = reader.readLine();

            if (dateContent == null || dateContent.trim().isEmpty()) {
                return false;
            }

            LocalDateTime fileDate = LocalDateTime.parse(dateContent.trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
            return fileDate.isBefore(localDateTime);
        } catch (IOException | java.time.format.DateTimeParseException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

}
