package club.frozed.lib.utils;

import club.frozed.lib.chat.CC;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Ryzeon
 * Project: FrozedHubDeluxe
 * Date: 10/11/2020 @ 13:30
 */

public class PlayerUtils {

    public static Location teleportToTop(Location loc) {
        return new Location(loc.getWorld(), loc.getX(), loc.getWorld().getHighestBlockYAt(loc.getBlockX(), loc.getBlockZ()), loc.getZ(), loc.getYaw(), loc.getPitch());
    }

    public static boolean hasAvailableSlot(Player player) {
        Inventory inv = player.getInventory();
        for (ItemStack item : inv.getContents()) {
            if (item == null) {
                return true;
            }
        }

        return false;
    }

    public static String getTps() {
        return TPSUtils.getTPS();
    }

    public static String getUptime() {
        long serverTime = ManagementFactory.getRuntimeMXBean().getStartTime();
        String text;
        text = DurationFormatUtils.formatDurationWords(System.currentTimeMillis() - serverTime, true, true);

        return text;
    }

    public static long getMaxMemory() {
        long text;
        text = Runtime.getRuntime().maxMemory() / 1024 / 1024;

        return text;
    }

    public static long getAllMemory() {
        long text;
        text = Runtime.getRuntime().totalMemory() / 1024 / 1024;

        return text;
    }

    public static long getFreeMemory() {
        long text;
        text = Runtime.getRuntime().freeMemory() / 1024 / 1024;

        return text;
    }

    public static void sendPlayerSound(Player p, String sound) {
        if (!(sound.equalsIgnoreCase("none") || sound == null)) {
            p.playSound(p.getLocation(), Sound.valueOf(sound), 2F, 2F);
        }
    }

    public static int getPing(Player who) {
        try {
            String bukkitversion = Bukkit.getServer().getClass().getPackage().getName().substring(23);
            Class<?> craftPlayer = Class.forName("org.bukkit.craftbukkit." + bukkitversion + ".entity.CraftPlayer");
            Object handle = craftPlayer.getMethod("getHandle").invoke(who);
            return (Integer) handle.getClass().getDeclaredField("ping").get(handle);
        } catch (Exception ignored) {
            return -1;
        }
    }

    public static boolean checkPlayerVote(UUID uuid, String server) {
        String pageRequest = "https://api.namemc.com/server/" + server + "/likes?profile=" + uuid.toString();
        try {
            URL url = new URL(pageRequest);
            ArrayList<Object> lines = new ArrayList();
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null)
                lines.add(line);
            if (lines.contains("true")) {
                return true;
            } else {
                return false;
            }
        } catch (IOException exception) {
            Bukkit.getConsoleSender().sendMessage("§cAn error occurred while checking vote on name-mc");
        }

        return false;
    }

    public static String getPingWithColor(int ping) {
        if (ping <= 40) {
            return CC.GREEN + ping;
        } else if (ping <= 70) {
            return CC.YELLOW + ping;
        } else if (ping <= 100) {
            return CC.GOLD + ping;
        } else {
            return CC.RED + ping;
        }
    }

    public static String getColorHealth(double health) {
        if (health > 15) {
            return CC.GREEN + convertHealth(health);
        } else if (health > 10) {
            return CC.GOLD + convertHealth(health);
        } else if (health > 5) {
            return CC.YELLOW + convertHealth(health);
        } else {
            return CC.RED + convertHealth(health);
        }
    }

    public static double convertHealth(double health) {
        double dividedHealth = health / 2;

        if (dividedHealth % 1 == 0) {
            return dividedHealth;
        }

        if (dividedHealth % .5 == 0) {
            return dividedHealth;
        }

        if (dividedHealth - ((int) dividedHealth) > .5) {
            return ((int) dividedHealth) + 1;
        } else if (dividedHealth - ((int) dividedHealth) > .25) {
            return ((int) dividedHealth) + .5;
        } else {
            return ((int) dividedHealth);
        }
    }

    public static String getHeartIcon() {
        return StringEscapeUtils.unescapeJava("\u2764");
    }

}
