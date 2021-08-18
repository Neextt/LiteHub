package net.lxsthw.redelite.hub.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import tk.slicecollections.maxteer.game.Game;
import net.lxsthw.redelite.hub.cmd.hl.BuildCommand;
import tk.slicecollections.maxteer.player.Profile;

public class InventoryClickListener implements Listener {

  @EventHandler
  public void onInventoryClick(InventoryClickEvent evt) {
    if (evt.getWhoClicked() instanceof Player) {
      Player player = (Player) evt.getWhoClicked();
      Profile profile = Profile.getProfile(player.getName());

      if (profile != null) {
        Game<?> game = profile.getGame();
        if (game == null) {
          evt.setCancelled(!BuildCommand.isBuilder(player));
        }
      }
    }
  }
}
