/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author SDavidLedesma
 */
public class ClienteHandler implements Runnable {

    private final Socket socket;
    private final Sala sala;
    private PrintWriter out;

    public ClienteHandler(Socket socket, Sala sala) {
        this.socket = socket;
        this.sala = sala;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            this.out = out;

            String accion;
            while ((accion = in.readLine()) != null) {
                String respuesta = sala.procesarAccion(accion, this);
                enviarMensaje(respuesta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enviarMensaje(String mensaje) {
        if (out != null) {
            out.println(mensaje);
        }
    }
}
