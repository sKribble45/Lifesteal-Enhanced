package org.sKribble.lifestealEnhanced.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.sKribble.lifestealEnhanced.ConfigManager
import org.sKribble.lifestealEnhanced.guis.ReviveGui
import org.sKribble.lifestealEnhanced.items.ReviveItem

class ReviveItemListener : Listener {
    //---------------------- Interaction ----------------------
    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent){
        if (!ConfigManager.reviveEnabled) return
        val player = event.player
        if (event.item !is ItemStack) return
        val item = event.item!!
        if (item.isSimilar(ReviveItem()) && event.action.isRightClick){
            player.openInventory(ReviveGui().inventory)
        }
    }
}