package net.lxsthw.redelite.hub.scoreboard;

import java.util.UUID;
import java.util.WeakHashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class ScoreRegister implements Listener{

    public static WeakHashMap<Player, ScoreBoardAPI> boards;

    static{
        boards = new WeakHashMap<>();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        e.setQuitMessage(null);
        boards.remove(e.getPlayer());
    }

    public static String getUUID(){
        return UUID.randomUUID().toString().substring(0, 6) + UUID.randomUUID().toString().substring(0, 6) + (int)Math.round(Math.random() * 100.0D);
    }

    public static void createScoreBoard(Player p){
        String UUIDRandom = getUUID();
        ScoreBoardAPI sb = new ScoreBoardAPI("§6§lREDE LITE", UUIDRandom);
        sb.blankLine(7);
        sb.add("  §fOnline: ", 6);
        sb.blankLine(5);
        sb.add("   §a• §fF. He", 4);
        sb.blankLine(3);
        sb.add("  §fGrupo", 2);
        sb.blankLine(1);
        sb.add("§e loja.rede-lite.com", 0);
        sb.build();
        ScoreRegister.boards.remove(p);
        sb.send(p);
        ScoreRegister.boards.put(p, sb);
    }
}

