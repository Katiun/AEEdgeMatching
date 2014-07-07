/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aejedgematching;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class JPanelEdge extends javax.swing.JPanel {

    private int DIMENSION;
    static ficha[][] tablero;
    private ArrayList<TriangleShape> triangleShapes;
    static final int SEPARACION_FICHA = 2;

    private int coordenada_fila;
    private int coordenada_columna;
    
    /**
     * Creates new form JPanelEdge
     * @param dimension
     * @param tablero
     */
    public JPanelEdge(int dimension, ficha[][] tablero) {
        initComponents();

        this.setBackground(Color.BLACK);
        DIMENSION = dimension;
        this.tablero = tablero;

        cargarTablero();
        
        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                coordenada_fila = (int)Math.floor(e.getY() / (Parametros.TAMANIO_FICHA + SEPARACION_FICHA));
                coordenada_columna = (int)Math.floor(e.getX() / (Parametros.TAMANIO_FICHA + SEPARACION_FICHA));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int fila = (int)Math.floor(e.getY() / (Parametros.TAMANIO_FICHA + SEPARACION_FICHA));
                int columna = (int)Math.floor(e.getX() / (Parametros.TAMANIO_FICHA + SEPARACION_FICHA));
                
                if ((fila == coordenada_fila) && (columna == coordenada_columna)){
                    JPanelEdge.tablero[fila][columna].rotarFicha(1);
                }else if ((coordenada_fila >= 0 && coordenada_fila < DIMENSION) && (coordenada_columna >= 0 && coordenada_columna < DIMENSION) &&
                        (fila >= 0 && fila < DIMENSION) && (columna >= 0 && columna < DIMENSION)){
                    ficha fichaAux = JPanelEdge.tablero[coordenada_fila][coordenada_columna];
                    JPanelEdge.tablero[coordenada_fila][coordenada_columna] = JPanelEdge.tablero[fila][columna];
                    JPanelEdge.tablero[fila][columna] = fichaAux;
                }
                cargarTablero();
                
                coordenada_fila = 0;
                coordenada_columna = 0;
            }

        });
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DIMENSION * Parametros.TAMANIO_FICHA + (DIMENSION - 1) * SEPARACION_FICHA, DIMENSION * Parametros.TAMANIO_FICHA + (DIMENSION - 1) * SEPARACION_FICHA);
//        return new Dimension(200, 200);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        int patronFicha = 0;
        
        for (int i = 0; i < triangleShapes.size(); i++){
//        for (int i = 0; i <= DIMENSION * 4; i++){
            TriangleShape triangleShape = triangleShapes.get(i);
            if ((patronFicha == 4) && (i > 0) && (((i / 4) % DIMENSION == 0))){
                g2d.translate(((Parametros.TAMANIO_FICHA + SEPARACION_FICHA) * DIMENSION * -1), Parametros.TAMANIO_FICHA + SEPARACION_FICHA);
            }
            if (patronFicha == 4){
                g2d.translate(Parametros.TAMANIO_FICHA + SEPARACION_FICHA, 0);
                patronFicha = 0;
            }
            g2d.setColor(triangleShape.getColorFicha());
            patronFicha++;
            g2d.fill(triangleShape);
        }
        g2d.dispose();
    }
    
    public void setTablero(ficha[][] tablero){
        this.tablero = tablero;
    }
    
    public ficha[][] getTablero(){
        return this.tablero;
    }
    
    public void cargarTablero(){
        
        triangleShapes = new ArrayList<>();

        Point2D[] points = new Point2D[3];
        TriangleShape triangleShape;
        
        for (int i = 0; i < DIMENSION; i++){
        
            for (int j = 0; j < DIMENSION; j++){
                //0
                points[0] = new Point2D.Double(Parametros.TAMANIO_FICHA / 2, Parametros.TAMANIO_FICHA / 2);
                points[1] = new Point2D.Double(0, 0);
                points[2] = new Point2D.Double(Parametros.TAMANIO_FICHA, 0);
                triangleShape = new TriangleShape(points, tablero[i][j].getColores()[0]);
                //triangleShape = new TriangleShape(points, 19);
                triangleShapes.add(triangleShapes.size(), triangleShape);

                //1
                points[0] = new Point2D.Double(Parametros.TAMANIO_FICHA / 2, Parametros.TAMANIO_FICHA / 2);
                points[1] = new Point2D.Double(Parametros.TAMANIO_FICHA, 0);
                points[2] = new Point2D.Double(Parametros.TAMANIO_FICHA, Parametros.TAMANIO_FICHA);
                triangleShape = new TriangleShape(points, tablero[i][j].getColores()[1]);
                //triangleShape = new TriangleShape(points, 19);
                triangleShapes.add(triangleShapes.size(), triangleShape);

                //2
                points[0] = new Point2D.Double(Parametros.TAMANIO_FICHA / 2, Parametros.TAMANIO_FICHA / 2);
                points[1] = new Point2D.Double(Parametros.TAMANIO_FICHA, Parametros.TAMANIO_FICHA);
                points[2] = new Point2D.Double(0, Parametros.TAMANIO_FICHA);
                triangleShape = new TriangleShape(points, tablero[i][j].getColores()[2]);
                //triangleShape = new TriangleShape(points, 19);
                triangleShapes.add(triangleShapes.size(), triangleShape);
        
                //3
                points[0] = new Point2D.Double(Parametros.TAMANIO_FICHA / 2, Parametros.TAMANIO_FICHA / 2);
                points[1] = new Point2D.Double(0, 0);
                points[2] = new Point2D.Double(0, Parametros.TAMANIO_FICHA);
                triangleShape = new TriangleShape(points, tablero[i][j].getColores()[3]);
                //triangleShape = new TriangleShape(points, 19);
                triangleShapes.add(triangleShapes.size(), triangleShape);

            }
        }
        
        validate();
        repaint();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
