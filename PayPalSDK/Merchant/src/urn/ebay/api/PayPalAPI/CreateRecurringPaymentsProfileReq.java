package urn.ebay.api.PayPalAPI;
import urn.ebay.api.PayPalAPI.CreateRecurringPaymentsProfileRequestType;
import com.paypal.core.SDKUtil;

/**
 * 
 */
public class CreateRecurringPaymentsProfileReq{


	/**
	 * 	 
	 */ 
	private CreateRecurringPaymentsProfileRequestType CreateRecurringPaymentsProfileRequest;

	

	/**
	 * Default Constructor
	 */
	public CreateRecurringPaymentsProfileReq (){
	}	

	/**
	 * Getter for CreateRecurringPaymentsProfileRequest
	 */
	 public CreateRecurringPaymentsProfileRequestType getCreateRecurringPaymentsProfileRequest() {
	 	return CreateRecurringPaymentsProfileRequest;
	 }
	 
	/**
	 * Setter for CreateRecurringPaymentsProfileRequest
	 */
	 public void setCreateRecurringPaymentsProfileRequest(CreateRecurringPaymentsProfileRequestType CreateRecurringPaymentsProfileRequest) {
	 	this.CreateRecurringPaymentsProfileRequest = CreateRecurringPaymentsProfileRequest;
	 }
	 


	public String toXMLString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<urn:CreateRecurringPaymentsProfileReq>");
		if(CreateRecurringPaymentsProfileRequest != null) {
			sb.append("<urn:CreateRecurringPaymentsProfileRequest>");
			sb.append(CreateRecurringPaymentsProfileRequest.toXMLString());
			sb.append("</urn:CreateRecurringPaymentsProfileRequest>");
		}
		sb.append("</urn:CreateRecurringPaymentsProfileReq>");
		return sb.toString();
	}


}