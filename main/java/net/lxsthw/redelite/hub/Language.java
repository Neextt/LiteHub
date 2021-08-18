package net.lxsthw.redelite.hub;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import tk.slicecollections.maxteer.plugin.config.MConfig;
import tk.slicecollections.maxteer.plugin.config.MWriter;
import tk.slicecollections.maxteer.plugin.config.MWriter.YamlEntry;
import tk.slicecollections.maxteer.plugin.logger.MLogger;
import tk.slicecollections.maxteer.utils.StringUtils;

@SuppressWarnings("rawtypes")
public class Language {

  public static String chat$delay = "§cAguarde mais {time}s para falar novamente.";
  public static String chat$color$default = "§7";
  public static String chat$color$custom = "§f";
  public static String chat$format$lobby = "{player}{color}: {message}";

  public static String motd$broadcast = "hadesplugins.com";

  public static String manutencao$join = "§C§lREDE HADES\n§R\n§r§cDesculpe, O servidor entrou em modo de manutenção.\n§R§cVoltaremos em breve.\n§R\n§R§cPara mais informações siga nosso Twitter §b@ServidoresHades\n";

  public static boolean lobby$tab$enabled = true;
  public static String lobby$tab$header = " \n§b§lREDE HADES\n  §fwww.redehades.com\n ";
  public static String lobby$tab$footer =
      " \n§eForúm: §fredehades.com/forum\n§eDiscord: §fredehades.com/discord\n \n§bObtenha vantagens acessando: §floja.redehades.com\n ";


  public static final MLogger LOGGER = ((MLogger) Main.getInstance().getLogger())
      .getModule("LANG");
  private static final MConfig CONFIG = Main.getInstance().getConfig("lang");

  public static void setupLanguage() {
    boolean save = false;
    for (Field field : Language.class.getDeclaredFields()) {
      if (field.getName().contains("$") && !Modifier.isFinal(field.getModifiers())) {
        String nativeName = field.getName().replace("$", ".").replace("_", "-");

        try {
          Object value = null;

          if (CONFIG.contains(nativeName)) {
            value = CONFIG.get(nativeName);
            if (value instanceof String) {
              value = StringUtils.formatColors((String) value).replace("\\n", "\n");
            } else if (value instanceof List) {
              List l = (List) value;
              List<Object> list = new ArrayList<>(l.size());
              for (Object v : l) {
                if (v instanceof String) {
                  list.add(StringUtils.formatColors((String) v).replace("\\n", "\n"));
                } else {
                  list.add(v);
                }
              }

              l = null;
              value = list;
            }

            field.set(null, value);
          } else {
            value = field.get(null);
            if (value instanceof String) {
              value = StringUtils.deformatColors((String) value).replace("\n", "\\n");
            } else if (value instanceof List) {
              List l = (List) value;
              List<Object> list = new ArrayList<>(l.size());
              for (Object v : l) {
                if (v instanceof String) {
                  list.add(StringUtils.deformatColors((String) v).replace("\n", "\\n"));
                } else {
                  list.add(v);
                }
              }

              l = null;
              value = list;
            }

            save = true;
          }
        } catch (ReflectiveOperationException e) {
          LOGGER.log(Level.WARNING, "Unexpected error on settings file: ", e);
        }
      }
    }

    if (save) {
      CONFIG.reload();
      Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(),
          () -> LOGGER.info("A config §6lang.yml §7foi modificada ou criada."));
    }
  }
}
