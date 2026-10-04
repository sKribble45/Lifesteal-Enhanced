package org.sKribble.lifestealEnhanced

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

fun removeItemFromInv(inv : Inventory, item: ItemStack, amount: Int){
    val invIterator = inv.iterator()
    while (invIterator.hasNext()) {
        val itemItr = invIterator.next()
        if (itemItr.isSimilar(item)) {
            itemItr.amount -= amount
            break
        }
    }
}

fun countItemsInInv(inv : Inventory, item: ItemStack) : Int{
    var amount = 0
    val invIterator = inv.iterator()
    while (invIterator.hasNext()) {
        val itemItr = invIterator.next()
        if (itemItr.isSimilar(item)) amount += 1
    }
    return amount
}