package net.lxsthw.redelite.hub.antimod;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutCustomPayload;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class Entrada implements Listener {
    public Entrada() {
    }

    @EventHandler(
            priority = EventPriority.LOWEST
    )
    public void onLogin(PlayerLoginEvent event) {
        Player p = event.getPlayer();

        try {
            String url = "http://botscout.com/test/?ip=" + event.getAddress().getHostAddress();
            Scanner scanner = new Scanner((new URL(url)).openStream());
            if (scanner.findInLine("Y") != null) {
                event.disallow(Result.KICK_OTHER, "§cVocê está usando um proxy!");
                p.setWhitelisted(false);
            }

            scanner.close();
        } catch (MalformedURLException var5) {
        } catch (IOException var6) {
        }

    }

    public static void setLaby(Player p) {
        try {
            HashMap<String, Boolean> dList = new HashMap();
            dList.put(Entrada.DisableLayModFuctions.POTIONS.getName(), false);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            objOut.writeObject(dList);
            ByteBuf bb = Unpooled.copiedBuffer(out.toByteArray());
            PacketDataSerializer serializer = new PacketDataSerializer(bb);
            PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload("LABYMOD", serializer);
            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
        } catch (Exception var7) {
            var7.printStackTrace();
        }

    }

    public static File getWorkingDirectory() {
        return getWorkingDirectory("minecraft");
    }

    public static File getWorkingDirectory(String applicationName) {
        String userHome = System.getProperty("user.home", ".");
        File workingDirectory = null;
        String applicationData;
        switch($SWITCH_TABLE$tk$slicecollections$maxteer$lobby$antimod$Entrada$OS()[getPlatform().ordinal()]) {
            case 1:
                workingDirectory = new File(userHome, String.valueOf('.') + applicationName + '/');
                break;
            case 2:
                applicationData = System.getenv("APPDATA");
                if (applicationData != null) {
                    workingDirectory = new File(applicationData, "." + applicationName + '/');
                } else {
                    workingDirectory = new File(userHome, String.valueOf('.') + applicationName + '/');
                }
                break;
            case 3:
                workingDirectory = new File(userHome, "Library/Application Support/" + applicationName);
                break;
            case 4:
                applicationData = System.getenv("APPDATA");
                if (applicationData != null) {
                    workingDirectory = new File(applicationData, "." + applicationName + '/');
                } else {
                    workingDirectory = new File(userHome, String.valueOf('.') + applicationName + '/');
                }
                break;
            case 5:
                workingDirectory = new File(userHome, "Library/Application Support/" + applicationName);
                break;
            default:
                workingDirectory = new File(userHome, String.valueOf(applicationName) + '/');
        }

        if (!workingDirectory.exists() && !workingDirectory.mkdirs()) {
            throw new RuntimeException("The working directory could not be created: " + workingDirectory);
        } else {
            return workingDirectory;
        }
    }

    public static Entrada.OS getPlatform() {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            return Entrada.OS.WINDOWS;
        } else if (osName.contains("mac")) {
            return Entrada.OS.MACOS;
        } else if (osName.contains("solaris")) {
            return Entrada.OS.SOLARIS;
        } else if (osName.contains("sunos")) {
            return Entrada.OS.SOLARIS;
        } else if (osName.contains("linux")) {
            return Entrada.OS.LINUX;
        } else {
            return osName.contains("unix") ? Entrada.OS.LINUX : Entrada.OS.UNKNOWN;
        }
    }

    private static enum DisableLayModFuctions {
        POTIONS("POTIONS", 0, "POTIONS");

        private String name;

        private DisableLayModFuctions(String s, int n, String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    public static enum OS {
        LINUX("LINUX", 0),
        SOLARIS("SOLARIS", 1),
        WINDOWS("WINDOWS", 2),
        MACOS("MACOS", 3),
        UNKNOWN("UNKNOWN", 4);

        private OS(String s, int n) {
        }
    }
}
