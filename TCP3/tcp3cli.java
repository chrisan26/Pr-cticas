import java.io.*;
import java.net.*;

class tcp3cli {
    public static void main(String args[]) throws Exception {
        if (args.length != 3) {
            System.out.println("Uso correcto: tcp3cli ip_address port_number -u");
            System.out.println("");
            System.out.println("                     (Solo funciona UDP)");
            System.out.println("");
            return;
        }

        String IP = args[0];
        int puerto = Integer.parseInt(args[1]);

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket clientSocket = new DatagramSocket();
        clientSocket.setSoTimeout(10000);
        InetAddress IPAddress = InetAddress.getByName(IP);
        
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];

        System.out.print("Introduce una cadena de enteros: ");
        String sentence = inFromUser.readLine();
        if(sentence.trim().equals("0"))
        {
            clientSocket.close();
            return;
        }

        sendData = sentence.getBytes();
       
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, puerto);
        clientSocket.send(sendPacket);
        
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        try {
            clientSocket.receive(receivePacket);
        } catch (Exception e) {
            System.out.print("Timeout");
            clientSocket.close();
            return;
        } 
        
        String modifiedSentence = new String(receivePacket.getData());
        
        System.out.println("Suma total: " + modifiedSentence);
        clientSocket.close();
    }
}