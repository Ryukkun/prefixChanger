package fox.ryukkun_.prefixChanger;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class MCLogger {

    private static final String ErrorPrefix = ChatColor.DARK_RED + "P" + ChatColor.RED + "refix" +
            ChatColor.DARK_RED + "C" + ChatColor.RED + "hanger " +
            ChatColor.DARK_RED + ">> " + ChatColor.RED;

    private static final String WarningPrefix = ChatColor.GOLD + "P" + ChatColor.YELLOW + "refix" +
            ChatColor.GOLD + "C" + ChatColor.YELLOW + "hanger " +
            ChatColor.GOLD + ">> " + ChatColor.YELLOW;

    private static final String SuccessPrefix = ChatColor.DARK_GREEN + "P" + ChatColor.GREEN + "refix" +
            ChatColor.DARK_GREEN + "C" + ChatColor.GREEN + "hanger " +
            ChatColor.DARK_GREEN + ">> " + ChatColor.GREEN;

    private static final String WhitePrefix = "PrefixChanger >> ";


    public static void sendMessage(Player player, Level level, String text) {
        player.sendMessage( getMessage( text, level));
    }

    public static void sendMessage(CommandSender sender, Level level ,String text) {
        sender.sendMessage( getMessage( text, level));
    }

    public static void syncSendMessage(CommandSender sender, Level level ,String text) {
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(sender, level, text);
            }
        }.runTask( PrefixChanger.plugin);
    }

    public static void syncSendMessage(Player player, Level level ,String text) {
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(player, level, text);
            }
        }.runTask( PrefixChanger.plugin);
    }

    private static String getMessage(String text, Level level) {
        if (level.equals( Level.Error)) {
            return ErrorPrefix+text;
        } else if (level.equals( Level.Warning)) {
            return WarningPrefix+text;
        } else if (level.equals( Level.Success)) {
            return SuccessPrefix+text;
        } else {
            return WhitePrefix+text;
        }
    }

    public enum Level {
        Error, Warning, Success, White
    }
}