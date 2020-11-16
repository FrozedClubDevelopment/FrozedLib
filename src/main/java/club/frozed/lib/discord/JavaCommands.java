package club.frozed.lib.discord;

import lombok.Getter;
import org.bukkit.Bukkit;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryzeon
 * Project: StaffPlaytime
 * Date: 22/09/2020 @ 14:38
 */

@Getter
public class JavaCommands {
    //Kinda WIP, didn't make this, just have an auto restart script if you're not on windows.
    public void restart() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                List<String> args = ManagementFactory.getRuntimeMXBean().getInputArguments();
                List<String> command = new ArrayList<String>();
                command.add(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java.exe");
                for(int i = 0; i < args.size(); i++) {
                    command.add(args.get(i));
                }
                command.add("-jar");
                command.add(new File(Bukkit.class.getProtectionDomain().getCodeSource().getLocation().getFile()).getAbsolutePath());
                try {
                    new ProcessBuilder(command).start();
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Bukkit.shutdown();
    }
}
