package org.example.andrealexandre

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.gson.*
import io.ktor.features.*
import io.ktor.util.url
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)


@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    val db: MutableMap<UUID, Order> = HashMap()

    routing {
        route("/orders") {
            get {
                call.respond(db.values)
            }

            post {
                val order = call.receive<OrderRequest>().generateNewOrder()
                db[order.id] = order
                this.call.response.status(HttpStatusCode.Created)
                this.call.response.header(
                    HttpHeaders.Location,
                    call.url { path(call.request.path(), "/${order.id}") }
                )
            }

            get("/{uuid}") {
                val uuid = call.parameters["uuid"].let { e -> UUID.fromString(e) }

                db[uuid]?.let { e -> call.respond(e)  }
                    ?: call.response.status(HttpStatusCode.NotFound)
            }

            put("/{uuid}") {
                val uuid = call.parameters["uuid"].let { e -> UUID.fromString(e) }
                val order = call.receive<OrderRequest>().let { e -> Order(uuid, e.description) }

                db[uuid] = order
                call.response.status(HttpStatusCode.Accepted)
            }

            delete("/{uuid}") {
                val uuid = call.parameters["uuid"].let { e -> UUID.fromString(e) }

                db.remove(uuid)?.let { e -> call.respond(e) }
                    ?: call.response.status(HttpStatusCode.NotFound)
            }
        }
    }
}

