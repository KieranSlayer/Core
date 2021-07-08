package net.gothammc.networkcore.ess.spigot.managers;

import net.gothammc.networkcore.ess.core.main.EssentialsSpigot;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class PlaceholderManager
{
    private final EssentialsSpigot instance;

    public PlaceholderManager(EssentialsSpigot instance)
    {
        this.instance = instance;
    }

    public void registerPAPI()
    {
        if (!Bukkit.getPluginManager().getPlugin("PlaceholderAPI").isEnabled()) {
            Bukkit.getPluginManager().disablePlugin(instance);
            System.out.println(ChatColor.translateAlternateColorCodes('&', "&4##################################"));
            System.out.println(ChatColor.translateAlternateColorCodes('&', "&4      &6NetworkCore Essentials"));
            System.out.println(ChatColor.translateAlternateColorCodes('&', "&4##################################"));
            System.out.println(ChatColor.translateAlternateColorCodes('&', "&4     "));
            System.out.println(ChatColor.translateAlternateColorCodes('&', "&4NetworkCore Essentials Spigot has been Disabled"));
            System.out.println(ChatColor.translateAlternateColorCodes('&', "&4PAPI is not present in your plugins folder please"));
            System.out.println(ChatColor.translateAlternateColorCodes('&', "&4 put it in and reboot the server"));
            System.out.println(ChatColor.translateAlternateColorCodes('&', "&4     "));
            System.out.println(ChatColor.translateAlternateColorCodes('&', "&4##################################"));
        }
    }

    public void registerVault()
    {

    }

}
