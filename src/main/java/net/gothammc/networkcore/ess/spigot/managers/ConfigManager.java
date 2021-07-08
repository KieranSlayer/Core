package net.gothammc.networkcore.ess.spigot.managers;

import net.gothammc.networkcore.ess.core.main.EssentialsSpigot;
import net.gothammc.networkcore.ess.spigot.storage.h2.Commands.afk.AfkConfig;
import net.gothammc.networkcore.ess.spigot.storage.h2.Commands.afk.AfkData;

public class ConfigManager
{
    private final EssentialsSpigot instance;
    private AfkConfig afkConfig;
    private AfkData afkData;

    public ConfigManager(EssentialsSpigot instance)
    {
        this.instance = instance;
        afkConfig = new AfkConfig(instance);
        afkData = new AfkData(instance);
    }

    public void configs()
    {
        /**
         * AFK Configs
         */
        afkConfig.saveDefaultConfig();
        afkConfig.saveConfig();
        afkConfig.reloadConfig();



    }

    public AfkConfig getAfkConfig()
    {
        return afkConfig;
    }

}
