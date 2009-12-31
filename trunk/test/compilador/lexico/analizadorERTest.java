package compilador.lexico;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class analizadorERTest {

    private analizadorER instance;

    public analizadorERTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        instance = new analizadorER();
    }

    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Test of buscarInt method, of class analizadorER.
     */
    @Test
    public void testBuscarInt() {
        System.out.println("buscarInt");
        String str = "";
        assertEquals(false, instance.buscarInt(str));

        str = "#Soy un comentario\n";
        assertEquals(false, instance.buscarInt(str));

        str = "true";
        assertEquals(false, instance.buscarInt(str));

        str = "false";
        assertEquals(false, instance.buscarInt(str));

        str = "'a'";
        assertEquals(false, instance.buscarInt(str));

        str = "0";
        assertEquals(true, instance.buscarInt(str));

        str = "0.0";
        assertEquals(false, instance.buscarInt(str));

        str = "0.2";
        assertEquals(false, instance.buscarInt(str));

        str = "0.2";
        assertEquals(false, instance.buscarInt(str));

        str = "-0.2";
        assertEquals(false, instance.buscarInt(str));

        str = "-1e123";
        assertEquals(false, instance.buscarInt(str));

        str = "-1E123";
        assertEquals(false, instance.buscarInt(str));

        str = "-1.2e123";
        assertEquals(false, instance.buscarInt(str));

        str = "-1.2E123";
        assertEquals(false, instance.buscarInt(str));

        str = "-1.2-e123";
        assertEquals(false, instance.buscarInt(str));

        str = "-1.2-E123";
        assertEquals(false, instance.buscarInt(str));

        str = "0.20";
        assertEquals(false, instance.buscarInt(str));

        str = "01";
        assertEquals(false, instance.buscarInt(str));

        str = "12";
        assertEquals(true, instance.buscarInt(str));

        str = "12c";
        assertEquals(false, instance.buscarInt(str));

        str = "12e";
        assertEquals(true, instance.buscarInt(str));

        str = "12n";
        assertEquals(false, instance.buscarInt(str));

        str = "12r";
        assertEquals(false, instance.buscarInt(str));

        str = "-12";
        assertEquals(true, instance.buscarInt(str));
    }

    /**
     * Test of buscarNat method, of class analizadorER.
     */
    @Test
    public void testBuscarNat() {
        System.out.println("buscarNat");
        String str = "";
        assertEquals(false, instance.buscarNat(str));

        str = "true";
        assertEquals(false, instance.buscarNat(str));

        str = "false";
        assertEquals(false, instance.buscarNat(str));

        str = "#Soy un comentario\n";
        assertEquals(false, instance.buscarNat(str));

        str = "'a'";
        assertEquals(false, instance.buscarNat(str));

        str = "0";
        assertEquals(true, instance.buscarNat(str));

        str = "0.0";
        assertEquals(false, instance.buscarNat(str));

        str = "0.2";
        assertEquals(false, instance.buscarNat(str));

        str = "-0.2";
        assertEquals(false, instance.buscarNat(str));

        str = "-1e123";
        assertEquals(false, instance.buscarNat(str));

        str = "-1E123";
        assertEquals(false, instance.buscarNat(str));

        str = "-1.2e123";
        assertEquals(false, instance.buscarNat(str));

        str = "-1.2E123";
        assertEquals(false, instance.buscarNat(str));

        str = "-1.2-e123";
        assertEquals(false, instance.buscarNat(str));

        str = "-1.2-E123";
        assertEquals(false, instance.buscarNat(str));

        str = "0.20";
        assertEquals(false, instance.buscarNat(str));
        
        str = "01";
        assertEquals(false, instance.buscarNat(str));

        str = "12";
        assertEquals(true, instance.buscarNat(str));

        str = "12c";
        assertEquals(false, instance.buscarNat(str));

        str = "12e";
        assertEquals(false, instance.buscarNat(str));

        str = "12n";
        assertEquals(true, instance.buscarNat(str));

        str = "12r";
        assertEquals(false, instance.buscarNat(str));

        str = "-12";
        assertEquals(false, instance.buscarNat(str));
    }

    /**
     * Test of buscarFloat method, of class analizadorER.
     */
    @Test
    public void testBuscarFloat() {
        System.out.println("buscarFloat");
        String str = "";
        assertEquals(false, instance.buscarFloat(str));

        str = "#Soy un comentario\n";
        assertEquals(false, instance.buscarFloat(str));

        str = "true";
        assertEquals(false, instance.buscarFloat(str));

        str = "false";
        assertEquals(false, instance.buscarFloat(str));

        str = "'a'";
        assertEquals(false, instance.buscarFloat(str));

        str = "0";
        assertEquals(false, instance.buscarFloat(str));

        str = "0.0";
        assertEquals(true, instance.buscarFloat(str));

        str = "0.2";
        assertEquals(true, instance.buscarFloat(str));

        str = "-0.2";
        assertEquals(true, instance.buscarFloat(str));

        str = "-1e123";
        assertEquals(true, instance.buscarFloat(str));
        
        str = "-1E123";
        assertEquals(true, instance.buscarFloat(str));

        str = "-1.2e123";
        assertEquals(true, instance.buscarFloat(str));

        str = "-1.2E123";
        assertEquals(true, instance.buscarFloat(str));

        str = "-1.2e-123";
        assertEquals(true, instance.buscarFloat(str));

        str = "-1.2E-123";
        assertEquals(true, instance.buscarFloat(str));

        str = "0.20";
        assertEquals(false, instance.buscarFloat(str));

        str = "01";
        assertEquals(false, instance.buscarFloat(str));

        str = "12";
        assertEquals(false, instance.buscarFloat(str));

        str = "12c";
        assertEquals(false, instance.buscarFloat(str));

        str = "12e";
        assertEquals(false, instance.buscarFloat(str));

        str = "12n";
        assertEquals(false, instance.buscarFloat(str));

        str = "12r";
        assertEquals(false, instance.buscarFloat(str));

        str = "-12";
        assertEquals(false, instance.buscarFloat(str));
    }

    /**
     * Test of buscarBool method, of class analizadorER.
     */
    @Test
    public void testBuscarBool() {
        System.out.println("buscarBool");
        String str = "";
        assertEquals(false, instance.buscarBool(str));

        str = "#Soy un comentario\n";
        assertEquals(false, instance.buscarBool(str));

        str = "true";
        assertEquals(true, instance.buscarBool(str));

        str = "false";
        assertEquals(true, instance.buscarBool(str));

        str = "'a'";
        assertEquals(false, instance.buscarBool(str));

        str = "0";
        assertEquals(false, instance.buscarBool(str));

        str = "0.0";
        assertEquals(false, instance.buscarBool(str));

        str = "0.2";
        assertEquals(false, instance.buscarBool(str));

        str = "-0.2";
        assertEquals(false, instance.buscarBool(str));

        str = "-1e123";
        assertEquals(false, instance.buscarBool(str));

        str = "-1E123";
        assertEquals(false, instance.buscarBool(str));

        str = "-1.2e123";
        assertEquals(false, instance.buscarBool(str));

        str = "-1.2E123";
        assertEquals(false, instance.buscarBool(str));

        str = "-1.2-e123";
        assertEquals(false, instance.buscarBool(str));

        str = "-1.2-E123";
        assertEquals(false, instance.buscarBool(str));

        str = "0.20";
        assertEquals(false, instance.buscarBool(str));

        str = "01";
        assertEquals(false, instance.buscarBool(str));

        str = "12";
        assertEquals(false, instance.buscarBool(str));

        str = "12c";
        assertEquals(false, instance.buscarBool(str));

        str = "12e";
        assertEquals(false, instance.buscarBool(str));

        str = "12n";
        assertEquals(false, instance.buscarBool(str));

        str = "12r";
        assertEquals(false, instance.buscarBool(str));

        str = "-12";
        assertEquals(false, instance.buscarBool(str));
    }

    /**
     * Test of buscarChar method, of class analizadorER.
     */
    @Test
    public void testBuscarChar() {
        System.out.println("buscarChar");
        String str = "";
        assertEquals(false, instance.buscarChar(str));

        str = "#Soy un comentario\n";
        assertEquals(false, instance.buscarChar(str));

        str = "true";
        assertEquals(false, instance.buscarChar(str));

        str = "false";
        assertEquals(false, instance.buscarChar(str));

        str = "'a'";
        assertEquals(true, instance.buscarChar(str));

        str = "'ab'";
        assertEquals(false, instance.buscarChar(str));

        str = "0";
        assertEquals(false, instance.buscarChar(str));

        str = "0.0";
        assertEquals(false, instance.buscarChar(str));

        str = "0.2";
        assertEquals(false, instance.buscarChar(str));

        str = "-0.2";
        assertEquals(false, instance.buscarChar(str));

        str = "-1e123";
        assertEquals(false, instance.buscarChar(str));

        str = "-1E123";
        assertEquals(false, instance.buscarChar(str));

        str = "-1.2e123";
        assertEquals(false, instance.buscarChar(str));

        str = "-1.2E123";
        assertEquals(false, instance.buscarChar(str));

        str = "-1.2-e123";
        assertEquals(false, instance.buscarChar(str));

        str = "-1.2-E123";
        assertEquals(false, instance.buscarChar(str));

        str = "0.20";
        assertEquals(false, instance.buscarChar(str));

        str = "01";
        assertEquals(false, instance.buscarChar(str));

        str = "12";
        assertEquals(false, instance.buscarChar(str));

        str = "12c";
        assertEquals(false, instance.buscarChar(str));

        str = "12e";
        assertEquals(false, instance.buscarChar(str));

        str = "12n";
        assertEquals(false, instance.buscarChar(str));

        str = "12r";
        assertEquals(false, instance.buscarChar(str));

        str = "-12";
        assertEquals(false, instance.buscarChar(str));
    }

    /**
     * Test of buscarComentario method, of class analizadorER.
     */
    @Test
    public void testBuscarComentario() {
        System.out.println("buscarComentario");
        String str = "";
        assertEquals(false, instance.buscarComentario(str));

        str = "#Soy un comentario\n";
        assertEquals(true, instance.buscarComentario(str));

        str = "true";
        assertEquals(false, instance.buscarComentario(str));

        str = "false";
        assertEquals(false, instance.buscarComentario(str));

        str = "'a'";
        assertEquals(false, instance.buscarComentario(str));

        str = "'ab'";
        assertEquals(false, instance.buscarComentario(str));

        str = "0";
        assertEquals(false, instance.buscarComentario(str));

        str = "0.0";
        assertEquals(false, instance.buscarComentario(str));

        str = "0.2";
        assertEquals(false, instance.buscarComentario(str));

        str = "-0.2";
        assertEquals(false, instance.buscarComentario(str));

        str = "-1e123";
        assertEquals(false, instance.buscarComentario(str));

        str = "-1E123";
        assertEquals(false, instance.buscarComentario(str));

        str = "-1.2e123";
        assertEquals(false, instance.buscarComentario(str));

        str = "-1.2E123";
        assertEquals(false, instance.buscarComentario(str));

        str = "-1.2-e123";
        assertEquals(false, instance.buscarComentario(str));

        str = "-1.2-E123";
        assertEquals(false, instance.buscarComentario(str));

        str = "0.20";
        assertEquals(false, instance.buscarComentario(str));

        str = "01";
        assertEquals(false, instance.buscarComentario(str));

        str = "12";
        assertEquals(false, instance.buscarComentario(str));

        str = "12c";
        assertEquals(false, instance.buscarComentario(str));

        str = "12e";
        assertEquals(false, instance.buscarComentario(str));

        str = "12n";
        assertEquals(false, instance.buscarComentario(str));

        str = "12r";
        assertEquals(false, instance.buscarComentario(str));

        str = "-12";
        assertEquals(false, instance.buscarComentario(str));
    }

}