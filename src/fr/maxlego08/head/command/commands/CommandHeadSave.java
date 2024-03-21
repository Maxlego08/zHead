package fr.maxlego08.head.command.commands;

import fr.maxlego08.head.HeadPlugin;
import fr.maxlego08.head.api.Head;
import fr.maxlego08.head.api.HeadManager;
import fr.maxlego08.head.api.HeadSignature;
import fr.maxlego08.head.command.VCommand;
import fr.maxlego08.head.zcore.enums.Message;
import fr.maxlego08.head.zcore.enums.Permission;
import fr.maxlego08.head.zcore.utils.commands.CommandType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class CommandHeadSave extends VCommand {

    public CommandHeadSave(HeadPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.ZHEAD_SAVE);
        this.addSubCommand("save");
        this.setDescription(Message.DESCRIPTION_SAVE);
        this.onlyPlayers();
    }

    @Override
    protected CommandType perform(HeadPlugin plugin) {

        ItemStack itemStack = this.player.getItemInHand();
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            message(sender, Message.INFO_ERROR_AIR);
            return CommandType.SUCCESS;
        }

        HeadManager manager = plugin.getHeadManager();
        HeadSignature headSignature = manager.getHeadSignature();
        String headId = headSignature.getHeadId(itemStack);
        if (headId == null) {
            message(sender, Message.INFO_ERROR_NOT);
            return CommandType.SUCCESS;
        }

        Optional<Head> optional = manager.getHead(headId);
        if (!optional.isPresent()) {
            message(sender, Message.INFO_ERROR_HEAD, "%id%", headId);
            return CommandType.SUCCESS;
        }

        manager.saveHead(this.sender, optional.get());

        return CommandType.SUCCESS;
    }

}
