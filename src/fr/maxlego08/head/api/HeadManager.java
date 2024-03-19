package fr.maxlego08.head.api;

import fr.maxlego08.head.api.enums.HeadCategory;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface HeadManager {

    void downloadHead(boolean force);

    void loadHeads();

    long count();

    void openCategory(Player player, HeadCategory headCategory, int page);

    long count(HeadCategory headCategory);

    List<Head> getHeads(HeadCategory headCategory);

    void give(CommandSender sender, Player player, Head head, int amount);

    void give(CommandSender sender, Player player, String headId, int amount);

    Optional<Head> getHead(String id);

    ItemStack createItemStack(String id);

    Date getUpdatedAt();
}
