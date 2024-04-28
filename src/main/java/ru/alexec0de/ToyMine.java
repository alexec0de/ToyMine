package ru.alexec0de;

import org.bukkit.plugin.java.JavaPlugin;
import ru.alexec0de.classes.Mine;
import ru.alexec0de.commands.MineCommand;

public final class ToyMine extends JavaPlugin {

    public static ToyMine instance;
    public static Mine mine;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        mine = new Mine();
        getCommand("mine").setExecutor(new MineCommand());
        getServer().getPluginManager().registerEvents(mine, instance);

    }

    @Override
    public void onDisable() {
        mine.hologram.delete();
    }
}
