import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ClientUDP {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("localhost");
            Scanner scanner = new Scanner(System.in);

            System.out.println("Client avviato. Inserisci 'fine' per terminare.");

            while (true) {
                System.out.print("Inserisci messaggio: ");
                String message = scanner.nextLine();
                byte[] sendBuffer = message.getBytes();

                // Invio del pacchetto
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, 6789);
                socket.send(sendPacket);
                System.out.println("Messaggio inviato: " + message);

                // Ricezione della risposta
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);

                String receivedData = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Risposta dal server: " + receivedData);

                if (message.equalsIgnoreCase("fine")) {
                    System.out.println("Chiusura del client...");
                    break;
                }
            }
            socket.close();
            scanner.close();
            System.out.println("Client chiuso.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
