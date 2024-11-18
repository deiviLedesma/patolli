package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author SDavidLedesma
 */
public class Servidor {

    private final int puerto;
    private final List<Sala> salas; // Lista de salas activas
    private boolean activo;

    public Servidor(int puerto) {
        this.puerto = puerto;
        this.salas = new ArrayList<>();
        this.activo = true;
    }

    // Método para iniciar el servidor
    public void iniciar() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("Servidor iniciado en el puerto " + puerto);

            while (activo) {
                Socket clienteSocket = serverSocket.accept(); // Espera conexiones de clientes
                System.out.println("Cliente conectado: " + clienteSocket.getInetAddress());

                // Maneja al cliente en un nuevo hilo
                new Thread(() -> {
                    try {
                        manejarCliente(clienteSocket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }
    }

    // Método para manejar la conexión de un cliente
    private void manejarCliente(Socket clienteSocket) throws IOException {
        try {
            // Enviar bienvenida al cliente y pedir el ID de la sala
            var writer = clienteSocket.getOutputStream();
            writer.write("Introduce el ID de la sala:\n".getBytes());

            // Leer el ID de la sala desde el cliente
            var reader = new java.io.BufferedReader(new java.io.InputStreamReader(clienteSocket.getInputStream()));
            String idSala = reader.readLine();

            // Obtener o crear la sala
            Sala sala = obtenerOSalaCrear(idSala);

            // Crear y agregar el cliente a la sala
            ClienteHandler clienteHandler = new ClienteHandler(clienteSocket, sala);
            sala.agregarCliente(clienteHandler);

            // Iniciar el manejo del cliente
            clienteHandler.run();
        } catch (IOException e) {
            System.out.println("Error manejando cliente: " + e.getMessage());
            clienteSocket.close();
        }
    }

    // Método para obtener una sala existente o crear una nueva
    private synchronized Sala obtenerOSalaCrear(String idSala) {
        return salas.stream()
                .filter(sala -> sala.getId().equals(idSala))
                .findFirst()
                .orElseGet(() -> {
                    Sala nuevaSala = new Sala(idSala);
                    salas.add(nuevaSala);
                    System.out.println("Nueva sala creada: " + idSala);
                    return nuevaSala;
                });
    }

    // Método para detener el servidor
    public void detener() {
        this.activo = false;
        System.out.println("Servidor detenido.");
    }

    // Método principal para ejecutar el servidor
    public static void main(String[] args) {
        int puerto = 8080; // Define el puerto en el que el servidor escuchará
        Servidor servidor = new Servidor(puerto);

        try {
            servidor.iniciar();
        } catch (IOException e) {
            System.out.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }
}
