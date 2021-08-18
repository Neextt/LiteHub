package net.lxsthw.redelite.hub.cmd;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OnlineCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
        if (cmd.getName().equalsIgnoreCase("list")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                p.sendMessage("");
                p.sendMessage("§7 • §fAtualmente estamos com " + Bukkit.getOnlinePlayers().size() + "§fjogadores online.");
                p.sendMessage("");
                p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);
            }
        }
        return false;
    }
}

