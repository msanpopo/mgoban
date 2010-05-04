/*
 *  mGoban - GUI for Go
 *  Copyright (C) 2007, 2009, 2010  sanpo
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

package go;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GoRank implements Comparable<GoRank>{
	/*
	 * UNKNOWN	:-3
	 * "NR"		:-2
	 * "30k"	:1
	 * "1k"		:30
	 * "1d"		:31
	 * "9d"		:39
	 * "1p"		:40
	 * "9p"		:48
	 */
    private String origStr;
    private int rank;
    private boolean isSolid;
    
    private static final Pattern p = Pattern.compile("(\\d+)([kdp])(.*)");
    
    public GoRank(){
        origStr = "";
        rank = -3;
        isSolid = false;
    }
    
    public GoRank(String str){
       // System.out.println("GoRank:" + str + ":");

        origStr = str;
        if(str.equals("NR")){
            rank = -2;
            isSolid = true;
        }else{
            Matcher m = p.matcher(str);

            if(m.matches()){
            int n = Integer.parseInt(m.group(1));
            String kdp = m.group(2);
                    String sld = m.group(3);
            //System.out.println("Rank:" + n + ":" + kdp + ":" + sld + ":");
            if(kdp.equals("k")){
                rank = 31 - n;
            }else if(kdp.equals("d")){
                rank = n + 30;
            }else if(kdp.equals("p")){
                rank = n + 39;
            }else{
                rank = -3;
                System.out.println("Rank: kdp ????:" + str);
            }

            if (sld.length() == 1){		// ”*” あり
                isSolid = true;
            }else{	// ”*” なし
                         isSolid = false;
            }
            }else{
            isSolid = false;
            rank = -3;

            System.out.println("Rank:????:" + str + ":");
            }
        }
    }
    
    // NR や Unknown まで比較してしまっているのは問題かも
    public int compareTo(GoRank rank){
        if(this.rank < rank.rank){
            return -1;
        }else if(this.rank == rank.rank){
            return 0;
        }else{
            return 1;
        }
    }
    
    @Override
    public String toString(){
        return origStr;
    }
    
    public String getDescription(){
        StringBuilder s = new StringBuilder();
        s.append(origStr);
        return s.toString();
    }
    
    public int getRankNumber(){
        return rank;
    }
    
    public boolean isSolid(){
        return isSolid;
    }
    
    public boolean isUnknown(){
        if(rank == -3){
            return true;
        }else{
            return false;
        }
    }
    
    public void debugPrint(){
        System.out.println("Rank:" + rank);
    }
}