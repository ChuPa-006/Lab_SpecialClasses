package task14

sealed interface Outcome<out T> {
    data class Ok<T>(val value: T) : Outcome<T>
    data class Fail(val reason: String) : Outcome<Nothing>
}

fun parseQuantity(text: String): Outcome<Int> {
    val number = text.trim().toIntOrNull()
    if (number == null) return Outcome.Fail("'$text' is not a whole number")
    if (number <= 0) return Outcome.Fail("The quantity must be positive")
    if (number > 100) return Outcome.Fail("We cannot sell more than 100 at once")
    return Outcome.Ok(number)
}

fun parsePrice(text: String): Outcome<Double> {
    val price = text.trim().toDoubleOrNull()
    if (price == null) return Outcome.Fail("'$text' is not a price")
    if (price <= 0.0) return Outcome.Fail("The price must be positive")
    return Outcome.Ok(price)
}

fun parseEmail(text: String): Outcome<String> {
    if (!text.contains("@")) return Outcome.Fail("'$text' is not a valid e-mail")
    return Outcome.Ok(text)
}

fun main() {
    print("Quantity: ")
    val quantityResult = parseQuantity(readln())
    val quantity = when (quantityResult) {
        is Outcome.Fail -> {
            println("Quantity error: ${quantityResult.reason}")
            return
        }
        is Outcome.Ok -> quantityResult.value
    }

    print("Unit price: ")
    val priceResult = parsePrice(readln())
    val price = when (priceResult) {
        is Outcome.Fail -> {
            println("Price error: ${priceResult.reason}")
            return
        }
        is Outcome.Ok -> priceResult.value
    }

    print("E-mail: ")
    val emailResult = parseEmail(readln())
    when (emailResult) {
        is Outcome.Fail -> println("E-mail error: ${emailResult.reason}")
        is Outcome.Ok -> println("E-mail accepted: ${emailResult.value}")
    }

    println("Total: ${quantity * price}")
}
