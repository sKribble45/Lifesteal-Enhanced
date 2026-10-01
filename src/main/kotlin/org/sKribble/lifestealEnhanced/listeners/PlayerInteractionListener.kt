package org.sKribble.lifestealEnhanced.listeners

import changeHearts
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.sKribble.lifestealEnhanced.ConfigManager
import org.sKribble.lifestealEnhanced.PluginManager.plugin
import org.sKribble.lifestealEnhanced.items.HeartItem

class PlayerInteractionListener : Listener {
    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent){
        val player = event.player
        if (event.item !is ItemStack) return
        val item = event.item!!
        if (item.isSimilar(HeartItem()) && event.action.isRightClick){
            if (player.changeHearts(1.0)){
                item.amount -= 1
            }
            else{
                player.sendActionBar(
                    Component.text("Cannot absorb, maximum hearts reached")
                        .color(TextColor.color(252, 65, 71))
                )
            }
            // prevent putting heart into shelf or pot
            if (ConfigManager.allowedInventories != null) event.isCancelled = true
        }
    }

    @EventHandler
    fun onPlayerInteractEntity(event: PlayerInteractEntityEvent) {
        val entity = event.rightClicked
        val item = event.player.inventory.getItem(event.hand)
        // Prevent putting heart into item frame
        if (item.isSimilar(HeartItem()) && (entity.type == EntityType.ITEM_FRAME || entity.type == EntityType.GLOW_ITEM_FRAME) && ConfigManager.allowedInventories != null) event.isCancelled = true
    }
}