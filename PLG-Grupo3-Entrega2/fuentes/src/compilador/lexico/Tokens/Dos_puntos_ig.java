/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compilador.lexico.Tokens;

/**
 *
 * @author GRUPO 3: Gonzalo Ortiz Jaureguizar, Alicia Perez Jimenez, Laura Reyero Sainz, Hector Sanjuan Redondo, Ruben Tarancon Garijo
 */
public class Dos_puntos_ig extends Token{
    public Dos_puntos_ig(int n){
        super(n);
    }

    public String toString(){
        return "Simbolo :=";
    }
}
