class Automovil(
    nombre: String,
    marca: String,
    modelo: String,
    capacidadCombustible: Float,
    combustibleActual: Float,
    kilometrosActuales: Float,
    private val esHibrido: Boolean
) : Vehiculo(nombre, marca, modelo, capacidadCombustible, combustibleActual, kilometrosActuales) {
    /** Companion object con la constante AHORRO_ELECTRICO */
    companion object {
        const val AHORRO_ELECTRICO = 5.0f
    }

    /** Método para calcular la autonomía del automóvil */
    override fun calcularAutonomia(): Float {
        var autonomia = super.calcularAutonomia()
        if (esHibrido) {
            autonomia += AHORRO_ELECTRICO
        }
        return autonomia
    }

    /** Método para realizar un derrape con el automóvil */
    fun realizaDerrape(): Float {
        val gasto = if (esHibrido) 6.25f else 7.5f
        return if (combustibleActual > gasto) {
            combustibleActual -= gasto
            gasto
        } else {
            println("No se puede realizar el derrape, no hay combustible suficiente.")
            0f
        }
    }
}