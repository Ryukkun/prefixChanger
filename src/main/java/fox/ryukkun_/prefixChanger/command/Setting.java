package fox.ryukkun_.prefixChanger.command;

import fox.ryukkun_.prefixChanger.Config;
import fox.ryukkun_.prefixChanger.MCLogger;
import fox.ryukkun_.prefixChanger.PrefixChanger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Setting extends SubCommand {
    public Setting() {
        addSubCommand("addTeam", new AddTeam());
        addSubCommand("removeTeam", new RemoveTeam());
        addSubCommand("changePrefix", new ChangePrefix());
        addSubCommand("reload", new Reload());
    }

    public static class AddTeam implements TabExecutor {
        @Override
        public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
            if (strings.length <= 2) {
                MCLogger.sendMessage(commandSender, MCLogger.Level.Error, "引数が足りません。");
                return true;
            }

            Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
            if (scoreboard.getTeam(strings[0]) != null) {
                MCLogger.sendMessage(commandSender, MCLogger.Level.Error, "すでにIDが同じのチームが存在しています。");
                return true;
            }

            // strings   0 : id,  1 : name,  2 : prefix
            strings[2] = String.join(" ", Arrays.copyOfRange(strings, 2, strings.length));
            PrefixChanger.makeTeam(strings[0], strings[2]);

            Config.addTeam(strings[0], strings[1], strings[2]);
            Config.save();
            MCLogger.sendMessage(commandSender, MCLogger.Level.Success, "チームの作成に成功しました。");
            return true;
        }

        @Override
        public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
            final List<String> tabList = new ArrayList<>();
            final List<String> candidate;

            switch (strings.length) {
                case 1:
                    if (strings[0].isEmpty()) tabList.add("<team id>");
                    candidate = Config.getTeamIds();
                    break;
                case 2:
                    if (strings[1].isEmpty()) tabList.add("<team name>");
                    candidate = Config.getTeamNames();
                    break;
                case 3:
                    if (strings[2].isEmpty()) tabList.add("<team prefix>");
                    candidate = Config.getTeamPrefixes();
                    break;
                default:
                    candidate = List.of();
            }

            String last = strings[strings.length-1];
            for (String s2 : candidate) {
                if (last.isEmpty() || s2.startsWith(last)) {
                    tabList.add(s2);
                }
            }
            return tabList;
        }
    }

    public static class RemoveTeam implements TabExecutor {
        @Override
        public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
            Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
            Team team = scoreboard.getTeam(strings[0]);
            if (team == null) {
                MCLogger.sendMessage(commandSender, MCLogger.Level.Error, "そんなチームないよ～ん。");
                return true;
            }

            Config.removeTeam(strings[0]);
            Config.save();
            team.unregister();
            MCLogger.sendMessage(commandSender, MCLogger.Level.Success, "チームの削除に成功しました。");
            return true;
        }

        @Override
        public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
            List<String> tabList = new ArrayList<>();
            String last = strings[strings.length-1];
            for (String s2 : Config.getTeamIds()) {
                if (last.isEmpty() || s2.startsWith(last)) {
                    tabList.add(s2);
                }
            }
            return tabList;
        }
    }

    public static class ChangePrefix implements TabExecutor {
        @Override
        public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
            int i = Config.getTeamIds().indexOf(strings[0]);
            if (i == -1) {
                MCLogger.sendMessage(commandSender, MCLogger.Level.Error, "そんなチームないよ～ん。");
                return true;
            }

            List<String> prefixes = Config.getTeamPrefixes();
            prefixes.set(i, String.join(" ", Arrays.copyOfRange(strings, 1, strings.length)));
            Config.setTeamPrefixes(prefixes);
            Config.save();
            PrefixChanger.reloadTeam();
            MCLogger.sendMessage(commandSender, MCLogger.Level.Success, "prefixを更新しました。");
            return true;
        }

        @Override
        public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
            List<String> tabList = new ArrayList<>();
            List<String> candidate;
            switch (strings.length) {
                case 1:
                    candidate = Config.getTeamIds();
                    break;
                case 2:
                    int i = Config.getTeamIds().indexOf(strings[0]);
                    candidate = (i == -1) ? Config.getTeamPrefixes() : List.of( Config.getTeamPrefixes().get(i));
                    break;
                default:
                    candidate = List.of();
                    break;
            };

            String last = strings[strings.length-1];
            for (String s2 : candidate) {
                if(last.isEmpty() || s2.startsWith(last)) {
                    tabList.add(s2);
                }
            }
            return tabList;
        }
    }

    public static class Reload implements TabExecutor {
        @Override
        public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
            PrefixChanger.reloadTeam();
            MCLogger.sendMessage(commandSender, MCLogger.Level.Success, "チームをリロードしました。");
            return true;
        }

        @Override
        public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
            return List.of();
        }
    }
}
