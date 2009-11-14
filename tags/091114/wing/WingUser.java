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

package wing;

import go.GoRank;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * コンストラクターの引数に渡されている文字列に先頭の "27 " はない。
27  Info       Name       Idle   Rank |  Info       Name       Idle   Rank
27  Qa --   29 DOKIDOKI*  45m     3d* |  Qu --   10 Hironi     14s     NR
27   X 56   -- Red        13s     NR  |  QX --   -- guest0086   1m     NR
27     14   -- guest0096  53s     NR  |     29   -- guest0120   9s     NR
 
27   g --    7 TakuXP      1m     1k* |   g --    7 Niko       40s     1k*
27  QX --   -- tomkit      4s     2d* |  Qg --   68 S2          1s     2d*
27  Qg --   14 nove9      14s     2d* |  Qg --   14 Tadao      27s     2d*
27   g --   68 kumax       1s     2d* |
27                 ******** 173 Players 54 Total Games ********
 */
public class WingUser extends User {
    private String origStr;
    
    private String info0;
    private String info1;
    private int observe;	// 観戦中の対局番号 （-1 は観戦してない）
    private int gameNo;	// 対局中の対局番号 （-1 は対局してない）
    
    private String idle;
    private int idleNum;
    
    private static final Pattern pw = Pattern.compile("^ (.)(.)(...) (....) (..........) (...)    (....)\\s*");
    private static final Pattern pi = Pattern.compile("\\s*(\\d+)([mshd])\\s*");
    
    public WingUser(String name, String rank){
        super(name, rank);
        
	this.origStr = "";
	this.info0 = "";
	this.info1 = "";
	this.observe = -1;
	this.gameNo = -1;
	this.idle = "";
	this.idleNum = 0;
    }
    
    public WingUser(String str){
        super();
        //" SX245   -- garykhuang  3s     6k*"

        // System.out.println(str);
        origStr = str;
        name = "";
        rank = null;
        info0 = "";
        info1 = "";
        observe = -1;
        gameNo = -1;
        idle = "";
        idleNum = 0;

        String observeStr = "--";
        String gameStr = "--";

        Matcher m = pw.matcher(str);
        if(m.matches()){
            info0 = m.group(1);
            info1 = m.group(2);
            observeStr = removeSpace(m.group(3));
            gameStr = removeSpace(m.group(4));
            name = removeSpace(m.group(5));
            idle = removeSpace(m.group(6));
            rank = new GoRank(removeSpace(m.group(7)));
        }else{
            System.err.println("error:User :can not parse:"+ str + ":");
        }

        //System.out.println(name + ":" + rank + ":" + info0 + info1 + ":" + idle + ":" + observeStr + ":" + gameStr + ":");

        if(observeStr.indexOf("--") > -1){
            observe = -1;
        }else{
            observe = Integer.parseInt(observeStr);
        }
        if(gameStr.indexOf("--") > -1){
            gameNo = -1;
        }else{
            gameNo = Integer.parseInt(gameStr);
        }

        Matcher mi = pi.matcher(idle);
        if(mi.matches()){
            int n = Integer.parseInt(mi.group(1));
            String s = mi.group(2);
            if(s.equals("s")){
            idleNum = n;
            }else if(s.equals("m")){
            idleNum = n * 60;
            }else if(s.equals("h")){
            idleNum = n * 60 * 60;
            }else if(s.equals("d")){
            idleNum = n * 60 * 60 * 24;
            }
        }else{
            System.err.println("error:User :can not parse idle_str:"+ idle);
        }
    }
    
    public int getGameNo(){
        return gameNo;
    }
    
    @Override
    public String toString(){
        return origStr;
    }

    public String getDebugString(){
        return origStr;
    }
    
    private static final Pattern pspace = Pattern.compile("\\s*(\\S+)\\s*");
    private String removeSpace(String s){
	Matcher mspace = pspace.matcher(s);
	if(mspace.matches()){
	    return mspace.group(1);
	}else{
	    System.err.println("error:User :removeSpace fail:"+ s);
	    return "";
	}
    }
}