/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabble_client;

import GUI.InicialFrame;
import java.io.IOException;

/**
 *
 * @author Hugo Pereira <your.name at your.org>
 */
public class Client {
    public static void main(String args[]) throws IOException {
        InicialFrame scrabblegui = new InicialFrame("homeP");
        scrabblegui.pack();
        scrabblegui.setVisible(true);
    }
}
