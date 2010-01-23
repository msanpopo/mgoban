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

package wing;

import go.GoRank;

public class User implements Comparable<User>{
    private static final int DESCRIPTION_LENGTH = 16;
    protected String name;
    protected GoRank rank;
    
    private String str;
    
    public User(){
        this.name = "";
        this.rank = new GoRank();
        this.str = createDescription();
    }
    
    public User(String name, String rank) {
        this.name = name;
        this.rank = new GoRank(rank);
        this.str = createDescription();
    }
    
    private String createDescription(){
        StringBuilder s = new StringBuilder();
        s.append(" ");
        s.append(name);
        int n = DESCRIPTION_LENGTH - name.length() - 5;
        for(int i = 0; i < n; ++i){
            s.append(" ");
        }
        s.append(rank.getDescription());
        
        return s.toString();
    }
    
    @Override
    public String toString(){
        return str;
    }
    
    public String getName(){
        return name;
    }
    
    public GoRank getRank(){
        return rank;
    }
    
    public int compareTo(User u) {
        if(rank.compareTo(u.getRank()) == 0){
            if(name.equals(u.name)){
                return 0;
            }else{
                return name.compareTo(u.name);
            }
        }else{
            return rank.compareTo(u.getRank());
        }
    }
}