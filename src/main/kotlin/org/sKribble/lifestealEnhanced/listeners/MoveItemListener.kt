package org.sKribble.lifestealEnhanced.listeners

import com.sun.org.apache.xpath.internal.operations.Bool
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BundleMeta
import org.sKribble.lifestealEnhanced.ConfigManager
import org.sKribble.lifestealEnhanced.PluginManager.plugin
import org.sKribble.lifestealEnhanced.items.HeartItem

class MoveItemListener : Listener{
    val allowedInvs = ConfigManager.allowedInventories

    private fun isHeart(item: ItemStack?) : Boolean{
        item ?: return false
        return item.isSimilar(HeartItem())
    }
    private fun isBundle(item: ItemStack?): Boolean {
        return item != null && item.type.name.contains("BUNDLE")
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun onInventoryClick(event: InventoryClickEvent) {
        allowedInvs ?: return
        val topInventory = event.view.topInventory
        val clickedInventory = event.clickedInventory ?: return
        val currentItem = event.currentItem
        val cursorItem = event.cursor

        // moving the item into a non-player inventory with shift click
        if (event.isShiftClick && isHeart(currentItem)) {
            if (topInventory.type !in allowedInvs) {
                event.isCancelled = true
            }
        }

        // swapping the item into an external inventory
        if (clickedInventory.type !in allowedInvs) {
            if (isHeart(cursorItem) || isHeart(currentItem)) {
                event.isCancelled = true
            }
        }

        // prevent bundles
        if (event.isLeftClick && (isBundle(cursorItem) && isHeart(currentItem) || (isBundle(currentItem) && isHeart(cursorItem)))){
            event.isCancelled = true
        }

    }

    @EventHandler
    fun onInventoryDrag(event: InventoryDragEvent){
        allowedInvs ?: return
        plugin.logger.info("hi event.inventory.type: ${event.inventory.type} event.newItems: ${event.newItems} event.rawSlots: ${event.rawSlots} event.cursor: ${event.cursor} event.oldCursor: ${event.oldCursor}")
        if (event.inventory.type !in allowedInvs && (isHeart(event.cursor) || isHeart(event.oldCursor))){
            plugin.logger.info("hi23")
            event.isCancelled = true
        }
    }
}