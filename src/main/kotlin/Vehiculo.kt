open class Vehiculo(
    val nombre: String,
    val marca: String,
    val modelo: String,
    private val capacidadCombustible: Float = 10.0f,
    combustibleActual: Float,
    kilometrosActuales: Float
) {
    /** Propiedades del vehículo */
    var combustibleActual: Float = combustibleActual
        get() {
            return redondearDosDecimales(field)
        }
        set(valor) {
            field = comprobarCombustibleActual(valor)
        }

    var kilometrosActuales: Float = kilometrosActuales
        get() {
            return redondearDosDecimales(field)
        }
        set(valor) {
            field = comprobarKilometrosActuales(valor)
        }

    var numRepostajes: Int = 0
        private set

    /** Inicialización del vehículo */
    init {
        comprobarCombustibleActual(combustibleActual)
        comprobarKilometrosActuales(kilometrosActuales)
    }

    private fun comprobarCombustibleActual(valor: Float): Float {
        return if (valor > capacidadCombustible) {
            capacidadCombustible
        } else {
            valor
        }
    }

    private fun comprobarKilometrosActuales(valor: Float): Float {
        require(valor >= 0f) { "Los kilómetros actuales no pueden ser negativos." }
        return valor
    }

    /** Método para obtener información del vehículo */
    open fun obtenerInformacion(): String {
        val autonomia = calcularAutonomia()
        return "El vehículo $nombre puede recorrer $autonomia kilómetros."
    }

    /** Método para calcular la autonomía del vehículo */
    open fun calcularAutonomia(): Float {
        return redondearDosDecimales(combustibleActual * KM_POR_LITRO)
    }

    /** Método para realizar un viaje con el vehículo */
    open fun realizaViaje(distancia: Float): Float {
        val distanciaMaxima = calcularAutonomia()
        return if (distancia <= distanciaMaxima) {
            val combustibleConsumido = distancia / KM_POR_LITRO
            combustibleActual -= combustibleConsumido
            kilometrosActuales += distancia
            0f
        } else {
            if (combustibleActual <= 0f) {
                println("El vehículo ${this.nombre} no puede realizar el viaje porque no tiene combustible.")
                return distancia
            }
            val distanciaRestante = distanciaMaxima - kilometrosActuales
            combustibleActual = 0f
            kilometrosActuales += distanciaRestante
            redondearDosDecimales(distancia - distanciaRestante)
        }
    }

    /** Método para repostar combustible en el vehículo */
    open fun repostar(cantidad: Float): Pair<Float, Int> {
        require(cantidad >= 0f) { "La cantidad de combustible a repostar no puede ser negativa." }
        val repostado = if (cantidad > 0f) {
            val espacioDisponible = capacidadCombustible - combustibleActual
            val repostadoCantidad = if (cantidad <= espacioDisponible) {
                cantidad
            } else {
                espacioDisponible
            }
            combustibleActual += repostadoCantidad
            repostadoCantidad
        } else {
            val repostadoCantidad = capacidadCombustible - combustibleActual
            combustibleActual = capacidadCombustible
            repostadoCantidad
        }
        numRepostajes++
        return Pair(repostado, numRepostajes)
    }

    /** Método para redondear un número a dos decimales */
    private fun redondearDosDecimales(numero: Float): Float {
        return (numero * 100).toInt() / 100f
    }

    /** Constante relacionada con la clase Vehiculo */
    companion object {
        const val KM_POR_LITRO = 10.0f
    }
}