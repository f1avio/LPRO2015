package scrabble_server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**@author  Adam Kopnicky
 *          Ewa Godlewska
 *          Flavio Dias
 *          Hugo Pereira
 *          Jose Carvalho
 */
class ClientThread extends Thread{
    
    private Socket connectionSocket = null;
    public ScrabbleServer server = null;
    int ID = -1;
    public String username = "";
    public DataInputStream inFromClient = null;
    public DataOutputStream outToClient = null;
    
    
    /**
     * ServiceClient constructor that stores the parameter socket
     * @param socket the socket that needs to be stored
     */
   public ClientThread(ScrabbleServer argServer, Socket argSocket)
    {
        super();
        server = argServer;
        connectionSocket = argSocket;
        ID = connectionSocket.getPort();
    }
   
   public void send(String msg) {
        try {
            outToClient.writeUTF(msg);
            outToClient.flush();
        } catch (IOException ex) {
            System.out.println("[Server][Socket]" + "Exception [SocketClient : send(...)]");
        }
    }
   
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
                System.out.println("[Server][Socket]" + "MSG = " + msg);
                server.handle(ID, msg);
            } catch(Exception ex){
                System.out.println("[Server][Socket]" + ID + " ERROR reading: " + ex);
                server.remove(ID);
                break;
                //stop();
            }
        }
        
    }
    
    public void open() throws IOException {
        outToClient = new DataOutputStream(connectionSocket.getOutputStream());
        outToClient.flush();
        inFromClient = new DataInputStream(connectionSocket.getInputStream());
    }
    
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
