/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Juego;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author SDavidLedesma
 */
public class Juego {

    private final Tablero tablero;
    private final List<Jugador> jugadores;
    private int turnoActual;
    private final Random random;

    public Juego(int totalCasillas) {
        this.turnoActual = 0;
        this.random = new Random();
        this.tablero = new Tablero(totalCasillas);
        this.jugadores = new ArrayList<>();
    }

    public void agregarJugador(String nombre) {
        jugadores.add(new Jugador(nombre));
    }

    public String tirarDado() {
        Jugador jugadorActual = jugadores.get(turnoActual);
        int dado = (int) (Math.random() * 6) + 1;
        jugadorActual.mover(dado);
        if (tablero.esFinal(jugadorActual.getPosicion())) {
            return jugadorActual.getNombre() + " ha ganado!";
        }
        avanzarTurno();
        return jugadorActual.getNombre() + " avanzó a la posición " + jugadorActual.getPosicion();
    }

    private void avanzarTurno() {
        turnoActual = (turnoActual + 1) % jugadores.size();
    }

    public String obtenerEstado() {
        StringBuilder estado = new StringBuilder();
        for (Jugador jugador : jugadores) {
            estado.append(jugador.getNombre())
                    .append(": posición ")
                    .append(jugador.getPosicion())
                    .append("\n");
        }
        return estado.toString();
    }
}
