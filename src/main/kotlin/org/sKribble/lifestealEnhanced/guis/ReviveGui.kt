package org.sKribble.lifestealEnhanced.guis

import com.destroystokyo.paper.profile.PlayerProfile
import io.papermc.paper.ban.BanListType
import net.kyori.adventure.text.Component
import org.bukkit.BanEntry
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import org.sKribble.lifestealEnhanced.ConfigManager
import org.sKribble.lifestealEnhanced.PluginManager.plugin
import org.sKribble.lifestealEnhanced.items.ReviveItem
import org.sKribble.lifestealEnhanced.removeItemFromInv
import setHearts

class ReviveGui() : InventoryHolder{
    private var inventory: Inventory
    override fun getInventory(): Inventory {
        return this.inventory
    }

    init {
        val bannedPlayers : MutableList<PlayerProfile> = mutableListOf()
        val banList = Bukkit.getBanList(BanListType.PROFILE)
        banList.getEntries<BanEntry<PlayerProfile>>().forEach{
                entry -> if (entry.source == "org.sKribble.lifestealEnhanced") bannedPlayers.add(entry.banTarget)
        }
        inventory = plugin.server.createInventory(this, (bannedPlayers.size / 9+1) * 9, Component.text("Revive"))
        for (playerProfile in bannedPlayers){
            inventory.addItem(getSkull(playerProfile))
        }
    }

    private fun getSkull(playerProfile: PlayerProfile) : ItemStack{
        val skullItem = ItemStack(Material.PLAYER_HEAD)
        val skullMeta : SkullMeta = skullItem.itemMeta as SkullMeta
        playerProfile.complete(true)
        skullMeta.playerProfile = playerProfile
        skullItem.itemMeta = skullMeta
        return skullItem
    }

    fun onButtonClick(player : Player, item: ItemStack){
        val skullMeta : SkullMeta = item.itemMeta as? SkullMeta ?: return
        val revivedPlayerProfile = skullMeta.playerProfile ?: return
        val banList = Bukkit.getBanList(BanListType.PROFILE)
        banList.pardon(revivedPlayerProfile)
        revivedPlayerProfile.setHearts(ConfigManager.revivedHearts)
        player.sendMessage(Component.text("You revived ${revivedPlayerProfile.name}, they can now rejoin."))
        this.inventory.close()
        removeItemFromInv(player.inventory, ReviveItem(), 1)
    }
}