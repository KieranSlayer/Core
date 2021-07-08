package net.gothammc.networkcore.ess.spigot.systems.afk.detection;

import net.gothammc.networkcore.ess.core.main.EssentialsSpigot;
import net.gothammc.networkcore.ess.spigot.systems.afk.script.ActionType;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class FishingDetection implements Listener {
    private final EssentialsSpigot instance;

    public FishingDetection(EssentialsSpigot instance) {
        this.instance = instance;
    }

    private final Set<Player> playersFishing = new HashSet<>();
    private final HashMap<Player, Integer> violationLevel = new HashMap<>();

    @EventHandler
    public void fishEvent(PlayerFishEvent event) {
        if(!instance.getConfigs().getAfkConfig().getAfkConfig().getBoolean("detection.fishing.enabled")) {
            return;
        }

        Player player = event.getPlayer();
        if(event.getState() == PlayerFishEvent.State.FISHING) {
            playersFishing.add(player);
        } else {
            playersFishing.remove(player);
        }
    }

    @EventHandler
    public void playerInteract(PlayerInteractEvent event) {
        if(!instance.getConfigs().getAfkConfig().getAfkConfig().getBoolean("detection.fishing.enabled")) {
            return;
        }

        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getMaterial() == Material.FISHING_ROD && playersFishing.contains(event.getPlayer())) {
            if(event.getClickedBlock().getType() == Material.NOTE_BLOCK) {
                increaseViolationLevel(event.getPlayer());
            }
        }
    }

    private void increaseViolationLevel(Player player) {
        if(!violationLevel.containsKey(player)) {
            violationLevel.put(player, 1);
        } else {
            violationLevel.put(player, violationLevel.get(player)+1);
        }

        FileConfiguration config = instance.getConfigs().getAfkConfig().getAfkConfig();
        if(violationLevel.get(player) == config.getInt("detection.fishing.violationsNeeded")) {

            instance.getAfkManager().getActionParser().parse(player, ActionType.DETECTION_FISHING, "onFishingAFK");

            violationLevel.remove(player);
        }
    }
}
