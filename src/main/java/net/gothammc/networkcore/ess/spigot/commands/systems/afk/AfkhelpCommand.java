package net.gothammc.networkcore.ess.spigot.commands.systems.afk;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import net.gothammc.networkcore.ess.core.main.EssentialsSpigot;
import net.gothammc.networkcore.ess.spigot.systems.afk.util.LocationHelper;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.StringJoiner;

@CommandAlias("gafk")
public class AfkhelpCommand extends BaseCommand
{
    private EssentialsSpigot instance;

    public AfkhelpCommand(EssentialsSpigot instance)
    {
        this.instance = instance;
    }

    @Default
    public boolean afkHelp(CommandSender sender, Command command, String label, String[] args)
    {
        // Check permissions
        if(sender instanceof Player && !sender.hasPermission("afk.admin")) {
            sender.sendMessage(instance.getAfkManager().parse((Player) sender, instance.getConfigs().getAfkConfig().getAfkConfig().getString("messages.insufficientPermissions")));
            return true;
        }

        // Show help menu if there's 0 args.
        if(args.length == 0) {
            showHelpMenu(sender);
            return true;
        }

        // Parse the args
        switch(args[0].toLowerCase()) {
            case "set":
                set(sender, args);
                break;
            case "actionlist":
                sendAvailableActions(sender, args);
                break;
            case "reload":
                reloadPlugin(sender);
                break;
            case "help":
                showHelpMenu(sender);
                break;
            default:
                sender.sendMessage(instance.getAfkManager().parse(instance.getConfigs().getAfkConfig().getAfkConfig().getString("messages.invalidUsage")));
                break;
        }

        return true;
    }


    //
    // Private
    //
    private void showHelpMenu(CommandSender sender) {
        String genericCommand = ChatColor.RED + "/afkhelp ";

        sender.sendMessage(ChatColor.GRAY + "---- " + ChatColor.BLUE + "AFK Help" + ChatColor.GRAY + " ----");
        sender.sendMessage(genericCommand + "set <afkArea>" + ChatColor.GRAY + " - Sets the AFK area");
        sender.sendMessage(genericCommand + "actionlist" + ChatColor.GRAY + " - Shows the registered available actions");
        sender.sendMessage(genericCommand + "help" + ChatColor.GRAY + " - Shows this help menu");
        sender.sendMessage(genericCommand + "reload" + ChatColor.GRAY + " - Reloads configs");
    }

    private void sendAvailableActions(CommandSender sender, String[] args) {
        StringJoiner joiner = new StringJoiner(", ");

        for(String actionName : instance.getAfkManager().getActionParser().getActions().keySet()) {
            joiner.add(actionName);
        }

        sender.sendMessage("Available Actions: "+joiner.toString());
    }

    private void set(CommandSender sender, String[] args) {
        // Check if not a player
        if(!(sender instanceof Player)) {
            sender.sendMessage(instance.getAfkManager().parse(instance.getConfigs().getAfkConfig().getAfkConfig().getString("messages.mustBeAPlayer")));
            return;
        }

        Player player = (Player) sender;

        if(args[1].equalsIgnoreCase("afkarea")) {
            instance.getConfigs().getAfkConfig().getAfkConfig().set("afkLocation", LocationHelper.serialize(player.getLocation()));
            instance.getConfigs().getAfkConfig().saveConfig();

            player.sendMessage(instance.getAfkManager().parse(player, instance.getConfigs().getAfkConfig().getAfkConfig().getString("messages.afkAreaSet")));
        } else {
            player.sendMessage(instance.getAfkManager().parse(player, instance.getConfigs().getAfkConfig().getAfkConfig().getString("messages.invalidUsage")));
        }
    }

    private void reloadPlugin(CommandSender sender) {
        sender.sendMessage(instance.getAfkManager().parse(instance.getConfigs().getAfkConfig().getAfkConfig().getString("messages.reloading")));
        //Reload Here
        sender.sendMessage(instance.getAfkManager().parse(instance.getConfigs().getAfkConfig().getAfkConfig().getString("messages.reloaded")));
    }
}
