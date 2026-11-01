package org.sKribble.lifestealEnhanced.listeners

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.sKribble.lifestealEnhanced.PluginManager.plugin
import org.sKribble.lifestealEnhanced.guis.ReviveGui

class GuiListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val player = event.whoClicked as? Player ?: return
        val inv = event.clickedInventory ?: return
        val gui = inv.holder as? ReviveGui ?: return
        plugin.logger.info("currentItem: ${event.currentItem}")
        gui.onButtonClick(player, event.currentItem ?: return)
        event.isCancelled = true
    }
}