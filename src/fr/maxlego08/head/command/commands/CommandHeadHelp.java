package fr.maxlego08.head.command.commands;

import fr.maxlego08.head.HeadPlugin;
import fr.maxlego08.head.command.VCommand;
import fr.maxlego08.head.zcore.enums.Message;
import fr.maxlego08.head.zcore.enums.Permission;
import fr.maxlego08.head.zcore.utils.commands.CommandType;

public class CommandHeadHelp extends VCommand {

	public CommandHeadHelp(HeadPlugin plugin) {
		super(plugin);
		this.setPermission(Permission.ZHEAD_HELP);
		this.addSubCommand("reload", "rl");
		this.setDescription(Message.DESCRIPTION_HELP);
	}

	@Override
	protected CommandType perform(HeadPlugin plugin) {
		
		parent.syntaxMessage();
		
		return CommandType.SUCCESS;
	}

}
