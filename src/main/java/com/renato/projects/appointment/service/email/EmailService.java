package com.renato.projects.appointment.service.email;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Emailv31;

@Service
public class EmailService {

    @Value("${mailjet.api.key}")
    private String apiKey;

    @Value("${mailjet.api.secret}")
    private String apiSecret;

    
    public void sendEmail(EmailData emailData) {
    	MailjetRequest request;
		MailjetResponse response;
    	MailjetClient client = new MailjetClient(apiKey, apiSecret);

    	request = new MailjetRequest(Emailv31.resource).property(Emailv31.MESSAGES,
    		    new JSONArray().put(new JSONObject()
    		        .put(Emailv31.Message.FROM,
    		            new JSONObject()
    		                .put("Email", emailData.getFromEmail() + "@zendaavip.com.br")
    		                .put("Name", emailData.getFromName()))
    		        .put(Emailv31.Message.TO,
    		            new JSONArray().put(new JSONObject()
    		                .put("Email", emailData.getToEmail())
    		                .put("Name", emailData.getToNome())))
    		        .put(Emailv31.Message.SUBJECT, emailData.getAssunto())
    		        .put(Emailv31.Message.TEXTPART, emailData.getTextoSimples())
    		        .put(Emailv31.Message.HTMLPART, emailData.getHtmlConteudo())
    		    )
    		);

		try {
			response = client.post(request);
			System.out.println(response.getStatus());
			System.out.println(response.getData());
		} catch (MailjetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}