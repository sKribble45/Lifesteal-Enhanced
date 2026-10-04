package org.sKribble.lifestealEnhanced.commands

import changeHearts
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.LiteralCommandNode
import getHearts
import getHeartsExtensive
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver
import org.bukkit.entity.Player
import org.sKribble.lifestealEnhanced.PluginManager.plugin
import org.sKribble.lifestealEnhanced.items.HeartItem
import setHearts

class LifestealCommand {
    enum class HeartCommandType{
        ADD, REMOVE, SET, GET
    }

    fun createCommand(): LiteralCommandNode<CommandSourceStack> {
        val lifestealCommand = Commands.literal("lifesteal")
                .then(Commands.literal("hearts")
                    .requires { it.sender.hasPermission("lifesteal.command.sethearts") ||  it.sender.hasPermission("lifesteal.command.gethearts")}
                    .then(Commands.literal("add")
                        .requires { it.sender.hasPermission("lifesteal.command.sethearts") }
                        .then(Commands.argument("player", ArgumentTypes.player())
                            .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                                .executes { context -> heartCommand(context, HeartCommandType.ADD) })
                        )
                    )
                    .then(Commands.literal("remove")
                        .requires { it.sender.hasPermission("lifesteal.command.sethearts") }
                        .then(Commands.argument("player", ArgumentTypes.player())
                            .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                                .executes { context -> heartCommand(context, HeartCommandType.REMOVE) })
                        )
                    )
                    .then(Commands.literal("set")
                        .requires { it.sender.hasPermission("lifesteal.command.sethearts") }
                        .then(Commands.argument("player", ArgumentTypes.player())
                            .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                                .executes { context -> heartCommand(context, HeartCommandType.SET) })
                        )
                    )
                    .then(Commands.literal("get")
                        .requires { it.sender.hasPermission("lifesteal.command.gethearts") }
                        .then(Commands.argument("player", ArgumentTypes.player())
                            .executes { context -> heartCommand(context, HeartCommandType.GET) })
                    )
                )
                .then(Commands.literal("reload")
                    .requires { it.sender.hasPermission("lifesteal.command.reload") }
                    .executes { context -> reloadCommand(context) })
                .then(Commands.literal("give")
                    .requires { it.sender.hasPermission("lifesteal.command.give") }
                    .then(Commands.argument("player", ArgumentTypes.player())
                        .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                            .executes { context -> giveItemCommand(context) })
                    )
                )
        return lifestealCommand.build()!!
    }

    fun heartCommand(context : CommandContext<CommandSourceStack>, command : HeartCommandType) : Int{
        val sender = context.source.sender
        val player = context.getArgument("player", PlayerSelectorArgumentResolver::class.java)
            .resolve(context.source)
            .firstOrNull()
        if (player is Player){
            when (command) {
                HeartCommandType.ADD -> {
                    val amount = IntegerArgumentType.getInteger(context, "amount").toDouble()
                    player.changeHearts(amount)
                    sender.sendMessage("Added $amount heart(s) to ${player.name}, they are now on ${player.getHearts()}")
                    return 1
                }
                HeartCommandType.REMOVE -> {
                    val amount = IntegerArgumentType.getInteger(context, "amount").toDouble()
                    player.changeHearts((amount * -1))
                    sender.sendMessage("Removed $amount heart(s) from ${player.name}, they are now on ${player.getHearts()}")
                    return 1
                }
                HeartCommandType.SET -> {
                    val amount = IntegerArgumentType.getInteger(context, "amount").toDouble()
                    player.setHearts(amount)
                    sender.sendMessage("Set ${player.name}'s hearts to $amount")
                    return 1
                }
                HeartCommandType.GET -> {
                    val sender = context.source.sender
                    sender.sendMessage("${player.name} has ${player.getHearts()} hearts (on health metre) with ${player.getHeartsExtensive()} in total")
                    return 1
                }
            }
        }
        return 0
    }
    fun reloadCommand(context: CommandContext<CommandSourceStack>) : Int{
        plugin.reloadConfig()
        context.source.sender.sendMessage("Config reloaded!")
        return 1
    }
    fun giveItemCommand(context: CommandContext<CommandSourceStack>) : Int{
        val player = context.getArgument("player", PlayerSelectorArgumentResolver::class.java)
            .resolve(context.source)
            .firstOrNull()
        if (player is Player) {
            val heartStack = HeartItem()
            val amount = IntegerArgumentType.getInteger(context, "amount")
            heartStack.amount = amount
            player.give(heartStack)
            context.source.sender.sendMessage("Gave $amount heart(s) to ${player.name}")
            return 1
        }
        return 0
    }
}