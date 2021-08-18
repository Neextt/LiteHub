package net.lxsthw.redelite.hub.cmd.hl;

import net.lxsthw.redelite.hub.lobby.PlayNPC;
import net.lxsthw.redelite.hub.lobby.ServerEntry;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.lxsthw.redelite.hub.cmd.SubCommand;

public class NPCPlayCommand extends SubCommand {

  public NPCPlayCommand() {
    super("npc", "npc", "Adicione/remova NPC de Jogar.", true);
  }

  @Override
  public void perform(CommandSender sender, String[] args) {}

  @Override
  public void perform(Player player, String[] args) {
    if (args.length == 0) {
      player.sendMessage(" \n§eAjuda\n \n§a/hl npc adicionar [id] [entry] §f- §7Adicionar O NPC, de algum servidor.\n§a/hl npcjogar remover [id] §f- §7Remover NPC.\n ");
      return;
    }

    String action = args[0];
    if (action.equalsIgnoreCase("add")) {
      if (args.length <= 2) {
        player.sendMessage("§cUtilize /ml npc add [id] [entry]");
        return;
      }

      String id = args[1];
      if (PlayNPC.getById(id) != null) {
        player.sendMessage("§cJá existe um NPC de Jogar utilizando \"" + id + "\" como ID.");
        return;
      }

      ServerEntry entry = ServerEntry.getByKey(args[2]);
      if (entry == null) {
        player.sendMessage("§cUtilize /ml npc add [id] [entry]");
        return;
      }

      Location location = player.getLocation().getBlock().getLocation().add(0.5, 0, 0.5);
      location.setYaw(player.getLocation().getYaw());
      location.setPitch(player.getLocation().getPitch());
      PlayNPC.add(id, location, entry);
      player.sendMessage("§aNPC de Servidores adicionado com sucesso.");
    } else if (action.equalsIgnoreCase("remove")) {
      if (args.length <= 1) {
        player.sendMessage("§cUtilize /ml npc remove [id]");
        return;
      }

      String id = args[1];
      PlayNPC npc = PlayNPC.getById(id);
      if (npc == null) {
        player.sendMessage("§cNão existe um NPC de Jogar utilizando \"" + id + "\" como ID.");
        return;
      }

      PlayNPC.remove(npc);
      player.sendMessage("§cNPC de Jogar removido com sucesso.");
    } else {
      player.sendMessage(" \n§eAjuda - NPC\n \n§3/hl npc add [id] [entry] §f- §7Adicionar NPC de algum servidor.\n§3/hl npc remove [id] §f- §7Remover NPC.\n ");
    }
  }
}
