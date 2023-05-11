package flashcards

val a = HashMap<String, String>()

fun main() {
    a.put(readln(), readln())
    if (a[a.keys.first()] == readln()) {
        println("Your answer is right!")
    } else {
        println("Your answer is wrong...")
    }
}
