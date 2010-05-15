/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compilador.lexico.Tokens;

/**
 *
 * @author GRUPO 3: Gonzalo Ortiz Jaureguizar, Alicia Perez Jimenez, Laura Reyero Sainz, Hector Sanjuan Redondo, Ruben Tarancon Garijo
 */
public class Token_Absoluto extends Token{
    public Token_Absoluto(int n){
        super(n);
    }

    public String toString(){
        return "Barra de valor Absoluto";
    }
}
