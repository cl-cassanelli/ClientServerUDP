import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerUDP {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(6789);
            byte[] receiveBuffer = new byte[1024];
            byte[] sendBuffer;
            boolean running = true;

            System.out.println("Server avviato e in ascolto sulla porta 6789...");

            while (running) {
                // Ricezione del pacchetto
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);

                String receivedData = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Messaggio ricevuto: " + receivedData);

                // Preparazione della risposta
                if (receivedData.equalsIgnoreCase("fine")) {
                    running = false;
                    sendBuffer = "Server chiuso".getBytes();
                    System.out.println("Chiusura del server in corso...");
                } else {
                    sendBuffer = receivedData.toUpperCase().getBytes();
                }

                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                System.out.println("Invio risposta a " + clientAddress + ":" + clientPort);

                // Invio del pacchetto di risposta
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientAddress, clientPort);
                socket.send(sendPacket);
            }
            socket.close();
            System.out.println("Server chiuso.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
