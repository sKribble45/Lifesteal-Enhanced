package org.sKribble.lifestealEnhanced.listeners

import org.bukkit.entity.Item
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.ItemDespawnEvent
import org.sKribble.lifestealEnhanced.ConfigManager
import org.sKribble.lifestealEnhanced.PluginManager.plugin
import org.sKribble.lifestealEnhanced.items.HeartItem

class ItemDestructionListener : Listener {
    @EventHandler
    fun itemDespawnEvent(event: ItemDespawnEvent){
        val item = event.entity.itemStack
        if (item.isSimilar(HeartItem()) && ConfigManager.heartProtectionNum == 2){
            event.isCancelled = true
        }
    }

    @EventHandler
    fun entityDeathEvent(event: EntityDamageEvent){
        val entity = event.entity
        if (entity is Item){
            val item = entity.itemStack
            if (item.isSimilar(HeartItem()) && ConfigManager.heartProtectionNum >= 1) {
                event.isCancelled = true
            }
        }
    }
}