/*
    NametagCreaking Plugin © 2024 by raffel080108 is licensed under Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/
*/

package raffel080108.nametagCreaking;

import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.packs.DataPackManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import raffel080108.nametagCreaking.listener.PlayerInteractEntityListener;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public final class NametagCreaking extends JavaPlugin {
    private static NametagCreaking instance;
    private List<World> enabledWorlds = new ArrayList<>();
    
    @Override
    public void onEnable() {
        instance = this;

        Logger log = getLogger();

        Server server = getServer();
        DataPackManager dataPackManager = server.getDataPackManager();
        for (World world : server.getWorlds()) {
            if (dataPackManager.isEnabledByFeature(EntityType.CREAKING, world) && dataPackManager.isEnabledByFeature(EntityType.CREAKING_TRANSIENT, world)) {
                enabledWorlds.add(world);
            } else {
                log.warning("World \"" + world.getName() + "\" does not have the required features enabled to support use of this plugin");
            }
        }

        PluginManager pluginManager = server.getPluginManager();
        pluginManager.registerEvents(new PlayerInteractEntityListener(), this);

        log.info("Startup Complete");
    }

    @Override
    public void onDisable() {
        getLogger().info("Shutdown Complete");
    }

    public static NametagCreaking getInstance() {
        return instance;
    }

    public List<World> getEnabledWorlds() {
        return enabledWorlds;
    }
}
