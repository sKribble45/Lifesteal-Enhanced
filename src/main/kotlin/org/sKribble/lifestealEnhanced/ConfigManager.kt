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
        plugin.saveDefaultConfig()
        reloadConfig()
    }

    fun reloadConfig(){
        plugin.reloadConfig()
        config = plugin.config
        minHearts = config.getDouble("minimum-hearts")
        maxHearts = config.getDouble("maximum-hearts")
        banBelowMinHearts = config.getBoolean("ban-bellow-min")
        reviveEnabled = config.getBoolean("items.revive.enabled")
        revivedHearts = config.getDouble("items.revive.revived-hearts")
        reviveRecipe = parser.parseRecipe(
            config.getConfigurationSection("items.revive.recipe")
                ?: throw IllegalArgumentException("No 'items.revive.recipe' section"),
            NamespacedKey(plugin, "revive_recipe"),
            ReviveItem()
        )
        heartItemEnabled = config.getBoolean("items.heart.enabled")
        glowingHearts = config.getBoolean("items.heart.glowing")
        dropHeartOnKill = config.getString("items.heart.drop-on-kill")
            ?: throw IllegalArgumentException("items.heart.drop-on-kill not present in config.yml")
        storeHeartEchest = config.getBoolean("items.heart.storable-locations.E-CHEST")
        storeHeartAllContainers = config.getBoolean("items.heart.storable-locations.ALL-CONTAINERS")

        val priority = config.getStringList("items.heart.heart-removal-priority")
        if (!priority.contains("BAR")) throw IllegalArgumentException("items.heart.heart-removal-priority needs to contain the 'BAR' section")
        heartRemovalPriority = priority

        heartVoidImmunity = config.getBoolean("items.heart.immunity.void-immunity")
        heartDespawnImmunity = config.getBoolean("items.heart.immunity.despawn-immunity")
        heartDamageImmunity = config.getBoolean("items.heart.immunity.damage-immunity")
        heartRecipe = parser.parseRecipe(
            config.getConfigurationSection("items.heart.recipe")
                ?: throw IllegalArgumentException("No 'items.heart.recipe' section"),
            NamespacedKey(plugin, "heart_recipe"),
            HeartItem()
        )
        heartCraftingRestriction = config.getBoolean("items.heart.recipe.restrictions.enabled")
        heartCraftingRestrictionMaxHearts = config.getDouble("items.heart.recipe.restrictions.maximum-hearts")
        giveHeartItemOnMax = config.getBoolean("items.heart.give-item-on-max")
    }

    var minHearts : Double = 0.0
        private set
    var maxHearts : Double = 0.0
        private set

    var banBelowMinHearts : Boolean = false
        private set

    // Revive Item
    var reviveEnabled : Boolean = false
        private set
    var revivedHearts : Double = 0.0
        private set
    var reviveRecipe : CraftingRecipe? = null
        private set

    // Heart Item
    var heartItemEnabled : Boolean = false
        private set
    var glowingHearts : Boolean = false
        private set
    var dropHeartOnKill : String = ""
        private set

    var storeHeartEchest : Boolean = false
        private set
    var storeHeartAllContainers : Boolean = false
        private set

    var heartRemovalPriority : List<String> = emptyList()

    var heartVoidImmunity : Boolean = false
        private set
    var heartDespawnImmunity : Boolean = false
        private set
    var heartDamageImmunity : Boolean = false
        private set

    var heartRecipe : CraftingRecipe? = null
        private set

    var heartCraftingRestriction : Boolean = false
        private set
    var heartCraftingRestrictionMaxHearts : Double = 0.0
        private set

    var giveHeartItemOnMax : Boolean = false
        private set
}