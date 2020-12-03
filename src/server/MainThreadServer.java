/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author Admin
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Usage;

/**
 *
 * @author lamit
 */
public class MainThreadServer implements Runnable {
    
    private String serverHost;
    private int serverPort;
    private Socket clientSocket;
    private ServerSocket server;

    public MainThreadServer() {
        serverPort = Usage.port;
        this.serverHost = Usage.serverHost;
    }

    //this threaed will run forever util crash by user
    //so server always get any interact from client
    @Override
    public void run() {
        while (true) {
            try {
                server = new ServerSocket(serverPort);
                while (!Thread.currentThread().isInterrupted()) {
                    clientSocket = server.accept();
                    
                    System.out.println(clientSocket.getInetAddress());
                    System.out.println(clientSocket.getRemoteSocketAddress());
                    Thread thread = new Thread(new ServerControl(clientSocket));
                    thread.start();
                }
                Thread.sleep(100);
                clientSocket.close();
            } catch (InterruptedException ex) {
                Logger.getLogger(MainThreadServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MainThreadServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}