package net.lxsthw.redelite.hub.hook;

import com.comphenix.protocol.ProtocolLibrary;

import java.util.logging.Level;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scheduler.BukkitRunnable;
import net.lxsthw.redelite.hub.Main;
import net.lxsthw.redelite.hub.hook.hotbar.LHotbarActionType;
import net.lxsthw.redelite.hub.hook.protocollib.HologramAdapter;
import tk.slicecollections.maxteer.player.Profile;
import tk.slicecollections.maxteer.player.hotbar.Hotbar;
import tk.slicecollections.maxteer.player.hotbar.HotbarAction;
import tk.slicecollections.maxteer.player.hotbar.HotbarActionType;
import tk.slicecollections.maxteer.player.hotbar.HotbarButton;
import tk.slicecollections.maxteer.player.scoreboard.MScoreboard;
import tk.slicecollections.maxteer.player.scoreboard.scroller.ScoreboardScroller;
import tk.slicecollections.maxteer.plugin.config.MConfig;

public class LCoreHook {

  public static void setupHook() {
    setupHotbars();

        new BukkitRunnable() {
          @Override
          public void run() {
            Profile.listProfiles().forEach(profile -> {
              if (!profile.playingGame() && profile.getScoreboard() != null) {
                profile.update();
              }
            });
          }
        }.runTaskTimerAsynchronously(Main.getInstance(), 0, 20);

        ProtocolLibrary.getProtocolManager().addPacketListener(new HologramAdapter());
      }


      private static void setupHotbars() {
        HotbarActionType.addActionType("lobby", new LHotbarActionType());

        MConfig config = Main.getInstance().getConfig("hotbar");
        for (String id : new String[]{"lobby"}) {
          Hotbar hotbar = new Hotbar(id);

          ConfigurationSection hb = config.getSection(id);
          for (String button : hb.getKeys(false)) {
            try {
              hotbar.getButtons().add(new HotbarButton(hb.getInt(button + ".slot"), new HotbarAction(hb.getString(button + ".execute")), hb.getString(button + ".icon")));
            } catch (Exception ex) {
              Main.getInstance().getLogger().log(Level.WARNING, "Falha ao carregar o botao \"" + button + "\" da hotbar \"" + id + "\": ", ex);
            }
          }

          Hotbar.addHotbar(hotbar);
        }
      }
    }
