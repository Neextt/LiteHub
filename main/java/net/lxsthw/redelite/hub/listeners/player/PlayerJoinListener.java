package net.lxsthw.redelite.hub.listeners.player;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import tk.slicecollections.maxteer.Core;
import net.lxsthw.redelite.hub.Language;
import net.lxsthw.redelite.hub.Main;
import net.lxsthw.redelite.hub.lobby.Lobby;
import net.lxsthw.redelite.hub.tagger.TagUtils;
import tk.slicecollections.maxteer.nms.NMS;
import tk.slicecollections.maxteer.player.Profile;
import tk.slicecollections.maxteer.player.hotbar.Hotbar;
import tk.slicecollections.maxteer.player.role.Role;
import tk.slicecollections.maxteer.titles.TitleManager;
import tk.slicecollections.maxteer.utils.enums.EnumSound;

public class PlayerJoinListener implements Listener {

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent evt) {
    evt.setJoinMessage(null);

    Player player = evt.getPlayer();
    TagUtils.sendTeams(player);

    Profile profile = Profile.getProfile(player.getName());
    profile.setHotbar(Hotbar.getHotbarById("lobby"));
    profile.refresh();

    Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), () -> TitleManager.joinLobby(profile), 10);

    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
      TagUtils.setTag(evt.getPlayer());
      if (Role.getPlayerRole(player).isBroadcast()) {
        Profile.listProfiles().forEach(pf -> {
        });
      }
    }, 5);

    NMS.sendTitle(player, "", "", 0, 1, 0);
    if (Language.lobby$tab$enabled) {
      NMS.sendTabHeaderFooter(player, Language.lobby$tab$header, Language.lobby$tab$footer);
    }

    if (player.hasPermission("hades.cmd.lobby")) {
      if (!Lobby.WARNINGS.isEmpty()) {
        TextComponent component = new TextComponent("");
        for (BaseComponent components : TextComponent.fromLegacyText(
                " \n §6§lAVISO IMPORTANTE\n \n §7O sistema de servidores do mLobby foi alterado nessa nova versão e, aparentemente você utiliza a versão antiga!\n §7O novo padrão de 'servername' na lobbies.yml é 'IP:PORTA ; BungeeServerName' e você utiliza o antigo padrão 'BungeeServerName' nas seguintes entradas:")) {
          component.addExtra(components);
        }
        for (String warning : Lobby.WARNINGS) {
          for (BaseComponent components : TextComponent.fromLegacyText("\n§f" + warning)) {
            component.addExtra(components);
          }
        }
        for (BaseComponent components : TextComponent.fromLegacyText("\n ")) {
          component.addExtra(components);
        }

        player.spigot().sendMessage(component);
        EnumSound.ORB_PICKUP.play(player, 1.0F, 1.0F);
      }
    }
  }
}
