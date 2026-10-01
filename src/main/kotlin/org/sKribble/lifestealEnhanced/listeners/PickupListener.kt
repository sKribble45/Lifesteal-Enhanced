package org.sKribble.lifestealEnhanced.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryPickupItemEvent
import org.bukkit.event.inventory.InventoryType
import org.sKribble.lifestealEnhanced.ConfigManager
import org.sKribble.lifestealEnhanced.PluginManager.plugin
import org.sKribble.lifestealEnhanced.items.HeartItem

class PickupListener : Listener {
    @EventHandler
    fun onItemPickup(event: InventoryPickupItemEvent){
        val item = event.item.itemStack
        val inventory = event.inventory
        if (item.isSimilar(HeartItem()) && inventory.type != InventoryType.PLAYER && ConfigManager.allowedInventories != null) event.isCancelled = true
    }
}