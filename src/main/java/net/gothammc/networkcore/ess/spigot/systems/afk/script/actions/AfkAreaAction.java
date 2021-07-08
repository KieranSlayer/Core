package net.gothammc.networkcore.ess.spigot.systems.afk.script.actions;

import net.gothammc.networkcore.ess.core.main.EssentialsSpigot;
import net.gothammc.networkcore.ess.spigot.systems.afk.script.AbstractAction;
import net.gothammc.networkcore.ess.spigot.systems.afk.script.ActionType;
import net.gothammc.networkcore.ess.spigot.systems.afk.util.DataHandler;
import net.gothammc.networkcore.ess.spigot.systems.afk.util.LocationHelper;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class AfkAreaAction extends AbstractAction {
    EssentialsSpigot instance;

    public AfkAreaAction(EssentialsSpigot instance) {
        super("afkarea");
        this.instance = instance;
    }

    @Override
    public void execute(Player player, ActionType type, Map<String, String> config) {
        DataHandler data = instance.getAfkManager().getPlayerData();
        if(instance.getAfkManager().isAfkPlayer(player)) {
            data.getPlayer(player).set("location", LocationHelper.serialize(player.getLocation()));
            data.savePlayer(player);

            Location afkLocation = LocationHelper.deserialize(instance.getConfigs().getAfkConfig().getAfkConfig().getString("afkLocation"));
            player.teleport(afkLocation);
            instance.getAfkManager().setLastLocation(player, afkLocation);
            return;
        }

        player.teleport(LocationHelper.deserialize(instance.getAfkManager().getPlayerData().getPlayer(player).getString("location")));
        data.deletePlayer(player);
    }

    @Override
    public boolean verifySyntax(Map<String, String> actionConfig, Plugin plugin) {
        if(!this.instance.getConfigs().getAfkConfig().getAfkConfig().contains("afkLocation")) {
            plugin.getLogger().warning("[AfkAreaAction] No AFK area was set. Set it with /openafk set afkarea and then reload the server.");
            return false;
        }

        return true;
    }
}
