package net.gothammc.networkcore.ess.spigot.systems.afk.handlers;

import net.gothammc.networkcore.ess.core.main.EssentialsSpigot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class OnBlockBreak implements Listener {
    private final EssentialsSpigot instance;

    public OnBlockBreak(EssentialsSpigot instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(!instance.getConfigs().getAfkConfig().getAfkConfig().getBoolean("detection.events.blockBreakEvent")) {
            return;
        }

        instance.getAfkManager().setCheckAmount(event.getPlayer(), 0);
    }
}
