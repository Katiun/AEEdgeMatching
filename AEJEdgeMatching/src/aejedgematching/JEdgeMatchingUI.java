/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aejedgematching;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.UIManager;
/**
 *
 * @author Usuario
 */
public class JEdgeMatchingUI extends javax.swing.JFrame {

   
    JPanelEdge pnlPuzzle;
    
    private final String ACTION_COMMAND_CONFIG = "ACTION_COMMAND_CONFIG";
    private final String ACTION_COMMAND_NUEVO = "ACTION_COMMAND_NUEVO";
    private final String ACTION_COMMAND_ROTAR = "ACTION_COMMAND_ROTAR";
    private final String ACTION_COMMAND_CAMBIAR = "ACTION_COMMAND_CAMBIAR";
    private final String ACTION_COMMAND_MEZCLAR = "ACTION_COMMAND_MEZCLAR";
    private final String ACTION_COMMAND_GUARDAR = "ACTION_COMMAND_GUARDAR";
    private final String ACTION_COMMAND_CARGAR = "ACTION_COMMAND_CARGAR";
    private final String ACTION_COMMAND_ALGORITMO_EVOLUTIVO = "ACTION_COMMAND_ALGORITMO_EVOLUTIVO";
    private final String ACTION_COMMAND_ALGORITMO_DETERMINISTA = "ACTION_COMMAND_ALGORITMO_DETERMINISTA";

    JLabel lblDim;
    JTextField txtDim;
    JLabel lblColor;
    JTextField txtColor;
    JButton btnCambiarConfig;
    
    JPanel pnlVisualizacion;
    
    JButton btnNuevo;
    JButton btnRotar;
    JButton btnCambiar;
    JButton btnMezclar;

    private Individuo individuoOriginal;
    private List<Poblacion> poblaciones;
    private boolean archivoCargado;
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout pnlLayout = new javax.swing.GroupLayout(pnl);
        pnl.setLayout(pnlLayout);
        pnlLayout.setHorizontalGroup(
            pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 592, Short.MAX_VALUE)
        );
        pnlLayout.setVerticalGroup(
            pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 441, Short.MAX_VALUE)
        );

        jMenu1.setText("File");

        jMenuItem1.setText("jMenuItem1");
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public JEdgeMatchingUI() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                }

                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setLayout(new GridBagLayout());
                
                ActionListener actionListener = new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        Ficha[][] tablero = pnlPuzzle.getTablero();
                        boolean cargarTablero = true;
                        
                        switch (e.getActionCommand()){
                            
                            case ACTION_COMMAND_CONFIG:
                                JDialog pop = new JDialog();
                                pop.add(new JPanelConfig(JEdgeMatchingUI.this, Parametros.DIMENSION_TABLERO, Parametros.MAX_COLOR));
                                pop.setModal(true);
                                pop.setMinimumSize(new Dimension(230, 180));
                                pop.setMaximumSize(new Dimension(230, 180));
                                pop.setSize(new Dimension(230, 180));
                                pop.setPreferredSize(new Dimension(230, 180));
                                pop.setLocationRelativeTo(null);
                                pop.setVisible(true);
                                cargarTablero = false;
                                break;
                                
                            case ACTION_COMMAND_NUEVO:
                                tablero = generarPuzle();
                                break;
                                
                            case ACTION_COMMAND_ROTAR:
                                tablero = rotarFichasPuzle(tablero);
                                break;
                                
                            case ACTION_COMMAND_CAMBIAR:
                                tablero = cambiarFichasPuzle(tablero);
                                break;
                                
                            case ACTION_COMMAND_MEZCLAR:
                                tablero = rotarFichasPuzle(tablero);
                                tablero = cambiarFichasPuzle(tablero);
                                break;
                                
                            case ACTION_COMMAND_GUARDAR:
                                guardarArchivo(tablero);
                                cargarTablero = false;
                                break;
                                
                            case ACTION_COMMAND_CARGAR:
                                cargarArchivo();
                                break;
                                
                            case ACTION_COMMAND_ALGORITMO_EVOLUTIVO:
                                if (!archivoCargado){
                                    JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún archivo.", "Error", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                ejecutarAlgoritmoEvolutivo();
                                break;
                                
                            case ACTION_COMMAND_ALGORITMO_DETERMINISTA:
                                if (!archivoCargado){
                                    JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún archivo.", "Error", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                ejecutarAlgoritmoDeterminista();
                                break;
                        }
                        if (cargarTablero){
                            pnlPuzzle.setTablero(tablero);
                            pnlPuzzle.cargarTablero();
                        }
                    }
                };
                
                GridBagConstraints c = new GridBagConstraints();
                
                JMenuBar menu = new JMenuBar();
                
                //MENU CONFIGURACION
                JMenu menuConfig = new JMenu("Configuración");
                menu.add(menuConfig);
                
                JMenuItem itConfig = new JMenuItem("Cambiar");
                itConfig.addActionListener(actionListener);
                itConfig.setActionCommand(ACTION_COMMAND_CONFIG);
                menuConfig.add(itConfig);

                //MENU PUZLE
                JMenu menuPuzle = new JMenu("Puzle");
                menu.add(menuPuzle);

                JMenuItem itNuevo = new JMenuItem("Nuevo");
                itNuevo.addActionListener(actionListener);
                itNuevo.setActionCommand(ACTION_COMMAND_NUEVO);
                menuPuzle.add(itNuevo);
                
                JSeparator sep1 = new JSeparator();
                menuPuzle.add(sep1);
                
                JMenuItem itRotar = new JMenuItem("Rotar");
                itRotar.addActionListener(actionListener);
                itRotar.setActionCommand(ACTION_COMMAND_ROTAR);
                menuPuzle.add(itRotar);
                
                JMenuItem itCambiar = new JMenuItem("Cambiar");
                itCambiar.addActionListener(actionListener);
                itCambiar.setActionCommand(ACTION_COMMAND_CAMBIAR);
                menuPuzle.add(itCambiar);
                
                JMenuItem itMezclar = new JMenuItem("Mezclar");
                itMezclar.addActionListener(actionListener);
                itMezclar.setActionCommand(ACTION_COMMAND_MEZCLAR);
                menuPuzle.add(itMezclar);

                //MENU ARCHIVO
                JMenu menuArchivo = new JMenu("Archivo");
                menu.add(menuArchivo);
                
                JMenuItem itGuardar = new JMenuItem("Guardar");
                itGuardar.addActionListener(actionListener);
                itGuardar.setActionCommand(ACTION_COMMAND_GUARDAR);
                menuArchivo.add(itGuardar);
                
                JSeparator sep2 = new JSeparator();
                menuArchivo.add(sep2);
                
                JMenuItem itCargar = new JMenuItem("Cargar");
                itCargar.addActionListener(actionListener);
                itCargar.setActionCommand(ACTION_COMMAND_CARGAR);
                menuArchivo.add(itCargar);

                //MENU ALGORITMOS
                JMenu menuAlgoritmo = new JMenu("Algoritmos");
                menu.add(menuAlgoritmo);
                
                JMenuItem itAlgoritmoEvolutivo = new JMenuItem("Algoritmo Evolutivo");
                itAlgoritmoEvolutivo.addActionListener(actionListener);
                itAlgoritmoEvolutivo.setActionCommand(ACTION_COMMAND_ALGORITMO_EVOLUTIVO);
                menuAlgoritmo.add(itAlgoritmoEvolutivo);
                
                JSeparator sep3 = new JSeparator();
                menuAlgoritmo.add(sep3);
                
                JMenuItem itAlgoritmoDeterminista = new JMenuItem("Algoritmo Determinista");
                itAlgoritmoEvolutivo.addActionListener(actionListener);
                itAlgoritmoEvolutivo.setActionCommand(ACTION_COMMAND_ALGORITMO_DETERMINISTA);
                menuAlgoritmo.add(itAlgoritmoDeterminista);
                
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridy = 0;
                c.gridx = 0;
                add(menu, c);
                                
                pnlVisualizacion = new JPanel(new GridBagLayout());
                
                c.fill = GridBagConstraints.HORIZONTAL;
                
                Ficha[][] tablero = generarPuzle();
                pnlPuzzle = new JPanelEdge(Parametros.DIMENSION_TABLERO, tablero);
                pnlPuzzle.validate();
                pnlPuzzle.repaint();
                pnlVisualizacion.add(pnlPuzzle);
                
                c.fill = GridBagConstraints.BOTH;
                c.gridy = 1;
                c.gridx = 0;
                c.weightx = 1;
                c.weighty = 1;
                add(pnlVisualizacion, c);
                                
                pack();
                setLocationRelativeTo(null);
                setExtendedState(JFrame.MAXIMIZED_BOTH);
                setVisible(true);
                
                archivoCargado = false;
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new JEdgeMatchingUI();
    }

    public void cambiarConfiguracion(){
        Ficha[][] tablero = generarPuzle();
        pnlVisualizacion.remove(pnlPuzzle);
        pnlPuzzle = null;
        pnlPuzzle = new JPanelEdge(Parametros.DIMENSION_TABLERO, tablero);
        pnlVisualizacion.add(pnlPuzzle);
        pnlVisualizacion.validate();
        pnlVisualizacion.repaint();
    }
    
    private static Ficha[][] iniciarPuzle(){
        
        int nroPieza = 1;
        Ficha[][] tablero = new Ficha[Parametros.DIMENSION_TABLERO][Parametros.DIMENSION_TABLERO];

        for (int fila = 0; fila < Parametros.DIMENSION_TABLERO; fila++){
            for (int columna = 0; columna < Parametros.DIMENSION_TABLERO; columna++){
                int[] colores = new int[Parametros.CANT_COLOR_FICHA];
                for (int patron = 0; patron < Parametros.CANT_COLOR_FICHA; patron++){
                    colores[patron] = 0;
                }
                
                Ficha nuevaFicha = new Ficha(nroPieza, colores);
                tablero[fila][columna] = nuevaFicha;
                nroPieza++;
            }
        }
        
        return tablero;
    }
    
    private Ficha[][] generarPuzle(){
        
        int nroPieza = 1;
        Ficha[][] tablero = new Ficha[Parametros.DIMENSION_TABLERO][Parametros.DIMENSION_TABLERO];
        
        int indiceEsquinas = 0;
        int indiceBordes = 0;
        int filaInterior = 0, columnaInteriro = 0;
        Ficha[] esquinas = new Ficha[4];
        Ficha[] bordes = new Ficha[(Parametros.DIMENSION_TABLERO - 2) * 4];
        Ficha[][] interior = new Ficha[Parametros.DIMENSION_TABLERO - 2][Parametros.DIMENSION_TABLERO - 2];
        
        
        for (int fila = 0; fila < Parametros.DIMENSION_TABLERO; fila++){
            for (int columna = 0; columna < Parametros.DIMENSION_TABLERO; columna++){
                int[] colores = new int[Parametros.CANT_COLOR_FICHA];
                for (int patron = 0; patron < Parametros.CANT_COLOR_FICHA; patron++){
                    colores[patron] = 1 + (int)(Math.random() * Parametros.MAX_COLOR);
                }
                if (fila == 0){
                    colores[0] = 0;
                }else{
                    colores[0] = tablero[fila-1][columna].getColores()[2];
                    if (fila == Parametros.DIMENSION_TABLERO - 1){
                        colores[2] = 0;
                    }
                }
                
                if (columna == 0){
                    colores[3] = 0;
                }else {
                    colores[3] = tablero[fila][columna-1].getColores()[1];
                    if (columna == Parametros.DIMENSION_TABLERO - 1){
                        colores[1] = 0;
                    }
                }
                
                //para prueba
//                if ((i == Parametros.DIMENSION - 1) && (j == Parametros.DIMENSION - 1)){
//                    colores[3] = 10;
//                }
                
                Ficha nuevaFicha = new Ficha(nroPieza, colores);
                tablero[fila][columna] = nuevaFicha;
                nroPieza++;
                
                if ((fila == 0) || (fila == Parametros.DIMENSION_TABLERO - 1) ||
                        (columna == 0) || (columna == Parametros.DIMENSION_TABLERO - 1)){
                    if ((fila == columna) || (fila + columna == Parametros.DIMENSION_TABLERO - 1)){
                        esquinas[indiceEsquinas] = nuevaFicha;
                        indiceEsquinas++;
                    }else{
                        bordes[indiceBordes] = nuevaFicha;
                        indiceBordes++;
                    }
                }else{
                    interior[filaInterior][columnaInteriro] = nuevaFicha;
                    columnaInteriro++;
                    if (columnaInteriro > Parametros.DIMENSION_TABLERO - 3){
                        filaInterior++;
                        columnaInteriro = 0;
                    }
                }
            }
        }
        
//        Individuo ind = new Individuo(esquinas, bordes, interior);
//        AE ae = new AE();
//        System.out.println("++++++++++++++++++++++");
//        for (int i = 0; i < 20; i++){
//            ae.cruzamiento(ind, ind);
//            System.out.println("----------------------");
//        }
//        System.out.println("++++++++++++++++++++++");
//        System.out.println("Prob. cambio: " + ind.getFitness());
//        System.out.println("Prob. cambio: " + ind.getFitness());

//        System.out.println("───────────────────────────────────");
//        for (int fila = 0; fila < Parametros.DIMENSION; fila++){
//            for (int columna = 0; columna < Parametros.DIMENSION; columna++){
//                System.out.print("│  ");
//                if (tablero[fila][columna].getColores()[0] < 10){
//                    System.out.print("0");
//                }
//                System.out.print(tablero[fila][columna].getColores()[0] + "  ");
//            }
//
//            System.out.println("│");
//            for (int columna = 0; columna < Parametros.DIMENSION; columna++){
//                System.out.print("│");
//                if (tablero[fila][columna].getColores()[3] < 10){
//                    System.out.print("0");
//                }
//                System.out.print(tablero[fila][columna].getColores()[3] + "  ");
//                if (tablero[fila][columna].getColores()[1] < 10){
//                    System.out.print("0");
//                }
//                System.out.print(tablero[fila][columna].getColores()[1]);
//            }
//            System.out.println("│");
//
//            for (int columna = 0; columna < Parametros.DIMENSION; columna++){
//                System.out.print("│  ");
//                if (tablero[fila][columna].getColores()[2] < 10){
//                    System.out.print("0");
//                }
//                System.out.print(tablero[fila][columna].getColores()[2] + "  ");
//            }
//            System.out.println("│");
//            System.out.println("───────────────────────────────────");
//        }
        
        return tablero;
    }
    
    private Ficha[][] rotarFichasPuzle(Ficha[][] tablero){
        
        int rotacion;
        
        for (int fila = 0; fila < Parametros.DIMENSION_TABLERO; fila++){
            for (int columna = 0; columna < Parametros.DIMENSION_TABLERO; columna++){
                rotacion = (int)(Math.random() * 4);
                tablero[fila][columna].rotarFicha(rotacion);
            }
        }
        return tablero;
        
    }

    private Ficha[][] cambiarFichasPuzle(Ficha[][] tablero){
        
        int filaSeleccionada, columnaSeleccionada, probabilidadCambio;
        Ficha fichaSeleccionada;
        
        for (int fila = 0; fila < Parametros.DIMENSION_TABLERO; fila++){
            for (int columna = 0; columna < Parametros.DIMENSION_TABLERO; columna++){
                if (!tablero[fila][columna].isCambioPosicion()){
                    probabilidadCambio = (int)(Math.random() * 10);
                    if (probabilidadCambio > 2){
                        tablero[fila][columna].setCambioPosicion(true);
                        filaSeleccionada = (int)(Math.random() * Parametros.DIMENSION_TABLERO);
                        columnaSeleccionada = (int)(Math.random() * Parametros.DIMENSION_TABLERO);
                        fichaSeleccionada = tablero[filaSeleccionada][columnaSeleccionada];
                        tablero[filaSeleccionada][columnaSeleccionada] = tablero[fila][columna];
                        tablero[fila][columna] = fichaSeleccionada;
                    }
                }
            }
        }
        
        for (int fila = 0; fila < Parametros.DIMENSION_TABLERO; fila++){
            for (int columna = 0; columna < Parametros.DIMENSION_TABLERO; columna++){
                tablero[fila][columna].setCambioPosicion(false);
            }
        }
        
        return tablero;
    }
    
    public int getHeightTablero(){
        return pnlPuzzle.getHeight();
    }
    
    private void guardarArchivo(Ficha[][] tablero){

        FileOutputStream fos = null;
        PrintStream ps = null;
        String path = System.getProperty("user.dir");
        DateFormat format = new SimpleDateFormat("YYYYMMdHm");
        path += "/Edge" + format.format(new Date()) + ".txt";
        
        try{
            fos = new FileOutputStream(path);
            ps = new PrintStream(fos);
            
            for (int fila = 0; fila < Parametros.DIMENSION_TABLERO; fila++){
                for (int columna = 0; columna < Parametros.DIMENSION_TABLERO; columna++){
                    for (int patron = 0; patron < Parametros.CANT_COLOR_FICHA; patron++){
                        ps.print(tablero[fila][columna].getColores()[patron]);
                        if (patron != Parametros.CANT_COLOR_FICHA - 1){
                            ps.print(" ");
                        }
                    }
                    if ((fila != Parametros.DIMENSION_TABLERO - 1) || (columna != Parametros.DIMENSION_TABLERO - 1)){
                        ps.println();
                    }
                }
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
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    }
    
    private void cargarArchivo(){
        try{
            JFileChooser fc = new JFileChooser();
            int retVal = fc.showOpenDialog(JEdgeMatchingUI.this);
        
            switch(retVal){
                case JFileChooser.APPROVE_OPTION:
                    File f = fc.getSelectedFile();
                    BufferedReader br = new BufferedReader(new FileReader(f));
                    String l;
                    int nroFicha = 1;
                    ArrayList<Ficha> fichas = new ArrayList<>();
                    
                    while ((l = br.readLine()) != null){
                        String[] col = l.split(" ");
                        int[] colores = new int[Parametros.CANT_COLOR_FICHA];
                        for (int patron = 0; patron < Parametros.CANT_COLOR_FICHA; patron++){
                            colores[patron] = Integer.parseInt(col[patron]);
                        }
                        Ficha fichaN = new Ficha(nroFicha, colores);
                        nroFicha++;
                        fichas.add(fichaN);
                    }
                    br.close();
                    
                    Parametros.DIMENSION_TABLERO = (int)Math.sqrt(nroFicha - 1);

                    Ficha[] esquinas = new Ficha[4];
                    Ficha[] bordes = new Ficha[(Parametros.DIMENSION_TABLERO - 2) * 4];
                    Ficha[][] interior = new Ficha[Parametros.DIMENSION_TABLERO - 2][Parametros.DIMENSION_TABLERO - 2];
                    int fila = 0;
                    int columna = 0;
                    int indiceEsq = 0;
                    int indiceBorde = 0;
                    
                    for(Ficha fichaLeida : fichas){
                        if (fichaLeida.isEsquina()){
                            esquinas[indiceEsq] = fichaLeida;
                            indiceEsq++;
                        }else if (fichaLeida.isBorde()){
                            bordes[indiceBorde] = fichaLeida;
                            indiceBorde++;
                        }else{
                            if (columna >= Parametros.DIMENSION_TABLERO - 2){
                                fila++;
                                columna = 0;
                            }
                            interior[fila][columna] = fichaLeida;
                            columna++;
                        }
                    }
                    
                    individuoOriginal = new Individuo(esquinas, bordes, interior);
                    archivoCargado = true;
                    
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "No se seleccionó ningún archivo", "Cargar archivo", JOptionPane.WARNING_MESSAGE);
                    break;
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * A partir del individuo original se crea una población aleatoria
     */
    private void crearPoblacionInicial(){
        
        List<Individuo> poblacionInicial = new ArrayList<>();
        poblacionInicial.add(individuoOriginal);
        
        int cantidadEsquinas = 4;
        int cantidadBordes = (Parametros.DIMENSION_TABLERO - 2) * 4;
        int cantidadInterior = (Parametros.DIMENSION_TABLERO - 2) * (Parametros.DIMENSION_TABLERO - 2);
        
        for(int indiceIndividuo = 1; indiceIndividuo < Parametros.TOTAL_POBLACION; indiceIndividuo++){
            
            Ficha[] esquinas = new Ficha[cantidadEsquinas];
            Ficha[] bordes = new Ficha[cantidadBordes];
            Ficha[][] interior = new Ficha[Parametros.DIMENSION_TABLERO - 2][Parametros.DIMENSION_TABLERO - 2];

            System.arraycopy(individuoOriginal.getEsquinas(), 0, esquinas, 0, cantidadEsquinas);
            System.arraycopy(individuoOriginal.getBordes(), 0, bordes, 0, cantidadBordes);
            for (int indiceFila = 0; indiceFila < Parametros.DIMENSION_TABLERO - 2; indiceFila++){
                for (int indiceColumna = 0; indiceColumna < Parametros.DIMENSION_TABLERO - 2; indiceColumna++){
                    interior[indiceFila][indiceColumna] = individuoOriginal.getInterior()[indiceFila][indiceColumna].clone();
                }
            }
            
            //Se mezcla para crear un nuevo individuo
            for (int indiceEsquina = 0; indiceEsquina < cantidadEsquinas; indiceEsquina++){
                int indiceDesde = (int)(Math.random() * cantidadEsquinas);
                int indiceHasta = (int)(Math.random() * cantidadEsquinas);
                Ficha fichaCambio = esquinas[indiceDesde];
                esquinas[indiceDesde] = esquinas[indiceHasta];
                esquinas[indiceHasta] = fichaCambio;
            }
            for (int indiceBorde = 0; indiceBorde < cantidadBordes; indiceBorde++){
                int indiceDesde = (int)(Math.random() * cantidadBordes);
                int indiceHasta = (int)(Math.random() * cantidadBordes);
                Ficha fichaCambio = bordes[indiceDesde];
                bordes[indiceDesde] = bordes[indiceHasta];
                bordes[indiceHasta] = fichaCambio;
            }
            for (int indiceInterior = 0; indiceInterior < cantidadInterior; indiceInterior++){
                int indiceDesde = (int)(Math.random() * cantidadInterior);
                int indiceHasta = (int)(Math.random() * cantidadInterior);
                Coordenadas coordDesde = obtenerCoordenadaFicha(indiceDesde);
                Coordenadas coordHasta = obtenerCoordenadaFicha(indiceHasta);
                int rotacion1 = (int)(Math.random() * 4);
                int rotacion2 = (int)(Math.random() * 4);
                Ficha fichaCambio = interior[coordDesde.getFila()][coordDesde.getColumna()];
                fichaCambio.rotarFicha(rotacion1);
                interior[coordDesde.getFila()][coordDesde.getColumna()] = interior[coordHasta.getFila()][coordHasta.getColumna()];
                interior[coordDesde.getFila()][coordDesde.getColumna()].rotarFicha(rotacion2);
                interior[coordHasta.getFila()][coordHasta.getColumna()] = fichaCambio;
            }
            
            ajustarEsquinas(esquinas);
            ajustarBordes(bordes);
            
            Individuo individuo = new Individuo(esquinas, bordes, interior);
            poblacionInicial.add(individuo);
        }
        
        Collections.sort(poblacionInicial, Individuo.getComparator());

        poblaciones = new ArrayList<>();
        poblaciones.add(new Poblacion(poblacionInicial));
    }
    
    /**
     * Ajusta las esquinas para dejar los grises hacia afuera
     * @param esquinasHijo1 Esquinas del primer hijo
     * @param esquinasHijo2 Esquinas del segundo hijo
     */
    private void ajustarEsquinas(Ficha[] esquinasHijo1){
        
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
        }
    }
    
    /**
     * Ajusta los bordes para dejar los grises hacia afuera
     * @param bordesHijo1 Bordes del primer hijo
     * @param bordesHijo2 Bordes del segundo hijo
     */
    private void ajustarBordes(Ficha[] bordesHijo1){
        
        for (int indice = 0; indice < Parametros.DIMENSION_TABLERO - 2; indice++){
            while (bordesHijo1[indice].getColores()[0] != 0){
                bordesHijo1[indice].rotarFicha(1);
            }
        }
        
        int patron = 3;
        for (int indice = Parametros.DIMENSION_TABLERO - 2; indice < (Parametros.DIMENSION_TABLERO - 2) * 3; indice++){
            while (bordesHijo1[indice].getColores()[patron] != 0){
                bordesHijo1[indice].rotarFicha(1);
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
        }
        
    }
 
    private Coordenadas obtenerCoordenadaFicha(int ficha){
        int fila, columna;
        fila = ficha / (Parametros.DIMENSION_TABLERO - 2);
        columna = ficha % (Parametros.DIMENSION_TABLERO - 2);
        return new Coordenadas(fila, columna);
    }
    
    private void ejecutarAlgoritmoEvolutivo(){

        
        int generacion = 1;
        int mejorFitness, fitnessPromedio;
        int mejorFitnessActual = 0;
        int fitenssOptimo = ((Parametros.DIMENSION_TABLERO - 1) * (Parametros.DIMENSION_TABLERO - 1) + 1) * 2 + ((Parametros.DIMENSION_TABLERO - 2) * 2);
        AE algoritmoEvolutivo = new AE();
        LogEventos log = new LogEventos();

        log.agregarEvento(new Evento(new Date(), "Inicio Algoritmo Evolutivo"));
        
        crearPoblacionInicial();

        log.agregarEvento(new Evento(new Date(), "Población inicial creada"));
        
        while (generacion < Parametros.CANT_GENERACIONES){
            
            Poblacion ultimaPoblacion = poblaciones.get(poblaciones.size() - 1);
            List<Individuo> individuos = new ArrayList<>();
            for (Individuo individuo : ultimaPoblacion.getPoblacion()){
                individuos.add(individuo.clone());
            }

            List<Individuo> nuevosIndividuos = new ArrayList<>();
            
            while (!individuos.isEmpty()){
//                //Seleccion de padres de forma aleatoria
//                int indiceIndividuo = (int)(Math.random() * individuos.size());
//                Individuo padre1 = individuos.remove(indiceIndividuo);
//                indiceIndividuo = (int)(Math.random() * individuos.size());
//                Individuo padre2 = individuos.remove(indiceIndividuo);
                
//                //Seleccion de padres de forma de compensar los fitness
//                Individuo padre1 = individuos.remove(0);
//                Individuo padre2 = individuos.remove(individuos.size() - 1);
                
                //Seleccion de padres de forma elitista
                Individuo padre1 = individuos.remove(0);
                Individuo padre2 = individuos.remove(0);

                //Cruzamiento
                if (Math.random() < Parametros.PROBABILIDAD_CRUZAMIENTO){
                    ArrayList<Individuo> hijos = algoritmoEvolutivo.cruzamiento(padre1, padre2);
                    nuevosIndividuos.addAll(hijos);
                }
            }

            //Ordeno los nuevos Individuos según su valor de fitness
            Collections.sort(nuevosIndividuos, Individuo.getComparator());
            
            //Si la cantidad de individuos generados es distinta a la poblacion total
            if (nuevosIndividuos.size() > Parametros.TOTAL_POBLACION){
                //Si hay más individuos me quedo solo con los de mejor fitness
                nuevosIndividuos = nuevosIndividuos.subList(0, Parametros.TOTAL_POBLACION);
            }else if (nuevosIndividuos.size() < Parametros.TOTAL_POBLACION){
                //Si hay menos individuos completo con los padres de mejor fitness
                for (int indicePadres = 0; indicePadres < Parametros.TOTAL_POBLACION - nuevosIndividuos.size(); indicePadres++){
                    nuevosIndividuos.add(ultimaPoblacion.getPoblacion().get(indicePadres).clone());
                }
            }

            mejorFitness = 0;
            fitnessPromedio = 0;
            //Mutacion
            for (Individuo hijo : nuevosIndividuos){
                if (Math.random() < Parametros.PROBABILIDAD_MUTACION){
                    algoritmoEvolutivo.mutacion(hijo);
                }
                if (mejorFitness < hijo.getFitness()){
                    mejorFitness = hijo.getFitness();
                }
                fitnessPromedio += hijo.getFitness();
            }
            
            if ((nuevosIndividuos != null) && (!nuevosIndividuos.isEmpty())){
                fitnessPromedio = fitnessPromedio / nuevosIndividuos.size();
            }

            if ((mejorFitness - mejorFitnessActual) * 100 / fitenssOptimo > 10){
                mejorFitnessActual = mejorFitness;
                log.agregarEvento(new Evento(new Date(), "Mejora 10% de fitness " + mejorFitness + ", generación " + generacion));
            }
        
            Poblacion nuevaPoblacion = new Poblacion(nuevosIndividuos);
            nuevaPoblacion.setFitnessPromedio(fitnessPromedio);
            nuevaPoblacion.setMejorFitness(mejorFitness);
            nuevaPoblacion.setGeneracion(generacion);
            
            poblaciones.add(nuevaPoblacion);
            
            generacion++;
        }
        
        log.agregarEvento(new Evento(new Date(), "Fin Algoritmo Evolutivo"));

        String path = obtenerNombreArchivo("logAE");

        log.imprimirEventos(path);
    }

    private void ejecutarAlgoritmoDeterminista(){

        String path = obtenerNombreArchivo("logAD");

        AD algoritmoDeterminista = new AD(individuoOriginal, path);
        algoritmoDeterminista.ejecutar(Parametros.TIEMPO_EJECUCION_AD);
        
    }
    
    private String obtenerNombreArchivo(String nombre){
        
        String path = System.getProperty("user.dir");
        DateFormat format = new SimpleDateFormat("YYYYMMdHm");
        path += "/" + nombre + format.format(new Date()) + ".txt";
        
        return path;

    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel pnl;
    // End of variables declaration//GEN-END:variables
}
