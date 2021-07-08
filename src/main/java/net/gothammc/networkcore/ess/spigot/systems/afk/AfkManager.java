package net.gothammc.networkcore.ess.spigot.systems.afk;

import me.clip.placeholderapi.PlaceholderAPI;
import net.gothammc.networkcore.ess.core.main.EssentialsSpigot;
import net.gothammc.networkcore.ess.spigot.systems.afk.detection.FishingDetection;
import net.gothammc.networkcore.ess.spigot.systems.afk.events.PlayerAfkEvent;
import net.gothammc.networkcore.ess.spigot.systems.afk.events.PlayerReturnEvent;
import net.gothammc.networkcore.ess.spigot.systems.afk.handlers.*;
import net.gothammc.networkcore.ess.spigot.systems.afk.script.ActionParser;
import net.gothammc.networkcore.ess.spigot.systems.afk.script.ActionType;
import net.gothammc.networkcore.ess.spigot.systems.afk.script.actions.*;
import net.gothammc.networkcore.ess.spigot.systems.afk.util.CheckTask;
import net.gothammc.networkcore.ess.spigot.systems.afk.util.DataHandler;
import net.gothammc.networkcore.ess.spigot.systems.afk.util.GroupConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class AfkManager {

    private static EssentialsSpigot instance;
    
    private DataHandler playerData;
    private GroupConfig groups;

    private ActionParser actionParser;
    private BukkitTask checkTask;

    private final Set<Player> afkPlayers = new HashSet<>();
    private final Set<Player> exemptPlayers = new HashSet<>();
    private final HashMap<Player, Location> lastLocations = new HashMap<>();
    private final HashMap<Player, Integer> checkAmounts = new HashMap<>();

    
    public void enable() {
        this.playerData = new DataHandler(instance);

        this.groups = new GroupConfig(instance);
        this.groups.init();

        // Register Actions and Scripts
        this.actionParser = new ActionParser(instance);

        actionParser.registerAction(new InvincibilityAction(instance));
        actionParser.registerAction(new InvisibilityAction(instance));
        actionParser.registerAction(new ActionBarAction(instance));
        actionParser.registerAction(new AfkAreaAction(instance));
        actionParser.registerAction(new BroadcastAction());
        actionParser.registerAction(new NametagAction());
        actionParser.registerAction(new CommandAction());
        actionParser.registerAction(new MessageAction());
        actionParser.registerAction(new TitleAction());
        actionParser.registerAction(new LookAction());

        loadScripts();

        // Events
        PluginManager manager = instance.getServer().getPluginManager();
        manager.registerEvents(new PlayerDisconnect(instance), instance);
        manager.registerEvents(new PlayerConnect(instance), instance);
        manager.registerEvents(new FishingDetection(instance), instance);
        manager.registerEvents(new OnBlockBreak(instance), instance);
        manager.registerEvents(new OnBlockPlace(instance), instance);
        manager.registerEvents(new OnChat(instance), instance);

        for(Player player : Bukkit.getOnlinePlayers()) {
            if(playerData.playerHasData(player)) {
                playerData.getPlayer(player);
                afkPlayers.add(player);
            }
        }

        checkTask = new CheckTask(instance).runTaskTimer(instance, 0L, this.getConfig().getLong("checkInterval", 20L));
    }

    public void makePlayerAfk(Player player, ActionType type, String script) {
        if(!afkPlayers.contains(player)) {
            PlayerAfkEvent event = new PlayerAfkEvent(player);
            Bukkit.getPluginManager().callEvent(event);

            if(!event.isCancelled()) {
                afkPlayers.add(player);
                actionParser.parse(player, type, script);
            }
        }
    }

    public void makePlayerReturn(Player player, ActionType type, String script) {
        checkAmounts.remove(player);

        if(afkPlayers.contains(player)) {
            PlayerReturnEvent event = new PlayerReturnEvent(player);
            Bukkit.getPluginManager().callEvent(event);

            if(!event.isCancelled()) {
                afkPlayers.remove(player);
                checkAmounts.remove(player);

                actionParser.parse(player, type, script);
            }
        }
    }

    public void reload() {
        // Cancel checkTask
        checkTask.cancel();

        // Reload Scripts
        actionParser.getScripts().clear();
        loadScripts();

        // Make new check task
        checkTask = new CheckTask(instance).runTaskTimer(instance, 0L, this.getConfig().getLong("checkInterval", 20L));
    }



    public static String parse(final Player player, final String s) {
        // This is an easter egg for whenever "perotin" is online. He insulted me okay Kappa.
        FileConfiguration config = instance.getConfigs().getAfkConfig().getAfkConfig();

        String prefix = config.getString("messages.prefix");
        if(player.getUniqueId().toString().equals("9d311c0a-e4cd-4bc6-aec5-a79f3381d19e")) {
            prefix = "&4[&cFrickOffPerotin&4] &7";
        }

        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            return PlaceholderAPI.setPlaceholders(player, s);
        }

        return ChatColor.translateAlternateColorCodes('&', s.replaceAll("%openafk_prefix%", prefix).replaceAll("%player_name%", player.getName()));
    }

    public static String parse(final String s) {
        FileConfiguration config = instance.getConfigs().getAfkConfig().getAfkConfig();
        return ChatColor.translateAlternateColorCodes('&', s.replaceAll("%openafk_prefix%", config.getString("messages.prefix")));
    }

    //
    // Private
    //
    private void loadScripts() {
        actionParser.registerScript("onAfk", instance.getConfigs().getAfkConfig().getAfkConfig().getMapList("scripts.onAfk"));
        actionParser.registerScript("onAfkCMD", instance.getConfigs().getAfkConfig().getAfkConfig().getMapList("scripts.onAfkCMD"));
        actionParser.registerScript("onReturn", instance.getConfigs().getAfkConfig().getAfkConfig().getMapList("scripts.onReturn"));
        actionParser.registerScript("onReturnCMD", instance.getConfigs().getAfkConfig().getAfkConfig().getMapList("scripts.onReturnCMD"));
        actionParser.registerScript("onFishingAFK", instance.getConfigs().getAfkConfig().getAfkConfig().getMapList("scripts.onFishingAFK"));
        actionParser.registerScript("onAfkDisconnect", instance.getConfigs().getAfkConfig().getAfkConfig().getMapList("scripts.onAfkDisconnect"));
    }

    //
    // Getters
    //

    public FileConfiguration getConfig() { return instance.getConfigs().getAfkConfig().getAfkConfig(); }
    public DataHandler getPlayerData() { return playerData; }
    public GroupConfig getGroups() { return groups; }
    public ActionParser getActionParser() { return actionParser; }
    public Integer getCheckAmount(Player player) { return checkAmounts.get(player); }
    public boolean isAfkPlayer(Player player) { return afkPlayers.contains(player); }
    public Set<Player> getAfkPlayers() { return new HashSet<>(afkPlayers); }
    public boolean isExempt(Player player) { return exemptPlayers.contains(player); }
    public Location getLastLocation(Player player) { return lastLocations.get(player); }

    //
    // Setters
    //
    public void setCheckAmount(Player player, int value) { checkAmounts.put(player, value); }
    public void removeAfkPlayerFromCache(Player player) { afkPlayers.remove(player); }
    public void addExemptPlayer(Player player) { exemptPlayers.add(player); }
    public void removeExemptPlayer(Player player) { exemptPlayers.remove(player); }
    public void setLastLocation(Player player, Location newLocation) { this.lastLocations.put(player, newLocation); }
}
