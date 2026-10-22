package task09

enum class Severity { MINOR, MAJOR, CRITICAL, BLOCKER }

sealed class Incident(val severity: Severity) {
    class FileProblem(val fileName: String) : Incident(Severity.MAJOR)
    class DatabaseDown(val database: String) : Incident(Severity.CRITICAL)
    class SlowPage(val url: String) : Incident(Severity.MINOR)
    class OutOfMemory : Incident(Severity.BLOCKER)
    object Crash : Incident(Severity.CRITICAL)
}

fun describe(incident: Incident): String {
    return when (incident) {
        is Incident.FileProblem -> "File ${incident.fileName} cannot be read"
        is Incident.DatabaseDown -> "Database ${incident.database} is down"
        is Incident.SlowPage -> "Page ${incident.url} is slow"
        is Incident.OutOfMemory -> "The application ran out of memory"
        Incident.Crash -> "The application crashed"
    }
}

fun main() {
    val log = mutableListOf<Incident>()
    println("Report: file NAME, db NAME, slow URL, crash, oom, or end")

    while (true) {
        print("> ")
        val line = readln().trim()
        val parts = line.split(" ", limit = 2)
        val argument = if (parts.size > 1) parts[1] else ""

        when (parts[0]) {
            "file" -> log.add(Incident.FileProblem(argument))
            "db" -> log.add(Incident.DatabaseDown(argument))
            "slow" -> log.add(Incident.SlowPage(argument))
            "crash" -> log.add(Incident.Crash)
            "oom" -> log.add(Incident.OutOfMemory())
            "end" -> break
            else -> println("Unknown report")
        }
    }

    // Compare two database incidents if the user entered at least two.
    val dbs = mutableListOf<Incident.DatabaseDown>()
    for (incident in log) {
        if (incident is Incident.DatabaseDown) dbs.add(incident)
    }
    if (dbs.size >= 2) {
        println("Same database incident object: ${dbs[0] === dbs[1]}")
        println("Same severity enum constant: ${dbs[0].severity === dbs[1].severity}")
    }

    // Sort incidents manually: most severe first.
    val sortedLog = mutableListOf<Incident>()
    for (incident in log) sortedLog.add(incident)

    var i = 0
    while (i < sortedLog.size) {
        var j = i + 1
        while (j < sortedLog.size) {
            if (sortedLog[j].severity > sortedLog[i].severity) {
                val temporary = sortedLog[i]
                sortedLog[i] = sortedLog[j]
                sortedLog[j] = temporary
            }
            j++
        }
        i++
    }

    var criticalCount = 0
    for (incident in sortedLog) {
        println("[${incident.severity}] ${describe(incident)}")
        if (incident.severity == Severity.CRITICAL) criticalCount++
        if (incident.severity >= Severity.CRITICAL) println("ALERT")
    }
    println("Critical incidents: $criticalCount of ${log.size}")
}
