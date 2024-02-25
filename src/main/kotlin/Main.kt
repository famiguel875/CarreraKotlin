fun main() {
    /** Lista de participantes en la carrera */
    val participantes = listOf(
        Automovil("Aurora", "Seat", "Panda", 50f, 5f, 0f, true),
        Automovil("Boreal", "BMW", "M8", 80f, 10f, 0f, false),
        Motocicleta("Céfiro", "Derbi", "Motoreta", 15f, 7.5f, 0f, 500),
        Automovil("Dinamo", "Cintroen", "Sor", 70f, 8f, 0f, true),
        Automovil("Eclipse", "Renault", "Espacio", 60f, 6f, 0f, false),
        Motocicleta("Fénix", "Honda", "Vital", 20f, 2f, 0f, 250)
    )

    /** Crear una instancia de la carrera y comenzar la carrera */
    val carrera = Carrera("Grand Prix", 1000f, participantes)
    carrera.iniciarCarrera()
}

