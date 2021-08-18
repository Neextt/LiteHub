package net.lxsthw.redelite.hub.utils;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PermissionsAPI {

    public static String getPrefix(String name){
        return "§r" + PermissionsEx.getUser(name).getPrefix().replace("&", "�").replace("[", "").replace("]", "");
    }
}
