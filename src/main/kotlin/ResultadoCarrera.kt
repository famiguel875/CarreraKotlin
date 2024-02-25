data class ResultadoCarrera(
    val vehiculo: Vehiculo,
    val posicion: Int,
    val kilometraje: Float,
    val paradasRepostaje: Int,
    val historialAcciones: List<String> = emptyList()
)
