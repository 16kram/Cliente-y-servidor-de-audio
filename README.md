Poner el ejecutable ServAudio en el PC que va a hacer de servidor de audio.

Crear un acceso directo del archivo ServAudio.

Teclear Windows + R.

Ejecutar-->shell:startup.

Mover acceso directo ServAudio a la carpeta de inicio.

En el icono de audio del PC servidor, ir a Sonidos, y en la pestaña grabar activar la mezcla estereo.

Poner el ejecutable AudioClient en el PC que ha a hacer de cliente de audio.

Añadir la dirección IP del servidor en el archivo AudioClient en la línea: Socket socket = new Socket("192.168.1.XXX", 50005);
