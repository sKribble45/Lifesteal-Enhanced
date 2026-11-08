package org.sKribble.lifestealEnhanced

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.inventory.CraftingRecipe
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.RecipeChoice
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe
import org.sKribble.lifestealEnhanced.items.HeartItem
import org.sKribble.lifestealEnhanced.items.ReviveItem

class ConfigParser {
    fun parseRecipe(recipeConfig: ConfigurationSection, namespacedKey: NamespacedKey, result: ItemStack): CraftingRecipe?{
        if (!recipeConfig.getBoolean("enabled")) return null
        val type = recipeConfig.getString("type")

        var recipe : CraftingRecipe?
        when (type) {
            "SHAPED" -> {
                val shapeList: MutableList<String> = recipeConfig.getStringList("shape")
                if (shapeList.size != 3) throw IllegalArgumentException("Recipe shape too large/small")
                val ingredientsSection : ConfigurationSection = recipeConfig.getConfigurationSection("ingredients")
                    ?: throw IllegalArgumentException("Recipe 'ingredients' section not found.")


                // Check if all shape chars are defined in the ingredients section
                for (char in shapeList.joinToString(""))
                    if (!ingredientsSection.getKeys(false).contains(char.toString()) && char != ' ')
                        throw IllegalArgumentException("No ingredient to correspond with '$char' in recipe shape")

                val shapeRecipe = ShapedRecipe(namespacedKey, result)
                shapeRecipe.shape(
                    shapeList[0],
                    shapeList[1],
                    shapeList[2]
                )
                for (key : String in ingredientsSection.getKeys(false)){
                    val materialStr = ingredientsSection.getString(key) ?: throw IllegalArgumentException("No value for ingredient at key: '${key}'")
                    when (materialStr) {
                        "HEART" -> shapeRecipe.setIngredient(key.first(), RecipeChoice.exactChoice(HeartItem()))
                        "REVIVE" -> shapeRecipe.setIngredient(key.first(), RecipeChoice.exactChoice(ReviveItem()))
                        else -> shapeRecipe.setIngredient(key.first(), Material.matchMaterial(materialStr) ?: throw IllegalArgumentException("No item found for id: '${materialStr}'"))
                    }
                }
                recipe = shapeRecipe
            }
            "SHAPELESS" -> {
                val ingredientsSection : ConfigurationSection = recipeConfig.getConfigurationSection("ingredients")
                    ?: throw IllegalArgumentException("Recipe 'ingredients' section not found.")
                val shapelessRecipe = ShapelessRecipe(namespacedKey, result)

                for (materialStr in ingredientsSection.getKeys(false)){
                    val amount = ingredientsSection.getInt(materialStr)
                    when (materialStr) {
                        "HEART" -> {
                            val heartItem = HeartItem()
                            heartItem.amount = amount
                            shapelessRecipe.addIngredient(RecipeChoice.exactChoice(heartItem))
                        }
                        "REVIVE" -> {
                            val reviveItem = ReviveItem()
                            reviveItem.amount = amount
                            shapelessRecipe.addIngredient(RecipeChoice.exactChoice(reviveItem))
                        }
                        else -> {
                            val material = Material.matchMaterial(materialStr) ?: throw IllegalArgumentException("No item found for id: '${materialStr}'")
                            shapelessRecipe.addIngredient(ItemStack(material, amount))
                        }
                    }
                }
                recipe = shapelessRecipe
            }
            else -> {
                throw IllegalArgumentException("Unrecognised recipe type '$type'")
            }
        }
        return recipe
    }
}