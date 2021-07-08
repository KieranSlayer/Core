package net.gothammc.networkcore.ess.spigot.commands.systems.afk;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import net.gothammc.networkcore.ess.core.main.EssentialsSpigot;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

@CommandAlias("isafk")
public class IsAfkCommand extends BaseCommand
{

    private EssentialsSpigot instance;


    public IsAfkCommand(EssentialsSpigot instance)
    {
        this.instance = instance;
    }

    public boolean isAfk(CommandSender sender, Command command, String label, String[] args)
    {
        if(!sender.hasPermission("openafk.isafk")) {
            sender.sendMessage(instance.getAfkManager().parse(instance.getConfigs().getAfkConfig().getAfkConfig().getString("messages.insufficientPermissions")));
            return true;
        }

        if(args.length == 0) {
            sender.sendMessage(instance.getAfkManager().parse(instance.getConfigs().getAfkConfig().getAfkConfig().getString("messages.isAfk.notEnoughArgs")));
            return true;
        }

        if(args[0].length() == 36) {
            Player target = Bukkit.getPlayer(UUID.fromString(args[0]));
            return sendIsAFKResponse(sender, target);
        }

        Player target = Bukkit.getPlayer(args[0]);
        return sendIsAFKResponse(sender, target);
    }

    private boolean sendIsAFKResponse(CommandSender sender, Player target) {
        if(target == null) {
            sender.sendMessage(instance.getAfkManager().parse(instance.getConfigs().getAfkConfig().getAfkConfig().getString("messages.isAfk.unknown")));
            return true;
        }

        if(instance.getAfkManager().isAfkPlayer(target)) {
            sender.sendMessage(instance.getAfkManager().parse(target, instance.getConfigs().getAfkConfig().getAfkConfig().getString("messages.isAfk.afk")));
        } else {
            sender.sendMessage(instance.getAfkManager().parse(target, instance.getConfigs().getAfkConfig().getAfkConfig().getString("messages.isAfk.notAfk")));
        }
        return true;
    }
}
