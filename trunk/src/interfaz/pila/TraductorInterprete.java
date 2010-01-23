
package interfaz.pila;

import java.util.StringTokenizer;

import pila.Dato;
import pila.Instruccion;
import pila.interprete.datos.Booleano;
import pila.interprete.datos.Caracter;
import pila.interprete.datos.DatoPila;
import pila.interprete.datos.Entero;
import pila.interprete.datos.Natural;
import pila.interprete.datos.Real;
import pila.interprete.excepiones.DatoExc;
import pila.interprete.instrucciones.*;


public class TraductorInterprete extends TraductorPila {

    @Override
    protected Instruccion traducirApila(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new Apilar((DatoPila)traducirDato(st));
        return ins;
    }

    @Override
    protected Instruccion traducirApilaDir(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new ApilarDir((DatoPila)traducirDato(st));
        return ins;
    }

    @Override
    protected Instruccion traducirDesapila(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new Desapilar();
        return ins;
    }

    @Override
    protected Instruccion traducirDesapilaDir(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new DesapilarDir((DatoPila)traducirDato(st));
        return ins;
    }

    @Override
    protected Instruccion traducirChar(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new CastChar();
        return ins;
    }

    @Override
    protected Instruccion traducirFloat(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new CastFloat();
        return ins;
    }

    @Override
    protected Instruccion traducirInt(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new CastInt();
        return ins;
    }

    @Override
    protected Instruccion traducirDiv(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new Divide();
        return ins;
    }

    @Override
    protected Instruccion traducirIgual(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new Igual();
        return ins;
    }

    @Override
    protected Instruccion traducirMayor(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new Mayor();
        return ins;
    }

    @Override
    protected Instruccion traducirMayorIg(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new MayorIg();
        return ins;
    }

    @Override
    protected Instruccion traducirMenor(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new Menor();
        return ins;
    }

    @Override
    protected Instruccion traducirMenorIg(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new MenorIg();
        return ins;
    }

    @Override
    protected Instruccion traducirMenos(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new Menos();
        return ins;
    }

    @Override
    protected Instruccion traducirMod(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new Modulo();
        return ins;
    }

    @Override
    protected Instruccion traducirMult(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new Multiplica();
        return ins;
    }

    @Override
    protected Instruccion traducirNo(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new No();
        return ins;
    }

    @Override
    protected Instruccion traducirNoIgual(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new NoIgual();
        return ins;
    }

    @Override
    protected Instruccion traducirO(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new O();
        return ins;
    }

    @Override
    protected Instruccion traducirResta(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new Resta();
        return ins;
    }

    @Override
    protected Instruccion traducirShl(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new Shl();
        return ins;
    }

    @Override
    protected Instruccion traducirShr(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new Shr();
        return ins;
    }

    @Override
    protected Instruccion traducirSuma(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new Suma();
        return ins;
    }

    @Override
    protected Instruccion traducirY(StringTokenizer st) throws Exception {
        InstruccionInterprete ins;
        ins = new Y();
        return ins;
    }

    @Override
    protected Dato traducirInt(String str) throws Exception {
        return new Entero(Integer.valueOf(str));
    }

    @Override
    protected Dato traducirNat(String str) throws DatoExc {
        return new Natural(Integer.valueOf(str));
    }

    @Override
    protected Dato traducirFloat(String str) {
        return new Real(Float.valueOf(str));
    }

    @Override
    protected Dato traducirChar(String str) {
        return new Caracter(str.charAt(0));
    }

    @Override
    protected Dato traducirBool(String str) {
        return new Booleano(Boolean.valueOf(str));
    }

    @Override
    protected Instruccion traducirParar(StringTokenizer st) throws Exception {
        return new Parar();
    }

    @Override
    protected Instruccion traducirIn(StringTokenizer st) throws Exception {
        return new EntradaBool((DatoPila) traducirDato(st));
    }

    @Override
    protected Instruccion traducirOut(StringTokenizer st) throws Exception {
        return new Salida();
    }

    @Override
    protected Instruccion traducirAbs(StringTokenizer st) throws Exception {
        return new Abs();
    }

    @Override
    protected Instruccion traducirNat(StringTokenizer st) throws Exception {
        return new CastNat();
    }

}
