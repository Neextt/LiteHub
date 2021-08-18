package net.lxsthw.redelite.hub.cmd;

import net.lxsthw.redelite.hub.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.scheduler.BukkitRunnable;


public class CommandManutencao implements CommandExecutor,Listener {

    private boolean maintence_mode = false;
    @EventHandler
    public void joinWithMaintenceMode(PlayerLoginEvent e){
        Player p = e.getPlayer();
        if(maintence_mode){
            if(!p.hasPermission("hades.manutencao.bypass")){
                e.disallow(Result.KICK_OTHER, "§C§lREDE HADES\n§R\n§r§cDesculpe, O servidor entrou em modo de manutenção.\n§R§cVoltaremos em breve.\n§R\n§R§cPara mais informações siga nosso Twitter §b@ServidoresHades");
            }
        }
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
        if(cmd.getName().equalsIgnoreCase("manutencao")){
            if(sender instanceof Player){
                Player p = (Player)sender;
                if(p.hasPermission("hades.manutencao.admin")){
                    if(args.length < 1){
                        p.sendMessage("§cUtilize: /manutencao <on/off>");
                        return true;
                    }
                    if(args.length > 1){
                        p.sendMessage("§cUtilize: /manutencao <on/off>");
                        return true;
                    }
                    if(args.length == 1){
                        String digitado = args[0];
                        if(digitado.equalsIgnoreCase("on")){
                            if(maintence_mode){
                                p.sendMessage("§cO servidor já esta em modo de manutenção");
                            }else{
                                Bukkit.broadcastMessage("");
                                Bukkit.broadcastMessage("§c (!) Nosso servidor irá entrar em modo de manutenção");
                                Bukkit.broadcastMessage("§c (!) em alguns minutos, você será kickado para o lobby");
                                Bukkit.broadcastMessage("§c (!) quando esta manutenção iniciar.");
                                Bukkit.broadcastMessage("");
                                new BukkitRunnable() {

                                    @Override
                                    public void run() {
                                        // TODO Auto-generated method stub
                                        Bukkit.broadcastMessage("");
                                        Bukkit.broadcastMessage("§c (!) Iniciando modo de manutenção...");
                                        Bukkit.broadcastMessage("");
                                        for(Player online : Bukkit.getOnlinePlayers()){
                                            if(!online.hasPermission("hades.manutencao.bypass")){
                                                online.kickPlayer("§C§lREDE HADES\n§R\n§r§cDesculpe, O servidor entrou em modo de manutenção.\n§R§cVoltaremos em breve.\n§R\n§R§cPara mais informações siga nosso Twitter §b@ServidoresHades");
                                            }
                                        }
                                        Main.getInstance().getConfig().set("Manutencao", true);
                                        maintence_mode = true;
                                    }
                                }.runTaskLater(Main.getPlugin(Main.class), 20L*30);
                            }
                        }else if(digitado.equalsIgnoreCase("off")){
                            if(maintence_mode){
                                Main.getInstance().getConfig().set("Manutencao", false);
                                maintence_mode = false;
                                p.sendMessage("§aO modo de manutenção foi desabilitado.");
                            }else{
                                p.sendMessage("§cNenhuma manutenção esta ocorrendo neste momento.");
                            }
                        }
                    }
                }else{
                    p.sendMessage("§cVocê precisa ser cargo Admin ou superior para executar este comando.");
                    return true;
                }
            }
        }
        return false;
    }

}
