package at.fhjoanneum.lanfinderkotlin.activities

/*
 * Contdelclass
 * Some unit tests for the techdemo
 * Mai 25, 2023
 */
public class Contdelclass {

    fun add(a: Int, b: Int): Int {
        return a + b
    }

    fun subtract(a: Int, b: Int): Int {
        return a - b
    }

    fun multiply(a: Int, b: Int): Int {
        return a * b
    }

    fun divide(a: Int, b: Int): Int {
        if (b == 0) {
            throw IllegalArgumentException("Divider cannot be zero")
        }
        return a / b
    }
}
