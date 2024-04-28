package ru.alexec0de.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.alexec0de.ToyMine;

import java.util.ArrayList;
import java.util.List;

public class MineCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 0){
            return true;
        }
        if (!commandSender.hasPermission("toymine.admin")){
            return true;
        }
        if (strings[0].equals("respawn")){
            ToyMine.mine.update();
            return true;
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> strings1 = new ArrayList<>();
        if (!commandSender.hasPermission("toymine.admin")){
            return null;
        }
        if (strings.length == 1){
            strings1.add("respawn");
            return strings1;
        }
        return null;
    }
}
