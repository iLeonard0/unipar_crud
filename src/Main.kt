import classes.Guest
import classes.Man
import classes.Woman

enum class Gender { M, F }

val guestList = mutableListOf<Guest>()

fun main() {
    menu()
}

private fun menu() {
    do {
        println(
            """
            1 - ADD
            2 - LIST
            3 - EDIT
            4 - DELETE
            0 - EXIT
        """.trimIndent()
        )

        val op = readOption()
        when (op) {
            1 -> create()
            2 -> listar()
            3 -> editar()
            4 -> deletar()
            0 -> println("LEAVING...")
        }
    } while (op != 0)
}

private fun readOption(): Int {
    while (true) {
        val input = readln().toIntOrNull()
        if (input != null && input in 0..4) {
            return input
        }
        println("Invalid option. Please enter a number between 0 and 4.")
    }
}

private fun create() {
    println("ADD")

    val name = prompt("Guest name: ")
    val gift = prompt("What's your gift? ")
    val dietary = prompt("Any dietary restrictions? ")

    val gender = promptGender()
    val guest = createGuest(name, gift, dietary, gender)

    guestList.add(guest)
    println("Guest added successfully!\n")
}

private fun prompt(promptText: String): String {
    print(promptText)
    return readln()
}

private fun promptGender(): Gender {
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
    return gender
}

private fun createGuest(name: String, gift: String, dietary: String, gender: Gender): Guest {
    return when (gender) {
        Gender.M -> Man().apply { this.name = name; this.clothing = gift; this.restriction = dietary }
        Gender.F -> Woman().apply { this.name = name; this.toy = gift; this.restriction = dietary }
    }
}

private fun listar() {
    if (guestList.isEmpty()) {
        println("No guests added yet.")
        return
    }

    println("Listando...")
    guestList.forEachIndexed { index, guest ->
        val gift = when (guest) {
            is Man -> guest.clothing
            is Woman -> guest.toy
            else -> "No gift"
        }

        println("${index + 1}; Name: ${guest.name} - Gift: $gift - Dietary Restrictions: ${guest.restriction} - Presence: ${guest.presence}")
    }

    println("\nPressione ENTER para continuar!")
    readln()
}

private fun editar() {
    if (guestList.isEmpty()) {
        println("No guests to edit.")
        return
    }

    listar()
    val posicao = readPosicao("Selecione o número do registro a ser alterado: ")

    val selectedGuest = guestList[posicao - 1]
    val resposta = prompt("Essa pessoa vai à festa? (S/N)").uppercase()

    when (resposta) {
        "S" -> selectedGuest.presence = true
        "N" -> selectedGuest.presence = false
        else -> println("Resposta inválida!")
    }
}

private fun deletar() {
    if (guestList.isEmpty()) {
        println("No guests to delete.")
        return
    }

    listar()
    val posicao = readPosicao("Selecione o número do registro a ser removido: ")

    guestList.removeAt(posicao - 1)
    println("Guest removed successfully!")
}

private fun readPosicao(promptText: String): Int {
    while (true) {
        print(promptText)
        val posicao = readln().toIntOrNull()
        if (posicao != null && posicao in 1..guestList.size) {
            return posicao
        }
        println("Invalid position! Please enter a valid number.")
    }
}
