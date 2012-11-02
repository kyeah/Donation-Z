package urn.ebay.apis.eBLBaseComponents;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.FailedPaymentActionType;
import com.paypal.core.SDKUtil;

/**
 * 
 */
public class ActivationDetailsType{


	/**
	 * 	  
	 *@Required	 
	 */ 
	private BasicAmountType InitialAmount;

	/**
	 * 	 
	 */ 
	private FailedPaymentActionType FailedInitialAmountAction;

	

	/**
	 * Constructor with arguments
	 */
	public ActivationDetailsType (BasicAmountType InitialAmount){
		this.InitialAmount = InitialAmount;
	}	

	/**
	 * Default Constructor
	 */
	public ActivationDetailsType (){
	}	

	/**
	 * Getter for InitialAmount
	 */
	 public BasicAmountType getInitialAmount() {
	 	return InitialAmount;
	 }
	 
	/**
	 * Setter for InitialAmount
	 */
	 public void setInitialAmount(BasicAmountType InitialAmount) {
	 	this.InitialAmount = InitialAmount;
	 }
	 
	/**
	 * Getter for FailedInitialAmountAction
	 */
	 public FailedPaymentActionType getFailedInitialAmountAction() {
	 	return FailedInitialAmountAction;
	 }
	 
	/**
	 * Setter for FailedInitialAmountAction
	 */
	 public void setFailedInitialAmountAction(FailedPaymentActionType FailedInitialAmountAction) {
	 	this.FailedInitialAmountAction = FailedInitialAmountAction;
	 }
	 


	public String toXMLString() {
		StringBuilder sb = new StringBuilder();
		if(InitialAmount != null) {
			sb.append("<ebl:InitialAmount");
			sb.append(InitialAmount.toXMLString());
			sb.append("</ebl:InitialAmount>");
		}
		if(FailedInitialAmountAction != null) {
			sb.append("<ebl:FailedInitialAmountAction>").append(SDKUtil.escapeInvalidXmlCharsRegex(FailedInitialAmountAction.getValue()));
			sb.append("</ebl:FailedInitialAmountAction>");
		}
		return sb.toString();
	}


}