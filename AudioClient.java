/**
 * Cliente de audio
 */
package com.mycompany.audioclient;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author char_
 */
public class AudioClient {

    public static void main(String[] args) {
        try {
            System.out.println("Escuchando...");
            Socket socket = new Socket("192.168.1.139", 50005); // Dirección IP del servidor y puerto
            InputStream in = socket.getInputStream();

            // Configuración del formato de audio
            AudioFormat format = new AudioFormat(44100, 16, 2, true, true);
            SourceDataLine speakers = AudioSystem.getSourceDataLine(format);
            speakers.open(format);
            speakers.start();

            //Ajusta el control de volumen
            FloatControl volumeControl = (FloatControl) speakers.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(6.0f);//Máximo valor 6.0f

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = in.read(buffer)) != -1) {
                speakers.write(buffer, 0, bytesRead);
            }

        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
