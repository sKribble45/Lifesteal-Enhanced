package org.sKribble.lifestealEnhanced.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.sKribble.lifestealEnhanced.ConfigManager

class PlayerConnectionListener : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent){
        val player = event.player
        ConfigManager.heartRecipes?.forEach { player.discoverRecipe(it.key) }
    }
}