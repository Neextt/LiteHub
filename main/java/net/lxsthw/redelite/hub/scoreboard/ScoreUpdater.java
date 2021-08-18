package net.lxsthw.redelite.hub.scoreboard;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.lxsthw.redelite.hub.cmd.CommandManutencao;
import com.whyze.lobby.utils.PermissionsAPI;
import com.whyze.lobby.utils.ServerInfo;
import com.whyze.lobby.utils.ServerInfo.Servers;

public class ScoreUpdater extends BukkitRunnable {

    private ServerInfo network = new ServerInfo(Servers.NETWORK);
    private ServerInfo thyre = new ServerInfo(Servers.CRYSTAL);

    @Override
    public void run() {
        network = new ServerInfo(Servers.NETWORK);
        thyre = new ServerInfo(Servers.CRYSTAL);
        Iterator<Player> iter = ScoreRegister.boards.keySet().iterator();
        try {
            while (iter.hasNext()) {
                Player p = iter.next();
                updateScoreBoard(p);
            }
        } catch (ConcurrentModificationException e) {}
    }

    public void updateScoreBoard(Player p) {
        if (Bukkit.getOnlinePlayers().size() == 0) {
            return;
        }
        ScoreBoardAPI sb = ScoreRegister.boards.get(p);
        if (sb == null) return;
        String on = (thyre.isOnline() ? "§a" + thyre.getPlayerOnline() : "§cOff.");
        if (CommandManutencao.checarOn(25600)) {
            on = "§cManu.";
        }
        sb.update("§b" + network.getPlayerOnline(), 6);
        sb.update("ads: " + on, 4);
        sb.update(": " + PermissionsAPI.getPrefix(p.getName()), 2);
    }
}
