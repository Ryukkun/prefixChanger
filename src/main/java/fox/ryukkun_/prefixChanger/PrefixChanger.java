package fox.ryukkun_.prefixChanger;

import fox.ryukkun_.prefixChanger.command.NameTag;
import fox.ryukkun_.prefixChanger.command.Setting;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.List;

public final class PrefixChanger extends JavaPlugin implements CommandExecutor {
    public static PrefixChanger plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        plugin = this;

        getCommand("nametag").setExecutor(new NameTag());
        getCommand("prefixchanger").setExecutor(new Setting());

        reloadTeam();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void reloadTeam() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        List<String> ids, prefixes;
        ids = Config.getTeamIds();
        prefixes = Config.getTeamPrefixes();
        for (int i=0; i<ids.size(); i++) {
            if (scoreboard.getTeam(ids.get(i)) == null) {
                makeTeam(ids.get(i), prefixes.get(i));
            } else {
                changeTeamPrefix(ids.get(i), prefixes.get(i));
            }
        }
    }

    public static void makeTeam(String id, String prefix) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.registerNewTeam(id);
        team.setPrefix(ChatColor.translateAlternateColorCodes('&', prefix+" "));
    }

    public static void changeTeamPrefix(String id, String prefix) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getTeam(id);
        team.setPrefix(ChatColor.translateAlternateColorCodes('&', prefix+" "));
    }
}
