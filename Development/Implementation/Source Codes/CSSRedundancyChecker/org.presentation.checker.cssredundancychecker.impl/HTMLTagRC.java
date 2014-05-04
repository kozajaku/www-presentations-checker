package CSSRedundancyChecker.org.presentation.checker.cssredundancychecker.impl;

/**
 * @author Adam
 * @version 1.0
 * @created 04-5-2014 20:56:24
 */
public class HTMLTagRC {

	private int id;
	/**
	 * Set of attributes, which are already influenced.
	 */
	private CSSAttributeRC influencedAttributesSet;
	private HTMLTagRC innerTags;
	private String name;

	public HTMLTagRC(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * Returns if attribute wasn't influenced yet.
	 * 
	 * @param attribute
	 */
	public boolean addInfluencedAttribute(CSSAttributeRC attribute){
		return false;
	}

}