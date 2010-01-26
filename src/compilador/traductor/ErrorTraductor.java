package compilador.traductor;

/**
 *
 * @author GRUPO 3: Gonzalo Ortiz Jaureguizar, Alicia Perez Jimenez, Laura Reyero Sainz, Hector Sanjuan Redondo, Ruben Tarancon Garijo
 */
public class ErrorTraductor {
	String desc;
	
	public ErrorTraductor(String s){
		desc=s;
	}
	
	public String getDesc(){
		return desc;
	}
}
