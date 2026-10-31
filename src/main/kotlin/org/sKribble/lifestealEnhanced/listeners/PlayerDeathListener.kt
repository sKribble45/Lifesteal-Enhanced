package org.sKribble.lifestealEnhanced.listeners

import addHeartForce
import getHeartsExtensive
import io.papermc.paper.ban.BanListType
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.sKribble.lifestealEnhanced.ConfigManager
import removeHeartForce

class PlayerDeathListener() : Listener {
    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val player = event.player
        val killer = player.killer
        if (player.removeHeartForce()) {
            if (killer !== null) killer.addHeartForce()
        }
        else{
            killer?.sendActionBar(Component.text("You killed someone at minimum hearts, no heart received").color(TextColor.color(252, 65, 71)))
            if (ConfigManager.banBellowMinHearts) {
                val banList = Bukkit.getBanList(BanListType.PROFILE)
                banList.addBan(
                    player.playerProfile,
                    "You ran out of hearts, you have been banned",
                    null as java.time.Duration?,
                    "Server"
                )
                player.kick(Component.text("You ran out of hearts, you have been banned"))
            }
        }
    }
}