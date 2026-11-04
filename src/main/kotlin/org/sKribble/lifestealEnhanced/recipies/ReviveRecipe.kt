package org.sKribble.lifestealEnhanced.recipies

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.RecipeChoice
import org.bukkit.inventory.ShapedRecipe
import org.sKribble.lifestealEnhanced.PluginManager.plugin
import org.sKribble.lifestealEnhanced.items.HeartItem
import org.sKribble.lifestealEnhanced.items.ReviveItem

class ReviveRecipe : ShapedRecipe(NamespacedKey(plugin, "revive_recipe"), ReviveItem()) {
    init {
        this.shape(
            "NHN",
            "HDH",
            "NHN"
        )
        this.setIngredient('D', Material.DIAMOND_BLOCK)
        this.setIngredient('N', Material.NETHERITE_SCRAP)
        this.setIngredient('H', RecipeChoice.exactChoice(HeartItem()))

    }
}