package github.regularrabbit05.anticheat.aurabot.commands;

import github.regularrabbit05.anticheat.aurabot.Plugin;
import github.regularrabbit05.anticheat.aurabot.Runner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor {
    private final Plugin plugin;
    public TestCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length != 1) return false;
        commandSender.sendMessage("Running!");
        Player p = plugin.getServer().getPlayer(strings[0]);
        if (p == null) {
            commandSender.sendMessage("§cPlayer not connected...");
            return true;
        }
        Runner runner = new Runner(plugin, commandSender);
        runner.make(p);
        plugin.addTask(runner);
        return true;
    }
}
