package org.sKribble.lifestealEnhanced.commands

import changeHearts
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.sKribble.lifestealEnhanced.items.HeartItem
import kotlin.time.times

class WithdrawCommand(){
    fun createCommand() : LiteralCommandNode<CommandSourceStack>{
        val withdrawCommand = Commands.literal("withdraw")
            .requires { it.sender.hasPermission("lifesteal.command.withdraw") }
            .executes { context -> withdrawCommand(context, 1) }
            .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                .executes {context ->  withdrawCommand(context, IntegerArgumentType.getInteger(context, "amount")) }
            )
        return withdrawCommand.build()
    }
    fun withdrawCommand(context: CommandContext<CommandSourceStack>, amount: Int) : Int{
        val sender = context.source.sender
        if (sender !is Player) return 0

        if (!sender.changeHearts((amount * -1).toDouble())){
            sender.sendActionBar(Component.text("Cannot withdraw that many hearts.").color(TextColor.color(252, 65, 71)))
            return 0
        }
        else{ // If heart removal was successful
            val heartItemStack = HeartItem()
            heartItemStack.amount = amount
            sender.give(heartItemStack)
            return 1
        }
    }
}

