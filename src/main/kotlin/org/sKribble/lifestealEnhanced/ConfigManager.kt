package org.sKribble.lifestealEnhanced

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.plugin.Plugin
import org.sKribble.lifestealEnhanced.recipies.AltHeartRecipe
import org.sKribble.lifestealEnhanced.recipies.HeartRecipe

object ConfigManager {
    private lateinit var config: FileConfiguration

    fun init(plugin: Plugin) {
        config = plugin.config
    }

    val minHearts: Double
        get() = config.getDouble("minimum-hearts")

    val maxHearts: Double
        get() = config.getDouble("maximum-hearts")

    val heartItemReward: Boolean
        get() = config.getBoolean("reward-heart-item")

    val takeEchestHearts: Boolean
        get() = config.getString("take-hearts") == "All"

    val takeInvHearts: Boolean
        get() = config.getString("take-hearts") == "Inv" || config.getString("take-hearts") == "All"

    val allowedInventories: List<InventoryType>?
        get() = when (config.getString("allowed-inventories")) {
            "EChest" -> listOf(InventoryType.CRAFTING, InventoryType.PLAYER, InventoryType.ENDER_CHEST)
            "Player" -> listOf(InventoryType.CRAFTING, InventoryType.PLAYER)
            else -> null
        }

    val heartRecipes: List<ShapedRecipe>?
        get() = when (config.getString("recipes-enabled")) {
            "Keys" -> listOf(HeartRecipe())
            "Totems" -> listOf(AltHeartRecipe())
            "Both" -> listOf(HeartRecipe(), AltHeartRecipe())
            else -> null
        }

    val limitHeartCrafting: Boolean
        get() = config.getBoolean("limit-heart-crafting")

    val maxCraftingHearts: Double
        get() = config.getDouble("max-heart-craft-hearts")

    val countEchestHearts: Boolean
        get() = config.getString("heart-count-include") == "All"

    val countInvHearts: Boolean
        get() = config.getString("heart-count-include") == "Inv" || config.getString("take-hearts") == "All"

    val heartProtectionNum: Int
        get() = when (config.getString("indestructible-hearts")) {
            "Complete" -> 2
            "Damage" -> 1
            else -> 0
        }

    val glowingHearts: Boolean
        get() = config.getBoolean("glowing-hearts")
}