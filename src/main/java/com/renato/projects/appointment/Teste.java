package com.renato.projects.appointment;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Emailv31;

public class Teste {

	public static void main(String[] args) throws MailjetException {

		MailjetRequest request;
		MailjetResponse response;
		String publicKey = "19cc3ad462ecfa81e8399daac866b76a";
		String privateKey = "16f49d26a29a5258f7109ea6ad50edcb";

		MailjetClient client = new MailjetClient(publicKey, privateKey);

		request = new MailjetRequest(Emailv31.resource).property(Emailv31.MESSAGES,
				new JSONArray().put(new JSONObject()
						.put(Emailv31.Message.FROM,
								new JSONObject().put("Email", "renato@zendaavip.com.br").put("Name", "Mailjet Pilot"))
						.put(Emailv31.Message.TO,
								new JSONArray().put(new JSONObject().put("Email", "lali.silveira.com@gmail.com")
										.put("Name", "passenger 1")))
						.put(Emailv31.Message.SUBJECT, "📬 Confirmação do seu acesso ao ZendaaVIP").put(
								Emailv31.Message.TEXTPART,
								"Olá! Este é um e-mail de teste do sistema ZendaaVIP. Se você recebeu esta mensagem, é porque tudo está funcionando perfeitamente. Obrigado por testar conosco!")
						.put(Emailv31.Message.HTMLPART, "<!DOCTYPE html>" + "<html>" + "<head>"
								+ "<meta charset=\"UTF-8\">" + "<style>"
								+ "  body { font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 20px; }"
								+ "  .container { max-width: 600px; margin: auto; background: #fff; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.05); }"
								+ "  h2 { color: #333; }" + "  p { color: #555; font-size: 16px; line-height: 1.5; }"
								+ "  a.button { background-color: #4CAF50; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; }"
								+ "</style>" + "</head>" + "<body>" + "<div class=\"container\">" + "  <h2>Olá! 👋</h2>"
								+ "  <p>Você está recebendo este e-mail como parte de um teste de envio feito pelo sistema <strong>ZendaaVIP</strong>.</p>"
								+ "  <p>Isso significa que tudo está funcionando corretamente e você está pronto para começar a usar o serviço!</p>"
								+ "  <p>Se você não esperava este e-mail, pode ignorá-lo com segurança.</p>"
								+ "  <p style=\"margin-top: 30px;\">Atenciosamente,<br>Equipe ZendaaVIP</p>" + "</div>"
								+ "</body>" + "</html>")));

		response = client.post(request);
		System.out.println(response.getStatus());
		System.out.println(response.getData());
	}
}
