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
 *
 * @author José Eduardo
 */
public class Dictionary {
            private TreeSet<String> ts = new TreeSet <String>();
            
            public Dictionary()
            {
                try
                {
                    BufferedReader br = new BufferedReader (new FileReader ("Dictionary.txt"));
                    String line = new String();
                    
                    while ((line = br.readLine()) != null)
                    {
                        ts.add(line.toUpperCase());
                    }
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(null,"Dictionary.txt not found!","Error",JOptionPane.ERROR_MESSAGE);
                }
              
            }
            
             public boolean checkWord(String w)
            {
                return ts.contains(w);
            }
                
}
    

