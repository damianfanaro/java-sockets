import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * UDP Server that sends messages read from the standard user input.
 *
 * @author Damian Fanaro (damianfanaro@gmail.com)
 */
public class UDPMulticastServer implements Runnable {

    private MulticastSocket socket;
    private String ip;
    private int port;
    private InetAddress group;

    public UDPMulticastServer(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public static void main(String[] args) {
        String ip;
        int port;
        try {
            ip = args[0];
            port = Integer.valueOf(args[1]);
            UDPMulticastServer server = new UDPMulticastServer(ip, port);
            new Thread(server).start();
        } catch (Exception e) {
            System.out.println("Invalid arguments. Try [224.0.0.0, 44460]");
        }
    }

    @Override
    public void run() {
        establishMulticastConnection();
        Integer counter = 0;
        while (counter < Integer.MAX_VALUE) {
            System.out.println("Sending: " + counter);
            byte[] input = String.valueOf(counter++).getBytes();
            DatagramPacket packet = new DatagramPacket(input, input.length, group, port);
            try {
                socket.send(packet);
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("An exception has occurred when sending data from the server. Reason: " + e.getMessage());
            }
        }
    }

    private void establishMulticastConnection() {
        try {
            socket = new MulticastSocket(port);
            group = InetAddress.getByName(ip);
        } catch (IOException e) {
            System.out.println(String.format("The connection could not be established with IP: %s and PORT: %d", ip, port));
        }
    }
}
