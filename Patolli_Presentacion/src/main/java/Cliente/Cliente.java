/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cliente;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author SDavidLedesma
 */
public class Cliente {

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8080); 
                BufferedReader in = 
                        new BufferedReader(new InputStreamReader(socket.getInputStream())); PrintWriter out = 
                                new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true); BufferedReader console = 
                                        new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Conectado al servidor. Introduce el ID de la sala:");
            String idSala = console.readLine();
            out.println(idSala);

            String input;
            while ((input = console.readLine()) != null) {
                out.println(input);
                String respuesta = in.readLine();
                System.out.println("Servidor: " + respuesta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
