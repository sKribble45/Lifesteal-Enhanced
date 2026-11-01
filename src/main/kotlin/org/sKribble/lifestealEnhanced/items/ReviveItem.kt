package org.sKribble.lifestealEnhanced.items

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack

class ReviveItem : ItemStack(Material.HEART_OF_THE_SEA) {
    init {
        val meta = itemMeta

        meta.displayName(
            Component.text("Revive")
                .color(TextColor.color(252, 113, 118))
                .decoration(TextDecoration.BOLD, true)
                .decoration(TextDecoration.ITALIC, false)
        )

        val lore = listOf(
            Component.text("Right click revive someone.")

                .decoration(TextDecoration.ITALIC, false),
        )
        meta.lore(lore)
        meta.itemModel = NamespacedKey("lifesteal", "revive")

        itemMeta = meta
    }
}