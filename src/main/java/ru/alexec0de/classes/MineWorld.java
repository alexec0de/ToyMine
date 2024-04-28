package ru.alexec0de.classes;

import java.util.Random;

public enum MineWorld {

    DEFAULT,
    NETHER,
    END;

    private static final Random PRNG = new Random();

    public static MineWorld randomMineWorld() {
        MineWorld[] mineWorlds = values();
        return mineWorlds[PRNG.nextInt(mineWorlds.length)];
    }
}
