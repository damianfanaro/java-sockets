import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * UDP Client listening in a {@link MulticastSocket} socket.
 *
 * @author Damian Fanaro (damianfanaro@gmail.com)
 */
public class UDPMulticastClient implements Runnable {

    private int packetLength;
    private String ip;
    private int port;
    private boolean keepRunning = true;

    public UDPMulticastClient(String ip, int port, int packetLength) {
        this.ip = ip;
        this.port = port;
        this.packetLength = packetLength;
    }

    public static void main(String[] args) {
        String ip;
        int port;
        int packetLength;
        try {
            ip = args[0];
            port = Integer.valueOf(args[1]);
            packetLength = Integer.valueOf(args[2]);
            UDPMulticastClient client = new UDPMulticastClient(ip, port, packetLength);
            new Thread(client).start();
        } catch (Exception e) {
            System.out.println("Invalid arguments. Try [224.0.0.0, 44460, 256]");
        }
    }

    @Override
    public void run() {
        MulticastSocket socket = establishMulticastConnection();
        while (keepRunning) {
            byte[] buffer = new byte[packetLength];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                System.out.println("An exception has occurred when receiving data from the server. Reason: " + e.getMessage());
            }
            String received = new String(packet.getData());
            System.out.println("Receiving: " + received);
        }
    }

    public void shutdown() {
        this.keepRunning = false;
    }

    private MulticastSocket establishMulticastConnection() {
        MulticastSocket socket = null;
        try {
            socket = new MulticastSocket(port);
            InetAddress group = InetAddress.getByName(ip);
            socket.joinGroup(group);
        } catch (IOException e) {
            System.out.println(String.format("The connection could not be established with IP: %s and PORT: %d", ip, port));
        }
        return socket;
    }
}
