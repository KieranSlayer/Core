package net.gothammc.networkcore.ess.spigot.commands.systems.afk;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.Default;
import net.gothammc.networkcore.ess.core.main.EssentialsSpigot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.StringJoiner;

public class AfkPlayersCommand extends BaseCommand
{

    private final EssentialsSpigot instance;

    public AfkPlayersCommand(EssentialsSpigot instance)
    {
        this.instance = instance;
    }


    @Default
    public boolean afkPlayers(CommandSender sender, Command command, String label, String[] args)
    {
        //if(!(sender instanceof Player) || (args.length == 1 && args[0].equals("list"))) { Made only function until inventory is added
        StringJoiner list = new StringJoiner("&7, ");

        instance.getAfkManager().getAfkPlayers().forEach(player -> { list.add("&a"+player.getName()); });
        sender.sendMessage(instance.getAfkManager().parse(instance.getAfkManager().getConfig().getString("messages.afkList.listPrefix") + list.toString()));
        return true;
        //}

        // TODO: Inventory of heads for AFK players when the sender is a player and didn't request in list form.

    }

}
