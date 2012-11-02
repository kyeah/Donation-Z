package urn.ebay.apis.eBLBaseComponents;

/**
 * 
 */
public enum  AutoBillType {

	NOAUTOBILL("NoAutoBill"),

	ADDTONEXTBILLING("AddToNextBilling");

	private String value;

	private AutoBillType (String value) {
		this.value = value;
	}

	public String getValue(){
		return value;
	}
	
	public static AutoBillType fromValue(String v) {
		for (AutoBillType c : values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}

}