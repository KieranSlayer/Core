package net.gothammc.networkcore.ess.spigot.systems.afk.handlers;

import net.gothammc.networkcore.ess.core.main.EssentialsSpigot;
import net.gothammc.networkcore.ess.spigot.systems.afk.script.ActionType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerDisconnect implements Listener {
    private final EssentialsSpigot instance;

    public PlayerDisconnect(EssentialsSpigot instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        if(this.instance.getAfkManager().isAfkPlayer(event.getPlayer())) {
            instance.getAfkManager().getActionParser().parse(event.getPlayer(), ActionType.OTHER, "onAfkDisconnect");
        }

        this.instance.getAfkManager().getPlayerData().unloadPlayer(event.getPlayer());
        this.instance.getAfkManager().removeAfkPlayerFromCache(event.getPlayer());
    }
}
