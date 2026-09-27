package fr.maxlego08.head.command.commands;

import fr.maxlego08.head.HeadPlugin;
import fr.maxlego08.head.command.VCommand;
import fr.maxlego08.head.zcore.enums.Message;
import fr.maxlego08.head.zcore.enums.Permission;
import fr.maxlego08.head.zcore.utils.commands.CommandType;
import fr.maxlego08.head.zcore.utils.nms.NmsVersion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class CommandPlayerHead extends VCommand {

    public CommandPlayerHead(HeadPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.ZHEAD_PLAYER_HEAD);
        this.onlyPlayers();
        this.addRequireArg("player");
    }

    @Override
    protected CommandType perform(HeadPlugin plugin) {

        // Command instances are shared: capture the invocation before profile resolution completes.
        String name = this.argAsString(0);
        Player recipient = this.player;
        if (!NmsVersion.nmsVersion.hasPlayerProfiles()) {
            // Preserve player heads on Paper versions predating the profile API.
            plugin.getScheduler().runAsync(() -> {
                ItemStack itemStack = playerHead(Bukkit.getOfflinePlayer(name));
                plugin.getScheduler().runPlayer(recipient, () -> {
                    give(recipient, itemStack);
                    message(recipient, Message.GIVE_HEAD, "%name%", name);
                });
            });
            return CommandType.SUCCESS;
        }
        Bukkit.createPlayerProfile(name).update().thenAccept(profile ->
                plugin.getScheduler().runPlayer(recipient, () -> {
                    ItemStack itemStack = playerHead();
                    SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
                    meta.setOwnerProfile(profile);
                    itemStack.setItemMeta(meta);
                    give(recipient, itemStack);
                    message(recipient, Message.GIVE_HEAD, "%name%", name);
                })).exceptionally(error -> {
                    plugin.getLogger().warning("Cannot resolve player head for " + name + ": " + error.getMessage());
                    return null;
                });

        return CommandType.SUCCESS;
    }

}
