package com.example.handlingformsubmission;


import com.example.handlingformsubmission.generateQRcode.QRCodeGenerator;
import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.CreateTransactionController;
import net.authorize.api.controller.base.ApiOperationBase;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AuthorizationAndCapture {

	public static ANetApiResponse run(String apiLoginId, String transactionKey, Double amount) {

		System.out.println("PayPal Authorize Capture Transaction");

		//Common code to set for all requests
		ApiOperationBase.setEnvironment(Environment.SANDBOX);

		MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
		merchantAuthenticationType.setName(apiLoginId);
		merchantAuthenticationType.setTransactionKey(transactionKey);
		ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

		PayPalType payPalType = new PayPalType();
		payPalType.setSuccessUrl("http://www.merchanteCommerceSite.com/Success/TC25262");
		payPalType.setCancelUrl("http://www.merchanteCommerceSite.com/Success/TC25262");

		//standard api call to retrieve response
		PaymentType paymentType = new PaymentType();
		paymentType.setPayPal(payPalType);

		// Create the payment transaction request
		TransactionRequestType transactionRequest = new TransactionRequestType();

		transactionRequest.setTransactionType(TransactionTypeEnum.AUTH_CAPTURE_TRANSACTION.value());
		transactionRequest.setPayment(paymentType);
		transactionRequest.setAmount(new BigDecimal(amount).setScale(2, RoundingMode.CEILING));

		// Make the API Request
		CreateTransactionRequest apiRequest = new CreateTransactionRequest();
		apiRequest.setTransactionRequest(transactionRequest);
		CreateTransactionController controller = new CreateTransactionController(apiRequest);
		controller.execute();

		CreateTransactionResponse response = controller.getApiResponse();

        if (response!=null) {
        	// If API Response is ok, go ahead and check the transaction response
        	if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {

				for (int i = 0; i <response.getMessages().getMessage().size() ; i++) {
					System.out.println(response.getMessages().getMessage().get(i));
				}

				TransactionResponse result = response.getTransactionResponse();
        		if (result.getMessages() != null) {
					System.out.println(result.getMessages());
					System.out.println("-----1-----");
					System.out.println(result.getMessages().getMessage());   ///  list
					System.out.println("------2----");
					System.out.println(result.getMessages().getMessage().get(0));



        			System.out.println("Successfully created transaction with Transaction ID: " + result.getTransId());
        			System.out.println("Response Code: " + result.getResponseCode());
        			System.out.println("Message Code: " + result.getMessages().getMessage().get(0).getCode());
        			System.out.println("Description: " + result.getMessages().getMessage().get(0).getDescription());
        			System.out.println("Secure Acceptance URL : " + result.getSecureAcceptance().getSecureAcceptanceUrl());

					String acceptanceURL = result.getSecureAcceptance().getSecureAcceptanceUrl();
					new QRCodeGenerator().qrCodeGenerator(acceptanceURL);
        		}
        		else {
        			System.out.println("Failed Transaction.");
        			if (response.getTransactionResponse().getErrors() != null) {
        				System.out.println("Error Code: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorCode());
        				System.out.println("Error message: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorText());
        			}
        		}
        	}
        	else {
        		System.out.println("Failed Transaction.");
        		if (response.getTransactionResponse() != null && response.getTransactionResponse().getErrors() != null) {
        			System.out.println("Error Code: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorCode());
        			System.out.println("Error message: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorText());
        		}
        		else {
        			System.out.println("Error Code: " + response.getMessages().getMessage().get(0).getCode());
        			System.out.println("Error message: " + response.getMessages().getMessage().get(0).getText());
        		}
        	}
        }
        else {
        	System.out.println("Null Response.");
        }

		return response;
	}
}