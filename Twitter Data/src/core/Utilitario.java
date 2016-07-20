package core;

public class Utilitario {

	public static String removeLineBreak(String valor) {
		return valor.replace("\n", "").replace("\r", "");
	}
}
