package fr.maxlego08.head;

import fr.maxlego08.head.api.HeadManager;
import fr.maxlego08.head.command.commands.CommandHead;
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
        this.registerInventory(EnumInventory.HEADS, new InventoryHeads());
        this.registerInventory(EnumInventory.HEADS_PAGINATION, new InventoryHeadPagination());
        this.registerInventory(EnumInventory.SEARCH, new InventoryHeadSearch());
        this.getServer().getServicesManager().register(HeadManager.class, this.headManager, this, ServicePriority.Highest);

        this.addSave(new MessageLoader(this));

        this.headManager.downloadHead(false);

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
}
