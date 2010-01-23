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

package sgf.value;

public class ValueReal implements Value{
    private double real;
    
    public ValueReal() {
        real = 0.0;
    }
    
    public ValueReal(double r){
        real = r;
    }

    public void setSgfString(String str) {
        try{
            real = Double.parseDouble(str);
        }catch(NumberFormatException e){
            // 新浪囲棋 で TM プロパティーに数字以外が入っている
            System.err.println("err:ValueReal:str:" + str + ":" + e);
            real = 0.0;
        }
    }

    public String getSgfString(){
        return Double.toString(real);
    }
    
    public String getValueString() {
        StringBuilder str = new StringBuilder();
        str.append("[");
        str.append(getSgfString());
        str.append("]");
        
        return str.toString();
    }
        
    public double getReal(){
        return real;
    }
    
    public void setReal(double real){
        this.real = real;
    }
}