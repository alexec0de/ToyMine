package ru.alexec0de.classes;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import ru.alexec0de.ToyMine;
import ru.alexec0de.utils.HexUtil;

public class Mine implements Listener {

    public Cuboid cuboid = new Cuboid(new Location(Bukkit.getWorld(ToyMine.instance.getConfig().getString("minePos.world")), ToyMine.instance.getConfig().getInt("minePos.pos1.x"), ToyMine.instance.getConfig().getInt("minePos.pos1.y"), ToyMine.instance.getConfig().getInt("minePos.pos1.z")),
            new Location(Bukkit.getWorld(ToyMine.instance.getConfig().getString("minePos.world")), ToyMine.instance.getConfig().getInt("minePos.pos2.x"), ToyMine.instance.getConfig().getInt("minePos.pos2.y"), ToyMine.instance.getConfig().getInt("minePos.pos2.z")));
    public MineWorld mineWorld;
    public MineType mineType;

    public Hologram hologram;
    public int secondsAutoUpdate;


    public Mine(){
        HologramsAPI.registerPlaceholder(ToyMine.instance, "{timeAutoUpdate}", 0.5D, this::timeToString);
        hologram = HologramsAPI.createHologram(ToyMine.instance, new Location(Bukkit.getWorld(ToyMine.instance.getConfig().getString("hologram.world")), ToyMine.instance.getConfig().getDouble("hologram.x"), ToyMine.instance.getConfig().getDouble("hologram.y"), ToyMine.instance.getConfig().getDouble("hologram.z")));
        hologram.setAllowPlaceholders(true);
        update();
    }

    private String timeToString() {
        long min = secondsAutoUpdate / 60 % 60,
                sec = secondsAutoUpdate / 1 % 60;
        return String.format("%02d:%02d", min, sec);
    }

    BukkitRunnable autoUpdate1;
    private void setAutoUpdate(){
        autoUpdate1 = new BukkitRunnable() {
            int seconds = ToyMine.instance.getConfig().getInt("settings.autoUpdate") * 60;
            @Override
            public void run() {
                if (seconds == 0){
                    update();
                    seconds = ToyMine.instance.getConfig().getInt("settings.autoUpdate") * 60;
                    cancel();
                }
                seconds--;
                secondsAutoUpdate = seconds;
            }
        };
    }

    public void update(){
        mineWorld = MineWorld.randomMineWorld();
        mineType = MineType.randomMineType();
        cuboid.fill(mineType, mineWorld);
        hologram.clearLines();
        for (String s : ToyMine.instance.getConfig().getStringList("hologram.lines")){
            s = s.replace("{mineType}", HexUtil.translate(ToyMine.instance.getConfig().getString("mineType." + mineType.toString().toLowerCase())));
            s = s.replace("{mineWorld}", HexUtil.translate(ToyMine.instance.getConfig().getString("mineWorld." + mineWorld.toString().toLowerCase())));
            hologram.appendTextLine(HexUtil.translate(s));
        }
        if (autoUpdate1 != null && !autoUpdate1.isCancelled()){
            autoUpdate1.cancel();
        }
        autoUpdate1 = null;
        setAutoUpdate();
        autoUpdate1.runTaskTimer(ToyMine.instance, 0L, 20L);

    }
    
    @EventHandler
    public void onBreakEvent(BlockBreakEvent e){
        if (!e.getBlock().getLocation().getWorld().getName().equals(ToyMine.instance.getConfig().getString("minePos.world")))
            return;
        if (e.getBlock().getType() == Material.PURPLE_GLAZED_TERRACOTTA){
            e.setDropItems(false);
            e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.ENDER_PEARL));
        }
        if (e.getBlock().getType() == Material.PURPUR_BLOCK){
            e.setDropItems(false);
            e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.CHORUS_FRUIT));
        }
    }






}
