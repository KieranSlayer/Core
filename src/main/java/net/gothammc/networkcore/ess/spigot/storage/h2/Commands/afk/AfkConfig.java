package net.gothammc.networkcore.ess.spigot.storage.h2.Commands.afk;

import net.gothammc.networkcore.ess.core.main.EssentialsSpigot;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class AfkConfig
{
    private final EssentialsSpigot instance;
    private FileConfiguration afkConfig;
    private File afkConfigFile;


    public AfkConfig(EssentialsSpigot instance)
    {
        this.instance = instance;
        saveDefaultConfig();
    }

    public void reloadConfig()
    {
        if (this.afkConfigFile  == null)
            this.afkConfigFile = new File(this.instance.getDataFolder(), "Spigot/Commands/Afk/" + "config.yml");

        this.afkConfig = YamlConfiguration.loadConfiguration(this.afkConfigFile);

        InputStream defaultStream = this.instance.getResource("Spigot/Commands/Afk/" + "config.yml");
        if (defaultStream == null)
        {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.afkConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getAfkConfig()
    {
        if (this.afkConfig == null)
            reloadConfig();
        return  this.afkConfig;
    }

    public void saveConfig()
    {
        if (this.afkConfig == null || this.afkConfigFile == null) return;

        try
        {
            this.getAfkConfig().save(this.afkConfigFile);
        } catch (IOException ex)
        {
            instance.getLogger().log(Level.SEVERE, "Couldn't save Config file : " + "Spigot/Commands/Afk/" + "config.yml");
        }
    }

    public void saveDefaultConfig()
    {
        if (this.afkConfigFile == null)
            this.afkConfigFile = new File(instance.getDataFolder(), "Spigot/Commands/Afk/" + "config.yml");

        if (!this.afkConfigFile.exists())
            instance.saveResource("Spigot/Commands/Afk/" + "config.yml", false);
    }
}
