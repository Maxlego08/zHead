package fr.maxlego08.head.api;

import fr.maxlego08.head.api.enums.HeadCategory;
import org.bukkit.entity.Player;

import java.util.List;

public interface HeadManager {

    void downloadHead(boolean force);

    void downloadHead(HeadCategory headCategory);

    void loadHead(HeadCategory headCategory);

    long count();

    void openCategory(Player player, HeadCategory headCategory, int page);

    long count(HeadCategory headCategory);

    List<Head> getHeads(HeadCategory headCategory);
}
