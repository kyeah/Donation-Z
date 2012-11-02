package urn.ebay.apis.eBLBaseComponents;

/**
 * 
 *     AckCodeType
 *     This code identifies the acknowledgement code types that
 * 
 *     could be used to communicate the status of processing a 
 *     (request) message to an application. This code would be
 * used 
 *     as part of a response message that contains an
 * application 
 *     level acknowledgement element.
 * 
 */
public enum  AckCodeType {

	SUCCESS("Success"),

	FAILURE("Failure"),

	WARNING("Warning"),

	SUCCESSWITHWARNING("SuccessWithWarning"),

	FAILUREWITHWARNING("FailureWithWarning"),

	PARTIALSUCCESS("PartialSuccess"),

	CUSTOMCODE("CustomCode");

	private String value;

	private AckCodeType (String value) {
		this.value = value;
	}

	public String getValue(){
		return value;
	}
	
	public static AckCodeType fromValue(String v) {
		for (AckCodeType c : values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}

}