package net.lxsthw.redelite.hub.scoreboard;

import org.bukkit.Bukkit;
import net.lxsthw.redelite.hub.Main;


public class Score {

    public static void register() {
        registerListeners();
        loadScoreTask();
    }

    public static void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new ScoreRegister(), Main.getInstance());
    }

    public static void loadScoreTask() {
        Bukkit.getOnlinePlayers().forEach(p -> {
            ScoreRegister.createScoreBoard(p);
        });
    }
}