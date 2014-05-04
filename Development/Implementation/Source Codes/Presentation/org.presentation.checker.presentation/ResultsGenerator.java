package Presentation.org.presentation.checker.presentation;

/**
 * Generate results from MessageReport class.
 * @author radio.koza
 * @version 1.0
 * @created 04-5-2014 20:56:25
 */
public class ResultsGenerator {

	private static ResultsGenerator instance;



	public void finalize() throws Throwable {

	}

	/**
	 * Public constructor is forbidden.
	 */
	private ResultsGenerator(){

	}

	public static ResultsGenerator getInstance(){
		return null;
	}

	/**
	 * 
	 * @param msgReport
	 */
	public String generateHTMLCode(MessageReport msgReport){
		return "";
	}

}