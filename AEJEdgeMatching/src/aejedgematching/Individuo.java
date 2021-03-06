/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aejedgematching;

import java.util.Comparator;

/**
 *
 * @author Usuario
 */
public class Individuo {
    
    private Ficha[] esquinas;
    private Ficha[] bordes;
    private Ficha[][] interior;
    private int fitness;

    public Individuo(Ficha[] esquinas, Ficha[] bordes, Ficha[][] interior) {
        this.esquinas = esquinas;
        this.bordes = bordes;
        this.interior = interior;
        this.fitness = -1;
    }

    public Ficha[] getEsquinas() {
        return esquinas;
    }

    public void setEsquinas(Ficha[] esquinas) {
        this.esquinas = esquinas;
    }

    public Ficha[] getBordes() {
        return bordes;
    }

    public void setBordes(Ficha[] bordes) {
        this.bordes = bordes;
    }

    public Ficha[][] getInterior() {
        return interior;
    }

    public void setInterior(Ficha[][] interior) {
        this.interior = interior;
    }
    
    public int getFitness(){
        if (fitness < 0){
            int indiceEsquinas = 0;
            int indiceBordes = 0;
            fitness = 0;
            Ficha[][] rep = new Ficha[Parametros.DIMENSION_TABLERO][Parametros.DIMENSION_TABLERO];
            
            //armo el puzzle como quedaria
            for (int fila = 0; fila < Parametros.DIMENSION_TABLERO; fila++){
                for (int columna = 0; columna < Parametros.DIMENSION_TABLERO; columna++){
                    if ((fila == 0 && columna == 0) || (fila == 0 && columna == Parametros.DIMENSION_TABLERO - 1) ||
                            (fila == Parametros.DIMENSION_TABLERO - 1 && columna == 0) || (fila == Parametros.DIMENSION_TABLERO - 1 && columna == Parametros.DIMENSION_TABLERO - 1)){
                        rep[fila][columna] = esquinas[indiceEsquinas];
                        indiceEsquinas++;
                    }else if ((fila == 0) || (fila == Parametros.DIMENSION_TABLERO - 1) || 
                            (columna == 0) || (columna == Parametros.DIMENSION_TABLERO - 1)){
                        rep[fila][columna] = bordes[indiceBordes];
                        indiceBordes++;
                    }else{
                        rep[fila][columna] = interior[fila - 1][columna - 1];
                    }
                }
            }

            //calculo del fitness con el puzle armado
            for (int fila = 0; fila < Parametros.DIMENSION_TABLERO - 1; fila++){
                for (int columna = 0; columna < Parametros.DIMENSION_TABLERO - 1; columna++){
                    if (rep[fila][columna].getColores()[1] == rep[fila][columna + 1].getColores()[3]){
                        fitness++;
                    }
                    if (rep[fila][columna].getColores()[2] == rep[fila + 1][columna].getColores()[0]){
                        fitness++;
                    }
                }
            }
            
            for (int fila = 0; fila < Parametros.DIMENSION_TABLERO - 1; fila++){
                if (rep[fila][Parametros.DIMENSION_TABLERO - 1].getColores()[2] == rep[fila + 1][Parametros.DIMENSION_TABLERO - 1].getColores()[0]){
                    fitness++;
                }
            }

            for (int columna = 0; columna < Parametros.DIMENSION_TABLERO - 1; columna++){
                if (rep[Parametros.DIMENSION_TABLERO - 1][columna].getColores()[1] == rep[Parametros.DIMENSION_TABLERO - 1][columna + 1].getColores()[3]){
                    fitness++;
                }
            }
        }
        return fitness;
    }

    public int compareTo(Individuo individuo){
        return individuo.getFitness() - this.getFitness();
    }
    
    public static Comparator<Individuo> getComparator() {
        return new Comparator<Individuo>() {
            @Override
            public int compare(Individuo individuo1, Individuo individuo2) {
                return (individuo2.getFitness() - individuo1.getFitness());
            }
        };
    }

    @Override
    public Individuo clone(){
        Individuo ret = new Individuo(esquinas.clone(), bordes.clone(), interior.clone());
        ret.fitness = getFitness();
        return ret;
    }

}
