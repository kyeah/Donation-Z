package urn.ebay.api.PayPalAPI;
import urn.ebay.apis.eBLBaseComponents.AbstractRequestType;
import com.paypal.core.SDKUtil;

/**
 * 
 */
public class GetPalDetailsRequestType extends AbstractRequestType {


	

	/**
	 * Default Constructor
	 */
	public GetPalDetailsRequestType (){
	}	



	public String toXMLString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toXMLString());
		return sb.toString();
	}


}