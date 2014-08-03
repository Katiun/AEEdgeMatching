/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aejedgematching;

import java.util.Date;

/**
 *
 * @author Usuario
 */
public class Evento {
    
    private Date fecha;
    private String mensaje;

    public Evento(Date fecha, String mensaje) {
        this.fecha = fecha;
        this.mensaje = mensaje;
    }

    public Date getTiempo() {
        return fecha;
    }

    public void setTiempo(Date fecha) {
        this.fecha = fecha;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString(){
        return mensaje + ": " + fecha.getTime();
    }
}
