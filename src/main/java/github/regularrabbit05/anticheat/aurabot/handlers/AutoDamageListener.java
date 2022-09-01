package github.regularrabbit05.anticheat.aurabot.handlers;

import github.regularrabbit05.anticheat.aurabot.Plugin;
import github.regularrabbit05.anticheat.aurabot.Runner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AutoDamageListener implements Listener {
    private final Plugin plugin;
    private final int maxCount;
    public AutoDamageListener(Plugin plugin) {
        this.plugin = plugin;
        this.maxCount = plugin.getConfig().getInt("maxCount");
    }

    private int count = 0;

    @EventHandler (priority = EventPriority.LOWEST)
    public void onEntityDamagedByPlayer(EntityDamageByEntityEvent event) {
        if (maxCount == -1) return; //In config set max to -1 to disable auto check
        if (event.isCancelled()) return;
        if (event.getDamager().getType() != EntityType.PLAYER) return;
        count++;
        if (count >= maxCount-1) {
            Runner runner = new Runner(plugin);
            runner.make(((Player) event.getDamager()));
            plugin.addTask(runner);
            count = 0;
        }
    }
}
