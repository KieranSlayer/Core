package net.gothammc.networkcore.ess.core.main;

import net.md_5.bungee.api.plugin.Plugin;

public final class EssentialsProxy extends Plugin {

    private EssentialsProxy instance;

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public EssentialsProxy getProxyInstance() {
        return instance;
    }
}
