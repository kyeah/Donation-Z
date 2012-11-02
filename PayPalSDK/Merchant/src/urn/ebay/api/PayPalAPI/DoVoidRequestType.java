package urn.ebay.api.PayPalAPI;
import urn.ebay.apis.eBLBaseComponents.AbstractRequestType;
import com.paypal.core.SDKUtil;

/**
 * The value of the original authorization identification
 * number returned by a PayPal product. If you are voiding a
 * transaction that has been reauthorized, use the ID from the
 * original authorization, and not the reauthorization.
 * Required Character length and limits: 19 single-byte
 * characters 
 */
public class DoVoidRequestType extends AbstractRequestType {


	/**
	 * The value of the original authorization identification
	 * number returned by a PayPal product. If you are voiding a
	 * transaction that has been reauthorized, use the ID from the
	 * original authorization, and not the reauthorization.
	 * Required Character length and limits: 19 single-byte
	 * characters	  
	 *@Required	 
	 */ 
	private String AuthorizationID;

	/**
	 * An informational note about this settlement that is
	 * displayed to the payer in email and in transaction history.
	 * Optional Character length and limits: 255 single-byte
	 * characters	 
	 */ 
	private String Note;

	/**
	 * Unique id for each API request to prevent duplicate
	 * payments. Optional Character length and limits: 38
	 * single-byte characters maximum. 	 
	 */ 
	private String MsgSubID;

	

	/**
	 * Constructor with arguments
	 */
	public DoVoidRequestType (String AuthorizationID){
		this.AuthorizationID = AuthorizationID;
	}	

	/**
	 * Default Constructor
	 */
	public DoVoidRequestType (){
	}	

	/**
	 * Getter for AuthorizationID
	 */
	 public String getAuthorizationID() {
	 	return AuthorizationID;
	 }
	 
	/**
	 * Setter for AuthorizationID
	 */
	 public void setAuthorizationID(String AuthorizationID) {
	 	this.AuthorizationID = AuthorizationID;
	 }
	 
	/**
	 * Getter for Note
	 */
	 public String getNote() {
	 	return Note;
	 }
	 
	/**
	 * Setter for Note
	 */
	 public void setNote(String Note) {
	 	this.Note = Note;
	 }
	 
	/**
	 * Getter for MsgSubID
	 */
	 public String getMsgSubID() {
	 	return MsgSubID;
	 }
	 
	/**
	 * Setter for MsgSubID
	 */
	 public void setMsgSubID(String MsgSubID) {
	 	this.MsgSubID = MsgSubID;
	 }
	 


	public String toXMLString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toXMLString());
		if(AuthorizationID != null) {
			sb.append("<urn:AuthorizationID>").append(SDKUtil.escapeInvalidXmlCharsRegex(AuthorizationID));
			sb.append("</urn:AuthorizationID>");
		}
		if(Note != null) {
			sb.append("<urn:Note>").append(SDKUtil.escapeInvalidXmlCharsRegex(Note));
			sb.append("</urn:Note>");
		}
		if(MsgSubID != null) {
			sb.append("<urn:MsgSubID>").append(SDKUtil.escapeInvalidXmlCharsRegex(MsgSubID));
			sb.append("</urn:MsgSubID>");
		}
		return sb.toString();
	}


}