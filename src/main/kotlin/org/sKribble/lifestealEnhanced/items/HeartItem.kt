package org.sKribble.lifestealEnhanced.items

import changeHearts
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.EntityType
import org.bukkit.entity.Item
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.ItemDespawnEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryPickupItemEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.sKribble.lifestealEnhanced.ConfigManager

class HeartItem : ItemStack(Material.NETHER_STAR) {
    init {
        val meta = itemMeta

        meta.displayName(
            Component.text("Heart")
                .color(TextColor.color(252, 113, 118))
                .decoration(TextDecoration.BOLD, true)
                .decoration(TextDecoration.ITALIC, false)
        )

        val lore = listOf(
            Component.text("Right click to absorb.")

                .decoration(TextDecoration.ITALIC, false),
        )
        meta.lore(lore)
        meta.itemModel = NamespacedKey("lifesteal", "heart")

        itemMeta = meta
    }
}

