/**
 * Servidor de audio
 */
package com.mycompany.servaudio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author char_
 */
public class ServAudio {

    public static void main(String[] args) {
        try ( ServerSocket serverSocket = new ServerSocket(50005)) {
            System.out.println("Esperando conexión del cliente...");

            while (true) {  // Mantiene el servidor corriendo para múltiples clientes
                try ( Socket socket = serverSocket.accept()) {
                    System.out.println("Cliente conectado. Iniciando transmisión de audio...");

                    // Configuración del formato de audio
                    AudioFormat format = new AudioFormat(44100, 16, 2, true, true);
                    TargetDataLine microphone = AudioSystem.getTargetDataLine(format);

                    // Abre y comienza a capturar audio
                    microphone.open(format);
                    microphone.start();

                    try ( OutputStream out = socket.getOutputStream()) {
                        byte[] buffer = new byte[4096];
                        int bytesRead;

                        while (true) {
                            bytesRead = microphone.read(buffer, 0, buffer.length);
                            out.write(buffer, 0, bytesRead);
                        }
                    } catch (SocketException se) {
                        System.out.println("El cliente se ha desconectado.");
                    } finally {
                        // La línea de audio se cierre al final
                        microphone.stop();
                        microphone.close();
                    }

                } catch (LineUnavailableException e) {
                    System.out.println("La línea de audio no está disponible: " + e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
