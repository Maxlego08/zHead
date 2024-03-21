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

public class CommandHeadInfo extends VCommand {

    public CommandHeadInfo(HeadPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.ZHEAD_INFO);
        this.addSubCommand("info", "i");
        this.setDescription(Message.DESCRIPTION_INFO);
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

        manager.sendInformations(this.sender, optional.get());

        return CommandType.SUCCESS;
    }

}
