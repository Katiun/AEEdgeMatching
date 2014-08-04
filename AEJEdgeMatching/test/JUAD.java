/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import aejedgematching.AD;
import aejedgematching.Ficha;
import aejedgematching.Individuo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Usuario
 */
public class JUAD {
    
    public JUAD() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void test1(){
        
        Ficha[] esquinas1 = new Ficha[] {new Ficha(10, new int[] {0,1,1,0}), 
            new Ficha(2, new int[] {0,0,1,2}),
            new Ficha(12, new int[] {4,3,0,0}), 
            new Ficha(7, new int[] {1,0,0,6})
        };
        Ficha[] bordes1 = new Ficha[] {new Ficha(3, 2, new int[] {0,2,3,4}), 
            new Ficha(5, 3, new int[] {0,2,5,5}),
            new Ficha(6, 1, new int[] {3,4,1,0}),
            new Ficha(8, 2, new int[] {6,0,1,6}),
            new Ficha(13, 1, new int[] {1,2,5,0}),
            new Ficha(14, 3, new int[] {2,0,1,2}),
            new Ficha(15, 3, new int[] {6,1,0,5}),
            new Ficha(16, 3, new int[] {3,1,0,5})
        };
        Ficha[][] interior1 = new Ficha[2][2];
        interior1[0][0] = new Ficha (1, new int[] {5,3,1,2});
        interior1[0][1] = new Ficha (4, new int[] {6,2,1,2});
        interior1[1][0] = new Ficha (9, new int[] {3,2,6,2});
        interior1[1][1] = new Ficha (11, new int[] {3,3,2,4});
        Individuo i1 = new Individuo(esquinas1, bordes1, interior1);

        AD algoritmoDeterminista = new AD(i1);
        Individuo res = algoritmoDeterminista.ejecutar(20);
    }
    
}
