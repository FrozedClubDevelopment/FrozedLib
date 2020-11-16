package club.frozed.lib.scoreboard;

import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Ryzeon
 * Project: FrozedHubDeluxe
 * Date: 10/11/2020 @ 13:30
 */

public interface BoardAdapter {

    String getTitle(Player player);

    List<String> getLines(Player player);

    BoardStyle getBoardStyle(Player player);
}

