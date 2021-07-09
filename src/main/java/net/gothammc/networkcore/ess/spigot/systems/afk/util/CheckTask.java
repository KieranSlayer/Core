package net.gothammc.networkcore.ess.spigot.systems.afk.util;

import net.gothammc.networkcore.ess.core.main.EssentialsSpigot;
import net.gothammc.networkcore.ess.spigot.systems.afk.script.ActionType;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

;

public final class CheckTask extends BukkitRunnable {
    private final EssentialsSpigot instance;

    public CheckTask(EssentialsSpigot instance) {
        this.instance = instance;
    }

    private boolean movedEnough(Player player) {
        Location lastLocation = instance.getAfkManager().getLastLocation(player);
        if(lastLocation == null) {
            instance.getAfkManager().setLastLocation(player, player.getLocation());
            return false;
        }

        // Prevent an error where it cannot measure distance between 2 different worlds
        if(instance.getAfkManager().getLastLocation(player).getWorld() != player.getLocation().getWorld()) {
            instance.getAfkManager().setLastLocation(player, player.getLocation());
            return true;
        }

        boolean movedEnough = instance.getAfkManager().getLastLocation(player).distance(player.getLocation()) > this.instance.getConfigs().getAfkConfig().getAfkConfig().getInt("movementDistance");
        instance.getAfkManager().setLastLocation(player, player.getLocation());

        return movedEnough;
    }

    @Override
    public void run() {
        for(Player player : instance.getServer().getOnlinePlayers()) {
            // Debug
//            Integer checks = plugin.getCheckAmount(player);
//            if(checks != null) player.sendMessage("Checks: " + checks);

            // Cancel for conditions
            if(instance.getAfkManager().isExempt(player)) continue;
            if(player.hasPermission("openafk.exempt") && !player.isOp()) continue;

            FileConfiguration c = instance.getConfigs().getAfkConfig().getAfkConfig();
            if(c.getBoolean("detection.operatorsExempt") && player.isOp()) continue;

            if(!c.getBoolean("detection.gamemodes.survival") && player.getGameMode() == GameMode.SURVIVAL) continue;
            if(!c.getBoolean("detection.gamemodes.adventure") && player.getGameMode() == GameMode.ADVENTURE) continue;
            if(!c.getBoolean("detection.gamemodes.creative") && player.getGameMode() == GameMode.CREATIVE) continue;
            if(!c.getBoolean("detection.gamemodes.spectator") && player.getGameMode() == GameMode.SPECTATOR) continue;

            if(this.movedEnough(player)) {
                instance.getAfkManager().makePlayerReturn(player, ActionType.RETURN, "onReturn");
                continue;
            }

            Integer currentAmount = instance.getAfkManager().getCheckAmount(player);
            if(currentAmount == null) {
                instance.getAfkManager().setCheckAmount(player, 1);
                continue;
            }

            int checksBeforeAfk = instance.getConfigs().getAfkConfig().getAfkConfig().getInt("checksBeforeAfk");

            ConfigurationSection group = instance.getAfkManager().getGroups().getGroupForPlayer(player);
            if(group != null) {
                checksBeforeAfk = group.getInt("checkAmount", checksBeforeAfk);
            }

            if(currentAmount == checksBeforeAfk) {
                instance.getAfkManager().makePlayerAfk(player, ActionType.AFK, "onAfk");
                continue;
            }
            instance.getAfkManager().setCheckAmount(player, currentAmount + 1);
        }
    }
}
