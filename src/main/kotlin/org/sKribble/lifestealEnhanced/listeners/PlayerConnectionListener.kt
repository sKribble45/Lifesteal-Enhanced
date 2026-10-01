package org.sKribble.lifestealEnhanced.listeners

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.sKribble.lifestealEnhanced.ConfigManager
import org.sKribble.lifestealEnhanced.PluginManager.plugin
import org.sKribble.lifestealEnhanced.recipies.AltHeartRecipe
import org.sKribble.lifestealEnhanced.recipies.HeartRecipe

class PlayerConnectionListener : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent){
        val player = event.player
        ConfigManager.heartRecipes?.forEach { player.discoverRecipe(it.key) }
    }
}