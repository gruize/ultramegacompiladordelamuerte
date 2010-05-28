
package interfaz.pila;

import java.util.StringTokenizer;


import pila.interprete.datos.Bool;
import pila.interprete.datos.Caracter;
import pila.interprete.datos.DatoPila;
import pila.interprete.datos.Entero;
import pila.interprete.datos.Nat;
import pila.interprete.datos.Real;
import pila.interprete.excepiones.DatoExc;
import pila.interprete.instrucciones.*;

/**
 *
 * @author GRUPO 3: Gonzalo Ortiz Jaureguizar, Alicia Perez Jimenez, Laura Reyero Sainz, Hector Sanjuan Redondo, Ruben Tarancon Garijo
 */


public class TraductorInterprete extends TraductorPila {

    @Override
    protected InstruccionInterprete traducirApila(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new Apilar((DatoPila)traducirDato(st));
        return ins;
    }

    @Override
    protected InstruccionInterprete traducirApilaDir(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new ApilarDir((DatoPila)traducirDato(st));
        return ins;
    }

    @Override
    protected InstruccionInterprete traducirDesapila(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new Desapilar();
        return ins;
    }

    @Override
    protected InstruccionInterprete traducirDesapilaDir(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new DesapilarDir((DatoPila)traducirDato(st));
        return ins;
    }

    @Override
    protected InstruccionInterprete traducirChar(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new CastChar();
        return ins;
    }

    @Override
    protected InstruccionInterprete traducirFloat(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new CastFloat();
        return ins;
    }

    @Override
    protected InstruccionInterprete traducirInt(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new CastInt();
        return ins;
    }

    @Override
    protected InstruccionInterprete traducirDiv(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new Divide();
        return ins;
    }

    @Override
    protected InstruccionInterprete traducirIgual(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new Igual();
        return ins;
    }

    @Override
    protected InstruccionInterprete traducirMayor(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new Mayor();
        return ins;
    }

    @Override
    protected InstruccionInterprete traducirMayorIg(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new MayorIg();
        return ins;
    }

    @Override
    protected InstruccionInterprete traducirMenor(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new Menor();
        return ins;
    }

    @Override
    protected InstruccionInterprete traducirMenorIg(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new MenorIg();
        return ins;
    }

    @Override
    protected InstruccionInterprete traducirMenos(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new Menos();
        return ins;
    }

    @Override
    protected InstruccionInterprete traducirMod(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new Modulo();
        return ins;
    }

    @Override
    protected InstruccionInterprete traducirMult(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new Multiplica();
        return ins;
    }

    @Override
    protected InstruccionInterprete traducirNo(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new No();
        return ins;
    }

    @Override
    protected InstruccionInterprete traducirNoIgual(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new NoIgual();
        return ins;
    }

    @Override
    protected InstruccionInterprete traducirO(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new O();
        return ins;
    }

    @Override
    protected InstruccionInterprete traducirResta(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new Resta();
        return ins;
    }

    @Override
    protected InstruccionInterprete traducirShl(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new Shl();
        return ins;
    }

    @Override
    protected InstruccionInterprete traducirShr(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new Shr();
        return ins;
    }

    @Override
    protected InstruccionInterprete traducirSuma(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new Suma();
        return ins;
    }

    @Override
    protected InstruccionInterprete traducirY(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new Y();
        return ins;
    }

    @Override
    protected DatoPila traducirInt(String str) throws Exception {
        return new Entero(Integer.valueOf(str));
    }

    @Override
    protected DatoPila traducirNat(String str) throws DatoExc {
        return new Nat(Integer.valueOf(str));
    }

    @Override
    protected DatoPila traducirFloat(String str) {
        return new Real(Float.valueOf(str));
    }

    @Override
    protected DatoPila traducirChar(String str) {
        return new Caracter(str.charAt(0));
    }

    @Override
    protected DatoPila traducirBool(String str) {
        return new Bool(Boolean.valueOf(str));
    }

    @Override
    protected InstruccionInterprete traducirParar(StringTokenizer st) throws Exception {
        return new Parar();
    }

    @Override
    protected InstruccionInterprete traducirIn(StringTokenizer st) throws Exception {
        return new EntradaBool((DatoPila) traducirDato(st));
    }

    @Override
    protected InstruccionInterprete traducirOut(StringTokenizer st) throws Exception {
        return new Salida();
    }

    @Override
    protected InstruccionInterprete traducirAbs(StringTokenizer st) throws Exception {
        return new Abs();
    }

    @Override
    protected InstruccionInterprete traducirNat(StringTokenizer st) throws Exception {
        return new CastNat();
    }

}
