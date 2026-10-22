package task01

enum class OrderStatus {
    NEW, PAID, SHIPPED, DELIVERED, CANCELLED
}

fun main() {
    val allStatuses = OrderStatus.values()

    print("Statuses: ")
    var i = 0
    while (i < allStatuses.size) {
        print(allStatuses[i])
        if (i < allStatuses.size - 1) print(", ")
        i++
    }
    println()

    print("Current status of your order: ")
    val text = readln().trim().uppercase()

    var status: OrderStatus? = null
    for (item in allStatuses) {
        if (item.name == text) {
            status = item
        }
    }

    if (status == null) {
        println("'$text' is not a status.")
        print("Valid statuses: ")
        for (item in allStatuses) print("$item ")
        println()
        return
    }

    var currentIndex = -1
    i = 0
    while (i < allStatuses.size) {
        if (allStatuses[i] == status) currentIndex = i
        i++
    }

    println("You entered ${status.name}: step ${currentIndex + 1} of ${allStatuses.size}")
    if (currentIndex + 1 < allStatuses.size) {
        println("Next step: ${allStatuses[currentIndex + 1]}")
    } else {
        println("This is the last step.")
    }

    // Required identity experiment from Task 1.
    println(OrderStatus.PAID === OrderStatus.valueOf("PAID"))
}
