/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Juego;

/**
 *
 * @author SDavidLedesma
 */
public class Tablero {

    private final int totalCasillas;

    public Tablero(int totalCasillas) {
        this.totalCasillas = totalCasillas;
    }

    public int getTotalCasillas() {
        return totalCasillas;
    }

    public boolean esFinal(int posicion) {
        return posicion >= totalCasillas;
    }
}
