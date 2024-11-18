/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import Juego.Juego;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SDavidLedesma
 */
public class Sala {

    private final String id;
    private final List<ClienteHandler> clientes;
    private final Juego juego;

    public Sala(String id) {
        this.id = id;
        this.clientes = new ArrayList<>();
        this.juego = new Juego(30); // Tablero de 30 casillas
    }

    public void agregarCliente(ClienteHandler cliente) {
        clientes.add(cliente);
    }

    public synchronized String procesarAccion(String accion, ClienteHandler cliente) {
        switch (accion.toUpperCase()) {
            case "ROLL":
                return juego.tirarDado();
            case "STATUS":
                return juego.obtenerEstado();
            default:
                return "Acción no reconocida.";
        }
    }

    public String getId() {
        return id;
    }
    
    
}
