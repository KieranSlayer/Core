package net.gothammc.networkcore.ess.spigot.systems.afk.util;

import net.gothammc.networkcore.ess.core.main.EssentialsSpigot;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class GroupConfig {
    private final HashMap<String, ConfigurationSection> groups = new HashMap<>();

    private final EssentialsSpigot instance;

    public GroupConfig(EssentialsSpigot instance) {
        this.instance = instance;
    }

    public void init() {
        FileConfiguration config = instance.getSpigotInstance().getConfigs().getAfkConfig().getAfkConfig();

        config.getConfigurationSection("groups").getKeys(false).forEach(key -> {
            ConfigurationSection group = config.getConfigurationSection("groups."+key);
            if(group == null) return;

            this.groups.put(key, group);
        });
    }

    public ConfigurationSection getGroupForPlayer(Player player) {
        for(String name : this.groups.keySet()) {
            if(player.hasPermission("afk.group."+name)) {
                return this.groups.get(name);
            }
        }

        return null;
    }

    public ConfigurationSection getGroup(String group) {
        return this.groups.get(group);
    }

    public HashMap<String, ConfigurationSection> getGroups() {
        return this.groups;
    }
}
