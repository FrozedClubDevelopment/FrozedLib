package club.frozed.lib.nametag;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

/**
 * Created by Ryzeon
 * Project: FrozedLib
 * Date: 26/11/2020 @ 21:33
 */

public class NameTagAPI {

    public static void addToTeam(Player player, Player other, String text) {
        Team team = player.getScoreboard().getTeam("mole");
        if (team == null)
            try {
                team = player.getScoreboard().registerNewTeam("mole");
                team.setPrefix(text);
            } catch (Exception exception) {
            }
        if (team.hasEntry(other.getName()))
            return;
        removeFromTeams(player, other);
        team.addEntry(other.getName());
    }

    public static void addToTeam(Player player, Player other, ChatColor color) {
        addToTeam(player, other, color, false);
    }

    public static void addToTeam(Player player, Player other, ChatColor color, boolean magic) {
        Team team = player.getScoreboard().getTeam(getTeamName(color) + (magic ? "_magic" : ""));
        if (team == null)
            try {
                team = player.getScoreboard().registerNewTeam(getTeamName(color) + (magic ? "_magic" : ""));
                team.setPrefix(color.toString() + (magic ? ChatColor.MAGIC.toString() : ""));
            } catch (Exception exception) {
            }
        if (team.hasEntry(other.getName()))
            return;
        removeFromTeams(player, other);
        team.addEntry(other.getName());
    }

    public static void removeFromTeams(Player player, Player other) {
        if (player == null || other == null)
            return;
        for (Team team : player.getScoreboard().getTeams()) {
            if (team.hasEntry(other.getName()))
                team.removeEntry(other.getName());
        }
    }
}

