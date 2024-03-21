package fr.maxlego08.head.command.commands;

import fr.maxlego08.head.HeadPlugin;
import fr.maxlego08.head.api.HeadManager;
import fr.maxlego08.head.command.VCommand;
import fr.maxlego08.head.zcore.enums.Message;
import fr.maxlego08.head.zcore.enums.Permission;
import fr.maxlego08.head.zcore.utils.commands.CommandType;
import org.bukkit.entity.Player;

public class CommandHeadRandom extends VCommand {

    public CommandHeadRandom(HeadPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.ZHEAD_RANDOM);
        this.addSubCommand("random");
        this.setDescription(Message.DESCRIPTION_RANDOM);
        this.addOptionalArg("amount");
        this.addOptionalArg("player");
    }

    @Override
    protected CommandType perform(HeadPlugin plugin) {

        int amount = this.argAsInteger(0, 1);
        Player player = this.argAsPlayer(1, this.player);

        HeadManager manager = plugin.getHeadManager();
        manager.give(this.sender, player, randomElement(manager.getHeads()), amount);

        return CommandType.SUCCESS;
    }

}
