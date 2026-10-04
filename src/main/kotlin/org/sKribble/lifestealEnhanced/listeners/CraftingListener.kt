package org.sKribble.lifestealEnhanced.listeners

import getHeartsExtensive
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.CraftItemEvent
import org.sKribble.lifestealEnhanced.ConfigManager
import org.sKribble.lifestealEnhanced.items.HeartItem

class CraftingListener : Listener {
    @EventHandler
    fun onCraftItem(event: CraftItemEvent) {
        if (!ConfigManager.limitHeartCrafting) return
        if (event.recipe.result.isSimilar(HeartItem())){
            val player = event.whoClicked as? Player ?: return
            // Disable crafting the item if hearts is above maximum
            if (player.getHeartsExtensive() >= ConfigManager.maxCraftingHearts){
                player.sendActionBar(Component.text("You have too many hearts to craft one.").color(TextColor.color(252, 65, 71)))
                event.isCancelled = true
            }
        }
    }
}