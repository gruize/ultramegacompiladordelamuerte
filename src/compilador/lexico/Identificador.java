/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compilador.lexico;

/**
 *
 * @author ruben
 */
public class Identificador extends Token{
    String lex;
    public Identificador(String l, int n){
        super(n);
        lex = l;
    }
}
