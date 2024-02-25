class Motocicleta(
    nombre: String,
    marca: String,
    modelo: String,
    capacidadCombustible: Float,
    combustibleActual: Float,
    kilometrosActuales: Float,
    private val cilindrada: Int
) : Vehiculo(nombre, marca, modelo, capacidadCombustible, combustibleActual, kilometrosActuales) {
    /** Constantes relacionadas con la clase Motocicleta */
    companion object {
        const val MIN_CILINDRADA = 125
        const val MAX_CILINDRADA = 1000
        const val AHORRO_MOTOCICLETA = 20.0f
    }

    /** Inicializador de la clase Motocicleta */
    init {
        // Verificar que la cilindrada esté dentro del rango permitido
        require(cilindrada in MIN_CILINDRADA..MAX_CILINDRADA) {
            "La cilindrada debe estar entre $MIN_CILINDRADA y $MAX_CILINDRADA cc"
        }
    }

    /** Método para calcular la autonomía de la motocicleta */
    override fun calcularAutonomia(): Float {
        var autonomia = super.calcularAutonomia()
        autonomia += AHORRO_MOTOCICLETA
        if (cilindrada < MAX_CILINDRADA) {
            val restaPorCilindrada = (MAX_CILINDRADA - cilindrada) / 1000f
            autonomia -= restaPorCilindrada
        }
        return autonomia
    }

    /** Método para realizar un caballito con la motocicleta */
    fun realizaCaballito(): Float {
        val gasto = 6.5f
        return if (combustibleActual > gasto) {
            combustibleActual -= gasto
            gasto
        } else {
            println("No se puede realizar el caballito, no hay combustible suficiente.")
            0f
        }
    }
}