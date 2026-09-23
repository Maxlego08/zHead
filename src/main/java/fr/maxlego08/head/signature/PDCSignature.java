package fr.maxlego08.head.signature;

import fr.maxlego08.head.api.Head;
import fr.maxlego08.head.api.HeadSignature;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class PDCSignature implements HeadSignature {

    private final NamespacedKey namespacedKey;

    public PDCSignature(Plugin plugin) {
        this.namespacedKey = new NamespacedKey(plugin, HeadSignature.KEY);
    }

    @Override
    public ItemStack sign(ItemStack itemStack, Head head) {
        if (itemStack == null) return null;
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return itemStack;
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        persistentDataContainer.set(namespacedKey, PersistentDataType.STRING, head.getId());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public String getHeadId(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return null;
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        return persistentDataContainer.has(this.namespacedKey, PersistentDataType.STRING) ? persistentDataContainer.get(this.namespacedKey, PersistentDataType.STRING) : null;
    }
}
