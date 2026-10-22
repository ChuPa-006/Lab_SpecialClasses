package task10

sealed interface PlayerCommand

enum class Basic : PlayerCommand { PLAY, PAUSE, STOP, MUTE }
data class Seek(val seconds: Int) : PlayerCommand
data class Volume(val level: Int) : PlayerCommand

fun parse(line: String): PlayerCommand? {
    val parts = line.trim().lowercase().split(" ")
    if (parts.isEmpty()) return null

    var number: Int? = null
    if (parts.size > 1) number = parts[1].toIntOrNull()

    return when (parts[0]) {
        "play" -> Basic.PLAY
        "pause" -> Basic.PAUSE
        "stop" -> Basic.STOP
        "mute" -> Basic.MUTE
        "seek" -> if (number != null && number >= 0) Seek(number) else null
        "volume" -> if (number != null && number in 0..100) Volume(number) else null
        else -> null
    }
}

class Player {
    private var position = 0
    private var volume = 50

    fun handle(command: PlayerCommand) {
        when (command) {
            Basic.PLAY -> println("Playing from ${position}s")
            Basic.PAUSE -> println("Paused at ${position}s")
            Basic.STOP -> {
                position = 0
                println("Stopped")
            }
            Basic.MUTE -> {
                volume = 0
                println("Muted")
            }
            is Seek -> {
                position = command.seconds
                println("Jumped to ${position}s")
            }
            is Volume -> {
                volume = command.level
                println("Volume is now $volume%")
            }
        }
    }
}

fun main() {
    val player = Player()
    println("Commands: play, pause, stop, mute, seek N, volume N (0-100), quit")
    while (true) {
        print("> ")
        val line = readln()
        if (line.trim() == "quit") break
        val command = parse(line)
        if (command == null) println("Unknown command") else player.handle(command)
    }
}
