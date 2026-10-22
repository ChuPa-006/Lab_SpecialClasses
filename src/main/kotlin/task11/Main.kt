package task11

sealed interface Chargeable { val batteryHours: Int }
sealed interface Wireless { val range: Int }
sealed interface Waterproof

data class Phone(
    override val batteryHours: Int = 24,
    override val range: Int = 50
) : Chargeable, Wireless

data class Headphones(
    override val batteryHours: Int = 8,
    override val range: Int = 10
) : Chargeable, Wireless, Waterproof

data class SmartLamp(
    override val batteryHours: Int = 4,
    override val range: Int = 30
) : Chargeable, Wireless

class Toaster

fun create(name: String): Any? {
    return when (name.lowercase()) {
        "phone" -> Phone()
        "headphones" -> Headphones()
        "lamp" -> SmartLamp()
        "toaster" -> Toaster()
        else -> null
    }
}

fun chargingNote(device: Chargeable): String {
    return when (device) {
        is Phone -> "Charge the phone every night"
        is Headphones -> "Charge the headphones after 8 hours"
        is SmartLamp -> "Charge the smart lamp after 4 hours"
    }
}

fun main() {
    val devices = mutableListOf<Any>()
    println("Add devices: phone, headphones, lamp, toaster (type 'done' to finish)")

    while (true) {
        print("> ")
        val name = readln().trim()
        if (name == "done") break
        val device = create(name)
        if (device == null) println("Unknown device") else devices.add(device)
    }

    for (device in devices) {
        var abilityText = ""
        if (device is Chargeable) abilityText = "battery ${device.batteryHours} h"
        if (device is Wireless) {
            if (abilityText != "") abilityText += ", "
            abilityText += "wireless ${device.range} m"
        }
        if (device is Waterproof) {
            if (abilityText != "") abilityText += ", "
            abilityText += "waterproof"
        }
        if (abilityText == "") abilityText = "no special abilities"
        println("${device.javaClass.simpleName}: $abilityText")
        if (device is Chargeable) println(chargingNote(device))
    }

    var chargeableCount = 0
    for (device in devices) {
        if (device is Chargeable) chargeableCount++
    }
    println("Chargeable devices: $chargeableCount")
}
