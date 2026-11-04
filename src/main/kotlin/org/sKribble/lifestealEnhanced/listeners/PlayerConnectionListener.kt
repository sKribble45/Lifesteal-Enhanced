package org.sKribble.lifestealEnhanced.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.sKribble.lifestealEnhanced.ConfigManager
import org.sKribble.lifestealEnhanced.recipies.ReviveRecipe

class PlayerConnectionListener : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent){
        val player = event.player
        ConfigManager.heartRecipes?.forEach { player.discoverRecipe(it.key) }
        if (ConfigManager.reviveItem) player.discoverRecipe(ReviveRecipe().key)
    }
}