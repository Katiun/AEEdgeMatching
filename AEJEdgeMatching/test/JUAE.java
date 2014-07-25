/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import aejedgematching.AE;
import aejedgematching.Individuo;
import aejedgematching.Ficha;
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
public class JUAE {
    
    public JUAE() {
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
        Ficha[][] interior1 = new Ficha[14][14];
        Individuo i1 = new Individuo(esquinas1, bordes1, interior1);

        Ficha[] esquinas2 = new Ficha[] {new Ficha(2, new int[] {0,1,2,0}), 
            new Ficha(7, new int[] {0,0,6,1}),
            new Ficha(10, new int[] {1,1,0,0}), 
            new Ficha(12, new int[] {3,0,0,4})};
        for (int i = 0; i < 4; i++){
            esquinas2[i].setRotacion(3);
        }
        Ficha[] bordes2 = new Ficha[] {new Ficha(14, 2, new int[] {0,1,2,2}),
            new Ficha(3, 2, new int[] {0,2,3,4}), 
            new Ficha(6, 1, new int[] {3,4,1,0}),
            new Ficha(13, 3, new int[] {5,0,1,2}),
            new Ficha(5, 2, new int[] {2,5,5,0}),
            new Ficha(16, 2, new int[] {1,0,5,3}),
            new Ficha(15, 3, new int[] {6,1,0,5}),
            new Ficha(8, 3, new int[] {6,6,0,1})
        };
        Ficha[][] interior2 = new Ficha[14][14];
        Individuo i2 = new Individuo(esquinas2, bordes2, interior2);
        
        AE ae = new AE();
        ae.cruzamiento(i1, i2);
    }
    
}
