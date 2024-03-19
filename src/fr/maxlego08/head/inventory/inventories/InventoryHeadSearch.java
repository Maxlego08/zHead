package fr.maxlego08.head.inventory.inventories;

import fr.maxlego08.head.HeadPlugin;
import fr.maxlego08.head.api.Head;
import fr.maxlego08.head.api.HeadManager;
import fr.maxlego08.head.exceptions.InventoryOpenException;
import fr.maxlego08.head.inventory.VInventory;
import fr.maxlego08.head.save.Config;
import fr.maxlego08.head.zcore.enums.EnumInventory;
import fr.maxlego08.head.zcore.utils.builder.ItemBuilder;
import fr.maxlego08.head.zcore.utils.inventory.InventoryResult;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class InventoryHeadSearch extends VInventory {

    @Override
    public InventoryResult openInventory(HeadPlugin plugin, Player player, int page, Object... args) throws InventoryOpenException {

        HeadManager headManager = plugin.getHeadManager();
        List<Head> heads = (List<Head>) args[0];

        createInventory(color(getMessage(Config.searchInventoryName, "%count%", format(heads.size()))), 54);

        InventoryHeadPagination.searchButton(this);
        InventoryHeadPagination.backButton(this, plugin);
        InventoryHeadPagination.displayHeads(this, headManager, page, heads);
        displayPagination(heads, page, headManager);


        return InventoryResult.SUCCESS;
    }

    private void displayPagination(List<Head> heads, int page, HeadManager manager) {

        int maxPage = getMaxPage(heads, InventoryHeadPagination.PAGINATION_SIZE);
        int size = 7;
        int slot = 46;
        page -= 3;

        for (int i = 0; i != size; i++) {

            int index = page + i;
            if (index > maxPage || index < 1) {
                slot++;
                continue;
            }

            ItemBuilder itemBuilder = new ItemBuilder(Material.PAPER, color(getMessage(Config.pageItemName, "%page%", index)));
            itemBuilder.setAmount(index > 64 ? 1 : index);
            if (index == this.page) itemBuilder.glow();
            addItem(slot++, itemBuilder).setClick(event -> createInventory(this.plugin, player, EnumInventory.SEARCH, index, heads));
        }
    }
}
