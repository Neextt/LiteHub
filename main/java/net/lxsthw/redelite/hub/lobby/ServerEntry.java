package net.lxsthw.redelite.hub.lobby;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import net.lxsthw.redelite.hub.Main;
import tk.slicecollections.maxteer.plugin.config.MConfig;
import tk.slicecollections.maxteer.plugin.logger.MLogger;
import tk.slicecollections.maxteer.servers.ServerItem;
import tk.slicecollections.maxteer.utils.BukkitUtils;

import java.util.ArrayList;
import java.util.List;

public class ServerEntry {

  private String key;
  private List<String> holograms;
  private ItemStack hand;
  private String skinValue;
  private String skinSignature;

  public ServerEntry(String key, List<String> holograms, ItemStack hand, String skinValue, String skinSignature) {
    this.key = key;
    this.holograms = holograms;
    this.hand = hand;
    this.skinValue = skinValue;
    this.skinSignature = skinSignature;
  }

  public String getKey() {
    return this.key;
  }

  public ServerItem getServerItem() {
    return ServerItem.getServerItem(this.key);
  }

  public List<String> listHologramLines() {
    return this.holograms;
  }

  public ItemStack getHand() {
    return this.hand;
  }

  public String getSkinValue() {
    return this.skinValue;
  }

  public String getSkinSignature() {
    return this.skinSignature;
  }

  public static final MLogger LOGGER = ((MLogger) Main.getInstance().getLogger()).getModule("ENTRIES");
  private static final List<ServerEntry> ENTRIES = new ArrayList<>();

  public static void setupEntries() {
    MConfig config = Main.getInstance().getConfig("entries");
    for (String key : config.getKeys(false)) {
      if (!config.contains(key + ".hand")) {
        config.set(key + ".hand", "AIR : 1");
      }
      ServerEntry se = new ServerEntry(config.getString(key + ".key"), config.getStringList(key + ".holograms"), BukkitUtils.deserializeItemStack(config.getString(key + ".hand")),
        config.getString(key + ".skin.value"), config.getString(key + ".skin.signature"));
      if (se.getServerItem() == null) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> LOGGER.warning("A entry " + key + " (entries.yml) possui uma key invalida."));
        continue;
      }

      ENTRIES.add(se);
    }
  }

  public static ServerEntry getByKey(String key) {
    return ENTRIES.stream().filter(entry -> entry.getKey().equals(key)).findFirst().orElse(null);
  }
}
