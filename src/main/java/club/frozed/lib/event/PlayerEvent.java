package club.frozed.lib.event;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Ryzeon
 * Project: FrozedHubDeluxe
 * Date: 10/11/2020 @ 13:30
 */


@Getter
public class PlayerEvent extends BaseEvent {

    private Player player;

    public PlayerEvent(Player player) {
        this.player = player;
    }

    public UUID getUniqueId() {
        return player.getUniqueId();
    }
}

