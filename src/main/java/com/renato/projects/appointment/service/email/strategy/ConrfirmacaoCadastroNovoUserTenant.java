package com.renato.projects.appointment.service.email.strategy;

import org.springframework.stereotype.Component;

import com.renato.projects.appointment.security.domain.User;
import com.renato.projects.appointment.service.email.EmailData;
import com.renato.projects.appointment.service.email.EmailService;

@Component
public class ConrfirmacaoCadastroNovoUserTenant implements EnviarEmailStrategy {

	private EmailService emailService;

	public ConrfirmacaoCadastroNovoUserTenant(EmailService emailService) {
		super();
		this.emailService = emailService;
	}

	@Override
	public void enviarEmail(Object object) {
		User user = (User) object;
		
		// Conteúdo simples (texto plano)
	    String textoSimples = "Olá " + user.getLogin() + ",\n\n"
	        + "Obrigado por se cadastrar na Zendaa!\n"
	        + "Seu código de confirmação é: " + user.getCodigoConfirmacaoEmail() + "\n\n"
	        + "Por favor, insira este código na aplicação para confirmar seu e-mail.\n\n"
	        + "Atenciosamente,\n"
	        + "Equipe Zendaa";

	    // Conteúdo HTML (para e-mails com formatação)
	    String htmlConteudo = "<html><body>"
	        + "<h2>Confirmação de inscrição</h2>"
	        + "<p>Olá <strong>" + user.getLogin() + "</strong>,</p>"
	        + "<p>Obrigado por se cadastrar na <strong>Zendaa</strong>!</p>"
	        + "<p>Seu código de confirmação é:</p>"
	        + "<h3 style='color: #2d89ef;'>" + user.getCodigoConfirmacaoEmail() + "</h3>"
	        + "<p>Insira este código na aplicação para confirmar seu e-mail.</p>"
	        + "<br>"
	        + "<p>Atenciosamente,<br>Equipe Zendaa</p>"
	        + "</body></html>";
		
		EmailData emailData = new EmailData("zendaa", "Equipe Zendaa", user.getLogin(), " ", "Confirmação de inscrição", textoSimples, htmlConteudo);
		
		emailService.sendEmail(emailData);
	}

}
