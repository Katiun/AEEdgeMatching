/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aejedgematching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Usuario
 */
public class AE {
    
    private boolean testCruzamiento;
    private int corteEsquina;
    private int corteBorde;
    private int inicioCorteFila;
    private int inicioCorteColumna;
    private int largoCorteFila;
    private int largoCorteColumna;

    private boolean testMutacion;
    private int probabilidadZona;
    private int fichaMutacion1;
    private int fichaMutacion2;
    
    private Parametros parametros;

    public AE(){
        testCruzamiento = false;
        testMutacion = false;
    }
    
    public AE(Parametros params){        
        this.parametros = params;
    }

    /**
     * Setea las variables utilizadas para los algoritmos de cruzamiento y mutación
     * @param corteEsquina es donde se corta las fichas de las esquinas para el cruzamiento, debe ser >= 0 y < 4
     * @param corteBorde es donde se corta las fichas de los bordes para el cruzamiento, debe ser >= 0 y < (parametros.DIMENSION_TABLERO - 2) * 4
     * @param inicioCorteFila fila inicial del corte de la región interna, debe ser >= 0 y < (parametros.DIMENSION_TABLERO - 2)
     * @param inicioCorteColumna columna inicial del corte de la región interna, debe ser >= 0 y < (parametros.DIMENSION_TABLERO - 2)
     * @param largoCorteFila largo de fila del corte de la región interna, debe ser >= 0 y < (parametros.DIMENSION_TABLERO - 2 - inicioCorteFila)
     * @param largoCorteColumna largo de columna del corte de la región interna, debe ser >= 0 y < (parametros.DIMENSION_TABLERO - 2 - inicioCorteColumna)
     * @param probabilidadZona indica que zona debe ser mutada,
        [0,3] se mutan las esquinas
        [4,(parametros.DIMENSION_TABLERO - 1) * 4) se mutan los bordes
        >= (parametros.DIMENSION_TABLERO - 1) * 4 se muta el interior
     * @param fichaMutacion1 indica la primera ficha a mutar, si la zona a mutar es:
        las esquinas [0,3]
        los bordes [0,(parametros.DIMENSION_TABLERO - 2) * 4 - 1)]
        el interior [0,(parametros.DIMENSION_TABLERO - 2) * (parametros.DIMENSION_TABLERO - 2) - 1)]
     * @param fichaMutacion2 indica la segunda ficha a mutar, si la zona a mutar es:
        las esquinas [0,3]
        los bordes [0,(parametros.DIMENSION_TABLERO - 2) * 4 - 1)]
        el interior [0,(parametros.DIMENSION_TABLERO - 2) * (parametros.DIMENSION_TABLERO - 2) - 1)]
     */
    public void setAmbienteTest(int corteEsquina, int corteBorde, 
            int inicioCorteFila, int inicioCorteColumna, int largoCorteFila, 
            int largoCorteColumna, int probabilidadZona, int fichaMutacion1,
            int fichaMutacion2){
        setAmbienteTestCruzamiento(corteEsquina, corteBorde, inicioCorteFila, inicioCorteColumna, largoCorteFila, largoCorteColumna);
        setAmbienteTestMutacion(probabilidadZona, fichaMutacion1, fichaMutacion2);
    }
    
    public void setAmbienteTestParametros (Parametros params){
        this.parametros = params;
    }
    
    /**
     * Setea todas las variables utilizadas en el algoritmo de cruzamiento
     * @param corteEsquina es donde se corta las fichas de las esquinas para el cruzamiento, debe ser >= 0 y < 4
     * @param corteBorde es donde se corta las fichas de los bordes para el cruzamiento, debe ser >= 0 y < (parametros.DIMENSION_TABLERO - 2) * 4
     * @param inicioCorteFila fila inicial del corte de la región interna, debe ser >= 0 y < (parametros.DIMENSION_TABLERO - 2)
     * @param inicioCorteColumna columna inicial del corte de la región interna, debe ser >= 0 y < (parametros.DIMENSION_TABLERO - 2)
     * @param largoCorteFila largo de fila del corte de la región interna, debe ser >= 0 y < (parametros.DIMENSION_TABLERO - 2 - inicioCorteFila)
     * @param largoCorteColumna largo de columna del corte de la región interna, debe ser >= 0 y < (parametros.DIMENSION_TABLERO - 2 - inicioCorteColumna)
     */
    public void setAmbienteTestCruzamiento(int corteEsquina, int corteBorde, 
            int inicioCorteFila, int inicioCorteColumna, int largoCorteFila, 
            int largoCorteColumna) {
        this.testCruzamiento = true;
        this.corteEsquina = corteEsquina;
        this.corteBorde = corteBorde;
        this.inicioCorteFila = inicioCorteFila;
        this.inicioCorteColumna = inicioCorteColumna;
        this.largoCorteFila = largoCorteFila;
        this.largoCorteColumna = largoCorteColumna;
    }

    /**
     * Setea todas las variables utilizadas en el algoritmo de mutación, fichaMutacion1 debe ser distinta a fichaMutacion2
     * @param probabilidadZona indica que zona debe ser mutada,
        [0,3] se mutan las esquinas
        [4,(parametros.DIMENSION_TABLERO - 1) * 4) se mutan los bordes
        >= (parametros.DIMENSION_TABLERO - 1) * 4 se muta el interior
     * @param fichaMutacion1 indica la primera ficha a mutar, si la zona a mutar es:
        las esquinas [0,3]
        los bordes [0,(parametros.DIMENSION_TABLERO - 2) * 4 - 1)]
        el interior [0,(parametros.DIMENSION_TABLERO - 2) * (parametros.DIMENSION_TABLERO - 2) - 1)]
     * @param fichaMutacion2 indica la segunda ficha a mutar, si la zona a mutar es:
        las esquinas [0,3]
        los bordes [0,(parametros.DIMENSION_TABLERO - 2) * 4 - 1)]
        el interior [0,(parametros.DIMENSION_TABLERO - 2) * (parametros.DIMENSION_TABLERO - 2) - 1)]
     */
    public void setAmbienteTestMutacion(int probabilidadZona, int fichaMutacion1,
            int fichaMutacion2) {
        this.testMutacion = true;
        this.probabilidadZona = probabilidadZona;
        this.fichaMutacion1 = fichaMutacion1;
        this.fichaMutacion2 = fichaMutacion2;
    }
    
    /**
     * Realiza el cruzamiento entre los padres pasados por parámetros
     * @param padre1 primer padre para el cruzamiento
     * @param padre2 segundo padre para el cruzamiento
     * @return Devuelve todos los hijos generados del cruzamiento entre padre1 y padre2 que superen el fitness de ambos padres
     */
    public ArrayList<Individuo> cruzamiento(Individuo padre1, Individuo padre2){
        
        if (!testCruzamiento){
            corteEsquina = (int)(Math.random() * 3);
            corteBorde = (int)(Math.random() * ((parametros.DIMENSION_TABLERO - 2) * 4 - 1));
            inicioCorteFila = (int)(Math.random() * (parametros.DIMENSION_TABLERO - 2));
            inicioCorteColumna = (int)(Math.random() * (parametros.DIMENSION_TABLERO - 2));
            largoCorteFila = (int)(Math.random() * (parametros.DIMENSION_TABLERO - 2 - inicioCorteFila));
            largoCorteColumna = (int)(Math.random() * (parametros.DIMENSION_TABLERO - 2 - inicioCorteColumna));
        }
        
        ArrayList<Ficha[]> nuevasEsquinas = corteSPX(4, corteEsquina, padre1.getEsquinas(), padre2.getEsquinas());
        ajustarEsquinas(nuevasEsquinas.get(0), nuevasEsquinas.get(1));

        ArrayList<Ficha[]> nuevosBordes = corteSPX((parametros.DIMENSION_TABLERO - 2) * 4, corteBorde, padre1.getBordes(), padre2.getBordes());
        ajustarBordes(nuevosBordes.get(0), nuevosBordes.get(1));
        
        ArrayList<Ficha[][]> interiores = corteRegion(inicioCorteFila, inicioCorteColumna, largoCorteFila, largoCorteColumna, padre1.getInterior(), padre2.getInterior());
        
        return crearHijos(padre1, padre2, nuevasEsquinas, nuevosBordes, interiores);
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
        
        for (int indice = 0; indice < parametros.DIMENSION_TABLERO - 2; indice++){
            while (bordesHijo1[indice].getColores()[0] != 0){
                bordesHijo1[indice].rotarFicha(1);
            }
            while (bordesHijo2[indice].getColores()[0] != 0){
                bordesHijo2[indice].rotarFicha(1);
            }
        }
        
        int patron = 3;
        for (int indice = parametros.DIMENSION_TABLERO - 2; indice < (parametros.DIMENSION_TABLERO - 2) * 3; indice++){
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
        
        for (int indice = (parametros.DIMENSION_TABLERO - 2) * 3; indice < (parametros.DIMENSION_TABLERO - 2) * 4; indice++){
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
        
        Ficha[][] hijo1Padre1 = new Ficha[parametros.DIMENSION_TABLERO - 2][parametros.DIMENSION_TABLERO - 2];
        Ficha[][] hijo2Padre1 = new Ficha[parametros.DIMENSION_TABLERO - 2][parametros.DIMENSION_TABLERO - 2];
        Ficha[][] hijo3Padre1 = new Ficha[parametros.DIMENSION_TABLERO - 2][parametros.DIMENSION_TABLERO - 2];
        Ficha[][] hijo4Padre1 = new Ficha[parametros.DIMENSION_TABLERO - 2][parametros.DIMENSION_TABLERO - 2];
        Ficha[][] hijo1Padre2 = new Ficha[parametros.DIMENSION_TABLERO - 2][parametros.DIMENSION_TABLERO - 2];
        Ficha[][] hijo2Padre2 = new Ficha[parametros.DIMENSION_TABLERO - 2][parametros.DIMENSION_TABLERO - 2];
        Ficha[][] hijo3Padre2 = new Ficha[parametros.DIMENSION_TABLERO - 2][parametros.DIMENSION_TABLERO - 2];
        Ficha[][] hijo4Padre2 = new Ficha[parametros.DIMENSION_TABLERO - 2][parametros.DIMENSION_TABLERO - 2];
        
        int filaRot1 = filaCorte + largoCorteFila;
        int columnaRot1 = columnaCorte + largoCorteColumna;
        int filaRot2 = filaCorte + largoCorteFila;
        int columnaRot2 = columnaCorte + largoCorteColumna;
        int filaRot3 = filaCorte + largoCorteFila;
        int columnaRot3 = columnaCorte;
        
        HashMap<Integer, Integer> mapCambioPadre1 = new HashMap<>();
        HashMap<Integer, Coordenadas> mapCoordenadasPadre1 = new HashMap<>();
        
        HashMap<Integer, Integer> mapCambioPadre2 = new HashMap<>();
        HashMap<Integer, Coordenadas> mapCoordenadasPadre2 = new HashMap<>();
        
        for (int fila = 0; fila < parametros.DIMENSION_TABLERO - 2; fila++){
            for (int columna = 0; columna < parametros.DIMENSION_TABLERO - 2; columna++){
                //Estoy fuera de la región que quiero intercambiar, copio la misma información del padre
                if ((fila < filaCorte) || (fila > filaCorte + largoCorteFila) ||
                        (columna < columnaCorte) || (columna > columnaCorte + largoCorteColumna)){
                    
                    hijo1Padre1[fila][columna] = interiorPadre1[fila][columna].clone();
                    hijo2Padre1[fila][columna] = interiorPadre1[fila][columna].clone();
                    mapCoordenadasPadre1.put(interiorPadre1[fila][columna].getNroFicha(), new Coordenadas(fila, columna));

                    hijo1Padre2[fila][columna] = interiorPadre2[fila][columna].clone();
                    hijo2Padre2[fila][columna] = interiorPadre2[fila][columna].clone();
                    mapCoordenadasPadre2.put(interiorPadre2[fila][columna].getNroFicha(), new Coordenadas(fila, columna));

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
                    
                    if (interiorPadre1[fila][columna].getNroFicha() != interiorPadre2[fila][columna].getNroFicha()){
                        
                        int nroFicha1 = interiorPadre1[fila][columna].getNroFicha();
                        int nroFicha2 = interiorPadre2[fila][columna].getNroFicha();
                        
                        if (mapCambioPadre2.containsKey(nroFicha1)){
                            nroFicha1 = mapCambioPadre2.remove(nroFicha1);
                            mapCambioPadre1.remove(nroFicha1);
                        }
                        
                        if (mapCambioPadre1.containsKey(nroFicha2)){
                            nroFicha2 = mapCambioPadre1.remove(nroFicha2);
                            mapCambioPadre2.remove(nroFicha2);
                        }
                        
                        mapCambioPadre1.put(nroFicha1, nroFicha2);
                        mapCambioPadre2.put(nroFicha2, nroFicha1);
                    }
                    
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
        
        for (Map.Entry<Integer, Integer> entrySet : mapCambioPadre1.entrySet()){
            hijo1Padre1[mapCoordenadasPadre1.get(entrySet.getValue()).getFila()][mapCoordenadasPadre1.get(entrySet.getValue()).getColumna()] =
                interiorPadre2[mapCoordenadasPadre2.get(entrySet.getKey()).getFila()][mapCoordenadasPadre2.get(entrySet.getKey()).getColumna()].clone();
            hijo2Padre1[mapCoordenadasPadre1.get(entrySet.getValue()).getFila()][mapCoordenadasPadre1.get(entrySet.getValue()).getColumna()] =
                interiorPadre2[mapCoordenadasPadre2.get(entrySet.getKey()).getFila()][mapCoordenadasPadre2.get(entrySet.getKey()).getColumna()].clone();

            hijo1Padre2[mapCoordenadasPadre2.get(entrySet.getKey()).getFila()][mapCoordenadasPadre2.get(entrySet.getKey()).getColumna()] =
                interiorPadre1[mapCoordenadasPadre1.get(entrySet.getValue()).getFila()][mapCoordenadasPadre1.get(entrySet.getValue()).getColumna()].clone();
            hijo2Padre2[mapCoordenadasPadre2.get(entrySet.getKey()).getFila()][mapCoordenadasPadre2.get(entrySet.getKey()).getColumna()] =
                interiorPadre1[mapCoordenadasPadre1.get(entrySet.getValue()).getFila()][mapCoordenadasPadre1.get(entrySet.getValue()).getColumna()].clone();

            if (largoCorteFila == largoCorteColumna){
                hijo3Padre1[mapCoordenadasPadre1.get(entrySet.getValue()).getFila()][mapCoordenadasPadre1.get(entrySet.getValue()).getColumna()] =
                    interiorPadre2[mapCoordenadasPadre2.get(entrySet.getKey()).getFila()][mapCoordenadasPadre2.get(entrySet.getKey()).getColumna()].clone();
                hijo4Padre1[mapCoordenadasPadre1.get(entrySet.getValue()).getFila()][mapCoordenadasPadre1.get(entrySet.getValue()).getColumna()] =
                    interiorPadre2[mapCoordenadasPadre2.get(entrySet.getKey()).getFila()][mapCoordenadasPadre2.get(entrySet.getKey()).getColumna()].clone();

                hijo3Padre2[mapCoordenadasPadre2.get(entrySet.getKey()).getFila()][mapCoordenadasPadre2.get(entrySet.getKey()).getColumna()] =
                    interiorPadre1[mapCoordenadasPadre1.get(entrySet.getValue()).getFila()][mapCoordenadasPadre1.get(entrySet.getValue()).getColumna()].clone();
                hijo4Padre2[mapCoordenadasPadre2.get(entrySet.getKey()).getFila()][mapCoordenadasPadre2.get(entrySet.getKey()).getColumna()] =
                    interiorPadre1[mapCoordenadasPadre1.get(entrySet.getValue()).getFila()][mapCoordenadasPadre1.get(entrySet.getValue()).getColumna()].clone();
            }
            
        }
        
        return interiores;
    }

    private ArrayList<Individuo> crearHijos(Individuo padre1, Individuo padre2, ArrayList<Ficha[]> nuevasEsquinas, ArrayList<Ficha[]> nuevosBordes,
            ArrayList<Ficha[][]> interiores){
        
        ArrayList<Individuo> hijos = new ArrayList<>();

        //Agrego los hijos solo aplicando corte a las esquinas
        Individuo hijo = new Individuo(nuevasEsquinas.get(0), padre1.getBordes(), padre1.getInterior());
        if ((hijo.getFitness() > padre1.getFitness()) && (hijo.getFitness() > padre2.getFitness())){
            hijos.add(hijo);
        }
        hijo = new Individuo(nuevasEsquinas.get(1), padre2.getBordes(), padre2.getInterior());
        if ((hijo.getFitness() > padre1.getFitness()) && (hijo.getFitness() > padre2.getFitness())){
            hijos.add(hijo);
        }

        //Agrego los hijos solo aplicando corte a los bordes
        hijo = new Individuo(padre1.getBordes(), nuevosBordes.get(0), padre1.getInterior());
        if ((hijo.getFitness() > padre1.getFitness()) && (hijo.getFitness() > padre2.getFitness())){
            hijos.add(hijo);
        }
        hijo = new Individuo(padre2.getBordes(), nuevosBordes.get(1), padre2.getInterior());
        if ((hijo.getFitness() > padre1.getFitness()) && (hijo.getFitness() > padre2.getFitness())){
            hijos.add(hijo);
        }
        
        //Agrego los hijos aplicando cortes a esquinas y bordes
        hijo = new Individuo(nuevasEsquinas.get(0), nuevosBordes.get(0), padre1.getInterior());
        if ((hijo.getFitness() > padre1.getFitness()) && (hijo.getFitness() > padre2.getFitness())){
            hijos.add(hijo);
        }
        hijo = new Individuo(nuevasEsquinas.get(1), nuevosBordes.get(1), padre2.getInterior());
        if ((hijo.getFitness() > padre1.getFitness()) && (hijo.getFitness() > padre2.getFitness())){
            hijos.add(hijo);
        }

        for (int i = 0; i < interiores.size() / 2; i++){
            
            //Agrego los hijos solo aplicando corte al interior
            hijo = new Individuo(padre1.getBordes(), padre1.getBordes(), interiores.get(i));
            if ((hijo.getFitness() > padre1.getFitness()) && (hijo.getFitness() > padre2.getFitness())){
                hijos.add(hijo);
            }
            
            hijo = new Individuo(padre2.getBordes(), padre2.getBordes(), interiores.get(i + interiores.size() / 2));
            if ((hijo.getFitness() > padre1.getFitness()) && (hijo.getFitness() > padre2.getFitness())){
                hijos.add(hijo);
            }

            //Agrego los hijos aplicando corte al interior y esquinas
            hijo = new Individuo(nuevasEsquinas.get(0), padre1.getBordes(), interiores.get(i));
            if ((hijo.getFitness() > padre1.getFitness()) && (hijo.getFitness() > padre2.getFitness())){
                hijos.add(hijo);
            }
            
            hijo = new Individuo(nuevasEsquinas.get(1), padre2.getBordes(), interiores.get(i + interiores.size() / 2));
            if ((hijo.getFitness() > padre1.getFitness()) && (hijo.getFitness() > padre2.getFitness())){
                hijos.add(hijo);
            }
            
            //Agrego los hijos aplicando corte al interior y bordes
            hijo = new Individuo(padre1.getBordes(), nuevosBordes.get(0), interiores.get(i));
            if ((hijo.getFitness() > padre1.getFitness()) && (hijo.getFitness() > padre2.getFitness())){
                hijos.add(hijo);
            }
            
            hijo = new Individuo(padre2.getBordes(), nuevosBordes.get(1), interiores.get(i + interiores.size() / 2));
            if ((hijo.getFitness() > padre1.getFitness()) && (hijo.getFitness() > padre2.getFitness())){
                hijos.add(hijo);
            }
            
            //Agrego los hijos aplicando corte al interior, bordes y esquinas
            hijo = new Individuo(nuevasEsquinas.get(0), nuevosBordes.get(0), interiores.get(i));
            if ((hijo.getFitness() > padre1.getFitness()) && (hijo.getFitness() > padre2.getFitness())){
                hijos.add(hijo);
            }
            
            hijo = new Individuo(nuevasEsquinas.get(1), nuevosBordes.get(1), interiores.get(i + interiores.size() / 2));
            if ((hijo.getFitness() > padre1.getFitness()) && (hijo.getFitness() > padre2.getFitness())){
                hijos.add(hijo);
            }
        }
        
        return hijos;
    }

    /**
     * Realiza la mutación de un individuo
     * @param individuo es el individuo a ser mutado
     */
    public void mutacion(Individuo individuo){
        
        if (!testMutacion){
            probabilidadZona = (int)(Math.random() * (parametros.DIMENSION_TABLERO * parametros.DIMENSION_TABLERO));
        }
        
        if (probabilidadZona < 4){
            //Selecciono una esquina
            if (!testMutacion){
                fichaMutacion1 = (int)(Math.random() * 4);
                fichaMutacion2 = (int)(Math.random() * 3);

                if (fichaMutacion2 >= fichaMutacion1){
                    fichaMutacion2++;
                }
            }
            
            Ficha fichaCambio = individuo.getEsquinas()[fichaMutacion1];
            individuo.getEsquinas()[fichaMutacion1] = individuo.getEsquinas()[fichaMutacion2];
            individuo.getEsquinas()[fichaMutacion2] = fichaCambio;
            
        }else if (probabilidadZona < (parametros.DIMENSION_TABLERO - 1) * 4){
            //Selecciono un borde
            if (!testMutacion){
                fichaMutacion1 = (int)(Math.random() * (parametros.DIMENSION_TABLERO - 2) * 4);
                fichaMutacion2 = (int)(Math.random() * ((parametros.DIMENSION_TABLERO - 2) * 4 - 1));

                if (fichaMutacion2 >= fichaMutacion1){
                    fichaMutacion2++;
                }
            }
            
            Ficha fichaCambio = individuo.getBordes()[fichaMutacion1];
            individuo.getBordes()[fichaMutacion1] = individuo.getBordes()[fichaMutacion2];
            individuo.getBordes()[fichaMutacion2] = fichaCambio;
            
        }else{
            //Selecciono el interior
            if (!testMutacion){
                fichaMutacion1 = (int)(Math.random() * (parametros.DIMENSION_TABLERO - 2) * (parametros.DIMENSION_TABLERO - 2));
                fichaMutacion2 = (int)(Math.random() * ((parametros.DIMENSION_TABLERO - 2) * (parametros.DIMENSION_TABLERO - 2) - 1));

                if (fichaMutacion2 >= fichaMutacion1){
                    fichaMutacion2++;
                }
            }
            
            Coordenadas coordFicha1 = obtenerCoordenadaFicha(fichaMutacion1);
            Coordenadas coordFicha2 = obtenerCoordenadaFicha(fichaMutacion2);
            
            Ficha fichaCambio = individuo.getInterior()[coordFicha1.getFila()][coordFicha1.getColumna()];
            individuo.getInterior()[coordFicha1.getFila()][coordFicha1.getColumna()] = individuo.getInterior()[coordFicha2.getFila()][coordFicha2.getColumna()];
            individuo.getInterior()[coordFicha2.getFila()][coordFicha2.getColumna()] = fichaCambio;
            
        }

    }
    
    private Coordenadas obtenerCoordenadaFicha(int ficha){
        int fila, columna;
        fila = ficha / (parametros.DIMENSION_TABLERO - 2);
        columna = ficha % (parametros.DIMENSION_TABLERO - 2);
        return new Coordenadas(fila, columna);
    }
    
}
