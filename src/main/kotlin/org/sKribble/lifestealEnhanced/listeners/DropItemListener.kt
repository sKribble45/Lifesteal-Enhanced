package org.sKribble.lifestealEnhanced.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerDropItemEvent
import org.sKribble.lifestealEnhanced.ConfigManager

class DropItemListener : Listener {
    @EventHandler
    fun itemDropEvent(event: PlayerDropItemEvent){
        val itemEntity = event.itemDrop
        itemEntity.isGlowing = ConfigManager.glowingHearts
    }
}