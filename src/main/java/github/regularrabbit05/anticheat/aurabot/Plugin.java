package github.regularrabbit05.anticheat.aurabot;

import github.regularrabbit05.anticheat.aurabot.commands.TestCommand;
import github.regularrabbit05.anticheat.aurabot.handlers.AutoDamageListener;
import github.regularrabbit05.anticheat.aurabot.handlers.NPCHitListener;
import net.jitse.npclib.NPCLib;
import net.jitse.npclib.skin.MineSkinFetcher;
import net.jitse.npclib.skin.Skin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public final class Plugin extends JavaPlugin {
    private NPCLib lib;
    private Skin skin;
    private final Random random = new Random();

    public NPCLib getLib() {
        return lib;
    }

    public List<Runner> tasks = new LinkedList<>();

    @Override
    public void onEnable() {
        random.setSeed(System.nanoTime());
        lib = new NPCLib(this);
        this.getConfig().addDefault("skin", 103974854);
        this.getConfig().addDefault("maxCount", 50);
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.reloadConfig();

        this.getCommand("auraPlayer").setExecutor(new TestCommand(this));
        this.getServer().getPluginManager().registerEvents(new AutoDamageListener(this), this);
        this.getServer().getPluginManager().registerEvents(new NPCHitListener(this), this);

        MineSkinFetcher.fetchSkinFromIdAsync(this.getConfig().getInt("skin"), s -> skin = s);
    }

    @Override
    public void onDisable() {
        for (Runner task:tasks) task.cancel();
    }

    public Skin getBotSkin() {
        return skin;
    }

    public Random getRandom() {
        return random;
    }

    public void addTask(Runner make) {
        tasks.add(make);
    }

    public void taskDone(Runner make) {
        tasks.remove(make);
    }

    public List<Runner> getList() {
        return tasks;
    }
}
