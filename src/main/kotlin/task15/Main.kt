package task15

enum class Priority(val level: Int) {
    LOW(1), NORMAL(2), HIGH(3), URGENT(4)
}

sealed interface Action
data class Open(val title: String, val priority: Priority) : Action
data class Close(val id: Int) : Action
data class Reopen(val id: Int) : Action
data class Comment(val id: Int, val text: String) : Action
data object ListAll : Action

class Ticket(val id: Int, val title: String, val priority: Priority) {
    var isOpen = true
    val comments = mutableListOf<String>()
}

val tickets = mutableListOf<Ticket>()

fun parse(line: String): Action? {
    val parts = line.trim().split(" ", limit = 3)
    if (parts.isEmpty()) return null

    return when (parts[0].lowercase()) {
        "open" -> {
            if (parts.size < 3) return null
            var priority: Priority? = null
            for (item in Priority.values()) {
                if (item.name == parts[1].uppercase()) priority = item
            }
            if (priority == null || parts[2].isBlank()) null else Open(parts[2], priority)
        }
        "close" -> {
            if (parts.size < 2) null else {
                val id = parts[1].toIntOrNull()
                if (id == null) null else Close(id)
            }
        }
        "reopen" -> {
            if (parts.size < 2) null else {
                val id = parts[1].toIntOrNull()
                if (id == null) null else Reopen(id)
            }
        }
        "comment" -> {
            if (parts.size < 3) return null
            val id = parts[1].toIntOrNull()
            if (id == null || parts[2].isBlank()) null else Comment(id, parts[2])
        }
        "list" -> ListAll
        else -> null
    }
}

fun execute(action: Action) {
    when (action) {
        is Open -> {
            val ticket = Ticket(tickets.size + 1, action.title, action.priority)
            tickets.add(ticket)
            println("Opened #${ticket.id}: ${ticket.title} [${ticket.priority}]")
        }
        is Close -> {
            var ticket: Ticket? = null
            for (item in tickets) if (item.id == action.id) ticket = item
            if (ticket == null) {
                println("There is no ticket #${action.id}")
            } else {
                ticket.isOpen = false
                println("Closed #${ticket.id}")
            }
        }
        is Reopen -> {
            var ticket: Ticket? = null
            for (item in tickets) if (item.id == action.id) ticket = item
            if (ticket == null) {
                println("There is no ticket #${action.id}")
            } else {
                ticket.isOpen = true
                println("Reopened #${ticket.id}")
            }
        }
        is Comment -> {
            var ticket: Ticket? = null
            for (item in tickets) if (item.id == action.id) ticket = item
            if (ticket == null) {
                println("There is no ticket #${action.id}")
            } else {
                ticket.comments.add(action.text)
                println("Comment added to #${ticket.id} (${ticket.comments.size} in total)")
            }
        }
        ListAll -> {
            val sortedTickets = mutableListOf<Ticket>()
            for (ticket in tickets) sortedTickets.add(ticket)

            var i = 0
            while (i < sortedTickets.size) {
                var j = i + 1
                while (j < sortedTickets.size) {
                    if (sortedTickets[j].priority.level > sortedTickets[i].priority.level) {
                        val temporary = sortedTickets[i]
                        sortedTickets[i] = sortedTickets[j]
                        sortedTickets[j] = temporary
                    }
                    j++
                }
                i++
            }

            for (ticket in sortedTickets) {
                val state = if (ticket.isOpen) "open" else "closed"
                println("#${ticket.id} $state [${ticket.priority}] ${ticket.title}")
            }

            var openCount = 0
            for (ticket in tickets) if (ticket.isOpen) openCount++
            println("Open tickets: $openCount")
        }
    }
}

fun main() {
    println("Commands: open PRIORITY TITLE, close ID, reopen ID, comment ID TEXT, list, exit")
    print("Priorities: ")
    for (priority in Priority.values()) print("$priority ")
    println()

    while (true) {
        print("> ")
        val line = readln()
        if (line.trim() == "exit") break
        val action = parse(line)
        if (action == null) println("I do not understand that") else execute(action)
    }
}
