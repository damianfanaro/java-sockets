import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Simple TCP Client implementation using java.net
 *
 * @author Damian Fanaro (damianfanaro@gmail.com)
 */
public class TCPClient implements Runnable {

    private String ip;
    private int port;

    public TCPClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public static void main(String[] args) {
        String ip;
        int port;
        try {
            ip = args[0];
            port = Integer.valueOf(args[1]);
            TCPClient client = new TCPClient(ip, port);
            new Thread(client).start();
        } catch (Exception e) {
            System.out.println("Invalid arguments. Try [127.0.0.1, 44460]");
        }
    }

    @Override
    public void run() {
        try (
                Socket echoSocket = new Socket(ip, port);
                PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        ) {
            while (true) {
                out.println("heartbeat");
                System.out.println("Response from server -> " + in.readLine());
                Thread.sleep(5000);
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + ip);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + ip);
            System.exit(1);
        } catch (InterruptedException e) {
            System.err.println("The thread has been interrupted");
            System.exit(1);
        }
    }
}
