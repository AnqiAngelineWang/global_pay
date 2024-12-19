package com.example.handlingformsubmission;

import net.authorize.api.contract.v1.ANetApiResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.awt.*;

@Controller
public class GreetingController {

	@GetMapping("/greeting")
	public String greetingForm(Model model) {
		callPayPal();
		model.addAttribute("greetingElementObj", new ElementObj());

		return "greetingElementObj";
	}

	@PostMapping("/greeting")
	public String greetingSubmit(@ModelAttribute ElementObj elementObj, Model model, Image img) {
		model.addAttribute("greeting", elementObj);
		new Image().getGraphics().complete/QRCode.png
		model.addAttribute("greeting", );
		return "result";
	}

	private void 	callPayPal(){
		String apiLoginId = "5KP3u95bQpv";
		String transactionKey = "346HZ32z3fP4hTG2";
		// Update the payedId with which you want to run the sample code
		String payerId = "6ZSCSYG33VP8Q";
		// Update the transactionId with which you want to run the sample code
		String transactionId = "123456";

		String customerProfileId = "918133235";
		String customerPaymentProfileId = "917585361";
		String customerAddressId = "918189891";

		String emailId = "test@test2021.com";

		String subscriptionId = "326280";

		Double amount = 123.45;
		ANetApiResponse res =  new AuthorizationAndCapture().run(apiLoginId,  transactionKey,  amount);
	}


//	@GetMapping("/greeting")
//	public String greetingForm(Model model) {
//		model.addAttribute("greeting", new Greeting());
//		return "greeting";
//	}
//
//	@PostMapping("/greeting")
//	public String greetingSubmit(@ModelAttribute Greeting greeting, Model model) {
//		model.addAttribute("greeting", greeting);
//		return "result";
//	}

}
