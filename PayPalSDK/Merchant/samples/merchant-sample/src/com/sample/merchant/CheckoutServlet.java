package com.sample.merchant;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentReq;
import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentRequestType;
import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentResponseType;
import urn.ebay.api.PayPalAPI.DoUATPExpressCheckoutPaymentReq;
import urn.ebay.api.PayPalAPI.DoUATPExpressCheckoutPaymentRequestType;
import urn.ebay.api.PayPalAPI.DoUATPExpressCheckoutPaymentResponseType;
import urn.ebay.api.PayPalAPI.ExecuteCheckoutOperationsReq;
import urn.ebay.api.PayPalAPI.ExecuteCheckoutOperationsRequestType;
import urn.ebay.api.PayPalAPI.ExecuteCheckoutOperationsResponseType;
import urn.ebay.api.PayPalAPI.ExternalRememberMeOptOutReq;
import urn.ebay.api.PayPalAPI.ExternalRememberMeOptOutRequestType;
import urn.ebay.api.PayPalAPI.ExternalRememberMeOptOutResponseType;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsReq;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsRequestType;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsResponseType;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutReq;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutRequestType;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutResponseType;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.AddressType;
import urn.ebay.apis.eBLBaseComponents.ApprovalSubTypeType;
import urn.ebay.apis.eBLBaseComponents.ApprovalTypeType;
import urn.ebay.apis.eBLBaseComponents.AuthorizationRequestType;
import urn.ebay.apis.eBLBaseComponents.BillingAgreementDetailsType;
import urn.ebay.apis.eBLBaseComponents.BillingApprovalDetailsType;
import urn.ebay.apis.eBLBaseComponents.BillingCodeType;
import urn.ebay.apis.eBLBaseComponents.BuyerDetailType;
import urn.ebay.apis.eBLBaseComponents.CountryCodeType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.DoExpressCheckoutPaymentRequestDetailsType;
import urn.ebay.apis.eBLBaseComponents.ExecuteCheckoutOperationsRequestDetailsType;
import urn.ebay.apis.eBLBaseComponents.ExternalRememberMeOwnerDetailsType;
import urn.ebay.apis.eBLBaseComponents.IdentificationInfoType;
import urn.ebay.apis.eBLBaseComponents.InfoSharingDirectivesType;
import urn.ebay.apis.eBLBaseComponents.ItemCategoryType;
import urn.ebay.apis.eBLBaseComponents.MerchantPullPaymentCodeType;
import urn.ebay.apis.eBLBaseComponents.MobileIDInfoType;
import urn.ebay.apis.eBLBaseComponents.OrderDetailsType;
import urn.ebay.apis.eBLBaseComponents.PaymentActionCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsItemType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsType;
import urn.ebay.apis.eBLBaseComponents.PaymentDirectivesType;
import urn.ebay.apis.eBLBaseComponents.PaymentInfoType;
import urn.ebay.apis.eBLBaseComponents.RememberMeIDInfoType;
import urn.ebay.apis.eBLBaseComponents.SetDataRequestType;
import urn.ebay.apis.eBLBaseComponents.SetExpressCheckoutRequestDetailsType;

import com.paypal.exception.ClientActionRequiredException;
import com.paypal.exception.HttpErrorException;
import com.paypal.exception.InvalidCredentialException;
import com.paypal.exception.InvalidResponseDataException;
import com.paypal.exception.MissingCredentialException;
import com.paypal.exception.SSLConfigurationException;
import com.paypal.sdk.exceptions.OAuthException;

/**
 * Servlet implementation class CheckoutServlet
 */
public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public CheckoutServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getRequestURI().contains("SetExpressCheckout"))
			getServletConfig().getServletContext()
					.getRequestDispatcher("/Checkout/SetExpressCheckout.jsp")
					.forward(request, response);
		else if (request.getRequestURI().contains("GetExpressCheckout"))
			getServletConfig().getServletContext()
					.getRequestDispatcher("/Checkout/GetExpressCheckout.jsp")
					.forward(request, response);
		else if (request.getRequestURI().contains("DoExpressCheckout"))
			getServletConfig().getServletContext()
					.getRequestDispatcher("/Checkout/DoExpressCheckout.jsp")
					.forward(request, response);
		else if (request.getRequestURI().contains(
				"DoUATPExpressCheckoutPayment"))
			getServletConfig()
					.getServletContext()
					.getRequestDispatcher(
							"/Checkout/DoUATPExpressCheckoutPayment.jsp")
					.forward(request, response);
		else if (request.getRequestURI().contains("ExternalRememberMeOptOut"))
			getServletConfig()
					.getServletContext()
					.getRequestDispatcher(
							"/Checkout/ExternalRememberMeOptOut.jsp")
					.forward(request, response);
		else if (request.getRequestURI().contains("ExecuteCheckoutOperations"))
			getServletConfig()
					.getServletContext()
					.getRequestDispatcher(
							"/Checkout/ExecuteCheckoutOperations.jsp")
					.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		session.setAttribute("url", request.getRequestURI());
		session.setAttribute(
				"relatedUrl",
				"<ul><li><a href='EC/SetExpressCheckout'>SetExpressCheckout</a></li><li><a href='EC/GetExpressCheckout'>GetExpressCheckout</a></li><li><a href='EC/DoExpressCheckout'>DoExpressCheckout</a></li></ul>");
		response.setContentType("text/html");
		try {
			PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(
					this.getServletContext().getRealPath("/")
							+ "/WEB-INF/sdk_config.properties");
			if (request.getRequestURI().contains("SetExpressCheckout")) {
				SetExpressCheckoutRequestType setExpressCheckoutReq = new SetExpressCheckoutRequestType();
				SetExpressCheckoutRequestDetailsType details = new SetExpressCheckoutRequestDetailsType();

				StringBuffer url = new StringBuffer();
				url.append("http://");
				url.append(request.getServerName());
				url.append(":");
				url.append(request.getServerPort());
				url.append(request.getContextPath());

				String returnURL = url.toString() + "/EC/GetExpressCheckout";
				String cancelURL = url.toString() + "/index.html";

				details.setReturnURL(returnURL + "?currencyCodeType="
						+ request.getParameter("currencyCode"));

				details.setCancelURL(cancelURL);
				details.setBuyerEmail(request.getParameter("buyerMail"));

				request.getSession().setAttribute("paymentType",
						request.getParameter("paymentType"));

				double itemTotal = 0.00;
				double orderTotal = 0.00;
				// populate line item details
				String amountItems = request.getParameter("itemAmount");
				String qtyItems = request.getParameter("itemQuantity");
				String names = request.getParameter("itemName");

				List<PaymentDetailsItemType> lineItems = new ArrayList<PaymentDetailsItemType>();

				PaymentDetailsItemType item = new PaymentDetailsItemType();
				BasicAmountType amt = new BasicAmountType();
				amt.setCurrencyID(CurrencyCodeType.fromValue(request
						.getParameter("currencyCode")));
				amt.setValue(amountItems);
				item.setQuantity(new Integer(qtyItems));
				item.setName(names);
				item.setAmount(amt);
				item.setItemCategory(ItemCategoryType.fromValue(request
						.getParameter("itemCategory")));
				item.setDescription(request.getParameter("itemDescription"));
				lineItems.add(item);

				if (request.getParameter("salesTax") != "") {
					item.setTax(new BasicAmountType(CurrencyCodeType
							.fromValue(request.getParameter("currencyCode")),
							request.getParameter("salesTax")));					
				}
				
				itemTotal += Double.parseDouble(qtyItems) * Double.parseDouble(amountItems);
				orderTotal += itemTotal;
				
				List<PaymentDetailsType> payDetails = new ArrayList<PaymentDetailsType>();
				PaymentDetailsType paydtl = new PaymentDetailsType();
				paydtl.setPaymentAction(PaymentActionCodeType.fromValue(request
						.getParameter("paymentType")));
				if (request.getParameter("shippingTotal") != "") {
					BasicAmountType shippingTotal = new BasicAmountType();
					shippingTotal.setValue(request
							.getParameter("shippingTotal"));
					shippingTotal.setCurrencyID(CurrencyCodeType
							.fromValue(request.getParameter("currencyCode")));
					orderTotal += Double.parseDouble(request
							.getParameter("shippingTotal"));
					paydtl.setShippingTotal(shippingTotal);
				}
				if (request.getParameter("insuranceTotal") != "") {
					paydtl.setInsuranceTotal(new BasicAmountType(
							CurrencyCodeType.fromValue(request
									.getParameter("currencyCode")), request
									.getParameter("insuranceTotal")));
					paydtl.setInsuranceOptionOffered("true");
					orderTotal += Double.parseDouble(request
							.getParameter("insuranceTotal"));
				}
				if (request.getParameter("handlingTotal") != "") {
					paydtl.setHandlingTotal(new BasicAmountType(
							CurrencyCodeType.fromValue(request
									.getParameter("currencyCode")), request
									.getParameter("handlingTotal")));
					orderTotal += Double.parseDouble(request
							.getParameter("handlingTotal"));
				}
				if (request.getParameter("taxTotal") != "") {
					paydtl.setTaxTotal(new BasicAmountType(CurrencyCodeType
							.fromValue(request.getParameter("currencyCode")),
							request.getParameter("taxTotal")));
					orderTotal += Double.parseDouble(request
							.getParameter("taxTotal"));
				}
				if (request.getParameter("orderDescription") != "") {
					paydtl.setOrderDescription(request
							.getParameter("orderDescription"));
				}

				
				BasicAmountType itemsTotal = new BasicAmountType();
				itemsTotal.setValue(Double.toString(itemTotal));
				itemsTotal.setCurrencyID(CurrencyCodeType.fromValue(request
						.getParameter("currencyCode")));
				paydtl.setOrderTotal(new BasicAmountType(CurrencyCodeType
						.fromValue(request.getParameter("currencyCode")),
						Double.toString(orderTotal)));
				paydtl.setPaymentDetailsItem(lineItems);

				paydtl.setItemTotal(itemsTotal);
				payDetails.add(paydtl);
				details.setPaymentDetails(payDetails);
				if (request.getParameter("billingAgreementText") != "") {
					BillingAgreementDetailsType billingAgreement = new BillingAgreementDetailsType(
							BillingCodeType.fromValue(request
									.getParameter("billingType")));
					billingAgreement.setBillingAgreementDescription(request
							.getParameter("billingAgreementText"));
					List<BillingAgreementDetailsType> billList = new ArrayList<BillingAgreementDetailsType>();
					billList.add(billingAgreement);
					details.setBillingAgreementDetails(billList);
				}
				
				//shipping address
				details.setReqConfirmShipping(request.getParameter("reqConfirmShipping"));
				details.setAddressOverride(request.getParameter("addressoverride"));
				AddressType shipToAddress=new AddressType();
				shipToAddress.setName(request.getParameter("name"));
				shipToAddress.setStreet1(request.getParameter("street1"));
				shipToAddress.setStreet2(request.getParameter("street2"));
				shipToAddress.setCityName(request.getParameter("city"));
				shipToAddress.setStateOrProvince(request.getParameter("state"));
				shipToAddress.setPostalCode(request.getParameter("postalCode"));
				shipToAddress.setCountry(CountryCodeType.fromValue(request.getParameter("countryCode")));
				details.setAddress(shipToAddress);
				
				// shipping display options
				details.setNoShipping(request.getParameter("noShipping"));
				
				// PayPal page styling attributes
				details.setBrandName(request.getParameter("brandName"));
				details.setCustom(request.getParameter("pageStyle"));
				details.setCppHeaderImage(request.getParameter("cppheaderimage"));
				details.setCppHeaderBorderColor(request.getParameter("cppheaderbordercolor"));
				details.setCppHeaderBackColor(request.getParameter("cppheaderbackcolor"));
				details.setCppPayflowColor(request.getParameter("cpppayflowcolor"));
				details.setAllowNote(request.getParameter("allowNote"));
				
				setExpressCheckoutReq
						.setSetExpressCheckoutRequestDetails(details);

				SetExpressCheckoutReq expressCheckoutReq = new SetExpressCheckoutReq();
				expressCheckoutReq
						.setSetExpressCheckoutRequest(setExpressCheckoutReq);

				SetExpressCheckoutResponseType setExpressCheckoutResponse = service
						.setExpressCheckout(expressCheckoutReq);

				if (setExpressCheckoutResponse != null) {
					session.setAttribute("lastReq", service.getLastRequest());
					session.setAttribute("lastResp", service.getLastResponse());
					if (setExpressCheckoutResponse.getAck().toString()
							.equalsIgnoreCase("SUCCESS")) {
						Map<Object, Object> map = new LinkedHashMap<Object, Object>();
						map.put("Ack", setExpressCheckoutResponse.getAck());
						map.put("Token", setExpressCheckoutResponse.getToken());
						map.put("Redirect URL",
								"<a href=https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token="
										+ setExpressCheckoutResponse.getToken()
										+ ">Redirect To PayPal</a>");
						session.setAttribute("map", map);
						response.sendRedirect("/merchant-sample/Response.jsp");
					} else {

						session.setAttribute("Error",
								setExpressCheckoutResponse.getErrors());
						response.sendRedirect("/merchant-sample/Error.jsp");
					}
				}
			}
			if (request.getRequestURI().contains("GetExpressCheckout")) {
				GetExpressCheckoutDetailsReq req = new GetExpressCheckoutDetailsReq();
				GetExpressCheckoutDetailsRequestType reqType = new GetExpressCheckoutDetailsRequestType(
						request.getParameter("token"));
				req.setGetExpressCheckoutDetailsRequest(reqType);
				GetExpressCheckoutDetailsResponseType resp = service
						.getExpressCheckoutDetails(req);
				if (resp != null) {
					session.setAttribute("lastReq", service.getLastRequest());
					session.setAttribute("lastResp", service.getLastResponse());
					if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
						Map<Object, Object> map = new LinkedHashMap<Object, Object>();
						map.put("Ack", resp.getAck());
						map.put("Token", resp
								.getGetExpressCheckoutDetailsResponseDetails()
								.getToken());
						map.put("Payer ID", resp
								.getGetExpressCheckoutDetailsResponseDetails()
								.getPayerInfo().getPayerID());
						session.setAttribute("map", map);
						response.sendRedirect("/merchant-sample/Response.jsp");
					} else {

						session.setAttribute("Error", resp.getErrors());
						response.sendRedirect("/merchant-sample/Error.jsp");
					}
				}
			}
			if (request.getRequestURI().contains("DoExpressCheckout")) {
				DoExpressCheckoutPaymentRequestType doCheckoutPaymentRequestType = new DoExpressCheckoutPaymentRequestType();
				DoExpressCheckoutPaymentRequestDetailsType details = new DoExpressCheckoutPaymentRequestDetailsType();
				details.setToken(request.getParameter("token"));
				details.setPayerID(request.getParameter("payerID"));
				details.setPaymentAction(PaymentActionCodeType
						.fromValue(request.getParameter("paymentAction")));
				double itemTotalAmt = 0.00;
				double orderTotalAmt = 0.00;
				String amt = request.getParameter("amt");
				String quantity = request.getParameter("itemQuantity");
				itemTotalAmt = Double.parseDouble(amt)
						* Double.parseDouble(quantity);
				orderTotalAmt += itemTotalAmt;
				PaymentDetailsType paymentDetails = new PaymentDetailsType();
				BasicAmountType orderTotal = new BasicAmountType();
				orderTotal.setValue(Double.toString(orderTotalAmt));
				orderTotal.setCurrencyID(CurrencyCodeType.fromValue(request
						.getParameter("currencyCode")));
				paymentDetails.setOrderTotal(orderTotal);

				BasicAmountType itemTotal = new BasicAmountType();
				itemTotal.setValue(Double.toString(itemTotalAmt));

				itemTotal.setCurrencyID(CurrencyCodeType.fromValue(request
						.getParameter("currencyCode")));
				paymentDetails.setItemTotal(itemTotal);

				List<PaymentDetailsItemType> paymentItems = new ArrayList<PaymentDetailsItemType>();
				PaymentDetailsItemType paymentItem = new PaymentDetailsItemType();
				paymentItem.setName(request.getParameter("itemName"));
				paymentItem.setQuantity(Integer.parseInt(request
						.getParameter("itemQuantity")));
				BasicAmountType amount = new BasicAmountType();
				amount.setValue(request.getParameter("amt"));
				amount.setCurrencyID(CurrencyCodeType.fromValue(request
						.getParameter("currencyCode")));
				paymentItem.setAmount(amount);
				paymentItems.add(paymentItem);
				paymentDetails.setPaymentDetailsItem(paymentItems);

				List<PaymentDetailsType> payDetailType = new ArrayList<PaymentDetailsType>();
				payDetailType.add(paymentDetails);
				details.setPaymentDetails(payDetailType);

				doCheckoutPaymentRequestType
						.setDoExpressCheckoutPaymentRequestDetails(details);
				DoExpressCheckoutPaymentReq doExpressCheckoutPaymentReq = new DoExpressCheckoutPaymentReq();
				doExpressCheckoutPaymentReq
						.setDoExpressCheckoutPaymentRequest(doCheckoutPaymentRequestType);

				DoExpressCheckoutPaymentResponseType doCheckoutPaymentResponseType = service
						.doExpressCheckoutPayment(doExpressCheckoutPaymentReq);
				response.setContentType("text/html");
				if (doCheckoutPaymentResponseType != null) {
					session.setAttribute("lastReq", service.getLastRequest());
					session.setAttribute("lastResp", service.getLastResponse());
					if (doCheckoutPaymentResponseType.getAck().toString()
							.equalsIgnoreCase("SUCCESS")) {
						Map<Object, Object> map = new LinkedHashMap<Object, Object>();
						map.put("Ack", doCheckoutPaymentResponseType.getAck());
						Iterator<PaymentInfoType> iterator = doCheckoutPaymentResponseType
								.getDoExpressCheckoutPaymentResponseDetails()
								.getPaymentInfo().iterator();
						int index = 1;
						while (iterator.hasNext()) {
							PaymentInfoType result = (PaymentInfoType) iterator
									.next();
							map.put("Transaction ID" + index,
									result.getTransactionID());
							index++;
						}
						map.put("Billing Agreement ID",
								doCheckoutPaymentResponseType
										.getDoExpressCheckoutPaymentResponseDetails()
										.getBillingAgreementID());
						session.setAttribute("map", map);
						response.sendRedirect("/merchant-sample/Response.jsp");
					} else {

						session.setAttribute("Error",
								doCheckoutPaymentResponseType.getErrors());
						response.sendRedirect("/merchant-sample/Error.jsp");
					}
				}

			} else if (request.getRequestURI()
					.contains("DoUATPExpressCheckout")) {
				DoUATPExpressCheckoutPaymentReq req = new DoUATPExpressCheckoutPaymentReq();
				DoUATPExpressCheckoutPaymentRequestType reqType = new DoUATPExpressCheckoutPaymentRequestType();

				DoExpressCheckoutPaymentRequestDetailsType checkoutDetails = new DoExpressCheckoutPaymentRequestDetailsType();
				checkoutDetails.setPayerID(request.getParameter("payerID"));
				checkoutDetails.setToken(request.getParameter("token"));
				checkoutDetails.setPaymentAction(PaymentActionCodeType
						.fromValue(request.getParameter("paymentAction")));
				BasicAmountType amount = new BasicAmountType(
						CurrencyCodeType.fromValue(request
								.getParameter("currencyID")),
						request.getParameter("amt"));
				PaymentDetailsType detailsType = new PaymentDetailsType();
				detailsType.setOrderTotal(amount);
				List<PaymentDetailsType> payList = new ArrayList<PaymentDetailsType>();
				payList.add(detailsType);
				checkoutDetails.setPaymentDetails(payList);
				reqType.setDoExpressCheckoutPaymentRequestDetails(checkoutDetails);
				req.setDoUATPExpressCheckoutPaymentRequest(reqType);
				DoUATPExpressCheckoutPaymentResponseType resp = service
						.doUATPExpressCheckoutPayment(req);

				if (resp != null) {
					session.setAttribute("lastReq", service.getLastRequest());
					session.setAttribute("lastResp", service.getLastResponse());
					if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
						Map<Object, Object> map = new LinkedHashMap<Object, Object>();
						map.put("Ack", resp.getAck());
						map.put("UATP Number", resp.getUATPDetails()
								.getUATPNumber());
						map.put("Expiry Month", resp.getUATPDetails()
								.getExpMonth());
						map.put("Expiry Year", resp.getUATPDetails()
								.getExpYear());
						Iterator<PaymentInfoType> iterator = resp
								.getDoExpressCheckoutPaymentResponseDetails()
								.getPaymentInfo().iterator();
						int index = 1;
						while (iterator.hasNext()) {
							PaymentInfoType result = (PaymentInfoType) iterator
									.next();
							map.put("Transaction ID" + index,
									result.getTransactionID());
							index++;
						}
						map.put("Billing Agreement ID", resp
								.getDoExpressCheckoutPaymentResponseDetails()
								.getBillingAgreementID());
						session.setAttribute("map", map);
						response.sendRedirect("/merchant-sample/Response.jsp");

					} else {

						session.setAttribute("Error", resp.getErrors());
						response.sendRedirect("/merchant-sample/Error.jsp");
					}
				}
			} else if (request.getRequestURI().contains(
					"ExternalRememberMeOptOut")) {
				ExternalRememberMeOptOutReq req = new ExternalRememberMeOptOutReq();
				ExternalRememberMeOptOutRequestType reqType = new ExternalRememberMeOptOutRequestType(
						request.getParameter("externalRememberMeID"));
				ExternalRememberMeOwnerDetailsType externalRememberMeOwner = new ExternalRememberMeOwnerDetailsType();
				externalRememberMeOwner
						.setExternalRememberMeOwnerIDType(request
								.getParameter("ownerIDType"));
				externalRememberMeOwner.setExternalRememberMeOwnerID(request
						.getParameter("ownerID"));
				reqType.setExternalRememberMeOwnerDetails(externalRememberMeOwner);
				req.setExternalRememberMeOptOutRequest(reqType);
				ExternalRememberMeOptOutResponseType resp = service
						.externalRememberMeOptOut(req);
				if (resp != null) {
					session.setAttribute("lastReq", service.getLastRequest());
					session.setAttribute("lastResp", service.getLastResponse());
					if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
						Map<Object, Object> map = new LinkedHashMap<Object, Object>();
						map.put("Ack", resp.getAck());
						session.setAttribute("map", map);
						response.sendRedirect("/merchant-sample/Response.jsp");
					} else {
						session.setAttribute("Error", resp.getErrors());
						response.sendRedirect("/merchant-sample/Error.jsp");
					}
				}
			} else if (request.getRequestURI().contains(
					"ExecuteCheckoutOperations")) {
				ExecuteCheckoutOperationsReq req = new ExecuteCheckoutOperationsReq();
				SetDataRequestType setDataRequest = new SetDataRequestType();
				List<BillingApprovalDetailsType> billingApprovalList = new ArrayList<BillingApprovalDetailsType>();
				BillingApprovalDetailsType billingApproval = new BillingApprovalDetailsType(
						ApprovalTypeType.fromValue(request
								.getParameter("billingApprovalType")));
				billingApproval.setApprovalSubType(ApprovalSubTypeType
						.fromValue(request
								.getParameter("billingApprovalSubType")));
				OrderDetailsType orderDetails = new OrderDetailsType();
				orderDetails.setMaxAmount(new BasicAmountType(CurrencyCodeType
						.fromValue(request.getParameter("currencyID")), request
						.getParameter("amt")));
				billingApproval.setOrderDetails(orderDetails);
				PaymentDirectivesType paymentDirectives = new PaymentDirectivesType();
				paymentDirectives.setPaymentType(MerchantPullPaymentCodeType
						.fromValue(request.getParameter("paymentType")));
				billingApproval.setPaymentDirectives(paymentDirectives);
				billingApprovalList.add(billingApproval);
				setDataRequest.setBillingApprovalDetails(billingApprovalList);

				BuyerDetailType buyerDetail = new BuyerDetailType();
				IdentificationInfoType identificationInfo = new IdentificationInfoType();
				if (request.getParameter("externalRememberMeID") != "") {
					RememberMeIDInfoType rememberMeIDInfo = new RememberMeIDInfoType();
					rememberMeIDInfo.setExternalRememberMeID(request
							.getParameter("externalRememberMeID"));
					identificationInfo.setRememberMeIDInfo(rememberMeIDInfo);
				}
				if (request.getParameter("sessionToken") != "") {
					MobileIDInfoType mobileIDInfo = new MobileIDInfoType();
					mobileIDInfo.setSessionToken(request
							.getParameter("sessionToken"));
					identificationInfo.setMobileIDInfo(mobileIDInfo);
				}
				buyerDetail.setIdentificationInfo(identificationInfo);
				setDataRequest.setBuyerDetail(buyerDetail);

				InfoSharingDirectivesType infoSharingDirectives = new InfoSharingDirectivesType();
				infoSharingDirectives.setReqBillingAddress(request
						.getParameter("reqBillingAddress"));
				setDataRequest.setInfoSharingDirectives(infoSharingDirectives);

				ExecuteCheckoutOperationsRequestDetailsType reqDetails = new ExecuteCheckoutOperationsRequestDetailsType(
						setDataRequest);

				AuthorizationRequestType authRequest = new AuthorizationRequestType(
						Boolean.parseBoolean(request
								.getParameter("isRequested")));
				reqDetails.setAuthorizationRequest(authRequest);

				ExecuteCheckoutOperationsRequestType reqType = new ExecuteCheckoutOperationsRequestType(
						reqDetails);
				req.setExecuteCheckoutOperationsRequest(reqType);
				ExecuteCheckoutOperationsResponseType resp = service
						.executeCheckoutOperations(req);
				if (resp != null) {
					session.setAttribute("lastReq", service.getLastRequest());
					session.setAttribute("lastResp", service.getLastResponse());
					if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
						Map<Object, Object> map = new LinkedHashMap<Object, Object>();
						map.put("Ack", resp.getAck());
						map.put("Token", resp
								.getExecuteCheckoutOperationsResponseDetails()
								.getSetDataResponse().getToken());
						session.setAttribute("map", map);
						response.sendRedirect("/merchant-sample/Response.jsp");
					} else {
						session.setAttribute("Error", resp.getErrors());
						response.sendRedirect("/merchant-sample/Error.jsp");
					}
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
		}
	}
}
