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

package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Config{
    private Properties prop;
    private File cfgFile;
    
    public Config(File cfgFile){
        this.prop = new Properties();
        this.cfgFile = cfgFile;
        
        InputStream is = null;
        
        try {
            is = new FileInputStream(cfgFile);
            
            try{
                prop.load(is);
            }catch(Exception e){
                System.out.println("Config prop.load:error e:" + e);
            }
            
        }catch(FileNotFoundException e){
            System.err.println(this.getClass() + ":" + e);
            // デフォルト値のセット
            for(GoConfig gc : GoConfig.values()){
                setProperty(gc, gc.getDefaultValue());
            }
        }finally{
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    System.out.println("Config prop.read close error e:" + e);
                }
            }
        }
    }
    
    public void setProperty(GoConfig goConfig, String value){
        if(value == null){
            System.err.println("error:Config.setProperty: value is null");
            return;
        }
        prop.setProperty(goConfig.getKey(), value);
    }
    
    public String getProperty(GoConfig goConfig){
        String value =  prop.getProperty(goConfig.getKey());
        
        // System.out.println("getProperty : name:" + propertyName + " value:" + value);
        
        if(value == null){
            value = goConfig.getDefaultValue();
            prop.setProperty(goConfig.getKey(), goConfig.getDefaultValue());
        }
        return value;
    }
    
    public void setBooleanProperty(GoConfig goConfig, boolean value){
        prop.setProperty(goConfig.getKey(), Boolean.toString(value));
    }
    
    public boolean getBooleanProperty(GoConfig goConfig){
        String value =  prop.getProperty(goConfig.getKey());
        
        // System.out.println("getProperty : name:" + propertyName + " value:" + value);
        
        if(value == null){
            value = goConfig.getDefaultValue();
            prop.setProperty(goConfig.getKey(), goConfig.getDefaultValue());
        }
        return Boolean.valueOf(value);
    }
    
    public void setIntProperty(GoConfig goConfig, int value){
        prop.setProperty(goConfig.getKey(), Integer.toString(value));
    }
    
    public int getIntProperty(GoConfig goConfig){
        String value =  prop.getProperty(goConfig.getKey());
        
        //System.out.println("getProperty : name:" + goConfig.getKey() + " value:" + value);
        
        if(value == null){
            value = goConfig.getDefaultValue();
            prop.setProperty(goConfig.getKey(), goConfig.getDefaultValue());
        }
        return Integer.parseInt(value);
    }
    
    public void write(){
        OutputStream os = null;
        
        try{
            os = new FileOutputStream(cfgFile);
            prop.store(os, null);
        }catch(FileNotFoundException e){
            System.err.println(this.getClass() + ":" + e);
        }catch (IOException e){
            System.err.println(this.getClass() + ":" + e);
        }finally{
            if(os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    System.out.println("Config prop.write close error e:" + e);
                }
            }
        }
    }
}