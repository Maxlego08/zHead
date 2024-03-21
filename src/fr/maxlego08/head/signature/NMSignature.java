package fr.maxlego08.head.signature;

import fr.maxlego08.head.api.Head;
import fr.maxlego08.head.api.HeadSignature;
import org.bukkit.inventory.ItemStack;

public class NMSignature implements HeadSignature {

    @Override
    public ItemStack sign(ItemStack itemStack, Head head) {
        if (itemStack == null) return null;
        return itemStack;
    }

    @Override
    public String getHeadId(ItemStack itemStack) {
        return "TODO";
    }
}
