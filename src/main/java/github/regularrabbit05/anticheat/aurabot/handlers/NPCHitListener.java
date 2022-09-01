package github.regularrabbit05.anticheat.aurabot.handlers;

import github.regularrabbit05.anticheat.aurabot.Plugin;
import github.regularrabbit05.anticheat.aurabot.Runner;
import net.jitse.npclib.events.NPCInteractEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NPCHitListener implements Listener {
    private final Plugin plugin;

    public NPCHitListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onNPCInteract(NPCInteractEvent event) {
        if (!event.getNPC().isActuallyShown(event.getWhoClicked())) return;
        if (event.getWhoClicked().hasPermission("watchdog.bypass")) return;
        for (Runner runner : plugin.getList()) {
            if (runner.getId() == event.getNPC().getEntityId()) {
                runner.click();
                if (runner.getTarget() == null) return;
                String tx = "§c§lWATCHDOG:§r §c" + runner.getTarget().getName() + " hit the target (" + runner.getClicks() + ")";
                if (runner.getSummoner() == null) {
                    plugin.getServer().broadcast(tx, "watchdog.notifications");
                    return;
                }
                runner.getSummoner().sendMessage(tx);
                return;
            }
        }
    }
}
