package task03

enum class Delivery {
    PICKUP, COURIER, EXPRESS, DRONE
}

fun shippingCost(type: Delivery, weightKg: Double): Double {
    return when (type) {
        Delivery.PICKUP -> 0.0
        Delivery.COURIER -> 15000.0 + 2000.0 * weightKg
        Delivery.EXPRESS -> 40000.0 + 5000.0 * weightKg
        Delivery.DRONE -> 25000.0 + 3000.0 * weightKg
    }
}

fun main() {
    print("Delivery type: ")
    val typeName = readln().trim().uppercase()

    var type: Delivery? = null
    for (item in Delivery.values()) {
        if (item.name == typeName) type = item
    }

    if (type == null) {
        println("Unknown delivery type")
        return
    }

    print("Weight in kg: ")
    val weight = readln().trim().toDoubleOrNull()
    if (weight == null || weight < 0) {
        println("Invalid weight")
        return
    }

    println("Shipping cost: ${"%.0f".format(shippingCost(type, weight))} UZS")
}
