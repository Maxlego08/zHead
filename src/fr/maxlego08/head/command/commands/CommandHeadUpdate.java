package fr.maxlego08.head.command.commands;

import fr.maxlego08.head.HeadPlugin;
import fr.maxlego08.head.command.VCommand;
import fr.maxlego08.head.zcore.enums.Message;
import fr.maxlego08.head.zcore.enums.Permission;
import fr.maxlego08.head.zcore.utils.commands.CommandType;

public class CommandHeadUpdate extends VCommand {

	public CommandHeadUpdate(HeadPlugin plugin) {
		super(plugin);
		this.setPermission(Permission.ZHEAD_UPDATE);
		this.addSubCommand("reload", "rl");
		this.setDescription(Message.DESCRIPTION_UPDATE);
	}

	@Override
	protected CommandType perform(HeadPlugin plugin) {
		
		plugin.reloadFiles();
		message(sender, Message.RELOAD);
		
		return CommandType.SUCCESS;
	}

}
