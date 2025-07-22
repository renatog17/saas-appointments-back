package com.renato.projects.appointment.service.email.strategy;

import org.springframework.stereotype.Component;

import com.renato.projects.appointment.domain.Agendamento;
import com.renato.projects.appointment.service.email.EmailData;
import com.renato.projects.appointment.service.email.EmailService;

@Component
public class ConfirmacaoAgendamento implements EnviarEmailStrategy {

	private EmailService emailService;

	public ConfirmacaoAgendamento(EmailService emailService) {
		super();
		this.emailService = emailService;
	}

	public void enviarEmail(Object context) {
		Agendamento agendamento = (Agendamento) context;

		String textoSimples = "Olá " + agendamento.getConsumidor().getNome() + ",\n\n"
				+ "Seu agendamento foi confirmado com sucesso!\n\n" + "Detalhes:\n" + "- Procedimento: "
				+ agendamento.getProcedimento().getNome() + "\n" + "- Profissional: "
				+ agendamento.getProcedimento().getTenant().getNome() + "\n" + "- Data: "
				+ agendamento.getDateTime().toLocalDate() + "\n" + "- Horário: "
				+ agendamento.getDateTime().toLocalTime() + "\n\n"
				+ "Se você tiver alguma dúvida, entre em contato conosco.\n\n" + "Atenciosamente,\nEquipe ZendaaVIP";

		String htmlConteudo = "<!DOCTYPE html>" + "<html>" + "<head>" + "<meta charset=\"UTF-8\">" + "<style>"
				+ "  body { font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 20px; }"
				+ "  .container { max-width: 600px; margin: auto; background: #fff; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.05); }"
				+ "  h2 { color: #333; }" + "  p { color: #555; font-size: 16px; line-height: 1.5; }"
				+ "  .details { background-color: #f1f1f1; padding: 15px; border-radius: 5px; margin: 20px 0; }"
				+ "  .details p { margin: 5px 0; }"
				+ "  a.button { background-color: #4CAF50; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; display: inline-block; }"
				+ "</style>" + "</head>" + "<body>" + "<div class=\"container\">" + "  <h2>Olá, "
				+ agendamento.getConsumidor().getNome() + " 👋</h2>"
				+ "  <p>Seu agendamento foi confirmado com sucesso! Confira os detalhes abaixo:</p>"
				+ "  <div class=\"details\">" + "    <p><strong>Procedimento:</strong> "
				+ agendamento.getProcedimento().getNome() + "</p>" + "    <p><strong>Profissional:</strong> "
				+ agendamento.getProcedimento().getTenant().getNome() + "</p>" + "    <p><strong>Data:</strong> "
				+ agendamento.getDateTime().toLocalDate() + "</p>" + "    <p><strong>Horário:</strong> "
				+ agendamento.getDateTime().toLocalTime() + "</p>" + "  </div>"
				+ "  <p>Se você tiver alguma dúvida ou precisar alterar seu agendamento, entre em contato conosco.</p>"
				+ "  <p style=\"margin-top: 30px;\">Atenciosamente,<br>Equipe ZendaaVIP</p>" + "</div>" + "</body>"
				+ "</html>";

		EmailData emailData = new EmailData(agendamento.getProcedimento().getTenant().getNome(),
				agendamento.getProcedimento().getTenant().getNome(), agendamento.getConsumidor().getEmail(),
				agendamento.getConsumidor().getNome(),
				"📬 Confirmação de agendamento com " + agendamento.getProcedimento().getTenant().getSlug(),
				textoSimples, htmlConteudo);
		
		emailService.sendEmail(emailData);
	}

}
