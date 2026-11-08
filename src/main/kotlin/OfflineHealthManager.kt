import com.destroystokyo.paper.profile.PlayerProfile
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import java.util.UUID

val pendingHealth : MutableMap<UUID, Double> = mutableMapOf()

class OfflineHealthListener() : Listener{
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent){
        val player = event.player
        if (pendingHealth.keys.contains(player.uniqueId)){
            val newHealth = pendingHealth[player.uniqueId] ?: return
            player.setHearts(newHealth)
        }
    }
}

fun PlayerProfile.setHearts(amount: Double){
    val uuid = this.id ?: return
    pendingHealth[uuid] = amount
}