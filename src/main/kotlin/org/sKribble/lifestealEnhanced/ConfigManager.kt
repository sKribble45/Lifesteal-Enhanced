package org.sKribble.lifestealEnhanced

import org.bukkit.NamespacedKey
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.inventory.CraftingRecipe
import org.bukkit.plugin.Plugin
import org.sKribble.lifestealEnhanced.PluginManager.plugin
import org.sKribble.lifestealEnhanced.items.HeartItem
import org.sKribble.lifestealEnhanced.items.ReviveItem

object ConfigManager {
    private lateinit var config: FileConfiguration
    private val parser: ConfigParser = ConfigParser()

    fun init(plugin: Plugin) {
        config = plugin.config
    }
    val minHearts : Double
        get() = config.getDouble("minimum-hearts")
    val maxHearts : Double
        get() = config.getDouble("maximum-hearts")

    val banBelowMinHearts : Boolean
        get() = config.getBoolean("ban-bellow-min")

    // Revive Item
    val reviveEnabled : Boolean
        get() = config.getBoolean("items.revive.enabled")
    val revivedHearts : Double
        get() = config.getDouble("items.revive.revived-hearts")
    val reviveRecipe : CraftingRecipe?
        get() = parser.parseRecipe(
            config.getConfigurationSection("items.revive.recipe")
                ?: throw IllegalArgumentException("No 'items.revive.recipe' section"),
            NamespacedKey(plugin, "revive_recipe"),
            ReviveItem()
        )

    // Heart Item
    val heartItemEnabled : Boolean
        get() = config.getBoolean("items.heart.enabled")
    val glowingHearts : Boolean
        get() = config.getBoolean("items.heart.glowing")
    val dropHeartOnKill : String
        get() = config.getString("items.heart.drop-on-kill")
            ?: throw IllegalArgumentException("items.heart.drop-on-kill not present in config.yml")

    val storeHeartEchest : Boolean
        get() = config.getBoolean("items.heart.storable-locations.E-CHEST")
    val storeHeartAllContainers : Boolean
        get() = config.getBoolean("items.heart.storable-locations.ALL-CONTAINERS")

    val heartRemovalPriority : List<String>
        get() {
            val priority = config.getStringList("items.heart.heart-removal-priority")
            if (!priority.contains("BAR")) throw IllegalArgumentException("items.heart.heart-removal-priority needs to contain the 'BAR' section")
            return priority
        }

    val heartVoidImmunity : Boolean
        get() = config.getBoolean("items.heart.immunity.void-immunity")
    val heartDespawnImmunity : Boolean
        get() = config.getBoolean("items.heart.immunity.despawn-immunity")
    val heartDamageImmunity : Boolean
        get() = config.getBoolean("items.heart.immunity.damage-immunity")

    val heartRecipe : CraftingRecipe?
        get() = parser.parseRecipe(
            config.getConfigurationSection("items.heart.recipe")
                ?: throw IllegalArgumentException("No 'items.heart.recipe' section"),
            NamespacedKey(plugin, "heart_recipe"),
            HeartItem()
        )

    val heartCraftingRestriction : Boolean
        get() = config.getBoolean("items.heart.recipe.restrictions.enabled")
    val heartCraftingRestrictionMaxHearts : Double
        get() = config.getDouble("items.heart.recipe.restrictions.maximum-hearts")

    val giveHeartItemOnMax : Boolean
        get() = config.getBoolean("items.heart.give-item-on-max")
}