package fr.maxlego08.head.command.commands;

import fr.maxlego08.head.HeadPlugin;
import fr.maxlego08.head.command.VCommand;
import fr.maxlego08.head.zcore.enums.Message;
import fr.maxlego08.head.zcore.enums.Permission;
import fr.maxlego08.head.zcore.utils.commands.CommandType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

public class CommandPlayerHead extends VCommand {

    public CommandPlayerHead(HeadPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.ZHEAD_PLAYER_HEAD);
        this.onlyPlayers();
        this.addRequireArg("player");
    }

    @Override
    protected CommandType perform(HeadPlugin plugin) {

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            OfflinePlayer offlinePlayer = this.argAsOfflinePlayer(0);
            ItemStack itemStack = playerHead(offlinePlayer);
            give(this.player, itemStack);
            message(this.sender, Message.GIVE_HEAD, "%name%", offlinePlayer.getName());
        });

        return CommandType.SUCCESS;
    }

}
