package fr.maxlego08.head.signature;

import fr.maxlego08.head.api.Head;
import fr.maxlego08.head.api.HeadSignature;
import fr.maxlego08.head.zcore.utils.nms.ItemStackCompound;
import org.bukkit.inventory.ItemStack;

public class NMSignature implements HeadSignature {

    @Override
    public ItemStack sign(ItemStack itemStack, Head head) {
        if (itemStack == null) return null;
        return ItemStackCompound.itemStackCompound.setString(itemStack, HeadSignature.KEY, head.getId());
    }

    @Override
    public String getHeadId(ItemStack itemStack) {
        return ItemStackCompound.itemStackCompound.getString(itemStack, HeadSignature.KEY);
    }
}
