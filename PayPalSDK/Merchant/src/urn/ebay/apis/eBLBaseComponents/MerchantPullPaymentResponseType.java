package urn.ebay.apis.eBLBaseComponents;
import urn.ebay.apis.eBLBaseComponents.PayerInfoType;
import urn.ebay.apis.eBLBaseComponents.PaymentInfoType;
import urn.ebay.apis.eBLBaseComponents.MerchantPullInfoType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import java.io.FileInputStream;
import java.io.StringReader;
import java.io.IOException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * MerchantPullPaymentResponseType Response data from the
 * merchant pull. 
 */
public class MerchantPullPaymentResponseType{


	/**
	 * information about the customer	 
	 */ 
	private PayerInfoType PayerInfo;

	/**
	 * Information about the transaction 	 
	 */ 
	private PaymentInfoType PaymentInfo;

	/**
	 * Specific information about the preapproved payment 	 
	 */ 
	private MerchantPullInfoType MerchantPullInfo;

	

	/**
	 * Default Constructor
	 */
	public MerchantPullPaymentResponseType (){
	}	

	/**
	 * Getter for PayerInfo
	 */
	 public PayerInfoType getPayerInfo() {
	 	return PayerInfo;
	 }
	 
	/**
	 * Setter for PayerInfo
	 */
	 public void setPayerInfo(PayerInfoType PayerInfo) {
	 	this.PayerInfo = PayerInfo;
	 }
	 
	/**
	 * Getter for PaymentInfo
	 */
	 public PaymentInfoType getPaymentInfo() {
	 	return PaymentInfo;
	 }
	 
	/**
	 * Setter for PaymentInfo
	 */
	 public void setPaymentInfo(PaymentInfoType PaymentInfo) {
	 	this.PaymentInfo = PaymentInfo;
	 }
	 
	/**
	 * Getter for MerchantPullInfo
	 */
	 public MerchantPullInfoType getMerchantPullInfo() {
	 	return MerchantPullInfo;
	 }
	 
	/**
	 * Setter for MerchantPullInfo
	 */
	 public void setMerchantPullInfo(MerchantPullInfoType MerchantPullInfo) {
	 	this.MerchantPullInfo = MerchantPullInfo;
	 }
	 



	private  boolean isWhitespaceNode(Node n) {
		if (n.getNodeType() == Node.TEXT_NODE) {
			String val = n.getNodeValue();
			return val.trim().length() == 0;
		} else if (n.getNodeType() == Node.ELEMENT_NODE ) {
			return (n.getChildNodes().getLength() == 0);
		} else {
			return false;
		}
	}
	
	public MerchantPullPaymentResponseType(Node node) throws XPathExpressionException {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		Node childNode = null;
		NodeList nodeList = null;
		childNode = (Node) xpath.evaluate("PayerInfo", node, XPathConstants.NODE);
        if (childNode != null && !isWhitespaceNode(childNode)) {
		    this.PayerInfo =  new PayerInfoType(childNode);
		}
		childNode = (Node) xpath.evaluate("PaymentInfo", node, XPathConstants.NODE);
        if (childNode != null && !isWhitespaceNode(childNode)) {
		    this.PaymentInfo =  new PaymentInfoType(childNode);
		}
		childNode = (Node) xpath.evaluate("MerchantPullInfo", node, XPathConstants.NODE);
        if (childNode != null && !isWhitespaceNode(childNode)) {
		    this.MerchantPullInfo =  new MerchantPullInfoType(childNode);
		}
	}
 
}