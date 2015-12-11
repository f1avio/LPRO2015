/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game_model;

/**
 *
 * @author HUGUETA
 */
public class Letter {
    protected Character Char;
    
    /**
	 * Array of all letters
	 */
	public static final Character[] letters = {'A','B','B','C','Ć','D','E','Ę','F','G','H','I','J','K','L','Ł','M','N','Ń','O','Ó','P','R','S','Ś','T','U','W','Y','Z','Ż','Ż','.'};
	private static final int[] letterValues = { 1,  5,  3,  2,  6,  2,  1,  5,  5,  3,  3,  1,  3,  2,  2,  3,  2,  1,  7,  1,  5,  2,  1,  1,  5,  2,  3,  1,  2,  1,  9,  5,  0 };
	private static final int[] amountInPool  = { 9,  1,  2,  3,  1,  3,  7,  1,  1,  2,  2,  8,  2,  3,  3,  2,  3,  5,  1,  6,  1,  3,  4,  4,  1,  3,  2,  4,  4,  5,  1,  1,  2 };

}
