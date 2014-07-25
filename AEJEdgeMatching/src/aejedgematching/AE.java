/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aejedgematching;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Usuario
 */
public class AE {
    
    
    public ArrayList<Individuo> cruzamiento(Individuo padre1, Individuo padre2){
        ArrayList<Individuo> hijos = new ArrayList<>();
        
        int corteEsquina = (int)(Math.random() * 3);
        corteEsquina = 1;
        int corteBorde = (int)(Math.random() * ((Parametros.DIMENSION_TABLERO - 2) * 4) - 1);
        corteBorde = 2;
        int fila = (int)(Math.random() * (Parametros.DIMENSION_TABLERO - 2));
        int columna = (int)(Math.random() * (Parametros.DIMENSION_TABLERO - 2));
        int largoFila = (int)(Math.random() * (Parametros.DIMENSION_TABLERO - 2 - fila));
        int largoColumna = (int)(Math.random() * (Parametros.DIMENSION_TABLERO - 2 - columna));
        
        ArrayList<Ficha[]> nuevasEsquinas = corteSPX(4, corteEsquina, padre1.getEsquinas(), padre2.getEsquinas());
        ajustarEsquinas(nuevasEsquinas.get(0), nuevasEsquinas.get(1));

        ArrayList<Ficha[]> nuevosBordes = corteSPX((Parametros.DIMENSION_TABLERO - 2) * 4, corteBorde, padre1.getBordes(), padre2.getBordes());
        ajustarBordes(nuevosBordes.get(0), nuevosBordes.get(1));
        
        System.out.println("corte esquina: " + corteEsquina);
        System.out.println("corte borde: " + corteBorde);
        System.out.println("corte fila: " + fila);
        System.out.println("corte columna: " + columna);
        System.out.println("corte largo fila: " + largoFila);
        System.out.println("corte largo columna: " + largoColumna);
        
        return hijos;
    }
    
    /**
     * Realiza corte de un punto para los padres
     * @param largo Tamaño de los padres
     * @param corte Lugar donde se realiza el corte
     * @param fichasPadre1 Gen primer padre
     * @param fichasPadre2 Gen segundo padre
     * @return 
     */
    private ArrayList<Ficha[]> corteSPX(int largo, int corte, Ficha[] fichasPadre1, Ficha[] fichasPadre2){
        
        ArrayList<Ficha[]> esquinas = new ArrayList<>();
        Ficha[] esquinasHijo1 = new Ficha[largo];
        Ficha[] esquinasHijo2 = new Ficha[largo];
        HashMap<Integer, Integer> map10 = new HashMap<>();
        HashMap<Integer, Integer> map11 = new HashMap<>();
        HashMap<Integer, Integer> map20 = new HashMap<>();
        HashMap<Integer, Integer> map21 = new HashMap<>();
        int val1, val2;
        
        for (int indiceMantener = 0; indiceMantener <= corte; indiceMantener++){
            
            esquinasHijo1[indiceMantener] = fichasPadre1[indiceMantener].clone();
            esquinasHijo2[indiceMantener] = fichasPadre2[indiceMantener].clone();
            
            if (map20.containsKey(esquinasHijo1[indiceMantener].getNroFicha())){
                val2 = map20.get(esquinasHijo1[indiceMantener].getNroFicha());
                map10.remove(val2);
                map10.put(val2, esquinasHijo2[indiceMantener].getNroFicha());
                map20.remove(esquinasHijo1[indiceMantener].getNroFicha());
                map20.put(esquinasHijo2[indiceMantener].getNroFicha(), val2);
            }else if (map10.containsKey(esquinasHijo2[indiceMantener].getNroFicha())){
                val1 = map10.get(esquinasHijo2[indiceMantener].getNroFicha());
                map20.remove(val1);
                map20.put(val1, esquinasHijo1[indiceMantener].getNroFicha());
                map10.remove(esquinasHijo2[indiceMantener].getNroFicha());
                map10.put(esquinasHijo1[indiceMantener].getNroFicha(), val1);
            }else{
                map10.put(esquinasHijo1[indiceMantener].getNroFicha(), esquinasHijo2[indiceMantener].getNroFicha());
                map20.put(esquinasHijo2[indiceMantener].getNroFicha(), esquinasHijo1[indiceMantener].getNroFicha());
            }
            map11.put(esquinasHijo1[indiceMantener].getNroFicha(), indiceMantener);
            map21.put(esquinasHijo2[indiceMantener].getNroFicha(), indiceMantener);
        }

        for (int indiceCambiar = corte + 1; indiceCambiar < largo; indiceCambiar++){
            if (map10.containsKey(fichasPadre2[indiceCambiar].getNroFicha())){
                esquinasHijo1[indiceCambiar] = fichasPadre2[map21.get(map10.get(fichasPadre2[indiceCambiar].getNroFicha()))].clone();
            }else{
                esquinasHijo1[indiceCambiar] = fichasPadre2[indiceCambiar].clone();
            }
            
            if (map20.containsKey(fichasPadre1[indiceCambiar].getNroFicha())){
                esquinasHijo2[indiceCambiar] = fichasPadre1[map11.get(map20.get(fichasPadre1[indiceCambiar].getNroFicha()))].clone();
            }else{
                esquinasHijo2[indiceCambiar] = fichasPadre1[indiceCambiar].clone();
            }
        }
        
        esquinas.add(esquinasHijo1);
        esquinas.add(esquinasHijo2);
        
        return esquinas;
    }

    /**
     * Ajusta las esquinas para dejar los grises hacia afuera
     * @param esquinasHijo1 Esquinas del primer hijo
     * @param esquinasHijo2 Esquinas del segundo hijo
     */
    private void ajustarEsquinas(Ficha[] esquinasHijo1, Ficha[] esquinasHijo2){
        
        int indicePatron1 = 0, indicePatron2 = 0;
        
        for (int indice = 0; indice < 4; indice++){
            switch (indice){
                case 0:
                    indicePatron1 = 0;
                    indicePatron2 = 3;
                    break;
                case 1:
                    indicePatron1 = 1;
                    indicePatron2 = 0;
                    break;
                case 2:
                    indicePatron1 = 3;
                    indicePatron2 = 2;
                    break;
                case 3:
                    indicePatron1 = 2;
                    indicePatron2 = 1;
                    break;
            }
            
            while ((esquinasHijo1[indice].getColores()[indicePatron1] != 0) || (esquinasHijo1[indice].getColores()[indicePatron2] != 0)){
                esquinasHijo1[indice].rotarFicha(1);
            }
            while ((esquinasHijo2[indice].getColores()[indicePatron1] != 0) || (esquinasHijo2[indice].getColores()[indicePatron2] != 0)){
                esquinasHijo2[indice].rotarFicha(1);
            }
        }
    }
    
    /**
     * Ajusta los bordes para dejar los grises hacia afuera
     * @param bordesHijo1 Bordes del primer hijo
     * @param bordesHijo2 Bordes del segundo hijo
     */
    private void ajustarBordes(Ficha[] bordesHijo1, Ficha[] bordesHijo2){
        
        for (int indice = 0; indice < Parametros.DIMENSION_TABLERO - 2; indice++){
            while (bordesHijo1[indice].getColores()[0] != 0){
                bordesHijo1[indice].rotarFicha(1);
            }
            while (bordesHijo2[indice].getColores()[0] != 0){
                bordesHijo2[indice].rotarFicha(1);
            }
        }
        
        int patron = 3;
        for (int indice = Parametros.DIMENSION_TABLERO - 2; indice < (Parametros.DIMENSION_TABLERO - 2) * 3; indice++){
            while (bordesHijo1[indice].getColores()[patron] != 0){
                bordesHijo1[indice].rotarFicha(1);
            }
            while (bordesHijo2[indice].getColores()[patron] != 0){
                bordesHijo2[indice].rotarFicha(1);
            }
            if (patron == 1){
                patron = 3;
            }else{
                patron = 1;
            }
        }
        
        for (int indice = (Parametros.DIMENSION_TABLERO - 2) * 3; indice < (Parametros.DIMENSION_TABLERO - 2) * 4; indice++){
            while (bordesHijo1[indice].getColores()[2] != 0){
                bordesHijo1[indice].rotarFicha(1);
            }
            while (bordesHijo2[indice].getColores()[2] != 0){
                bordesHijo2[indice].rotarFicha(1);
            }
        }
        
    }
 
    private ArrayList<Ficha[][]> corteRegion(int filaCorte, int columnaCorte, 
            int largoCorteFila, int largoCorteColumna, 
            Ficha[][] interiorPadre1, Ficha[][] interiorPadre2){
        
        ArrayList<Ficha[][]> interiores = new ArrayList<>();
        
        Ficha[][] hijo1Padre1 = new Ficha[Parametros.DIMENSION_TABLERO - 2][Parametros.DIMENSION_TABLERO - 2];
        Ficha[][] hijo2Padre1 = new Ficha[Parametros.DIMENSION_TABLERO - 2][Parametros.DIMENSION_TABLERO - 2];
        Ficha[][] hijo3Padre1 = new Ficha[Parametros.DIMENSION_TABLERO - 2][Parametros.DIMENSION_TABLERO - 2];
        Ficha[][] hijo4Padre1 = new Ficha[Parametros.DIMENSION_TABLERO - 2][Parametros.DIMENSION_TABLERO - 2];
        Ficha[][] hijo1Padre2 = new Ficha[Parametros.DIMENSION_TABLERO - 2][Parametros.DIMENSION_TABLERO - 2];
        Ficha[][] hijo2Padre2 = new Ficha[Parametros.DIMENSION_TABLERO - 2][Parametros.DIMENSION_TABLERO - 2];
        Ficha[][] hijo3Padre2 = new Ficha[Parametros.DIMENSION_TABLERO - 2][Parametros.DIMENSION_TABLERO - 2];
        Ficha[][] hijo4Padre2 = new Ficha[Parametros.DIMENSION_TABLERO - 2][Parametros.DIMENSION_TABLERO - 2];
        
        int filaRot1 = filaCorte + largoCorteFila;
        int columnaRot1 = columnaCorte + largoCorteColumna;
        int filaRot2 = filaCorte + largoCorteFila;
        int columnaRot2 = columnaCorte + largoCorteColumna;
        int filaRot3 = filaCorte + largoCorteFila;
        int columnaRot3 = columnaCorte;
        
        for (int fila = 0; fila < Parametros.DIMENSION_TABLERO - 2; fila++){
            for (int columna = 0; columna < Parametros.DIMENSION_TABLERO - 2; columna++){
                //Estoy fuera de la región que quiero intercambiar, copio la misma información del padre
                if ((fila < filaCorte) || (fila > filaCorte + largoCorteFila) ||
                        (columna < columnaCorte) || (columna > columnaCorte + largoCorteColumna)){
                    
                    hijo1Padre1[fila][columna] = interiorPadre1[fila][columna].clone();
                    hijo2Padre1[fila][columna] = interiorPadre1[fila][columna].clone();

                    hijo1Padre2[fila][columna] = interiorPadre2[fila][columna].clone();
                    hijo2Padre2[fila][columna] = interiorPadre2[fila][columna].clone();

                    if (largoCorteFila == largoCorteColumna){
                        //Si la región es cuadrada se agregan 2 hijos más para el padre 1
                        hijo3Padre1[fila][columna] = interiorPadre1[fila][columna].clone();
                        hijo4Padre1[fila][columna] = interiorPadre1[fila][columna].clone();
                        
                        //Si la región es cuadrada se agregan 2 hijos más para el padre 2
                        hijo3Padre2[fila][columna] = interiorPadre2[fila][columna].clone();
                        hijo4Padre2[fila][columna] = interiorPadre2[fila][columna].clone();
                    }
                    
                }else{
                    
                    hijo1Padre1[fila][columna] = interiorPadre2[fila][columna].clone();
                    hijo1Padre2[fila][columna] = interiorPadre1[fila][columna].clone();
                    
                    hijo2Padre1[filaRot2][columnaRot2] = interiorPadre2[fila][columna].clone();
                    hijo2Padre1[filaRot2][columnaRot2].rotarFicha(2);
                    hijo2Padre2[filaRot2][columnaRot2] = interiorPadre1[fila][columna].clone();
                    hijo2Padre2[filaRot2][columnaRot2].rotarFicha(2);
                    
                    columnaRot2--;
                    if (columnaRot2 < columnaCorte){
                        columnaRot2 = columnaCorte + largoCorteColumna;
                        filaRot2--;
                    }

                    if (largoCorteFila == largoCorteColumna){
                        
                        hijo3Padre1[filaRot1][columnaRot1] = interiorPadre2[fila][columna].clone();
                        hijo3Padre1[filaRot1][columnaRot1].rotarFicha(1);
                        hijo3Padre2[filaRot1][columnaRot1] = interiorPadre1[fila][columna].clone();
                        hijo3Padre2[filaRot1][columnaRot1].rotarFicha(1);
                        
                        filaRot1--;
                        if (filaRot1 < filaCorte){
                            filaRot1 = filaCorte + largoCorteFila;
                            columnaRot1--;
                        }
                        
                        hijo4Padre1[filaRot3][columnaRot3] = interiorPadre2[fila][columna].clone();
                        hijo4Padre1[filaRot3][columnaRot3].rotarFicha(3);
                        hijo4Padre2[filaRot3][columnaRot3] = interiorPadre1[fila][columna].clone();
                        hijo4Padre2[filaRot3][columnaRot3].rotarFicha(3);
                        
                        filaRot3--;
                        if (filaRot3 < filaCorte){
                            filaRot3 = filaCorte + largoCorteFila;
                            columnaRot3++;
                        }
                        
                    }
                    
                }
            }
        }

        interiores.add(hijo1Padre1);
        interiores.add(hijo2Padre1);
        interiores.add(hijo1Padre2);
        interiores.add(hijo2Padre2);
        
        if (largoCorteFila == largoCorteColumna){
            interiores.add(hijo3Padre1);
            interiores.add(hijo4Padre1);
            interiores.add(hijo3Padre2);
            interiores.add(hijo4Padre2);
        }
        
        return interiores;
    }

}
