package urn.ebay.api.PayPalAPI;
import urn.ebay.api.PayPalAPI.DoNonReferencedCreditRequestType;
import com.paypal.core.SDKUtil;

/**
 * 
 */
public class DoNonReferencedCreditReq{


	/**
	 * 	 
	 */ 
	private DoNonReferencedCreditRequestType DoNonReferencedCreditRequest;

	

	/**
	 * Default Constructor
	 */
	public DoNonReferencedCreditReq (){
	}	

	/**
	 * Getter for DoNonReferencedCreditRequest
	 */
	 public DoNonReferencedCreditRequestType getDoNonReferencedCreditRequest() {
	 	return DoNonReferencedCreditRequest;
	 }
	 
	/**
	 * Setter for DoNonReferencedCreditRequest
	 */
	 public void setDoNonReferencedCreditRequest(DoNonReferencedCreditRequestType DoNonReferencedCreditRequest) {
	 	this.DoNonReferencedCreditRequest = DoNonReferencedCreditRequest;
	 }
	 


	public String toXMLString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<urn:DoNonReferencedCreditReq>");
		if(DoNonReferencedCreditRequest != null) {
			sb.append("<urn:DoNonReferencedCreditRequest>");
			sb.append(DoNonReferencedCreditRequest.toXMLString());
			sb.append("</urn:DoNonReferencedCreditRequest>");
		}
		sb.append("</urn:DoNonReferencedCreditReq>");
		return sb.toString();
	}


}