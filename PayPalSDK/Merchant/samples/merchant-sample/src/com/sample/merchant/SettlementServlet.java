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

import urn.ebay.api.PayPalAPI.DoAuthorizationReq;
import urn.ebay.api.PayPalAPI.DoAuthorizationRequestType;
import urn.ebay.api.PayPalAPI.DoAuthorizationResponseType;
import urn.ebay.api.PayPalAPI.DoCaptureReq;
import urn.ebay.api.PayPalAPI.DoCaptureRequestType;
import urn.ebay.api.PayPalAPI.DoCaptureResponseType;
import urn.ebay.api.PayPalAPI.DoNonReferencedCreditReq;
import urn.ebay.api.PayPalAPI.DoNonReferencedCreditRequestType;
import urn.ebay.api.PayPalAPI.DoNonReferencedCreditResponseType;
import urn.ebay.api.PayPalAPI.DoReauthorizationReq;
import urn.ebay.api.PayPalAPI.DoReauthorizationRequestType;
import urn.ebay.api.PayPalAPI.DoReauthorizationResponseType;
import urn.ebay.api.PayPalAPI.DoUATPAuthorizationReq;
import urn.ebay.api.PayPalAPI.DoUATPAuthorizationRequestType;
import urn.ebay.api.PayPalAPI.DoUATPAuthorizationResponseType;
import urn.ebay.api.PayPalAPI.DoVoidReq;
import urn.ebay.api.PayPalAPI.DoVoidRequestType;
import urn.ebay.api.PayPalAPI.DoVoidResponseType;
import urn.ebay.api.PayPalAPI.ManagePendingTransactionStatusReq;
import urn.ebay.api.PayPalAPI.ManagePendingTransactionStatusRequestType;
import urn.ebay.api.PayPalAPI.ManagePendingTransactionStatusResponseType;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.api.PayPalAPI.RefundTransactionReq;
import urn.ebay.api.PayPalAPI.RefundTransactionRequestType;
import urn.ebay.api.PayPalAPI.RefundTransactionResponseType;
import urn.ebay.api.PayPalAPI.ReverseTransactionReq;
import urn.ebay.api.PayPalAPI.ReverseTransactionRequestType;
import urn.ebay.api.PayPalAPI.ReverseTransactionResponseType;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.CompleteCodeType;
import urn.ebay.apis.eBLBaseComponents.CreditCardDetailsType;
import urn.ebay.apis.eBLBaseComponents.CreditCardTypeType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.DoNonReferencedCreditRequestDetailsType;
import urn.ebay.apis.eBLBaseComponents.FMFPendingTransactionActionType;
import urn.ebay.apis.eBLBaseComponents.MerchantStoreDetailsType;
import urn.ebay.apis.eBLBaseComponents.RefundSourceCodeType;
import urn.ebay.apis.eBLBaseComponents.RefundType;
import urn.ebay.apis.eBLBaseComponents.ReverseTransactionRequestDetailsType;
import urn.ebay.apis.eBLBaseComponents.TransactionEntityType;
import urn.ebay.apis.eBLBaseComponents.UATPDetailsType;

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
public class SettlementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public SettlementServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getRequestURI().contains("DoAuthorization"))
			getServletConfig().getServletContext()
					.getRequestDispatcher("/Settlements/DoAuthorization.jsp")
					.forward(request, response);
		else if (request.getRequestURI().contains("DoReauthorization"))
			getServletConfig().getServletContext()
					.getRequestDispatcher("/Settlements/DoReauthorization.jsp")
					.forward(request, response);
		else if (request.getRequestURI().contains("DoVoid"))
			getServletConfig().getServletContext()
					.getRequestDispatcher("/Settlements/DoVoid.jsp")
					.forward(request, response);
		else if (request.getRequestURI().contains("DoCapture"))
			getServletConfig().getServletContext()
					.getRequestDispatcher("/Settlements/DoCapture.jsp")
					.forward(request, response);
		else if (request.getRequestURI().contains("Refund"))
			getServletConfig().getServletContext()
					.getRequestDispatcher("/Settlements/Refund.jsp")
					.forward(request, response);
		else if (request.getRequestURI().contains("DoUATPAuthorization"))
			getServletConfig()
					.getServletContext()
					.getRequestDispatcher(
							"/Settlements/DoUATPAuthorization.jsp")
					.forward(request, response);
		else if (request.getRequestURI().contains("ReverseTransaction"))
			getServletConfig()
					.getServletContext()
					.getRequestDispatcher("/Settlements/ReverseTransaction.jsp")
					.forward(request, response);
		else if (request.getRequestURI().contains("DoNonReferencedCredit"))
			getServletConfig()
					.getServletContext()
					.getRequestDispatcher(
							"/Settlements/DoNonReferencedCredit.jsp")
					.forward(request, response);
		else if (request.getRequestURI().contains(
				"ManagePendingTransactionStatus"))
			getServletConfig()
					.getServletContext()
					.getRequestDispatcher(
							"/Settlements/ManagePendingTransactionStatus.jsp")
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
				"<ul><li><a href='Settlement/DoAuthorization'>DoAuthorization</a></li><li><a href='Settlement/DoCapture'>DoCapture</a></li><li><a href='Settlement/DoVoid'>DoVoid</a></li><li><a href='Settlement/DoReauthorization'>DoReauthorization</a></li><li><a href='Settlement/Refund'>Refund</a></li><li><a href='Settlement/ReverseTransaction'>ReverseTransaction</a></li><li><a href='Settlement/DoNonReferencedCredit'>DoNonReferencedCredit</a></li><li><a href='Settlement/ManagePendingTransactionStatus'>ManagePendingTransactionStatus</a></li></ul>");
		response.setContentType("text/html");
		try {
			PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(
					this.getServletContext().getRealPath("/")
							+ "/WEB-INF/sdk_config.properties");
			if (request.getRequestURI().contains("DoAuthorization")) {
				DoAuthorizationReq req = new DoAuthorizationReq();
				BasicAmountType amount = new BasicAmountType(
						CurrencyCodeType.fromValue(request
								.getParameter("currencyCode")),
						request.getParameter("amt"));
				DoAuthorizationRequestType reqType = new DoAuthorizationRequestType(
						request.getParameter("transID"), amount);

				req.setDoAuthorizationRequest(reqType);
				DoAuthorizationResponseType resp = service.doAuthorization(req);
				if (resp != null) {
					session.setAttribute("lastReq", service.getLastRequest());
					session.setAttribute("lastResp", service.getLastResponse());
					if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
						Map<Object, Object> map = new LinkedHashMap<Object, Object>();
						map.put("Ack", resp.getAck());
						map.put("Amount", resp.getAmount().getValue() + " "
								+ resp.getAmount().getCurrencyID());
						map.put("Payment Status", resp.getAuthorizationInfo()
								.getPaymentStatus());
						session.setAttribute("map", map);
						response.sendRedirect("/merchant-sample/Response.jsp");

					} else {

						session.setAttribute("Error", resp.getErrors());
						response.sendRedirect("/merchant-sample/Error.jsp");
					}
				}
			} else if (request.getRequestURI().contains("DoReauthorization")) {
				DoReauthorizationReq req = new DoReauthorizationReq();
				BasicAmountType amount = new BasicAmountType(
						CurrencyCodeType.fromValue(request
								.getParameter("currencyCode")),
						request.getParameter("amt"));
				DoReauthorizationRequestType reqType = new DoReauthorizationRequestType(
						request.getParameter("authID"), amount);

				req.setDoReauthorizationRequest(reqType);
				DoReauthorizationResponseType resp = service
						.doReauthorization(req);

				if (resp != null) {
					session.setAttribute("lastReq", service.getLastRequest());
					session.setAttribute("lastResp", service.getLastResponse());
					if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
						Map<Object, Object> map = new LinkedHashMap<Object, Object>();
						map.put("Ack", resp.getAck());
						map.put("Authorization ID", resp.getAuthorizationID());
						map.put("Payment Status", resp.getAuthorizationInfo()
								.getPaymentStatus());
						session.setAttribute("map", map);
						response.sendRedirect("/merchant-sample/Response.jsp");
					} else {

						session.setAttribute("Error", resp.getErrors());
						response.sendRedirect("/merchant-sample/Error.jsp");
					}
				}
			} else if (request.getRequestURI().contains("DoVoid")) {
				DoVoidReq req = new DoVoidReq();
				DoVoidRequestType reqType = new DoVoidRequestType(
						request.getParameter("authID"));
				req.setDoVoidRequest(reqType);
				DoVoidResponseType resp = service.doVoid(req);
				if (resp != null) {
					session.setAttribute("lastReq", service.getLastRequest());
					session.setAttribute("lastResp", service.getLastResponse());
					if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
						Map<Object, Object> map = new LinkedHashMap<Object, Object>();
						map.put("Ack", resp.getAck());
						map.put("Authorization ID", resp.getAuthorizationID());
						session.setAttribute("map", map);
						response.sendRedirect("/merchant-sample/Response.jsp");
					} else {

						session.setAttribute("Error", resp.getErrors());
						response.sendRedirect("/merchant-sample/Error.jsp");
					}
				}
			} else if (request.getRequestURI().contains("DoCapture")) {
				DoCaptureReq req = new DoCaptureReq();
				BasicAmountType amount = new BasicAmountType(
						CurrencyCodeType.fromValue(request
								.getParameter("currencyCode")),
						request.getParameter("amt"));
				DoCaptureRequestType reqType = new DoCaptureRequestType(
						request.getParameter("authID"), amount,
						CompleteCodeType.fromValue(request
								.getParameter("completeCodeType")));
				req.setDoCaptureRequest(reqType);
				DoCaptureResponseType resp = service.doCapture(req);
				if (resp != null) {
					session.setAttribute("lastReq", service.getLastRequest());
					session.setAttribute("lastResp", service.getLastResponse());
					if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
						Map<Object, Object> map = new LinkedHashMap<Object, Object>();
						map.put("Ack", resp.getAck());
						map.put("Authorization ID", resp
								.getDoCaptureResponseDetails()
								.getAuthorizationID());
						map.put("Gross Amount", resp
								.getDoCaptureResponseDetails().getPaymentInfo()
								.getGrossAmount().getValue()
								+ " "
								+ resp.getDoCaptureResponseDetails()
										.getPaymentInfo().getGrossAmount()
										.getCurrencyID());
						session.setAttribute("map", map);
						response.sendRedirect("/merchant-sample/Response.jsp");

					} else {

						session.setAttribute("Error", resp.getErrors());
						response.sendRedirect("/merchant-sample/Error.jsp");
					}
				}
			} else if (request.getRequestURI().contains("Refund")) {
				RefundTransactionReq req = new RefundTransactionReq();
				RefundTransactionRequestType reqType = new RefundTransactionRequestType();
				reqType.setTransactionID(request.getParameter("transID"));
				if (request.getParameter("refundType") != "Full"
						& request.getParameter("refundType") != "") {
					reqType.setAmount(new BasicAmountType(CurrencyCodeType
							.fromValue(request.getParameter("currencyID")),
							request.getParameter("amt")));
				}
				if (request.getParameter("refundType") != "")
					reqType.setRefundType(RefundType.fromValue(request
							.getParameter("refundType")));
				if (request.getParameter("refundSource") != "")
					reqType.setRefundSource(RefundSourceCodeType
							.fromValue(request.getParameter("refundSource")));
				if (request.getParameter("storeID") != null) {
					MerchantStoreDetailsType merchantStoreDetails = new MerchantStoreDetailsType(
							request.getParameter("storeID"));
					merchantStoreDetails.setTerminalID(request
							.getParameter("terminalID"));
					reqType.setMerchantStoreDetails(merchantStoreDetails);
				}
				req.setRefundTransactionRequest(reqType);
				RefundTransactionResponseType resp = service
						.refundTransaction(req);
				if (resp != null) {
					session.setAttribute("lastReq", service.getLastRequest());
					session.setAttribute("lastResp", service.getLastResponse());
					if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
						Map<Object, Object> map = new LinkedHashMap<Object, Object>();
						map.put("Ack", resp.getAck());
						map.put("Refund Transaction ID",
								resp.getRefundTransactionID());
						map.put("Total Refunded Amount", resp
								.getTotalRefundedAmount().getValue()
								+ " "
								+ resp.getTotalRefundedAmount().getCurrencyID());
						session.setAttribute("map", map);
						response.sendRedirect("/merchant-sample/Response.jsp");
					} else {

						session.setAttribute("Error", resp.getErrors());
						response.sendRedirect("/merchant-sample/Error.jsp");
					}
				}

			} else if (request.getRequestURI().contains("DoUATPAuthorization")) {
				DoUATPAuthorizationReq req = new DoUATPAuthorizationReq();
				UATPDetailsType details = new UATPDetailsType();
				BasicAmountType amount = new BasicAmountType(
						CurrencyCodeType.fromValue(request
								.getParameter("currencyID")),
						request.getParameter("amt"));
				details.setExpMonth(Integer.parseInt(request
						.getParameter("expMonth")));
				details.setExpYear(Integer.parseInt(request
						.getParameter("expYear")));
				details.setUATPNumber(request.getParameter("UATPNum"));
				DoUATPAuthorizationRequestType reqType = new DoUATPAuthorizationRequestType(
						details, amount);
				reqType.setTransactionEntity(TransactionEntityType
						.fromValue(request.getParameter("transactionEntity")));
				req.setDoUATPAuthorizationRequest(reqType);
				DoUATPAuthorizationResponseType resp = service
						.doUATPAuthorization(req);
				if (resp != null) {
					session.setAttribute("lastReq", service.getLastRequest());
					session.setAttribute("lastResp", service.getLastResponse());
					if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
						Map<Object, Object> map = new LinkedHashMap<Object, Object>();
						map.put("Ack", resp.getAck());
						map.put("Authorization Code",
								resp.getAuthorizationCode());
						map.put("Payment Status", resp.getAuthorizationInfo()
								.getPaymentStatus());
						map.put("Amount", resp.getAmount().getValue() + " "
								+ resp.getAmount().getCurrencyID());
						session.setAttribute("map", map);
						response.sendRedirect("/merchant-sample/Response.jsp");
					} else {
						session.setAttribute("Error", resp.getErrors());
						response.sendRedirect("/merchant-sample/Error.jsp");
					}
				}
			} else if (request.getRequestURI().contains("ReverseTransaction")) {
				ReverseTransactionReq req = new ReverseTransactionReq();
				ReverseTransactionRequestDetailsType reqDetails = new ReverseTransactionRequestDetailsType();
				reqDetails.setTransactionID(request.getParameter("transID"));
				ReverseTransactionRequestType reqType = new ReverseTransactionRequestType(
						reqDetails);
				req.setReverseTransactionRequest(reqType);
				ReverseTransactionResponseType resp = service
						.reverseTransaction(req);
				if (resp != null) {
					session.setAttribute("lastReq", service.getLastRequest());
					session.setAttribute("lastResp", service.getLastResponse());
					if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
						Map<Object, Object> map = new LinkedHashMap<Object, Object>();
						map.put("Ack", resp.getAck());
						map.put("Reverse Transaction ID", resp
								.getReverseTransactionResponseDetails()
								.getReverseTransactionID());
						map.put("Status", resp
								.getReverseTransactionResponseDetails()
								.getStatus());
						session.setAttribute("map", map);
						response.sendRedirect("/merchant-sample/Response.jsp");
					} else {
						session.setAttribute("Error", resp.getErrors());
						response.sendRedirect("/merchant-sample/Error.jsp");
					}
				}

			} else if (request.getRequestURI()
					.contains("DoNonReferencedCredit")) {
				DoNonReferencedCreditReq req = new DoNonReferencedCreditReq();
				DoNonReferencedCreditRequestDetailsType reqDetails = new DoNonReferencedCreditRequestDetailsType();
				CreditCardDetailsType cardDetails = new CreditCardDetailsType();
				cardDetails.setCreditCardType(CreditCardTypeType
						.fromValue(request.getParameter("creditCardType")));
				cardDetails.setCreditCardNumber(request
						.getParameter("creditCardNumber"));
				cardDetails.setExpMonth(Integer.parseInt(request
						.getParameter("expMonth")));
				cardDetails.setExpYear(Integer.parseInt(request
						.getParameter("expYear")));
				cardDetails.setCVV2(request.getParameter("cvv"));
				reqDetails.setCreditCard(cardDetails);
				reqDetails.setComment(request.getParameter("comment"));
				reqDetails.setNetAmount(new BasicAmountType(CurrencyCodeType
						.fromValue(request.getParameter("currencyID")), request
						.getParameter("itemAmount")));
				reqDetails.setShippingAmount(new BasicAmountType(
						CurrencyCodeType.fromValue(request
								.getParameter("currencyID")), request
								.getParameter("shippingAmount")));
				reqDetails.setTaxAmount(new BasicAmountType(CurrencyCodeType
						.fromValue(request.getParameter("currencyID")), request
						.getParameter("taxAmount")));
				double totalAmount = Double.parseDouble(request
						.getParameter("itemAmount"))
						+ Double.parseDouble(request
								.getParameter("shippingAmount"))
						+ Double.parseDouble(request.getParameter("taxAmount"));
				reqDetails.setAmount(new BasicAmountType(CurrencyCodeType
						.fromValue(request.getParameter("currencyID")), String
						.valueOf(totalAmount)));
				DoNonReferencedCreditRequestType reqType = new DoNonReferencedCreditRequestType(
						reqDetails);
				req.setDoNonReferencedCreditRequest(reqType);
				DoNonReferencedCreditResponseType resp = service
						.doNonReferencedCredit(req);
				if (resp != null) {
					session.setAttribute("lastReq", service.getLastRequest());
					session.setAttribute("lastResp", service.getLastResponse());
					if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
						Map<Object, Object> map = new LinkedHashMap<Object, Object>();
						map.put("Ack", resp.getAck());
						map.put("Transaction ID", resp
								.getDoNonReferencedCreditResponseDetails()
								.getTransactionID());
						map.put("Amount",
								resp.getDoNonReferencedCreditResponseDetails()
										.getAmount().getValue()
										+ " "
										+ resp.getDoNonReferencedCreditResponseDetails()
												.getAmount().getCurrencyID());
						session.setAttribute("map", map);
						response.sendRedirect("/merchant-sample/Response.jsp");
					} else {
						session.setAttribute("Error", resp.getErrors());
						response.sendRedirect("/merchant-sample/Error.jsp");
					}
				}
			} else if (request.getRequestURI().contains(
					"ManagePendingTransactionStatus")) {
				ManagePendingTransactionStatusReq req = new ManagePendingTransactionStatusReq();
				ManagePendingTransactionStatusRequestType reqType = new ManagePendingTransactionStatusRequestType(
						request.getParameter("transactionID"),
						FMFPendingTransactionActionType.fromValue(request
								.getParameter("action")));
				req.setManagePendingTransactionStatusRequest(reqType);
				ManagePendingTransactionStatusResponseType resp = service
						.managePendingTransactionStatus(req);
				if (resp != null) {
					session.setAttribute("lastReq", service.getLastRequest());
					session.setAttribute("lastResp", service.getLastResponse());
					if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
						Map<Object, Object> map = new LinkedHashMap<Object, Object>();
						map.put("Ack", resp.getAck());
						map.put("Transaction ID", resp.getTransactionID());
						map.put("Status", resp.getStatus());
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
