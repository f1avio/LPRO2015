package scrabble_server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Allocates a thread to a new client.
 * @author Adam Kopnicky 
 * @author Ewa Godlewska 
 * @author Flavio Dias 
 * @author Hugo Pereira
 * @author Jose Carvalho
 */
class ClientThread extends Thread{
    
    private Socket connectionSocket = null;
    public ScrabbleServer server = null;
    int ID = -1;
    public String username = "";
    public DataInputStream inFromClient = null;
    public DataOutputStream outToClient = null;
    
    
    /**
     * Stores the connectionsocket and an instance of the class ScrabbleServer.
     * @param argServer The instance of the class ScrabbleServer.
     * @param argSocket connectionsocket.
     */
   public ClientThread(ScrabbleServer argServer, Socket argSocket)
    {
        super();
        server = argServer;
        connectionSocket = argSocket;
        ID = connectionSocket.getPort();
    }
   
   /**
    * Sends a message to the client.
    * @param msg The content of the message.
    */
   public void send(String msg) {
        try {
            outToClient.writeUTF(msg);
            outToClient.flush();
        } catch (IOException ex) {
            System.out.println("[Server][Socket]" + "Exception [SocketClient : send(...)]");
        }
    }
   /**
    * Return the port to which the socket is connected.
    * @return The port.
    */
   public int getID() {
        return ID;
    }

   /**
    * Method called when a new ClientThread object is started
    */
    @Override 
    public void run(){
        
        while(true) {
            try{
                String msg = inFromClient.readUTF();
                System.out.println(">> Received: " + msg);
                server.handle(ID, msg);
            } catch(Exception ex){
                System.out.println("[Server][Socket]" + ID + " ERROR reading: " + ex);
                server.remove(ID);
                break;
                //stop();
            }
        }
        
    }
    /**
     * Opens a DataOutputStream and a DataInputStream instance.
     * @throws IOException Exception produced by failed or interrupted I/O operations.
     */
    public void open() throws IOException {
        outToClient = new DataOutputStream(connectionSocket.getOutputStream());
        outToClient.flush();
        inFromClient = new DataInputStream(connectionSocket.getInputStream());
    }
    /**
     * Closes the socket, as well as the data input/output stream.
     * @throws IOException Exception produced by failed or interrupted I/O operations.
     */
    public void close() throws IOException {
        if (connectionSocket != null) {
            connectionSocket.close();
        }
        if (inFromClient != null) {
            inFromClient.close();
        }
        if (outToClient != null) {
            outToClient.close();
        }
    }
    
}
