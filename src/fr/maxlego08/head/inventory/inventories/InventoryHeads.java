package fr.maxlego08.head.inventory.inventories;

import fr.maxlego08.head.HeadPlugin;
import fr.maxlego08.head.api.HeadManager;
import fr.maxlego08.head.api.enums.HeadCategory;
import fr.maxlego08.head.exceptions.InventoryOpenException;
import fr.maxlego08.head.inventory.VInventory;
import fr.maxlego08.head.save.Config;
import fr.maxlego08.head.zcore.utils.inventory.InventoryResult;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryHeads extends VInventory {

    private final List<Integer> decorationSlot = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53);

    @Override
    public InventoryResult openInventory(HeadPlugin plugin, Player player, int page, Object... args) throws InventoryOpenException {

        HeadManager headManager = plugin.getHeadManager();
        long counts = headManager.count();

        createInventory(color(getMessage(Config.headInventoryName, "%count%", simplifyNumber(counts))), 54);

        displayDecoration();
        displayHeads(headManager);

        return InventoryResult.SUCCESS;
    }

    private void displayDecoration() {
        decorationSlot.forEach(slot -> {
            ItemStack itemStack = blackGlassPane();
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName("");
            itemStack.setItemMeta(itemMeta);
            addItem(slot, itemStack);
        });
    }

    private void displayHeads(HeadManager headManager) {
        int slot = 10;
        for (HeadCategory headCategory : HeadCategory.values()) {
            ItemStack itemStack = createSkull(headCategory.getUrl());
            ItemMeta itemMeta = itemStack.getItemMeta();

            itemMeta.setDisplayName(color(getMessage(Config.headItemName, "%category%", Config.categoryNames.get(headCategory))));
            itemMeta.setLore(color(Config.headItemLore).stream().map(e -> getMessage(e, "%count%", format(headManager.count(headCategory)))).collect(Collectors.toList()));

            itemStack.setItemMeta(itemMeta);
            addItem(slot++, itemStack).setClick(event -> headManager.openCategory(player, headCategory, 1));
            if (slot == 17) slot = 19;
        }
    }
}
