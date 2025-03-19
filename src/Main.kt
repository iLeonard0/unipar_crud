import classes.Guest
import classes.Man
import classes.Woman

// Variável Global
enum class Gender { M, F }
val guestList = mutableListOf<Guest>()

fun main() {
    menu()
}

private fun menu() {

    do {
        println("1 - ADD")
        println("2 - LIST")
        println("3 - EDIT")
        println("4 - DELETE")
        println("0 - EXIT")

        val op = readln()
        when (op.toInt()) {
            1 -> create(guestList)
            2 -> println("LIST")
            3 -> println("EDIT")
            4 -> println("DELETE")
            0 -> println("LEAVING...")
        }
    } while (op.toInt() != 0)
}

private fun create(guestList: MutableList<Guest>) {
    println("ADD")

    print("Guest name: ")
    val name = readln()

    print("What's your gift? ")
    val gift = readln()

    print("Any dietary restrictions? ")
    val dietary = readln()

    var gender: Gender? = null
    while (gender == null) {
        print("Gender (M or F): ")
        val input = readln().uppercase()
        gender = try {
            Gender.valueOf(input)
        } catch (e: IllegalArgumentException) {
            println("Invalid input. Please enter 'M' or 'F'.")
            null
        }
    }

    val guest = createGuest(name, gift, dietary, gender)
    guestList.add(guest)
    println("Guest added successfully!")
}

private fun createGuest(name: String, gift: String, dietary: String, gender: Gender): Guest {
    return when (gender) {
        Gender.M -> Man().apply {
            this.name = name
            this.clothing = gift
            this.restriction = dietary
        }
        Gender.F -> Woman().apply {
            this.name = name
            this.toy = gift
            this.restriction = dietary
        }
    }
}