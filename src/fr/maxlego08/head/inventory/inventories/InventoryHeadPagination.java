package fr.maxlego08.head.inventory.inventories;

import fr.maxlego08.head.HeadPlugin;
import fr.maxlego08.head.api.Head;
import fr.maxlego08.head.api.HeadManager;
import fr.maxlego08.head.api.enums.HeadCategory;
import fr.maxlego08.head.exceptions.InventoryOpenException;
import fr.maxlego08.head.inventory.VInventory;
import fr.maxlego08.head.save.Config;
import fr.maxlego08.head.zcore.enums.EnumInventory;
import fr.maxlego08.head.zcore.utils.builder.ItemBuilder;
import fr.maxlego08.head.zcore.utils.inventory.InventoryResult;
import fr.maxlego08.head.zcore.utils.inventory.Pagination;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class InventoryHeadPagination extends VInventory {

    public static final int PAGINATION_SIZE = 45;

    public static void backButton(VInventory inventory, HeadPlugin plugin) {

        ItemBuilder itemBuilder = new ItemBuilder(Material.BARRIER, inventory.color(Config.backItemName));
        inventory.addItem(45, itemBuilder).setClick(event -> inventory.createInventory(plugin, inventory.getPlayer(), EnumInventory.HEADS));
    }

    public static void searchButton(VInventory inventory) {

        ItemStack itemStack = new ItemStack(Material.COMPASS);
        Config.searchItem.apply(itemStack);
        inventory.addItem(53, itemStack);
    }

    public static void displayHeads(VInventory inventory, HeadManager headManager, int page, List<Head> heads) {

        Pagination<Head> pagination = new Pagination<>();
        heads = pagination.paginate(heads, PAGINATION_SIZE, page);

        for (int slot = 0; slot != heads.size(); slot++) {

            Head head = heads.get(slot);

            ItemStack itemStack = inventory.createSkull(head.getValue());
            Config.paginateItem.apply(itemStack, "%name%", head.getName(), "%category%", Config.categoryNames.get(head.getHeadCategory()), "%tags%", head.getTags(), "%id%", head.getId());
            inventory.addItem(slot, itemStack).setClick(event -> headManager.give(inventory.getPlayer(), inventory.getPlayer(), head, 1));
        }
    }

    @Override
    public InventoryResult openInventory(HeadPlugin plugin, Player player, int page, Object... args) throws InventoryOpenException {

        HeadManager headManager = plugin.getHeadManager();
        HeadCategory headCategory = (HeadCategory) args[0];
        List<Head> heads = headManager.getHeads(headCategory);
        String categoryName = Config.categoryNames.get(headCategory);

        createInventory(color(getMessage(Config.paginationInventoryName, "%count%", format(heads.size()), "%category%", categoryName)), 54);

        searchButton(this);
        backButton(this, plugin);
        displayHeads(this, headManager, page, heads);
        displayPagination(heads, headCategory, page, headManager);


        return InventoryResult.SUCCESS;
    }

    private void displayPagination(List<Head> heads, HeadCategory headCategory, int page, HeadManager manager) {

        int maxPage = getMaxPage(heads, PAGINATION_SIZE);
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
            addItem(slot++, itemBuilder).setClick(event -> manager.openCategory(player, headCategory, index));
        }
    }
}
