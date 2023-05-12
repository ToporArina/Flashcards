package flashcards

private val a:MutableMap<String, String> = mutableMapOf()

fun main() {
    println("Input the number of cards:")
    for (i in 0 until readln().toInt()) {
        println("Card #${i + 1}:")
        val k = readln()
        println("The definition for card #${i + 1}:")
        val v = readln()
        a.put(k, v)
    }

    for (i in 0 until a.size) {
        println("Print the definition of \"${a.keys.elementAt(i)}\"")
        if (a[a.keys.elementAt(i)] == readln()) {
            println("Correct!")
        } else {
            println("Wrong. The right answer is \"${a[a.keys.elementAt(i)]}\"")
        }
    }
}
