package org.sKribble.lifestealEnhanced

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.sKribble.lifestealEnhanced.commands.*
import org.sKribble.lifestealEnhanced.listeners.*
import org.sKribble.lifestealEnhanced.listeners.HeartItemListener
import org.sKribble.lifestealEnhanced.listeners.ReviveItemListener


class LifestealEnhanced : JavaPlugin() {

    override fun onEnable() {
        saveDefaultConfig()
        PluginManager.init(this)
        ConfigManager.init(this)
        server.pluginManager.registerEvents(PlayerDeathListener(), this)
        server.pluginManager.registerEvents(PlayerConnectionListener(), this)
        server.pluginManager.registerEvents(HeartItemListener(), this)
        server.pluginManager.registerEvents(ReviveItemListener(), this)
        server.pluginManager.registerEvents(GuiListener(), this)


        lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS){ event ->
            event.registrar().register(LifestealCommand().createCommand())
            event.registrar().register(WithdrawCommand().createCommand())
        }

        // Register recipes
        ConfigManager.heartRecipes?.forEach { Bukkit.addRecipe(it) }
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
