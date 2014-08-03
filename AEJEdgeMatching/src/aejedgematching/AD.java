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
public class AD {
    
    private final Individuo individuoOriginal;
    private boolean endBacktracking;
    private Ficha[][] tablero;
    private Date finEjecucion;
    private int mejorFitness;
    private int fitnessOptimo;
    private LogEventos log;
    
    public AD(Individuo individuo){
        individuoOriginal = individuo;
        tablero = new Ficha[Parametros.DIMENSION_TABLERO][Parametros.DIMENSION_TABLERO];
    }
    
    /**
     * Ejecuta un algoritmo constructivo basado en backtracking para obtener la solución del rompecabezas
     * @param tiempoEjecutar tiempo máximo en milisegundos que ejecuta el algoritmo determinista
     * @return 
     */
    public Individuo ejecutar(long tiempoEjecutar){
        
        Ficha[] esquinas = individuoOriginal.getEsquinas();
        Ficha[] bordes = individuoOriginal.getBordes();
        Ficha[][] interior = individuoOriginal.getInterior();
        endBacktracking = false;
        mejorFitness = 0;
        log = new LogEventos();

        fitnessOptimo = ((Parametros.DIMENSION_TABLERO - 1) * (Parametros.DIMENSION_TABLERO - 1) + 1) * 2 + ((Parametros.DIMENSION_TABLERO - 2) * 2);
        finEjecucion = new Date();
        Date inicio = new Date();
        finEjecucion.setTime(inicio.getTime() + tiempoEjecutar);
        
        log.agregarEvento(new Evento(inicio, "Inicio algoritmos determinista."));
        
        backtracking(esquinas, bordes, interior, 0, 0, 0);
        
        return new Individuo(esquinas, bordes, interior);
        
    }
    
    private void backtracking(Ficha[] esquinas, Ficha[] bordes, Ficha[][] interior, 
            int fila, int columna, int fitnessActual){
        
        if (columna >= Parametros.DIMENSION_TABLERO){
            columna = 0;
            fila++;
        }
        
        try{
            int sleep = (int)(Math.random() * 2);
            log.agregarEvento(new Evento(new Date(), "Duerme " + sleep));
            Thread.sleep(sleep); 
        }catch(InterruptedException e){}
        
        //Cuando mejora el fitness en 10% agrego como evento
        if ((fitnessActual - mejorFitness) * 100 / fitnessOptimo > 10){
            mejorFitness = fitnessActual;
            log.agregarEvento(new Evento(new Date(), "Mejora 10% de fitness " + mejorFitness));
        }

        //Si ya llegó al final del tablero o ejecutó por más tiempo que el establecido
        if ((fila >= Parametros.DIMENSION_TABLERO) || (finEjecucion.before(new Date()))){
            endBacktracking = true;
            log.agregarEvento(new Evento(new Date(), "Finaliza algoritmo determinista, mejor fitness " + mejorFitness));
            imprimirTablero();
            return;
        }
        
        if (((fila == 0) && (columna == 0)) || ((fila == 0) && (columna == Parametros.DIMENSION_TABLERO - 1)) || 
                ((fila == Parametros.DIMENSION_TABLERO - 1) && (columna == 0)) || ((fila == Parametros.DIMENSION_TABLERO - 1) && (columna == Parametros.DIMENSION_TABLERO - 1))){
            
            for (int i = 0; i < 4 && !endBacktracking; i++){
                if (!esquinas[i].isUtilizada()){
                    
                    Ficha fichaElegida = acomodarEsquina(esquinas[i], fila, columna);
                    boolean agregarFicha = true;
                    int agregadoFitness = 0;
                    
                    if ((fila == 0) && (columna == 0)){
                        agregarFicha = true;
                    }
                    if (columna != 0){
                        agregarFicha &= fichaElegida.getColores()[3] == tablero[fila][columna - 1].getColores()[1];
                        agregadoFitness++;
                    }
                    if (fila != 0){
                        agregarFicha &= fichaElegida.getColores()[0] == tablero[fila - 1][columna].getColores()[2];
                        agregadoFitness++;
                    }
                    
                    if (agregarFicha){
                        esquinas[i].setUtilizada(true);
                        tablero[fila][columna] = fichaElegida;
                        backtracking(esquinas, bordes, interior, fila, columna + 1, fitnessActual + agregadoFitness);
                        tablero[fila][columna] = null;
                        esquinas[i].setUtilizada(false);
                    }
                    
                }
            }
            
        }else if ((fila == 0) || (columna == 0) || (fila == Parametros.DIMENSION_TABLERO - 1) || (columna == Parametros.DIMENSION_TABLERO - 1)){
            
            for (int i = 0; i < (Parametros.DIMENSION_TABLERO - 2) * 4 && !endBacktracking; i++){
                if (!bordes[i].isUtilizada()){
                    
                    Ficha fichaElegida = acomodarBorde(bordes[i], fila, columna);                    
                    boolean agregarFicha = true;
                    int agregadoFitness = 0;

                    if ((fila == 0) || (fila == Parametros.DIMENSION_TABLERO - 1)){
                        agregarFicha &= fichaElegida.getColores()[3] == tablero[fila][columna - 1].getColores()[1];
                        agregadoFitness++;
                        if (fila == Parametros.DIMENSION_TABLERO - 1){ 
                            agregarFicha &= fichaElegida.getColores()[0] == tablero[fila - 1][columna].getColores()[2];
                            agregadoFitness++;
                        }
                    }
                    if ((columna == 0) || (columna == Parametros.DIMENSION_TABLERO - 1)){
                        agregarFicha &= fichaElegida.getColores()[0] == tablero[fila - 1][columna].getColores()[2];
                        agregadoFitness++;
                        if (columna == Parametros.DIMENSION_TABLERO - 1){ 
                            agregarFicha &= fichaElegida.getColores()[3] == tablero[fila][columna - 1].getColores()[1];
                            agregadoFitness++;
                        }
                    }
                    
                    if (agregarFicha){
                        bordes[i].setUtilizada(true);
                    
                        tablero[fila][columna] = acomodarBorde(bordes[i], fila, columna);
                        backtracking(esquinas, bordes, interior, fila, columna + 1, fitnessActual + agregadoFitness);
                        tablero[fila][columna] = null;
                    
                        bordes[i].setUtilizada(false);
                    }
                }
            }
            
        }else{
            
            for (int i = 0; i < Parametros.DIMENSION_TABLERO - 2 && !endBacktracking; i++){
                for (int j = 0; j < Parametros.DIMENSION_TABLERO - 2 && !endBacktracking; j++){
                    if (!interior[i][j].isUtilizada()){
                        interior[i][j].setUtilizada(true);

                        Ficha fichaElegida = interior[i][j];
                        int rotacion = 0;
                        while (rotacion < 4){
                            if ((fichaElegida.getColores()[0] == tablero[fila - 1][columna].getColores()[2]) &&
                                    (fichaElegida.getColores()[3] == tablero[fila][columna - 1].getColores()[1])){
                                tablero[fila][columna] = fichaElegida;
                                backtracking(esquinas, bordes, interior, fila, columna + 1, fitnessActual + 2);
                                tablero[fila][columna] = null;
                            }
                            fichaElegida.rotarFicha(1);
                            rotacion++;
                        }

                        interior[i][j].setUtilizada(false);
                    }
                }
            }
            
        }
    }
    
    private Ficha acomodarEsquina(Ficha ficha, int fila, int columna){
        
        int indicePatron1 = 0, indicePatron2 = 0;
        
        if ((fila == 0) && (columna == 0)){
//            indicePatron1 = 0;
            indicePatron2 = 3;
        }else if ((fila == 0) && (columna == Parametros.DIMENSION_TABLERO - 1)){
            indicePatron1 = 1;
//            indicePatron2 = 0;
        }else if ((fila == Parametros.DIMENSION_TABLERO - 1) && (columna == 0)){
            indicePatron1 = 3;
            indicePatron2 = 2;
        }else {
            indicePatron1 = 2;
            indicePatron2 = 1;
        }

        while ((ficha.getColores()[indicePatron1] != 0) || (ficha.getColores()[indicePatron2] != 0)){
            ficha.rotarFicha(1);
        }

        return ficha;
    }
    
    private Ficha acomodarBorde(Ficha ficha, int fila, int columna){
        int indicePatron = 0;

        if (columna == Parametros.DIMENSION_TABLERO - 1){
            indicePatron = 1;
        }else if (fila == Parametros.DIMENSION_TABLERO - 1){
            indicePatron = 2;
        }else if (columna == 0){
            indicePatron = 3;
        }
        
        while (ficha.getColores()[indicePatron] != 0){
            ficha.rotarFicha(1);
        }
        
        return ficha;
    }

    private void imprimirTablero(){
        for (int i = 0; i < Parametros.DIMENSION_TABLERO; i++){
            for (int j = 0; j < Parametros.DIMENSION_TABLERO; j++){
                System.out.println(tablero[i][j]);
            }
        }
    }

    /**
     * Crea el archivo con el nombre pasado por parámetro y guarda todos los eventos registrados
     * @param nombreArchivo nombre del archivo donde se guardan los datos
     */
    public void imprimirLog(String nombreArchivo){
        log.imprimirEventos(nombreArchivo);
    }

}
