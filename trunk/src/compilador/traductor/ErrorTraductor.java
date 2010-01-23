package compilador.traductor;

public class ErrorTraductor {
	String desc;
	
	public ErrorTraductor(String s){
		desc=s;
	}
	
	public String getDesc(){
		return desc;
	}
}
