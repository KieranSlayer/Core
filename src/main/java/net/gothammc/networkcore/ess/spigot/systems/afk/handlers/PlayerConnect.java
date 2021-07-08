package net.gothammc.networkcore.ess.spigot.systems.afk.handlers;

import net.gothammc.networkcore.ess.core.main.EssentialsSpigot;
import net.gothammc.networkcore.ess.spigot.systems.afk.util.DataHandler;
import net.gothammc.networkcore.ess.spigot.systems.afk.util.LocationHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerConnect implements Listener {

    final private EssentialsSpigot instance;
    public PlayerConnect(EssentialsSpigot instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerConnect(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        DataHandler data = instance.getAfkManager().getPlayerData();

        if(data.playerHasData(player)) {
            player.teleport(LocationHelper.deserialize(data.getPlayer(player).getString("location")));
            data.deletePlayer(player);

            player.resetTitle();
        }

    }
}
