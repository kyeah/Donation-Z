package com.sample.merchant;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import urn.ebay.api.PayPalAPI.DoDirectPaymentReq;
import urn.ebay.api.PayPalAPI.DoDirectPaymentRequestType;
import urn.ebay.api.PayPalAPI.DoDirectPaymentResponseType;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.AddressType;
import urn.ebay.apis.eBLBaseComponents.CountryCodeType;
import urn.ebay.apis.eBLBaseComponents.CreditCardDetailsType;
import urn.ebay.apis.eBLBaseComponents.CreditCardTypeType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.DoDirectPaymentRequestDetailsType;
import urn.ebay.apis.eBLBaseComponents.PayerInfoType;
import urn.ebay.apis.eBLBaseComponents.PaymentActionCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsType;
import urn.ebay.apis.eBLBaseComponents.PersonNameType;

import com.paypal.exception.ClientActionRequiredException;
import com.paypal.exception.HttpErrorException;
import com.paypal.exception.InvalidCredentialException;
import com.paypal.exception.InvalidResponseDataException;
import com.paypal.exception.MissingCredentialException;
import com.paypal.exception.SSLConfigurationException;
import com.paypal.sdk.exceptions.OAuthException;

public class DoDirectPaymentServlet extends HttpServlet {

	private static final long serialVersionUID = 12345456723541232L;

	public DoDirectPaymentServlet() {
		super();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		getServletConfig().getServletContext()
				.getRequestDispatcher("/DirectPayment/DoDirectPayment.jsp")
				.forward(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		session.setAttribute("url", req.getRequestURI());
		session.setAttribute(
				"relatedUrl",
				"<ul><li><a href='DCC/DoDirectPayment'>DoDirectPayment</a></li><li><a href='RT/DoReferenceTransaction'>DoReferenceTransaction</a></li><li><a href='RP/CreateRecurringPaymentsProfile'>CreateRecurringPaymentsProfile</a></li></ul>");
		DoDirectPaymentReq doPaymentReq = new DoDirectPaymentReq();
		DoDirectPaymentRequestType pprequest = new DoDirectPaymentRequestType();
		DoDirectPaymentRequestDetailsType details = new DoDirectPaymentRequestDetailsType();
		PaymentDetailsType paymentDetails = new PaymentDetailsType();

		BasicAmountType amount = new BasicAmountType();
		amount.setValue(req.getParameter("amount"));
		amount.setCurrencyID(CurrencyCodeType.fromValue(req
				.getParameter("currencyCode")));
		paymentDetails.setOrderTotal(amount);

		AddressType shipTo = new AddressType();
		shipTo.setName(req.getParameter("firstName") + " "
				+ req.getParameter("lastName"));
		shipTo.setStreet1(req.getParameter("address1"));
		shipTo.setStreet2(req.getParameter("address2"));
		shipTo.setCityName(req.getParameter("city"));
		shipTo.setStateOrProvince(req.getParameter("state"));
		shipTo.setCountry(CountryCodeType.fromValue(req
				.getParameter("countryCode")));
		shipTo.setPostalCode(req.getParameter("zip"));
		paymentDetails.setShipToAddress(shipTo);

		details.setPaymentDetails(paymentDetails);

		CreditCardDetailsType cardDetails = new CreditCardDetailsType();
		cardDetails.setCreditCardType(CreditCardTypeType.fromValue(req
				.getParameter("creditCardType")));
		cardDetails.setCreditCardNumber(req.getParameter("creditCardNumber"));
		cardDetails.setExpMonth(Integer.parseInt(req
				.getParameter("expDateMonth")));
		cardDetails
				.setExpYear(Integer.parseInt(req.getParameter("expDateYear")));
		cardDetails.setCVV2(req.getParameter("cvv2Number"));

		PayerInfoType payer = new PayerInfoType();
		PersonNameType name = new PersonNameType();
		name.setFirstName(req.getParameter("firstName"));
		name.setLastName(req.getParameter("lastName"));
		payer.setPayerName(name);
		payer.setPayerCountry(CountryCodeType.fromValue(req
				.getParameter("countryCode")));
		payer.setAddress(shipTo);

		cardDetails.setCardOwner(payer);

		details.setCreditCard(cardDetails);

		details.setIPAddress("127.0.0.1");
		details.setPaymentAction(PaymentActionCodeType.fromValue(req
				.getParameter("paymentType")));

		pprequest.setDoDirectPaymentRequestDetails(details);
		doPaymentReq.setDoDirectPaymentRequest(pprequest);

		try {
			PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(
					this.getServletContext().getRealPath("/")
							+ "/WEB-INF/sdk_config.properties");
			DoDirectPaymentResponseType ddresponse = service
					.doDirectPayment(doPaymentReq);
			res.setContentType("text/html");
			if (ddresponse != null) {
				session.setAttribute("lastReq", service.getLastRequest());
				session.setAttribute("lastResp", service.getLastResponse());
				if (ddresponse.getAck().toString().equalsIgnoreCase("SUCCESS")) {
					Map<Object, Object> map = new LinkedHashMap<Object, Object>();
					map.put("Ack", ddresponse.getAck());
					map.put("Transaction ID", ddresponse.getTransactionID());
					map.put("Amount", ddresponse.getAmount().getValue() + " "
							+ ddresponse.getAmount().getCurrencyID());
					map.put("Payment Status", ddresponse.getPaymentStatus());
					session.setAttribute("map", map);
					res.sendRedirect("/merchant-sample/Response.jsp");
				} else {
					session.setAttribute("Error", ddresponse.getErrors());
					res.sendRedirect("/merchant-sample/Error.jsp");
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SSLConfigurationException e) {
			e.printStackTrace();
		} catch (InvalidCredentialException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (HttpErrorException e) {
			e.printStackTrace();
		} catch (InvalidResponseDataException e) {
			e.printStackTrace();
		} catch (ClientActionRequiredException e) {
			e.printStackTrace();
		} catch (MissingCredentialException e) {
			e.printStackTrace();
		} catch (OAuthException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
