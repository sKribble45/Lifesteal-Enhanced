package org.sKribble.lifestealEnhanced.recipies

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ShapedRecipe
import org.sKribble.lifestealEnhanced.PluginManager.plugin
import org.sKribble.lifestealEnhanced.items.HeartItem

class HeartRecipe : ShapedRecipe(NamespacedKey(plugin, "heart_recipe"), HeartItem()) {
    init {
        this.shape(
            "K K",
            "DND",
            "K K"
        )
        this.setIngredient('K', Material.OMINOUS_TRIAL_KEY)
        this.setIngredient('D', Material.DIAMOND)
        this.setIngredient('N', Material.NETHERITE_SCRAP)
    }
}