package club.frozed.lib;

import club.frozed.lib.chat.CC;
import club.frozed.lib.commands.BaseCommand;
import club.frozed.lib.commands.CommandFramework;
import club.frozed.lib.config.FileConfig;
import club.frozed.lib.handler.RegisterHandler;
import club.frozed.lib.item.ItemCreator;
import club.frozed.lib.utils.TPSUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * Created by Ryzeon
 * Project: FrozedHubDeluxe
 * Date: 10/11/2020 @ 13:30
 */

@Getter
@Setter
public class FrozedLib {

    public static FrozedLib INSTANCE;

    private Plugin plugin;

    private RegisterHandler registerHandler;

    private CommandFramework commandFramework;

    private String noPermissionMessage = CC.translate("&cError! &7You don't have permissions");
    private String onlyGameCommandMessage = CC.translate("&cError! &7This command is only performable in game.");
    private String disableCommandMessage = CC.translate("&b[Command-Register] &7<command> command was not registered because it was disabled in the configuration");

    /*
    Don't Touch
     */
    private boolean excludeCommands = false;
    private FileConfig excludeCommandFile;
    private String excludeCommandsPath;

    public FrozedLib(JavaPlugin plugin) {
        INSTANCE = this;
        this.plugin = plugin;
        this.registerHandler = new RegisterHandler(plugin);
        ItemCreator.registerGlow();
        this.commandFramework = new CommandFramework(plugin);
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new TPSUtils(), 1, 1);
    }

    public void setExcludeCommandConfig(FileConfig fileConfig, String path) {
        this.excludeCommandFile = fileConfig;
        this.excludeCommandsPath = path;
        excludeCommands = true;
    }

    public void registerCommands(BaseCommand... baseCommands) {
        for (BaseCommand baseCommand : baseCommands) {
            commandFramework.registerCommands(baseCommand, null);
        }
    }

    public List<String> getExcludeCommandsList() {
        return excludeCommandFile.getStringList(excludeCommandsPath);
    }

    public void loadListenersFromPackage(String packageName) {
        registerHandler.loadListenersFromPackage(packageName);
    }

    public void loadCommandsFromPackage(String packageName) {
        registerHandler.loadCommandsFromPackage(packageName);
    }
}
