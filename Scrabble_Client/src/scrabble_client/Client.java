/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabble_client;

import GUI.InitialFrame;
import java.io.IOException;

/**
 *
 * @author Hugo Pereira <your.name at your.org>
 */
public class Client {

    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(String args[]) throws IOException {
        InitialFrame scrabblegui = new InitialFrame("homeP");
        scrabblegui.pack();
        scrabblegui.setVisible(true);
    }
}
