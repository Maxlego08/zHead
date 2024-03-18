package fr.maxlego08.head.command.commands;

import fr.maxlego08.head.HeadPlugin;
import fr.maxlego08.head.command.VCommand;
import fr.maxlego08.head.zcore.enums.Permission;
import fr.maxlego08.head.zcore.utils.commands.CommandType;

public class CommandTemplate extends VCommand {

	public CommandTemplate(HeadPlugin plugin) {
		super(plugin);
		this.setPermission(Permission.EXAMPLE_PERMISSION);
		this.addSubCommand(new CommandTemplateReload(plugin));
	}

	@Override
	protected CommandType perform(HeadPlugin plugin) {
		syntaxMessage();
		return CommandType.SUCCESS;
	}

}
