/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabble_client;

import GUI.InitialFrame;
import java.io.IOException;

/**@author  Adam Kopnicky
 *          Ewa Godlewska
 *          Flavio Dias
 *          Hugo Pereira
 *          Jose Carvalho
 */
public class Client {

    /**
     * Initiates the Graphical Interface, redirecting the client to the Homepage
     * @param args Arguments passed from the command line. They are ignored.
     */
    public static void main(String args[])  {
        InitialFrame scrabblegui = new InitialFrame("homeP");
        scrabblegui.pack();
        scrabblegui.setVisible(true);
    }
}
