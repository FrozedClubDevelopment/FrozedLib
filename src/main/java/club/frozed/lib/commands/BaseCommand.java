package club.frozed.lib.commands;

import club.frozed.lib.FrozedLib;
import org.bukkit.plugin.Plugin;

/**
 * Created by Ryzeon
 * Project: FrozedHubDeluxe
 * Date: 10/11/2020 @ 13:30
 */

public abstract class BaseCommand {

    public BaseCommand(){
        FrozedLib.INSTANCE.getCommandFramework().registerCommands(this, null);
    }

    public abstract void onCommand(CommandArgs command);
}
