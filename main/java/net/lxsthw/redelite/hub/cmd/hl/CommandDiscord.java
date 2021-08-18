package net.lxsthw.redelite.hub.cmd.hl;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandDiscord implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
        if(cmd.getName().equalsIgnoreCase("discord")) {
            if(sender instanceof Player) {
                Player p = (Player)sender;
                p.sendMessage("");
                p.sendMessage("§7 • §dFique por dentro de novidades, avisos, e muito mais em nosso Discord.");
                p.sendMessage("§7 • §dNão fique fora dessa, acesse já em: §fhttps://discord.gg/redehades");
                p.sendMessage("");
                p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);
            }
        }
        return false;
    }

}
