package org.sKribble.lifestealEnhanced.recipies

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ShapedRecipe
import org.sKribble.lifestealEnhanced.PluginManager.plugin
import org.sKribble.lifestealEnhanced.items.HeartItem

class AltHeartRecipe : ShapedRecipe(NamespacedKey(plugin, "alt_heart_recipe"), HeartItem()) {
    init {
        this.shape(
            "T T",
            "DND",
            "T T"
        )
        this.setIngredient('T', Material.TOTEM_OF_UNDYING)
        this.setIngredient('D', Material.DIAMOND)
        this.setIngredient('N', Material.NETHERITE_SCRAP)
    }
}