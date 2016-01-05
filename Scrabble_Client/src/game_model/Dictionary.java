/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game_model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.TreeSet;

import javax.swing.JOptionPane;

/**
 * Retrieves information from the dictionary.
 * @author José Eduardo
 */
public class Dictionary {
            private TreeSet<String> ts = new TreeSet <>();
            /**
             * Constructor that initializes the tree containing the dictionary.
             * <p> It starts by opening a the file Dictionary.txt and then copies
             * its information to the program.
             */
            public Dictionary()
            {
                try
                {
                    BufferedReader br = new BufferedReader (new FileReader ("Dictionary.txt"));
                    String line;
                    
                    while ((line = br.readLine()) != null)
                    {
                        ts.add(line.toUpperCase());
                    }
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(null,"Dictionary.txt not found!","Error",JOptionPane.ERROR_MESSAGE);
                }
              
            }
            /**
             * Verifies if a certain word exists on the dictionary.
             * @param w The word that will be verified.
             * @return A boolean stating if the dictionary contains the word.
             */
             public boolean checkWord(String w)
            {
                return ts.contains(w);
            }
                
}
    

