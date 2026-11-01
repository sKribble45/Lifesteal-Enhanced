package org.sKribble.lifestealEnhanced.listeners

import changeHearts
import com.destroystokyo.paper.profile.PlayerProfile
import com.google.common.collect.ImmutableList
import io.papermc.paper.ban.BanListType
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.BanEntry
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.sKribble.lifestealEnhanced.ConfigManager
import org.sKribble.lifestealEnhanced.guis.ReviveGui
import org.sKribble.lifestealEnhanced.items.HeartItem
import org.sKribble.lifestealEnhanced.items.ReviveItem

class ReviveItemListener : Listener {
    //---------------------- Interaction ----------------------
    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent){
        val player = event.player
        if (event.item !is ItemStack) return
        val item = event.item!!
        if (item.isSimilar(ReviveItem()) && event.action.isRightClick){
            player.openInventory(ReviveGui().inventory)
        }
    }
}