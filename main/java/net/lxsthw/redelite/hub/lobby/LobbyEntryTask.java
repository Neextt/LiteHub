package net.lxsthw.redelite.hub.lobby;

import static net.lxsthw.redelite.hub.lobby.Lobby.QUERY;

import org.bukkit.scheduler.BukkitRunnable;

public class LobbyEntryTask extends BukkitRunnable {

  @Override
  public void run() {
    QUERY.forEach(Lobby::fetch);
  }
}
