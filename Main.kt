package flashcards

import java.io.File
import kotlin.system.exitProcess

private val a: MutableMap<String, String> = mutableMapOf()
var card = ""
var definition = ""
var file = ""

fun main() {
    while (true) {
        println("Input the action (add, remove, import, export, ask, exit):")
        when (readln()) {
            "add" -> add()
            "remove" -> remove()
            "import" -> import()
            "export" -> export()
            "ask" -> ask()
            "exit" -> {
                println("Bye bye!")
                exitProcess(0)
            }
        }
        println()
    }
}

fun add() {
    println("The card:")
    card = readln()
    if (a.containsKey(card)) {
        println("The card \"$card\" already exists.")
        return
    } else {
        println("The definition of the card:")
        definition = readln()
        if (a.containsValue(definition)) {
            println("The definition \"$definition\" already exists.")
            return
        } else {
            a.put(card, definition)
            println("The pair (\"$card\":\"$definition\") has been added.")
        }
    }
}

fun remove() {
    println("Which card?")
    card = readln()
    if (a.containsKey(card)) {
        a.remove(card)
        println("The card has been removed.")
    } else {
        println("Can't remove \"$card\": there is no such card.")
        return
    }
}

fun import() {
    println("File name:")
    file = readln()
    if (File(file).exists()) {
        val list = File(file).readLines()
        for (i in 0 until list.size step 2) {
            a.put(list[i], list[i + 1])
        }
        println("${list.size / 2} cards have been loaded.")
    } else {
        println("File not found.")
        return
    }
}

fun export() {
    println("File name:")
    file = readln()
    File(file).createNewFile()
    var str = ""
    for (i in 0 until a.size) {
        str += "${a.keys.elementAt(i)}\n${a[a.keys.elementAt(i)]}\n"
    }
    File(file).writeText(str)
    println("${a.size} cards have been saved.")
}

fun ask() {
    println("How many times to ask?")
    val times = readln().toInt()
    for (i in 0 until times) {
        println("Print the definition of \"${a.keys.elementAt(i % a.size)}\"")
        var def = readln()
        if (a[a.keys.elementAt(i % a.size)] == def) {
            println("Correct!")
        } else {
            print("Wrong. The right answer is \"${a[a.keys.elementAt(i % a.size)]}\"")
            if (a.containsValue(def)) {
                println(", but you definition is correct for \"${a.filter { def == it.value }.keys.first()}\".")
            } else {
                println(".")
            }
        }

    }
}

