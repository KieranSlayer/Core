package net.gothammc.networkcore.ess.spigot.systems.afk.handlers;

import net.gothammc.networkcore.ess.core.main.EssentialsSpigot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OnChat implements Listener {
    private final EssentialsSpigot instance;

    public OnChat(EssentialsSpigot instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if(!instance.getConfigs().getAfkConfig().getAfkConfig().getBoolean("detection.events.chatEvent")) {
            return;
        }

        instance.getAfkManager().setCheckAmount(event.getPlayer(), 0);
    }
}
