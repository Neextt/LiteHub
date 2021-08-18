package net.lxsthw.redelite.hub.listeners;

import net.lxsthw.redelite.hub.listeners.entity.EntityListener;
import net.lxsthw.redelite.hub.listeners.server.ServerListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import net.lxsthw.redelite.hub.Main;
import net.lxsthw.redelite.hub.listeners.player.AsyncPlayerChatListener;
import net.lxsthw.redelite.hub.listeners.player.InventoryClickListener;
import net.lxsthw.redelite.hub.listeners.player.PlayerDeathListener;
import net.lxsthw.redelite.hub.listeners.player.PlayerInteractListener;
import net.lxsthw.redelite.hub.listeners.player.PlayerJoinListener;
import net.lxsthw.redelite.hub.listeners.player.PlayerQuitListener;
import net.lxsthw.redelite.hub.listeners.player.PlayerRestListener;

public class Listeners {

  public static void setupListeners() {
    try {
      PluginManager pm = Bukkit.getPluginManager();

      pm.getClass().getDeclaredMethod("registerEvents", Listener.class, Plugin.class).invoke(pm, new EntityListener(), Main.getInstance());

      pm.getClass().getDeclaredMethod("registerEvents", Listener.class, Plugin.class).invoke(pm, new AsyncPlayerChatListener(), Main.getInstance());
      pm.getClass().getDeclaredMethod("registerEvents", Listener.class, Plugin.class).invoke(pm, new InventoryClickListener(), Main.getInstance());
      pm.getClass().getDeclaredMethod("registerEvents", Listener.class, Plugin.class).invoke(pm, new PlayerDeathListener(), Main.getInstance());
      pm.getClass().getDeclaredMethod("registerEvents", Listener.class, Plugin.class).invoke(pm, new PlayerInteractListener(), Main.getInstance());
      pm.getClass().getDeclaredMethod("registerEvents", Listener.class, Plugin.class).invoke(pm, new PlayerJoinListener(), Main.getInstance());
      pm.getClass().getDeclaredMethod("registerEvents", Listener.class, Plugin.class).invoke(pm, new PlayerQuitListener(), Main.getInstance());
      pm.getClass().getDeclaredMethod("registerEvents", Listener.class, Plugin.class).invoke(pm, new PlayerRestListener(), Main.getInstance());

      pm.getClass().getDeclaredMethod("registerEvents", Listener.class, Plugin.class).invoke(pm, new ServerListener(), Main.getInstance());
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
