package fox.ryukkun_.prefixChanger.command;

import fox.ryukkun_.prefixChanger.Config;
import fox.ryukkun_.prefixChanger.MCLogger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NameTag implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            MCLogger.sendMessage(commandSender, MCLogger.Level.Error, "プレイヤーから実行してください。");
            return true;
        }
        final Player player = (Player) commandSender;
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        if (Objects.equals(strings[0], "非表示")) {
            Team team = scoreboard.getEntryTeam(player.getName());
            if (team != null) team.removeEntry(player.getName());
            MCLogger.sendMessage(player, MCLogger.Level.Success, "ネームタグを変更しました！");
            return true;
        }

        int i = Config.getTeamNames().indexOf(strings[0]);
        if (i == -1) {
            MCLogger.sendMessage(player, MCLogger.Level.Error, "チームが見つかりませんでした(1)");
            return true;
        }

        String name = Config.getTeamIds().get(i);
        Team team = scoreboard.getTeam(name);
        if (team == null) {
            MCLogger.sendMessage(player, MCLogger.Level.Error, "チームが見つかりませんでした(2)");
            return true;
        }

        team.addEntry(player.getName());
        MCLogger.sendMessage(player, MCLogger.Level.Success, "ネームタグを変更しました！");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> tabList = new ArrayList<>();
        List<String> candidate = Config.getTeamNames();
        candidate.addFirst("非表示");
        if (strings.length == 1) {
            for (String s2 : candidate) {
                if (strings[0].isEmpty() || s2.startsWith(strings[0])) {
                    tabList.add(s2);
                }
            }
        }
        return tabList;
    }
}
