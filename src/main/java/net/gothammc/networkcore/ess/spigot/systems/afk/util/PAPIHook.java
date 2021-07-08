package net.gothammc.networkcore.ess.spigot.systems.afk.util;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.gothammc.networkcore.ess.core.main.EssentialsSpigot;
import org.bukkit.entity.Player;

public class PAPIHook extends PlaceholderExpansion {
    private EssentialsSpigot instance;

    public PAPIHook(EssentialsSpigot instance) {
        this.instance = instance;
    }

    @Override
    public boolean persist(){
        return true;
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public String getAuthor(){
        return instance.getDescription().getAuthors().toString();
    }

    @Override
    public String getIdentifier(){
        return "afk";
    }

    @Override
    public String getVersion(){
        return instance.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier){

        if(player == null){
            return "";
        }

        if(identifier.equals("prefix")){
            return instance.getConfigs().getAfkConfig().getAfkConfig().getString("messages.prefix", "value doesnt exist");
        }

        return null;
    }
}
