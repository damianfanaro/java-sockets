import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Simple TCP Server implementation using java.net
 *
 * @author Damian Fanaro (damianfanaro@gmail.com)
 */
public class TCPServer implements Runnable {

    private int port;

    public TCPServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {

        int port;

        try {
            port = Integer.valueOf(args[0]);
            TCPServer server = new TCPServer(port);
            new Thread(server).start();

        } catch (Exception e) {
            System.out.println("Invalid arguments. Try [44460]");
        }
    }

    @Override
    public void run() {
        try (
                ServerSocket serverSocket = new ServerSocket(port);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received from socket -> " + inputLine);
                out.println(AppInformation.getCurrentState());
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port " + port + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
