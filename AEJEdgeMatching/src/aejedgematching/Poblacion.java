/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aejedgematching;

import java.util.List;

/**
 *
 * @author Usuario
 */
public class Poblacion {
    
    private List<Individuo> poblacion;
    private int fitnessPromedio;
    private int mejorFitness;
    private int generacion;

    public Poblacion(List<Individuo> poblacion) {
        this.poblacion = poblacion;
    }

    public List<Individuo> getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(List<Individuo> poblacion) {
        this.poblacion = poblacion;
    }

    public int getFitnessPromedio() {
        return fitnessPromedio;
    }

    public void setFitnessPromedio(int fitnessPromedio) {
        this.fitnessPromedio = fitnessPromedio;
    }

    public int getMejorFitness() {
        return mejorFitness;
    }

    public void setMejorFitness(int mejorFitness) {
        this.mejorFitness = mejorFitness;
    }

    public int getGeneracion() {
        return generacion;
    }

    public void setGeneracion(int generacion) {
        this.generacion = generacion;
    }
    
    
}
