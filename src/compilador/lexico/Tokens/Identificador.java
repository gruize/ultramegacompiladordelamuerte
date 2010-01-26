/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compilador.lexico.Tokens;

/**
 *
 * @author ruben
 */
public class Identificador extends Token{
    public Identificador(String l, int n){
        super(l,n);
        lex = l;
    }

    public String toString(){
        return "identificador \""+lex+"\"";
    }
}
