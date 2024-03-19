package fr.maxlego08.head.inventory.inventories;

import fr.maxlego08.head.HeadPlugin;
import fr.maxlego08.head.api.Head;
import fr.maxlego08.head.api.HeadManager;
import fr.maxlego08.head.api.enums.HeadCategory;
import fr.maxlego08.head.exceptions.InventoryOpenException;
import fr.maxlego08.head.inventory.VInventory;
import fr.maxlego08.head.save.Config;
import fr.maxlego08.head.zcore.utils.inventory.InventoryResult;
import fr.maxlego08.head.zcore.utils.inventory.Pagination;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryHeadPagination extends VInventory {

    private final int PAGINATION_SIZE = 45;

    @Override
    public InventoryResult openInventory(HeadPlugin plugin, Player player, int page, Object... args) throws InventoryOpenException {

        HeadManager headManager = plugin.getHeadManager();
        HeadCategory headCategory = (HeadCategory) args[0];
        List<Head> heads = headManager.getHeads(headCategory);
        String categoryName = Config.categoryNames.get(headCategory);

        createInventory(color(getMessage(Config.headInventoryName, "%count%", format(heads.size()), "%category%", categoryName)), 54);

        displayHeads(headManager, page, heads, categoryName);

        return InventoryResult.SUCCESS;
    }

    private void displayHeads(HeadManager headManager, int page, List<Head> heads, String categoryName) {

        Pagination<Head> pagination = new Pagination<>();
        heads = pagination.paginate(heads, PAGINATION_SIZE, page);

        for (int slot = 0; slot != heads.size(); slot++) {

            Head head = heads.get(slot);

            ItemStack itemStack = createSkull(head.getValue());
            ItemMeta itemMeta = itemStack.getItemMeta();

            itemMeta.setDisplayName(color(getMessage(Config.paginateItemName, "%name%", head.getName())));
            itemMeta.setLore(color(Config.paginateItemLore).stream().map(e -> getMessage(e,
                    "%category%", categoryName,
                    "%tags%", head.getTags(),
                    "%id%", "todo"
            )).collect(Collectors.toList()));

            itemStack.setItemMeta(itemMeta);
            addItem(slot, itemStack).setClick(event -> {

            });
        }
    }
}
