/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compilador.lexico.Tokens;

/**
 *
 * @author ruben
 */
public class Menor_ig extends Token{
    public Menor_ig(int n){
        super(n);
    }

    public String toString(){
        return "Simbolo <=";
    }
}
