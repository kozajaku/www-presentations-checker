package CSSRedundancyChecker.org.presentation.checker.cssredundancychecker.impl;

/**
 * @author Adam
 * @version 1.0
 * @created 04-5-2014 20:56:23
 */
public class CSSSelectorRC {

	private InfluencingAttributeRC attributesSet;
	private String name;
	private boolean used;

	public CSSSelectorRC(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * Returns if selector was succesfully applied. False = redundant selector.
	 * 
	 * @param HTML
	 */
	public boolean applySelector(HTMLCodeRC HTML){
		return false;
	}

}