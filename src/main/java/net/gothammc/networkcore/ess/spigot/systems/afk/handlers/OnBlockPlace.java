package net.gothammc.networkcore.ess.spigot.systems.afk.handlers;

import net.gothammc.networkcore.ess.core.main.EssentialsSpigot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class OnBlockPlace implements Listener {
    private final EssentialsSpigot instance;

    public OnBlockPlace(EssentialsSpigot instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if(!instance.getConfigs().getAfkConfig().getAfkConfig().getBoolean("detection.events.blockPlaceEvent")) {
            return;
        }

        instance.getAfkManager().setCheckAmount(event.getPlayer(), 0);
    }
}
