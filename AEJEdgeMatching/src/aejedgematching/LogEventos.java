/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aejedgematching;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class LogEventos {
    
    private List<Evento> eventos;

    public LogEventos() {
        this.eventos = new ArrayList<>();
    }
    
    public void agregarEvento(Evento evento){
        eventos.add(evento);
    }
    
    public void imprimirEventos(String nombreArchivo){
        
        FileOutputStream fos = null;
        PrintStream ps = null;
//        String path = System.getProperty("user.dir");
//        DateFormat format = new SimpleDateFormat("YYYYMMdHm");
//        path += "/logAD" + format.format(new Date()) + ".txt";
        
        try{
            fos = new FileOutputStream(nombreArchivo);
            ps = new PrintStream(fos);
            
            for(Evento ev : eventos){
                ps.println(ev);
            }
            
        }catch(FileNotFoundException ex){
            System.out.println(ex.getMessage());
        }finally{
            if (ps != null){
                ps.flush();
                ps.close();
            }
            try{
                if (fos != null){
                    fos.flush();
                    fos.close();
                }
            }catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }

    }

}
