package club.frozed.lib.commands;

import java.util.List;

/**
 * Created by Ryzeon
 * Project: FrozedHubDeluxe
 * Date: 10/11/2020 @ 13:30
 */

public abstract class BaseTabCompleter {

    public abstract List<String> gamemodeCompleter(CommandArgs args);
}
