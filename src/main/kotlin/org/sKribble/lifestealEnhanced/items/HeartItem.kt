package org.sKribble.lifestealEnhanced.items

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack

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

