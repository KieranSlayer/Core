package net.gothammc.networkcore.ess.bungeecord.managers;

import co.aikar.commands.BungeeCommandManager;
import net.gothammc.networkcore.ess.core.main.EssentialsProxy;

public class CommandManager
{
    private final EssentialsProxy instance;

    public CommandManager(EssentialsProxy instance)
    {
        this.instance = instance;
    }

    public void commands()
    {
        BungeeCommandManager manager = new BungeeCommandManager(instance);
    }
}
