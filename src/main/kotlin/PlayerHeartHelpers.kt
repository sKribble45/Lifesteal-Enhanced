import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeInstance
import org.bukkit.entity.Player
import org.sKribble.lifestealEnhanced.ConfigManager
import org.sKribble.lifestealEnhanced.countItemsInInv
import org.sKribble.lifestealEnhanced.items.HeartItem
import org.sKribble.lifestealEnhanced.removeItemFromInv

fun Player.changeHearts(amount: Double) : Boolean{
    val healthAttribute : AttributeInstance = this.getAttribute(Attribute.MAX_HEALTH)!!
    val initialHearts = healthAttribute.baseValue / 2
    val modifiedHearts =  initialHearts + amount
    if ((modifiedHearts < ConfigManager.minHearts && amount < 0) || (modifiedHearts > ConfigManager.maxHearts && amount > 0)) return false
    healthAttribute.baseValue = (initialHearts + amount)*2
    return true
}

fun Player.setHearts(amount: Double) : Boolean{
    val healthAttribute : AttributeInstance = this.getAttribute(Attribute.MAX_HEALTH)!!
    healthAttribute.baseValue = amount * 2
    return !(amount < ConfigManager.minHearts || amount > ConfigManager.maxHearts)
}

fun Player.getHearts() : Double{
    val healthAttribute : AttributeInstance = this.getAttribute(Attribute.MAX_HEALTH)!!
    return healthAttribute.baseValue / 2
}

/**
 * Gets the number of hearts the player has combined with the number in their enderchest and inventory (depending on if "heart-count-include" is set to All)
 * This function acts like Player.getHearts whilst considering the config value "heart-count-include"
 */
fun Player.getHeartsExtensive() : Double{
    val inventoryHearts = countItemsInInv(this.inventory, HeartItem())
    val echestHearts = countItemsInInv(this.enderChest, HeartItem())
    return this.getHearts() + inventoryHearts + echestHearts
}

/**
 * If heat removal fails, look in the inventory for heart items and remove one if found
 */
fun Player.removeHeartForce(): Boolean {

    for (removalMethod in ConfigManager.heartRemovalPriority){
        when (removalMethod){
            "BAR" -> {
                val success = this.changeHearts(-1.0)
                if (success) return true
                else continue
            }
            "INV" -> {
                if (this.inventory.containsAtLeast(HeartItem(), 1)) {
                    // Remove heart from inventory
                    removeItemFromInv(this.inventory, HeartItem(), 1)
                    return true
                }
                else continue
            }
            "E-CHEST" -> {
                if (ConfigManager.heartRemovalPriority.contains("E-CHEST") && this.enderChest.containsAtLeast(HeartItem(), 1)){
                    // Remove item from ender chest
                    removeItemFromInv(this.enderChest, HeartItem(), 1)
                    return true
                }
                else continue
            }
        }
    }

    this.sendActionBar(Component.text("You are at the minimum, no hearts lost.").color(TextColor.color(252, 65, 71)))
    return false
}

/**
 * If add heart fails, gives player heart item. If that fails, drops heart item on the ground
 */
fun Player.addHeartForce(): Boolean {
    val success = this.changeHearts(1.0)
    if (success) return true


    // Add heart to inventory
    if (ConfigManager.giveHeartItemOnMax) {
        this.give(HeartItem())
        return true
    }

    this.sendActionBar(Component.text("Maximum reached, you cannot gain more hearts.").color(TextColor.color(252, 65, 71)))
    return false
}