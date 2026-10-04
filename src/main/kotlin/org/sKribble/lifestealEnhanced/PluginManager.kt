package org.sKribble.lifestealEnhanced

import org.bukkit.plugin.Plugin

object PluginManager {
    public lateinit var plugin : Plugin
    fun init(pl : Plugin) {
        this.plugin = pl
    }
}