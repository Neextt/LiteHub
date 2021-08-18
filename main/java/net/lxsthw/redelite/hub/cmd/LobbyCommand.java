package net.lxsthw.redelite.hub.cmd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.lxsthw.redelite.hub.cmd.hl.BuildCommand;
import net.lxsthw.redelite.hub.cmd.hl.NPCPlayCommand;
import net.lxsthw.redelite.hub.cmd.hl.SetSpawnCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.slicecollections.maxteer.lobby.cmd.hl.*;

public class LobbyCommand extends Commands {

  private final List<SubCommand> commands = new ArrayList<>();

  public LobbyCommand() {
    super("hl", "hadeslobby");

    this.commands.add(new SetSpawnCommand());
    this.commands.add(new BuildCommand());
    this.commands.add(new NPCPlayCommand());
  }

  @Override
  public void perform(CommandSender sender, String label, String[] args) {
    if (!sender.hasPermission("hadeslobby.cmd.lobby")) {
      sender.sendMessage("§4HadesLobby §8(1.2.1) §8- §fCriado por §eSleckyz§f.");
      return;
    }

    if (args.length == 0) {
      this.sendHelp(sender, 1);
      return;
    }
    
    try {
      this.sendHelp(sender, Integer.parseInt(args[0]));
    } catch (Exception ex) {
      SubCommand subCommand = this.commands.stream().filter(sc -> sc.getName().equalsIgnoreCase(args[0])).findFirst().orElse(null);
      if (subCommand == null) {
        this.sendHelp(sender, 1);
        return;
      }

      List<String> list = new ArrayList<>();
      list.addAll(Arrays.asList(args));
      list.remove(0);
      if (subCommand.onlyForPlayer()) {
        if (!(sender instanceof Player)) {
          sender.sendMessage("§cEsse comando pode ser utilizado apenas pelos jogadores.");
          return;
        }

        subCommand.perform((Player) sender, list.toArray(new String[list.size()]));
      } else {
        subCommand.perform(sender, list.toArray(new String[list.size()]));
      }
    }
  }

  private void sendHelp(CommandSender sender, int page) {
    List<SubCommand> commands = this.commands.stream().filter(subcommand -> sender instanceof Player || !subcommand.onlyForPlayer()).collect(Collectors.toList());
    Map<Integer, StringBuilder> pages = new HashMap<>();

    int pagesCount = (commands.size() + 6) / 7;
    for (int index = 0; index < commands.size(); index++) {
      int currentPage = (index + 7) / 7;
      if (!pages.containsKey(currentPage)) {
        pages.put(currentPage, new StringBuilder(" \n§eAjuda - " + currentPage + "/" + pagesCount + "\n \n"));
      }

      pages.get(currentPage).append("§6/hl " + commands.get(index).getUsage() + " §f- §7" + commands.get(index).getDescription() + "\n");
    }

    StringBuilder sb = pages.get(page);
    if (sb == null) {
      sender.sendMessage("§cPágina não encontrada.");
      return;
    }

    sb.append(" ");
    sender.sendMessage(sb.toString());
  }
}
