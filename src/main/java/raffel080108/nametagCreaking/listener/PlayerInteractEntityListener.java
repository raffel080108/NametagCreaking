/*
    NametagCreaking Plugin © 2024 by raffel080108 is licensed under Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/
*/

package raffel080108.nametagCreaking.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import raffel080108.nametagCreaking.NametagCreaking;

import java.util.logging.Logger;

public class PlayerInteractEntityListener implements Listener {
    NametagCreaking mainInstance = NametagCreaking.getInstance();

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        if (!mainInstance.getEnabledWorlds().contains(world)) {
            return;
        }

        Entity clickedEntity = event.getRightClicked();
        if (clickedEntity.getType() != EntityType.CREAKING_TRANSIENT) {
            return;
        }

        PlayerInventory inventory = player.getInventory();
        ItemStack heldItem = inventory.getItemInMainHand();
        if (heldItem.getType() != Material.NAME_TAG) {
            return;
        }

        ItemMeta itemMeta = heldItem.getItemMeta();
        if (itemMeta == null) {
            return;
        }

        Location location = clickedEntity.getLocation();
        Logger log = mainInstance.getLogger();

        clickedEntity.remove();
        try {
            world.spawn(location, Creaking.class, (newEntity) -> newEntity.setCustomName(itemMeta.getDisplayName()));
        } catch (IllegalArgumentException e) {
            log.warning("Unable to spawn Creaking entity at Location " + location.getX() + ", " + location.getY() + ", " + location.getZ());
            return;
        }

        heldItem.setAmount(heldItem.getAmount() - 1);
    }
}
