package net.lxsthw.redelite.hub.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import tk.slicecollections.maxteer.libraries.npclib.api.event.NPCRightClickEvent;
import tk.slicecollections.maxteer.libraries.npclib.api.npc.NPC;
import tk.slicecollections.maxteer.menus.MenuDeliveries;
import tk.slicecollections.maxteer.player.Profile;
import tk.slicecollections.maxteer.servers.ServerItem;

public class PlayerInteractListener implements Listener {

  @EventHandler
  public void onNPCRightClick(NPCRightClickEvent evt) {
    Player player = evt.getPlayer();
    Profile profile = Profile.getProfile(player.getName());

    if (profile != null) {
      NPC npc = evt.getNPC();
      if (npc.data().has("play-npc")) {
        ServerItem si = ServerItem.getServerItem(npc.data().get("play-npc"));
        if (si != null) {
          si.connect(profile);
        }
      } else  if (npc.data().has("delivery-npc")) {
        new MenuDeliveries(profile);
      }
    }
  }
}
