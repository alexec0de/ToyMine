package ru.alexec0de.classes;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import ru.alexec0de.ToyMine;

import java.util.List;

public class Cuboid {
    private int xMin, xMax, yMin, yMax, zMin, zMax;

    private double xMinCentred, xMaxCentred, yMinCentred, yMaxCentred, zMinCentred, zMaxCentred;

    private World world;

    public Cuboid(Location point1, Location point2) {
        this.xMin = Math.min(point1.getBlockX(), point2.getBlockX());
        this.xMax = Math.max(point1.getBlockX(), point2.getBlockX());
        this.yMin = Math.min(point1.getBlockY(), point2.getBlockY());
        this.yMax = Math.max(point1.getBlockY(), point2.getBlockY());
        this.zMin = Math.min(point1.getBlockZ(), point2.getBlockZ());
        this.zMax = Math.max(point1.getBlockZ(), point2.getBlockZ());

        this.world = point1.getWorld();

        this.xMinCentred = this.xMin + 0.5;
        this.xMaxCentred = this.xMax + 0.5;
        this.yMinCentred = this.yMin + 0.5;
        this.yMaxCentred = this.yMax + 0.5;
        this.zMinCentred = this.zMin + 0.5;
        this.zMaxCentred = this.zMax + 0.5;
    }

    public int getTotalBlockSize() {
        return getHeight() * getXWidth() * getZWidth();
    }

    public int getHeight() {
        return yMax - yMin + 1;
    }

    public int getXWidth() {
        return xMax - xMin + 1;
    }

    public int getZWidth() {
        return zMax - zMin + 1;
    }
    public static int rnd(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    private Material randomWithMine(MineType mineType, MineWorld mineWorld){
        List<String> strings =  ToyMine.instance.getConfig().getStringList("mine." + mineWorld.toString().toLowerCase() + "." + mineType.toString().toLowerCase());
        for (int i = 0; i <= 20; i++) {
            String s = strings.get(rnd(0, strings.size() - 1));
            Material material = Material.valueOf(s.split(";")[0]);
            int chance = Integer.parseInt(s.split(";")[1]);
            if (Math.random() * 100.0D < chance) {
                return material;
            }
        }
        return randomWithMine(mineType, mineWorld);
    }

    public void fill(MineType mineType, MineWorld mineWorld) {
        for (int y = yMin; y <= yMax; y++) {
            for (int x = xMin; x <= xMax; x++) {
                for (int z = zMin; z <= zMax; z++) {
                    Location location = new Location(world, x, y, z);
                    location.getBlock().setType(randomWithMine(mineType, mineWorld));
                }
            }
        }
    }
}
