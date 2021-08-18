
package net.lxsthw.redelite.hub;

import net.lxsthw.redelite.hub.hook.LCoreHook;
import net.lxsthw.redelite.hub.listeners.Listeners;
import net.lxsthw.redelite.hub.lobby.Lobby;
import net.lxsthw.redelite.hub.lobby.PlayNPC;
import net.lxsthw.redelite.hub.scoreboard.Score;
import net.lxsthw.redelite.hub.scoreboard.ScoreBoardAPI;
import net.lxsthw.redelite.hub.tagger.TagUtils;
import org.bukkit.Bukkit;
import tk.slicecollections.maxteer.Core;
import tk.slicecollections.maxteer.libraries.MinecraftVersion;
import net.lxsthw.redelite.hub.cmd.CommandManutencao;
import net.lxsthw.redelite.hub.cmd.CommandTP;
import net.lxsthw.redelite.hub.cmd.Commands;
import net.lxsthw.redelite.hub.cmd.OnlineCommand;
import tk.slicecollections.maxteer.plugin.MPlugin;
import tk.slicecollections.maxteer.utils.BukkitUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Main extends MPlugin {

    private static Main instance;
    private static boolean validInit;
    public static String currentServerName;
    public static HashMap<String, Boolean> toggle;
    public static HashMap<String, Boolean> visibility;

    @Override
    public void start() {
        instance = this;
    }

    @Override
    public void load() {
        this.getServer().getConsoleSender().sendMessage("§e[LiteAPI] [LiteHub] connecting to api for hades: api.rede-lite.com...");
    }

    @Override
    public void enable() {
        saveDefaultConfig();
        currentServerName = getConfig().getString("lobby");
        if (getConfig().getString("spawn") != null) {
            Core.setLobby(BukkitUtils.deserializeLocation(getConfig().getString("spawn")));
            Bukkit.getPluginManager().registerEvents(new CommandManutencao(), this);
            Bukkit.getConsoleSender().sendMessage("§a[LiteHub] Inicializando...");
            Bukkit.getConsoleSender().sendMessage("§e[LiteAPI] Inicializando os hook globais...");
            getCommand("tp").setExecutor(new CommandTP());
            getCommand("list").setExecutor(new OnlineCommand());
            getCommand("manutencao").setExecutor(new CommandManutencao());
        }
        Language.setupLanguage();
        Score.register();
        LCoreHook.setupHook();
        Lobby.setupLobbies();
        PlayNPC.setupNPCs();
        Commands.setupCommands();
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Listeners.setupListeners();

        validInit = true;
        Bukkit.getConsoleSender().sendMessage("§6[LiteVersion] Detected version:" + MinecraftVersion.getCurrentVersion() + "");
        Bukkit.getConsoleSender().sendMessage("§a[LiteLobby] o plugin foi ativado com sucesso.");
    }

    @Override
    public void disable() {
        if (validInit) {
            PlayNPC.listNPCs().forEach(PlayNPC::destroy);
            Bukkit.getWorld("world").save();
            TagUtils.reset();

        }
        Bukkit.getConsoleSender().sendMessage("§c[LiteLobby] O plugin foi desativado com sucesso.");
    }
    public static String getHora() {
        Date date = new Date();
        DateFormat formato = new SimpleDateFormat("HH:mm:ss");
        return formato.format(date);
    }

    public static Main getInstance() {
        return instance;
    }
}
