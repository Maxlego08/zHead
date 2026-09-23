package fr.maxlego08.head.save;

import fr.maxlego08.head.zcore.utils.ZUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.stream.Collectors;

public class ItemConfiguration extends ZUtils {

    private final String name;
    private final List<String> lore;

    public ItemConfiguration(String name, List<String> lore) {
        this.name = name;
        this.lore = lore;
    }

    public String getName() {
        return name;
    }

    public List<String> getLore() {
        return lore;
    }

    public void apply(ItemStack itemStack, Object... objects) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(color(getMessage(this.name, objects)));
        itemMeta.setLore(color(this.lore).stream().map(e -> getMessage(e, objects)).collect(Collectors.toList()));

        itemStack.setItemMeta(itemMeta);
    }

    public void applyName(ItemStack itemStack, Object... objects) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(color(getMessage(this.name, objects)));

        itemStack.setItemMeta(itemMeta);
    }
}
