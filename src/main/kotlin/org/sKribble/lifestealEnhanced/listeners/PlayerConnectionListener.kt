package org.sKribble.lifestealEnhanced.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.sKribble.lifestealEnhanced.ConfigManager

class PlayerConnectionListener : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent){
        val player = event.player
        if (ConfigManager.reviveRecipe != null) player.discoverRecipe(ConfigManager.reviveRecipe!!.key)
        if (ConfigManager.heartRecipe != null) player.discoverRecipe(ConfigManager.heartRecipe!!.key)
    }
}