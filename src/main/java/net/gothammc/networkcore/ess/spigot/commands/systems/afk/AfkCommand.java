package net.gothammc.networkcore.ess.spigot.commands.systems.afk;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import net.gothammc.networkcore.ess.core.main.EssentialsSpigot;
import net.gothammc.networkcore.ess.spigot.systems.afk.script.ActionType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("afk")
public class AfkCommand extends BaseCommand
{
    private final EssentialsSpigot instance;

    public AfkCommand(EssentialsSpigot instance) { this.instance = instance; }

    @Default
    public boolean afk(CommandSender sender, Command command, String label, String[] args)
    {
        if(!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if(!player.hasPermission("openafk.afk")) {
            sender.sendMessage(instance.getAfkManager().parse(player, instance.getConfigs().getAfkConfig().getAfkConfig().getString("messages.insufficientPermissions")));
            return true;
        }

        if(instance.getAfkManager().isAfkPlayer(player)) {
            instance.getAfkManager().makePlayerReturn(player, ActionType.RETURN_BY_COMMAND, "onReturnCMD");
            return true;
        }

        instance.getAfkManager().makePlayerAfk(player, ActionType.AFK_BY_COMMAND, "onAfkCMD");
        return true;
    }

}
