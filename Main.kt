package flashcards

import java.io.File
import kotlin.random.Random
import kotlin.system.exitProcess

data class Card(val term: String, var def: String, var wrong: Int = 0)

private var a: MutableList<Card> = mutableListOf()
var card = ""
var definition = ""
var file = ""
var logText = ""

fun main(args: Array<String>) {
    for (i in args.indices step 2) {
        if (args[i] == "-import") {
            file = args[i + 1]
            import()
        }
    }
    while (true) {
        println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):")
        when (readln()) {
            "add" -> add()
            "remove" -> remove()
            "import" -> {
                println("File name:")
                file = readln()
                import()
            }
            "export" -> {
                println("File name:")
                file = readln()
                export()
            }
            "ask" -> ask()
            "exit" -> {
                for (i in args.indices step 2) {
                    if (args[i] == "-export") {
                        file = args[i + 1]
                        export()
                    }
                }
                println("Bye bye!")
                exitProcess(0)
            }
            "log" -> log()
            "hardest card" -> hardestCard()
            "reset stats" -> reset()
        }
        println()
    }
}

fun reset() {
    a.forEach { it.wrong = 0 }
    println("Card statistics have been reset.")
}

fun hardestCard() {
    a.sortWith(compareByDescending { it.wrong })
    if (a.size == 0) {
        println("There are no cards with errors.")
        return
    }
    val wrongCards = mutableListOf<Card>()
    if (a[0].wrong > 0) {
        wrongCards.add(a[0])
        if (a.size != 1) {
            for (i in a.indices - 1) {
                if (a[i].wrong == a[i + 1].wrong) {
                    wrongCards.add(a[i + 1])
                }
            }
        }
        println(
            "The hardest card${if (wrongCards.size == 1) " is" else "s are"} ${
                wrongCards.map { it.term }.joinToString { "\"$it\"" }
            }. You have ${wrongCards[0].wrong} errors answering ${if (wrongCards.size == 1) "it" else "them"}."
        )
    } else {
        println("There are no cards with errors.")
    }
}

fun add() {
    println("The card:")
    card = readln()
    for (i in 0 until a.size) {
        if (a[i].term == card) {
            println("The card \"$card\" already exists.")
            return
        }
    }
    println("The definition of the card:")
    definition = readln()
    for (i in 0 until a.size) {
        if (a[i].def == definition) {
            println("The definition \"$definition\" already exists.")
            return
        }
    }
    a.add(Card(card, definition, 0))
    println("The pair (\"$card\":\"$definition\") has been added.")
}

fun remove() {
    println("Which card?")
    card = readln()
    for (i in 0 until a.size) {
        if (a[i].term == card) {
            a.removeAt(i)
            println("The card has been removed.")
            return
        }
    }
    println("Can't remove \"$card\": there is no such card.")
}

fun import() {
    if (File(file).exists()) {
        val list = File(file).readLines()
        val b = mutableListOf<Card>()
        for (i in 0 until list.size step 3) {
            b.add(Card(list[i], list[i + 1], list[i + 2].toInt()))
        }
        for (card in b) {
            val index = a.indexOfFirst { it.term == card.term }
            if (index != -1) {
                a[index].def = card.def
            } else {
                a.add(card)
            }
        }

        println("${list.size / 3} cards have been loaded.")
    } else {
        println("File not found.")
        return
    }
}

fun export() {
    File(file).createNewFile()
    var str = ""
    for (i in 0 until a.size) {
        str += "${a[i].term}\n${a[i].def}\n${a[i].wrong}\n"
    }
    File(file).writeText(str)
    println("${a.size} cards have been saved.")
}

fun ask() {
    println("How many times to ask?")
    val times = readln().toInt()
    var nOfCard = 0
    qu@ for (i in 0 until times) {
        nOfCard = Random.nextInt(0, a.size)
        println("Print the definition of \"${a[nOfCard].term}\":")
        val def = readln()
        if (a[nOfCard].def == def) {
            println("Correct!")
        } else {
            a[nOfCard].wrong += 1
            print("Wrong. The right answer is \"${a[nOfCard].def}\"")
            for (i in 0 until a.size) {
                if (a[i].def == def) {
                    println(", but you definition is correct for \"${a[i].term}\".")
                    continue@qu
                }
            }
            println(".")
        }
    }
}

fun log() {
    println("File name:")
    file = readln()
    val log = File(file)
    log.writeText(logText)
    println("The log has been saved.")
}

fun println(str: String = "") {
    kotlin.io.println(str)
    logText += str + "\n"
}

fun readln(): String {
    var str = kotlin.io.readln()
    logText += ">$a\n"
    return str
}

