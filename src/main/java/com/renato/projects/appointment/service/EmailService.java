package com.renato.projects.appointment.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Emailv31;
import com.renato.projects.appointment.domain.Agendamento;
@Service
public class EmailService {

    @Value("${mailjet.api.key}")
    private String apiKey;

    @Value("${mailjet.api.secret}")
    private String apiSecret;

    public void sendEmail(Agendamento agendamento) throws MailjetException{
    	
    	String nomeProcedimento = agendamento.getProcedimento().getNome();
    	String slugProfissional = agendamento.getProcedimento().getTenant().getSlug();
    	String nomeConsumidor = agendamento.getConsumidor().getNome();
    	String emailConsumidor = agendamento.getConsumidor().getEmail();
    	String dataAgendamento = agendamento.getDateTime().toLocalDate().toString(); // formate conforme precisar
    	String horarioAgendamento = agendamento.getDateTime().toLocalTime().toString();// string ou formate aqui

    	String assunto = "📬 Confirmação de agendamento com " + slugProfissional;

    	String textoSimples = "Olá " + nomeConsumidor + ",\n\n" +
    	    "Seu agendamento foi confirmado com sucesso!\n\n" +
    	    "Detalhes:\n" +
    	    "- Procedimento: " + nomeProcedimento + "\n" +
    	    "- Profissional: " + slugProfissional + "\n" +
    	    "- Data: " + dataAgendamento + "\n" +
    	    "- Horário: " + horarioAgendamento + "\n\n" +
    	    "Se você tiver alguma dúvida, entre em contato conosco.\n\n" +
    	    "Atenciosamente,\nEquipe ZendaaVIP";

    	String htmlConteudo = "<!DOCTYPE html>" +
    	"<html>" +
    	"<head>" +
    	"<meta charset=\"UTF-8\">" +
    	"<style>" +
    	"  body { font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 20px; }" +
    	"  .container { max-width: 600px; margin: auto; background: #fff; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.05); }" +
    	"  h2 { color: #333; }" +
    	"  p { color: #555; font-size: 16px; line-height: 1.5; }" +
    	"  .details { background-color: #f1f1f1; padding: 15px; border-radius: 5px; margin: 20px 0; }" +
    	"  .details p { margin: 5px 0; }" +
    	"  a.button { background-color: #4CAF50; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; display: inline-block; }" +
    	"</style>" +
    	"</head>" +
    	"<body>" +
    	"<div class=\"container\">" +
    	"  <h2>Olá, " + nomeConsumidor + " 👋</h2>" +
    	"  <p>Seu agendamento foi confirmado com sucesso! Confira os detalhes abaixo:</p>" +
    	"  <div class=\"details\">" +
    	"    <p><strong>Procedimento:</strong> " + nomeProcedimento + "</p>" +
    	"    <p><strong>Profissional:</strong> " + slugProfissional + "</p>" +
    	"    <p><strong>Data:</strong> " + dataAgendamento + "</p>" +
    	"    <p><strong>Horário:</strong> " + horarioAgendamento + "</p>" +
    	"  </div>" +
    	"  <p>Se você tiver alguma dúvida ou precisar alterar seu agendamento, entre em contato conosco.</p>" +
    	"  <p style=\"margin-top: 30px;\">Atenciosamente,<br>Equipe ZendaaVIP</p>" +
    	"</div>" +
    	"</body>" +
    	"</html>";

    	
    	MailjetRequest request;
		MailjetResponse response;
    	MailjetClient client = new MailjetClient(apiKey, apiSecret);

    	request = new MailjetRequest(Emailv31.resource).property(Emailv31.MESSAGES,
    		    new JSONArray().put(new JSONObject()
    		        .put(Emailv31.Message.FROM,
    		            new JSONObject()
    		                .put("Email", slugProfissional + "@zendaavip.com.br")
    		                .put("Name", "ZendaaVIP"))
    		        .put(Emailv31.Message.TO,
    		            new JSONArray().put(new JSONObject()
    		                .put("Email", emailConsumidor)
    		                .put("Name", nomeConsumidor)))
    		        .put(Emailv31.Message.SUBJECT, assunto)
    		        .put(Emailv31.Message.TEXTPART, textoSimples)
    		        .put(Emailv31.Message.HTMLPART, htmlConteudo)
    		    )
    		);

		response = client.post(request);
		System.out.println(response.getStatus());
		System.out.println(response.getData());
    }
}