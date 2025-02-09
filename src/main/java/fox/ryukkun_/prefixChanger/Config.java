package fox.ryukkun_.prefixChanger;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Config {
    public static List<String> getTeamIds() {
        return  PrefixChanger.plugin.getConfig().getStringList("team.ids");
    }
    public static List<String> getTeamNames() {
        return  PrefixChanger.plugin.getConfig().getStringList("team.names");
    }
    public static List<String> getTeamPrefixes() {
        return  PrefixChanger.plugin.getConfig().getStringList("team.prefixes");
    }

    public static void setTeamIds(List<String> ids) {
        PrefixChanger.plugin.getConfig().set("team.ids", ids);
    }
    public static void setTeamNames(List<String> names) {
        PrefixChanger.plugin.getConfig().set("team.names", names);
    }
    public static void setTeamPrefixes(List<String> prefixes) {
        PrefixChanger.plugin.getConfig().set("team.prefixes", prefixes);
    }

    public static void save() {
        PrefixChanger.plugin.saveConfig();
    }

    public static void addTeam(String id, String name, String prefix) {
        FileConfiguration config = PrefixChanger.plugin.getConfig();
        List<String> list;
        list = config.getStringList("team.ids");
        list.add(id);
        config.set("team.ids", list);
        list = config.getStringList("team.names");
        list.add(name);
        config.set("team.names", list);
        list = config.getStringList("team.prefixes");
        list.add(prefix);
        config.set("team.prefixes", list);
    }

    public static void removeTeam(String id) {
        FileConfiguration config = PrefixChanger.plugin.getConfig();
        List<String> list;
        list = config.getStringList("team.ids");
        int i = list.indexOf(id);

        list.remove(i);
        config.set("team.ids", list);
        list = config.getStringList("team.names");
        list.remove(i);
        config.set("team.names", list);
        list = config.getStringList("team.prefixes");
        list.remove(i);
        config.set("team.prefixes", list);
    }
}
