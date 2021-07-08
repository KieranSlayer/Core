package net.gothammc.networkcore.ess.spigot.managers;

import co.aikar.commands.BukkitCommandManager;
import net.gothammc.networkcore.ess.core.main.EssentialsSpigot;

public class CommandManager
{
    private final EssentialsSpigot instance;

    public CommandManager(EssentialsSpigot instance)
    {
        this.instance = instance;
    }

    public void commands()
    {
        BukkitCommandManager manager = new BukkitCommandManager(instance);
    }
}
