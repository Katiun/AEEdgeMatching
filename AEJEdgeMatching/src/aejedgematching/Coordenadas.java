/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aejedgematching;

/**
 *
 * @author Usuario
 */
public class Coordenadas {
 
    private int fila;
    private int columna;

    public Coordenadas(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Coordenadas)){
            return false;
        }
        return fila == ((Coordenadas)obj).fila && ((Coordenadas)obj).columna == columna;
    }

    
}
