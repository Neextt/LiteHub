package net.lxsthw.redelite.hub.listeners.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import net.lxsthw.redelite.hub.cmd.hl.BuildCommand;
import net.lxsthw.redelite.hub.tagger.TagUtils;

public class PlayerQuitListener implements Listener {

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent evt) {
    evt.setQuitMessage(null);
    BuildCommand.remove(evt.getPlayer());
    TagUtils.reset(evt.getPlayer().getName());
  }
}
