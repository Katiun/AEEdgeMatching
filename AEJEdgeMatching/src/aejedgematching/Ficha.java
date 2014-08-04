/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aejedgematching;

import java.util.Arrays;

/**
 *
 * @author Usuario
 */
public class Ficha {
    
    private int nroFicha;
    private int rotacion;
    private int[] colores;
    private boolean esquina;
    private boolean borde;
    private boolean utilizada;
    private boolean cambioPosicion;

    
    
    
    public Ficha(int nroFicha, int[] colores) {
        this.nroFicha = nroFicha;
        this.rotacion = 0;
        this.colores = colores;
        this.esquina = (colores[0] == 0 && colores[3] == 0) || (colores[1] == 0 && colores[0] == 0) ||
                (colores[2] == 0 && colores[1] == 0) || (colores[3] == 0 && colores[2] == 0);
        this.borde = !this.esquina && 
                (colores[0] == 0 || colores[1] == 0 || colores[2] == 0 || colores[3] == 0);
    }

    public Ficha(int nroFicha, int rotacion, int[] colores) {
        this.nroFicha = nroFicha;
        this.rotacion = rotacion;
        this.colores = colores.clone();
        this.esquina = (colores[0] == 0 && colores[3] == 0) || (colores[1] == 0 && colores[0] == 0) ||
                (colores[2] == 0 && colores[1] == 0) || (colores[3] == 0 && colores[2] == 0);
        this.borde = !this.esquina && 
                (colores[0] == 0 || colores[1] == 0 || colores[2] == 0 || colores[3] == 0);
    }

    
    
    public int getNroFicha() {
        return nroFicha;
    }

    public void setNroFicha(int nroFicha) {
        this.nroFicha = nroFicha;
    }

    public int getRotacion() {
        return rotacion;
    }

    public void setRotacion(int rotacion) {
        this.rotacion = rotacion;
    }

    public int[] getColores() {
        return colores;
    }

    public void setColores(int[] colores) {
        this.colores = colores;
    }

    public boolean isEsquina() {
        return esquina;
    }

    public boolean isBorde() {
        return borde;
    }

    public boolean isUtilizada() {
        return utilizada;
    }

    public void setUtilizada(boolean utilizada) {
        this.utilizada = utilizada;
    }

    public boolean isCambioPosicion() {
        return cambioPosicion;
    }

    public void setCambioPosicion(boolean cambioPosicion) {
        this.cambioPosicion = cambioPosicion;
    }

    private void rotarFicha(){
        int color3 = colores[3];
        for (int i = 2; i >= 0; i--){
            colores[i+1] = colores[i];
        }
        colores[0] = color3;
    }

    private void antiRotacion(){
        int color0 = colores[0];
        for (int i = 1; i <= 3; i++){
            colores[i-1] = colores[i];
        }
        colores[3] = color0;
    }
    
    public void rotarFicha(int rotacion){
        switch (rotacion){
            case 1:
                rotarFicha();
                break;
            case 2:
                int colorOp = colores[0];
                colores[0] = colores[2];
                colores[2] = colorOp;
                colorOp = colores[1];
                colores[1] = colores[3];
                colores[3] = colorOp;
                break;
            case 3:
                antiRotacion();
                break;
        }
        this.rotacion += rotacion;
        this.rotacion = this.rotacion % 4;
    }
    
    @Override
    public Ficha clone(){
        return new Ficha(nroFicha, rotacion, colores.clone());
    }

    @Override
    public String toString(){
        return "Nro: " + nroFicha + " Rot: " + rotacion + " Col: " + Arrays.toString(colores);
    }
}
