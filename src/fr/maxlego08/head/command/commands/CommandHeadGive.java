package fr.maxlego08.head.command.commands;

import fr.maxlego08.head.HeadPlugin;
import fr.maxlego08.head.command.VCommand;
import fr.maxlego08.head.zcore.enums.Message;
import fr.maxlego08.head.zcore.enums.Permission;
import fr.maxlego08.head.zcore.utils.commands.CommandType;
import org.bukkit.entity.Player;

public class CommandHeadGive extends VCommand {

	public CommandHeadGive(HeadPlugin plugin) {
		super(plugin);
		this.setPermission(Permission.ZHEAD_GIVE);
		this.addSubCommand("reload", "rl");
		this.setDescription(Message.DESCRIPTION_UPDATE);
		this.addRequireArg("head id");
		this.addOptionalArg("amount");
		this.addOptionalArg("player");
	}

	@Override
	protected CommandType perform(HeadPlugin plugin) {

		String headId = this.argAsString(0);
		int amount = this.argAsInteger(1, 1);
		Player player = this.argAsPlayer(2, this.player);

		plugin.getHeadManager().give(sender, player, headId, amount);
		
		return CommandType.SUCCESS;
	}

}
