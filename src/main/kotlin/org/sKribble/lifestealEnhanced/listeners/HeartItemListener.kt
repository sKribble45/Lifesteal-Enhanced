package org.sKribble.lifestealEnhanced.listeners

import changeHearts
import getHeartsExtensive
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.block.Block
import org.bukkit.entity.EntityType
import org.bukkit.entity.Item
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityRemoveEvent
import org.bukkit.event.entity.ItemDespawnEvent
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.CraftItemEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryPickupItemEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector
import org.sKribble.lifestealEnhanced.ConfigManager
import org.sKribble.lifestealEnhanced.PluginManager.plugin
import org.sKribble.lifestealEnhanced.items.HeartItem

class HeartItemListener : Listener {
    private fun isShelf(block: Block) : Boolean{
        return block.type.name.endsWith("_SHELF")
    }

    //---------------------- Interaction ----------------------
    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent){
        val player = event.player
        if (event.item !is ItemStack) return
        val item = event.item!!
        if (item.isSimilar(HeartItem()) && event.action.isRightClick){
            if (player.changeHearts(1.0)){
                item.amount -= 1
            }
            else{
                player.sendActionBar(
                    Component.text("Cannot absorb, maximum hearts reached")
                        .color(TextColor.color(252, 65, 71))
                )
            }
            // prevent putting heart into shelf or pot
            if (!ConfigManager.storeHeartAllContainers) event.isCancelled = true
        }
    }

    @EventHandler
    fun disablePoweredShelves(event: PlayerInteractEvent){
        val player = event.player
        if (!ConfigManager.storeHeartAllContainers && event.action.isRightClick) {
            // Prevent powered shelves
            val hotbar: List<ItemStack?> =
                (0..8).map { player.inventory.getItem(it) }
            val hotbarContainsHeart = hotbar.any { it?.isSimilar(HeartItem()) == true }
            val block = event.clickedBlock ?: return
            plugin.logger.info("contains heart: $hotbarContainsHeart is shelf: ${isShelf(block)} is powered ${block.blockPower}")
            if (isShelf(block) && block.blockPower > 0 && hotbarContainsHeart)
                event.isCancelled = true
        }
    }

    @EventHandler
    fun onPlayerInteractEntity(event: PlayerInteractEntityEvent) {
        val entity = event.rightClicked
        val item = event.player.inventory.getItem(event.hand)
        // Prevent putting heart into item frame
        if (item.isSimilar(HeartItem()) && (entity.type == EntityType.ITEM_FRAME || entity.type == EntityType.GLOW_ITEM_FRAME) && !ConfigManager.storeHeartAllContainers) event.isCancelled = true
    }


    //---------------------- Pickup ---------------------
    @EventHandler
    fun onItemPickup(event: InventoryPickupItemEvent){
        val item = event.item.itemStack
        val inventory = event.inventory
        if (item.isSimilar(HeartItem()) && inventory.type != InventoryType.PLAYER && !ConfigManager.storeHeartAllContainers) event.isCancelled = true
    }


    //---------------------- Inventory ---------------------

    val allowedInvs =
        if (ConfigManager.storeHeartEchest)
            listOf(InventoryType.WORKBENCH, InventoryType.CRAFTING, InventoryType.PLAYER, InventoryType.ENDER_CHEST)
        else listOf(InventoryType.WORKBENCH, InventoryType.CRAFTING, InventoryType.PLAYER)

    private fun isHeart(item: ItemStack?) : Boolean{
        item ?: return false
        return item.isSimilar(HeartItem())
    }
    private fun isBundle(item: ItemStack?): Boolean {
        return item != null && item.type.name.contains("BUNDLE")
    }
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (ConfigManager.storeHeartAllContainers) return
        val topInventory = event.view.topInventory
        val clickedInventory = event.clickedInventory ?: return
        val currentItem = event.currentItem
        val cursorItem = event.cursor

        // moving the item into a non-player inventory with shift click
        if (event.isShiftClick && isHeart(currentItem)) {
            if (topInventory.type !in allowedInvs) {
                event.isCancelled = true
            }
        }

        // swapping the item into an external inventory
        if (clickedInventory.type !in allowedInvs) {
            if (isHeart(cursorItem) || isHeart(currentItem)) {
                event.isCancelled = true
            }
        }

        // prevent bundles
        if (event.isLeftClick && (isBundle(cursorItem) && isHeart(currentItem) || (isBundle(currentItem) && isHeart(cursorItem)))){
            event.isCancelled = true
        }

        // Prevent using num keys to transfer hearts to containers
        if (event.click == ClickType.NUMBER_KEY) {
            val player = event.whoClicked as? Player
            if (player != null){
                if (isHeart(player.inventory.getItem(event.hotbarButton)) && topInventory.type !in allowedInvs)
                    event.isCancelled = true
            }
        }

    }
    @EventHandler
    fun onInventoryDrag(event: InventoryDragEvent){
        if (ConfigManager.storeHeartAllContainers) return
        if (event.inventory.type !in allowedInvs && (isHeart(event.cursor) || isHeart(event.oldCursor))){
            event.isCancelled = true
        }
    }


    //---------------------- Destruction ---------------------
    @EventHandler
    fun itemDespawnEvent(event: ItemDespawnEvent){
        val item = event.entity.itemStack
        if (item.isSimilar(HeartItem()) && ConfigManager.heartDespawnImmunity){
            event.isCancelled = true
        }
    }

    @EventHandler
    fun entityDamageEvent(event: EntityDamageEvent){
        val entity = event.entity
        val damageCause = event.cause
        if (entity is Item){
            val item = entity.itemStack
            if (item.isSimilar(HeartItem())) {
                if (ConfigManager.heartDamageImmunity) {
                    event.isCancelled = true
                }
            }
        }
    }

    @EventHandler
    fun onEntityRemove(event: EntityRemoveEvent){
        if (!ConfigManager.heartVoidImmunity) return
        val droppedItem = event.entity as? Item ?: return
        if (event.cause == EntityRemoveEvent.Cause.OUT_OF_WORLD && isHeart(droppedItem.itemStack)) {
            val itemLocation = droppedItem.location.clone()
            itemLocation.y = 1.0
            val newItem = droppedItem.world.dropItem(itemLocation, HeartItem())
            newItem.setGravity(false)
            newItem.velocity = Vector(0, 0, 0)
            newItem.isGlowing = true
            newItem.isUnlimitedLifetime = true
        }
    }

    //---------------------- Crafting ---------------------
    @EventHandler
    fun onCraftItem(event: CraftItemEvent) {
        if (!ConfigManager.heartCraftingRestriction) return
        if (event.recipe.result.isSimilar(HeartItem())){
            val player = event.whoClicked as? Player ?: return
            // Disable crafting the item if hearts is above maximum
            if (player.getHeartsExtensive() >= ConfigManager.heartCraftingRestrictionMaxHearts){
                player.sendActionBar(Component.text("You have too many hearts to craft one.").color(TextColor.color(252, 65, 71)))
                event.isCancelled = true
            }
        }
    }

    //---------------------- Drop ---------------------
    @EventHandler
    fun itemDropEvent(event: PlayerDropItemEvent){
        val item = event.itemDrop
        if (isHeart(item.itemStack))
            item.isGlowing = ConfigManager.glowingHearts
    }
}