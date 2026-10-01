package org.sKribble.lifestealEnhanced

import org.bukkit.plugin.Plugin
import java.util.logging.Logger

object PluginManager {
    public lateinit var plugin : Plugin
    fun init(pl : Plugin) {
        this.plugin = pl
    }
}