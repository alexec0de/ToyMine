package ru.alexec0de.classes;

import java.util.Random;

public enum MineType {

    DEFAULT,
    RARE,
    EPIC;



    private static final Random PRNG = new Random();

    public static MineType randomMineType() {
        MineType[] mineTypes = values();
        return mineTypes[PRNG.nextInt(mineTypes.length)];
    }
}
