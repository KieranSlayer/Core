package net.gothammc.networkcore.ess.spigot.commands.admin;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

@CommandAlias("fly")
@Description("This is for Flying and setting fly speed")
public class FlyCommand extends BaseCommand
{
    private String onFlyPermission = "gothammc.admin.command.ess.fly";
    private String onFlyOtherPermission = "gothammc.admin.command.ess.fly.other";

    @Default
    public void onFly(CommandSender sender, String[] args)
    {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;
            if (args.length == 0)
            {
                if (player.hasPermission(onFlyPermission))
                {
                    if (!player.isFlying())
                    {
                        //Message Header

                        //Message Body

                        //Message footer

                        //Player Actions
                        player.setAllowFlight(true);
                    }
                    else
                    {
                        //Message Header

                        //Message Body

                        //Message footer

                        //Player Actions
                        player.setAllowFlight(false);
                    }

                }
            }
        }
        else if (sender instanceof ConsoleCommandSender)
        {

        }
    }

}
