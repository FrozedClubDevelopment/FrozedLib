package club.frozed.lib.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;

/**
 * Created by Ryzeon
 * Project: FrozedHubDeluxe
 * Date: 10/11/2020 @ 13:30
 */


public class CancellableEvent extends BaseEvent implements Cancellable {

    @Getter
    @Setter private boolean cancelled;

}
