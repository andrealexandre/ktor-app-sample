package org.example.andrealexandre

import java.util.*


data class Order(val id: UUID, val description: String)

data class OrderRequest(val description: String)

fun OrderRequest.generateNewOrder() = Order(UUID.randomUUID(), this.description)