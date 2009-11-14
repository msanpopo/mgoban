/*
 *  mGoban - GUI for Go
 *  Copyright (C) 2007, 2009  sanpo
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */   

package wing.message;

import java.util.Collection;
import java.util.TreeSet;

import wing.WingGame;
import wing.Block;

/*
7 [##]  white name [ rk ]      black name [ rk ] (Move size H Komi BY FR) (###)
7 [ 2]       os111 [ 1k*] vs.        gift [ 3k*] (104   19  2  -1.5 10  I) (  0)
7 [ 5]     mrymkro [ 3k*] vs.       AROMA [ 5k*] ( 49   19  3  3.5  8  I) (  1)
7 [ 6]     chako18 [ 7k*] vs.         mel [ 7k*] (131   19  0  3.5 10  I) (  0)
7 [ 7]        Niko [ 1k*] vs.      TakuXP [ 1k*] (125   19  0  3.5 12  I) (  3)
7 [ 8]      zin178 [ 2k*] vs.      pinsan [ 3k*] (116   19  0  -3.5 12  I) (  0)

略

7 [61]      simo63 [ 9k ] vs.    hamcourt [10k*] ( 93   19  2  -0.5 10  I) (  0)
7 [62]       wendy [ 3k*] vs.     kenyama [ 3k*] ( 30   19  0  0.5 10  I) (  0)
7 [63]      gusaku [ 5k*] vs.      kin295 [ 5k*] ( 55   19  0  5.5 10  I) (  0)
7 [64]         snn [ 8k*] vs.      kon201 [ 8k*] (124   19  0  5.5  8  I) (  0)
7 [65]       nonki [ 8k*] vs.   fukushima [ 9k*] ( 13   19  0  -3.5 10  I) (  0)
 */

public class GamesMessage extends Message {
    private TreeSet<WingGame> gameSet;
    
    public GamesMessage(Block block){
        super(block);
        
        boolean firstline = true;
        
        gameSet = new TreeSet<WingGame>();
        
        for (String s : block.getMessageCollection()){
            if(firstline){
                firstline = false;
                continue;
            }
            WingGame game = new WingGame(s);
            if(game.isInitialized()){
                gameSet.add(new WingGame(s));
            }else{
                System.err.println("err:GameMessage: game is not initialized:" + game.toString());
            }
        }
    }

    public Collection<WingGame> getGameCollection(){
        return gameSet;
    }
    
    public int size(){
        return gameSet.size();
    }
    
    public WingGame first(){
        return gameSet.first();
    }
}