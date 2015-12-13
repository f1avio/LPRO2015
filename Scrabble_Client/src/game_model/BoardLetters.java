/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game_model;

/*
 *
 * @author HUGUETA
 */
public class BoardLetters extends Letter {
    
    private int x;
    private int y;
    
    private int crossingWordStarts;
    private int crossingWordEnds;
	
    public BoardLetters(Letter l, int x, int y)
    {
        super(l.getChar());
		
        this.x = x;
        this.y = y;
    }
    
    /*
    * @return the x
    */
    public int getX() {
        return x;
    }

    /*
    * @return the y
    */
    public int getY() {
        return y;
    }

    /*
    * @return the crossingWordStarts
    */
    public int getCrossingWordStarts() {
        return crossingWordStarts;
    }
    
    /*
    * @param crossingWordStarts the crossingWordStarts to set
    */
    public void setCrossingWordStarts(int crossingWordStarts) {
        this.crossingWordStarts = crossingWordStarts;
    }

    /*
    * @return the crossingWordEnds
    */
    public int getCrossingWordEnds() {
        return crossingWordEnds;
    }

    /*
    * @param crossingWordEnds the crossingWordEnds to set
    */
    public void setCrossingWordEnds(int crossingWordEnds) {
        this.crossingWordEnds = crossingWordEnds;
    }
    
}
