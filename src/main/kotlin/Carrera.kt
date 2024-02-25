import kotlin.random.Random

class Carrera(
    private val nombreCarrera: String,
    private val distanciaTotal: Float,
    private val participantes: List<Vehiculo>
) {
    /** Estado de la carrera */
    private var estadoCarrera: Boolean = false
    /** Historial de acciones de los participantes */
    private val historialAcciones: MutableMap<String, MutableList<String>> = mutableMapOf()
    /** Posiciones de los participantes en la carrera */
    private val posiciones: MutableList<Pair<String, Float>> = mutableListOf()

    /** Método de inicialización de la carrera */
    init {
        println("¡Comienza la carrera $nombreCarrera!")
    }

    /** Método para iniciar la carrera */
    fun iniciarCarrera() {
        /** Continuar hasta que la carrera esté finalizada */
        estadoCarrera = true
        /** Avanzar cada vehículo */
        while (!esCarreraFinalizada()) {
            for (vehiculo in participantes) {
                if (!esCarreraFinalizada()) {
                    avanzarVehiculo(vehiculo)
                    realizarFiligranas(vehiculo)
                    actualizarPosiciones()
                } else {
                    break
                }
            }
        }
        /** Determinar el ganador y mostrar resultados al finalizar la carrera */
        determinarGanador()
        mostrarResultados()
    }

    private fun avanzarVehiculo(vehiculo: Vehiculo) {
        /** Generar una distancia aleatoria recorrida por el vehículo */
        val distanciaRecorrida = Random.nextInt(10, 201).toFloat()
        /** Calcular el combustible necesario para recorrer esa distancia */
        val combustibleNecesario = distanciaRecorrida / Vehiculo.KM_POR_LITRO
        /** Comprobar si el vehículo tiene suficiente combustible */
        if (vehiculo.combustibleActual < combustibleNecesario) {
            val combustibleFaltante = combustibleNecesario - vehiculo.combustibleActual
            println("${vehiculo.nombre} necesita repostar $combustibleFaltante litros de combustible.")
            vehiculo.repostar(combustibleFaltante)
        }
        /** Realizar el viaje y actualizar el estado del vehículo */
        val distanciaReal = vehiculo.realizaViaje(distanciaRecorrida)
        if (distanciaReal > 0) {
            println("${vehiculo.nombre} ha llegado al final de la carrera.")
        } else {
            println("${vehiculo.nombre} ha recorrido $distanciaRecorrida km.")
        }
    }

    /** Método para registrar una acción realizada por un vehículo */
    fun registrarAccion(vehiculo: String, accion: String) {
        val historialVehiculo = historialAcciones.getOrPut(vehiculo) { mutableListOf() }
        historialVehiculo.add(accion)
    }

    /** Método para realizar filigranas durante la carrera */
    private fun realizarFiligranas(vehiculo: Vehiculo) {
        if (debeRealizarFiligrana()) {
            val resultado = when (vehiculo) {
                is Automovil -> vehiculo.realizaDerrape()
                is Motocicleta -> vehiculo.realizaCaballito()
                else -> 0f
            }
            if (resultado > 0) {
                val accion = "Realizó una filigrana."
                println("${vehiculo.nombre} ha realizado una filigrana. $accion")
                registrarAccion(vehiculo.nombre, accion)
            }
        }
    }

    /** Método para determinar si se debe realizar una filigrana */
    private fun debeRealizarFiligrana(): Boolean {
        return Random.nextBoolean()
    }

    /** Método para actualizar las posiciones de los participantes en la carrera */
    private fun actualizarPosiciones() {
        posiciones.clear()
        for (vehiculo in participantes) {
            posiciones.add(Pair(vehiculo.nombre, vehiculo.kilometrosActuales))
        }
        posiciones.sortByDescending { it.second }
    }

    /** Método para comprobar si la carrera ha finalizado */
    private fun esCarreraFinalizada(): Boolean {
        return posiciones.any { it.second >= distanciaTotal }
    }

    /** Método para determinar el ganador de la carrera */
    private fun determinarGanador() {
        val ganadores = posiciones.filter { it.second >= distanciaTotal }
        if (ganadores.isNotEmpty()) {
            println("¡La carrera ha finalizado!")
            if (ganadores.size == 1) {
                println("El ganador es ${ganadores[0].first}.")
            } else {
                println("¡Ha habido un empate!")
                for ((indice, ganador) in ganadores.withIndex()) {
                    println("${indice + 1}. ${ganador.first}")
                }
            }
        }
    }

    /** Método para mostrar los resultados de la carrera */
    private fun mostrarResultados() {
        val resultados = obtenerResultados()
        println("Resultados de la carrera $nombreCarrera:")
        resultados.forEachIndexed { index, resultado ->
            val historialAcciones = resultado.historialAcciones.joinToString(", ")
            println("${index + 1}. ${resultado.vehiculo.nombre} - Posición: ${resultado.posicion}, Kilometraje: ${resultado.kilometraje}, Paradas de repostaje: ${resultado.paradasRepostaje}, Historial de acciones: $historialAcciones")
        }
    }

    /** Método para obtener los resultados de la carrera */
    private fun obtenerResultados(): List<ResultadoCarrera> {
        val resultados: MutableList<ResultadoCarrera> = mutableListOf()
        val posicionesOrdenadas = posiciones.mapIndexed { index, pair -> pair.first to index + 1 }.toMap()
        for (vehiculo in participantes) {
            // Obtener el historial de acciones del vehículo, si no hay acciones registradas, registrar un mensaje
            val historialVehiculo = historialAcciones.getOrPut(vehiculo.nombre) {
                registrarAccion(vehiculo.nombre, "No hay acciones registradas.")
                mutableListOf()
            }
            val posicion = posicionesOrdenadas[vehiculo.nombre] ?: 0
            val resultado = ResultadoCarrera(
                vehiculo,
                posicion,
                vehiculo.kilometrosActuales,
                vehiculo.numRepostajes,
                historialVehiculo
            )
            resultados.add(resultado)
        }
        return resultados.sortedBy { it.posicion }
    }
}


