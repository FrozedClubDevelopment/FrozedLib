package club.frozed.lib.task;

import club.frozed.lib.FrozedLib;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Ryzeon
 * Project: FrozedHubDeluxe
 * Date: 10/11/2020 @ 13:30
 */

public class TaskUtil {

    public static void run(Runnable runnable) {
        FrozedLib.INSTANCE.getPlugin().getServer().getScheduler().runTask(FrozedLib.INSTANCE.getPlugin(), runnable);
    }

    public static void runTimer(Runnable runnable, long delay, long timer) {
        FrozedLib.INSTANCE.getPlugin().getServer().getScheduler().runTaskTimer(FrozedLib.INSTANCE.getPlugin(), runnable, delay, timer);
    }

    public static void runTimer(BukkitRunnable runnable, long delay, long timer) {
        runnable.runTaskTimer(FrozedLib.INSTANCE.getPlugin(), delay, timer);
    }

    public static void runTimerAsync(BukkitRunnable runnable, long delay, long timer) {
        runnable.runTaskTimerAsynchronously(FrozedLib.INSTANCE.getPlugin(), delay, timer);
    }

    public static void runLater(Runnable runnable, long delay) {
        FrozedLib.INSTANCE.getPlugin().getServer().getScheduler().runTaskLater(FrozedLib.INSTANCE.getPlugin(), runnable, delay);
    }

    public static void runLaterAsync(Runnable runnable, long delay) {
        FrozedLib.INSTANCE.getPlugin().getServer().getScheduler().runTaskLaterAsynchronously(FrozedLib.INSTANCE.getPlugin(), runnable, delay);
    }

    public static void runTaskTimerAsynchronously(Runnable runnable, int delay) {
        FrozedLib.INSTANCE.getPlugin().getServer().getScheduler().runTaskTimerAsynchronously(FrozedLib.INSTANCE.getPlugin(), runnable, 20 * delay, 20 * delay);
    }

    public static void runAsync(Runnable runnable) {
        FrozedLib.INSTANCE.getPlugin().getServer().getScheduler().runTaskAsynchronously(FrozedLib.INSTANCE.getPlugin(), runnable);
    }
}