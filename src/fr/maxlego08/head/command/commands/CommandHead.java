package fr.maxlego08.head.command.commands;

import fr.maxlego08.head.HeadPlugin;
import fr.maxlego08.head.command.VCommand;
import fr.maxlego08.head.zcore.enums.EnumInventory;
import fr.maxlego08.head.zcore.enums.Permission;
import fr.maxlego08.head.zcore.utils.commands.CommandType;

public class CommandHead extends VCommand {

    public CommandHead(HeadPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.ZHEAD_USE);
        this.addSubCommand(new CommandHeadReload(plugin));
        this.addSubCommand(new CommandHeadUpdate(plugin));
        this.addSubCommand(new CommandHeadHelp(plugin));
        this.addSubCommand(new CommandHeadGive(plugin));
        this.addSubCommand(new CommandHeadVersion(plugin));
        this.onlyPlayers();
    }

    @Override
    protected CommandType perform(HeadPlugin plugin) {

        createInventory(plugin, this.player, EnumInventory.HEADS);
        return CommandType.SUCCESS;
    }

}
