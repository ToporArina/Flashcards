package flashcards

private val a: MutableMap<String, String> = mutableMapOf()
val b = mutableListOf<String>()
val c = mutableSetOf<String>()
val d = mutableMapOf<String, String>()
fun main() {
    println("Input the number of cards:")
    for (i in 0 until readln().toInt()) {
        println("Card #${i + 1}:")
        var k = readln()
        while (true) {
            if (a.containsKey(k)) {
                println("The term \"$k\" already exists. Try again:")
                k = readln()
            } else {
                break
            }
        }
        println("The definition for card #${i + 1}:")
        var v = readln()
        while (true) {
            if (a.containsValue(v)) {
                println("The definition of \"$v\" already exists. Try again:")
                v = readln()
            } else {
                break
            }
        }
        a.put(k, v)
    }

    for (i in 0 until a.size) {
        println("Print the definition of \"${a.keys.elementAt(i)}\"")
        var def = readln()
        if (a[a.keys.elementAt(i)] == def) {
            println("Correct!")
        } else {
            print("Wrong. The right answer is \"${a[a.keys.elementAt(i)]}\"")
            if (a.containsValue(def)) {
                println(", but you definition is correct for \"${a.filter { def == it.value }.keys.first()}\".")
            } else {
                println(".")
            }
        }

    }
}
