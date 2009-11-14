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
package go.gui;

import app.App;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

public class InputList {

    private ArrayList<String> list;
    private File file;

    public InputList(){
        this.list = new ArrayList<String>();
        this.file = null;
    }
    
    public InputList(String filename) {
        this.list = new ArrayList<String>();
        this.file = new File(App.getInstance().getAppDir(), filename);
        
        read(filename);
    }
    
    public Collection<String> getCollection(){
        return list;
    }
    
    public void clear(){
        list.clear();
    }
    
    public void add(String text){
        list.add(text);
    }

    private void read(String filename) {
        if(file == null){
            System.err.println("err:InputList: file == null");
            return;
        }
        
        BufferedReader in = null;
        
        try {
            InputStream defaultStream = this.getClass().getClassLoader().getResourceAsStream(filename);

            if (file.exists() == true) {
                in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            } else {
                in = new BufferedReader(new InputStreamReader(defaultStream, "UTF-8"));
            }

            while (true) {
                String l = in.readLine();

                if (l == null || l.isEmpty()) {
                    break;
                }
                System.out.println(this.getClass() + ":text list:" + l);
                list.add(l);
            }
        } catch (Exception ex) {
            System.out.println(this.getClass() + ":text list:" + ex);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    public void write(){
        PrintWriter out = null;
	
        try {
            out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
 
	    for(String s : list){
		out.println(s);
	    }
	} catch (Exception ex) {
	    System.err.println(this.getClass() + ": write:" + ex);
	} finally{
            if(out != null){
                out.close();
            }
        }
    }
}
