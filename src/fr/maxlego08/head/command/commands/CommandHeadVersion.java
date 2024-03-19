package fr.maxlego08.head.command.commands;

import fr.maxlego08.head.HeadPlugin;
import fr.maxlego08.head.command.VCommand;
import fr.maxlego08.head.zcore.enums.Message;
import fr.maxlego08.head.zcore.enums.Permission;
import fr.maxlego08.head.zcore.utils.commands.CommandType;
import org.bukkit.entity.Player;

public class CommandHeadVersion extends VCommand {

	public CommandHeadVersion(HeadPlugin plugin) {
		super(plugin);
		this.addSubCommand("version", "ver", "info", "i", "v");
		this.setDescription(Message.DESCRIPTION_VERSION);
	}

	@Override
	protected CommandType perform(HeadPlugin plugin) {

		message(this.sender, "§aVersion du plugin§7: §2" + plugin.getDescription().getVersion());
		message(this.sender, "§aAuteur§7: §2Maxlego08");
		message(this.sender, "§aDiscord§7: §2http://discord.groupez.dev/");
		message(this.sender, "§aSponsor§7: §chttps://minecraft-inventory-builder.com/");
		message(this.sender, "§fDatabase from§7: §chttps://minecraft-heads.com/");

		return CommandType.SUCCESS;
	}

}
