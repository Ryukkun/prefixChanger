package fox.ryukkun_.prefixChanger.command;

import fox.ryukkun_.prefixChanger.MCLogger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SubCommand implements TabExecutor {
    private final HashMap<String, TabExecutor> subCommands = new HashMap<>();


    public void addSubCommand(String name, TabExecutor executor) {
        subCommands.put(name, executor);
    }

    public void removeSubCommand(String name) {
        subCommands.remove(name);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (1 <= strings.length){
            if (subCommands.containsKey(strings[0])){
                try {
                    return subCommands.get(strings[0]).onCommand(commandSender, command, s, Arrays.copyOfRange(strings, 1, strings.length));

                } catch (Exception e) {
                    MCLogger.sendMessage(commandSender, MCLogger.Level.Error, e.getMessage());
                    e.printStackTrace();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (1 == strings.length){
            final List<String> tabList = new ArrayList<>();
            for (String name : subCommands.keySet()) {

                if (strings[0].isEmpty() || name.startsWith(strings[0])) {
                    tabList.add(name);
                }
            }
            return tabList;

        }else if (2 <= strings.length){
            if (subCommands.containsKey(strings[0])){
                return subCommands.get(strings[0]).onTabComplete(commandSender, command, s, Arrays.copyOfRange(strings, 1, strings.length));
            }
        }
        return null;
    }
}
