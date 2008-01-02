/*
 *  mGoban - GUI for Go
 *  Copyright (C) 2007  sanpo
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

public enum MatchType {
	MATCH("match"),
	EMATCH("ematch"),
	FMATCH("fmatch"),
	FEMATCH("fematch"),
	GMATCH("gmatch"),
	LMATCH("lmatch");
	
	private final String command;
	
	private MatchType(String command){
		this.command = command;
	}
	
	public static MatchType get(String str){
		for(MatchType type : MatchType.values()){
			if(str.equals(type.command)){
				return type;
			}
		}
		return null;
	}
	
	public String getCommand(){
		return command;
	}
	
	public String toString(){
		return command;
	}
}