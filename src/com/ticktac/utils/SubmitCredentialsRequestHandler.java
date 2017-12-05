package com.ticktac.utils;

import java.io.IOException;
import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import com.ticktac.business.CardCredentials;

public class SubmitCredentialsRequestHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CardCredentials cardCredentials = new CardCredentials();
		
		
		cardCredentials.setCardNumber(request.getParameter("cardNumber"));
		cardCredentials.setCv2Number(Integer.parseInt(request.getParameter("cv2Number")));
		cardCredentials.setExpireMonth(Integer.parseInt(request.getParameter("expireMonth")));
		cardCredentials.setExpireYear(Integer.parseInt(request.getParameter("expireYear")));
		
		
		Client client = ClientBuilder.newClient();
		WebTarget webResource = client.target("http://localhost:8081").path("banking")
				.queryParam("cardNumber", cardCredentials.getCardNumber())
				.queryParam("cv2Number", cardCredentials.getCv2Number())
				.queryParam("expireMonth",cardCredentials.getExpireMonth())
				.queryParam("expireYear", cardCredentials.getExpireYear());
		
		String out = webResource.request().accept("applicatio/json").get(String.class);
		
		request.setAttribute("out", out);
		
		return "/InsertBankInfo.jsp";
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response, EntityManager em,
			UserTransaction tr) throws ServletException, IOException {
		return null;
	}
	
	

}
