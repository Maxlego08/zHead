package fr.maxlego08.head.command.commands;

import fr.maxlego08.head.HeadPlugin;
import fr.maxlego08.head.command.VCommand;
import fr.maxlego08.head.zcore.enums.Message;
import fr.maxlego08.head.zcore.enums.Permission;
import fr.maxlego08.head.zcore.utils.commands.CommandType;

public class CommandHeadSearch extends VCommand {

    public CommandHeadSearch(HeadPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.ZHEAD_SEARCH);
        this.addSubCommand("search");
        this.setDescription(Message.DESCRIPTION_SEARCH);
        this.addRequireArg("value");
        this.onlyPlayers();
    }

    @Override
    protected CommandType perform(HeadPlugin plugin) {

        String value = this.argAsString(0);
        plugin.getHeadManager().search(this.player, value);

        return CommandType.SUCCESS;
    }

}
