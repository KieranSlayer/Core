package net.gothammc.networkcore.ess.core.main;

import net.gothammc.networkcore.ess.spigot.managers.*;
import net.gothammc.networkcore.ess.spigot.systems.afk.AfkManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class EssentialsSpigot extends JavaPlugin
{
    public EssentialsSpigot instance;
    private AfkManager afkManager;
    private ConfigManager configManager;

    @Override
    public void onEnable()
    {
        // Plugin startup logic
        registerCore();
        enabled();
    }

    @Override
    public void onLoad()
    {
        instance = this;
        registerConfigs();
        loaded();
    }

    @Override
    public void onDisable()
    {

        // Plugin shutdown logic
        disabled();
    }

    public void enabled()
    {
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6&l--------------------------------"));
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6       NetworkCore Essentials"));
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6       SPIGOT Version"));
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6&l--------------------------------"));
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6       &bSate: &2ENABLED"));
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6       &bVersion:" + getDescription().getVersion()));
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6       &bCoded by:" + getDescription().getAuthors()));
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6&l--------------------------------"));
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6           &aManagers"));
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6&l--------------------------------"));
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6Command Manager Registered: &2Successful"));
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6Event Manager Registered: &2Successful"));
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6Task Manager Registered: &2Successful"));
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6&l--------------------------------"));
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6             &aMYSQL"));
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6&l--------------------------------"));
    }

    public void loaded()
    {
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6&l--------------------------------"));
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6       NetworkCore Essentials"));
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6       SPIGOT Version"));
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6&l--------------------------------"));
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6       &bSate: &aLOADED"));
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6       &bVersion:" + getDescription().getVersion()));
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6       &bCoded by:" + getDescription().getAuthors()));
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6&l--------------------------------"));
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6           &aConfigs"));
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6&l--------------------------------"));
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6Messages Configs Registered: &2Successful"));
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6&l--------------------------------"));
    }

    public void disabled()
    {
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6&l--------------------------------"));
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6       NetworkCore Essentials"));
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6       SPIGOT Version"));
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6&l--------------------------------"));
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6       &bSate: &4DISABLED"));
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6       &bVersion:" + getDescription().getVersion()));
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6       &bCoded by:" + getDescription().getAuthors()));
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6&l--------------------------------"));
    }


    public void registerConfigs()
    {
        configManager = new ConfigManager(this);
        configManager.configs();
        afkManager = new AfkManager();

    }

    public void registerCore()
    {
        CommandManager commandManager = new CommandManager(this);
        commandManager.commands();

        EventManager eventManager = new EventManager(this);
        eventManager.events();

        TaskManager taskManager = new TaskManager(this);
        afkManager.enable();

        PlaceholderManager placeholderManager = new PlaceholderManager(this);
        placeholderManager.registerPAPI();
    }

    public EssentialsSpigot getSpigotInstance() {
        return instance;
    }

    public ConfigManager getConfigs() { return configManager; }

    public AfkManager getAfkManager() {
        return afkManager;
    }
}
