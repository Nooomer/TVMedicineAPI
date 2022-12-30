package com.tvmedicine.plugins

import com.tvmedicine.utils.Connection
import io.ktor.websocket.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import java.time.*
import java.util.*
import kotlin.collections.LinkedHashSet

fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    routing {
       /* webSocket("/chat/{id}") {
            val connections = Collections.synchronizedSet<Connection?>(LinkedHashSet())
            if((call.parameters["id"])=="1") {// websocketSession
                    println("Adding user!")
                    val thisConnection = Connection(this)
                    connections += thisConnection
                    try {
                        send("You are connected to chat 1! There are ${connections.count()} users here.")
                        for (frame in incoming) {
                            frame as? Frame.Text ?: continue
                            val receivedText = frame.readText()
                            val textWithUsername = "[${thisConnection.name}]: $receivedText"
                            connections.forEach {
                                it.session.send(textWithUsername)
                            }
                        }
                    } catch (e: Exception) {
                        println(e.localizedMessage)
                    } finally {
                        println("Removing $thisConnection!")
                        connections -= thisConnection
                    }
                }
            else{
                println("Adding user!")
                val thisConnection = Connection(this)
                connections += thisConnection
                try {
                    send("You are connected to chat 2! There are ${connections.count()} users here.")
                    for (frame in incoming) {
                        frame as? Frame.Text ?: continue
                        val receivedText = frame.readText()
                        val textWithUsername = "[${thisConnection.name}]: $receivedText"
                        connections.forEach {
                            it.session.send(textWithUsername)
                        }
                    }
                } catch (e: Exception) {
                    println(e.localizedMessage)
                } finally {
                    println("Removing $thisConnection!")
                    connections -= thisConnection
                }
            }
            }*/
        }
}
