package com.renato.projects.appointment.service.email.strategy;

import org.springframework.stereotype.Component;

import com.renato.projects.appointment.domain.Tenant;
import com.renato.projects.appointment.service.email.EmailData;
import com.renato.projects.appointment.service.email.EmailService;

@Component
public class ConfirmacaoCadastroNovoUserTenant implements IEnviarEmailStrategy {

	private EmailService emailService;

	public ConfirmacaoCadastroNovoUserTenant(EmailService emailService) {
		super();
		this.emailService = emailService;
	}

	@Override
	public void enviarEmail(Object object) {
		Tenant tenant = (Tenant) object;
		
		// Conteúdo simples (texto plano)
	    String textoSimples = "Olá " + tenant.getNome() + ",\n\n"
	        + "Obrigado por se cadastrar na Zendaa!\n"
	        + "Seu código de confirmação é: " + tenant.getUser().getCodigoAuxiliar().getCodigo()+ "\n\n"
	        + "Por favor, insira este código na aplicação para confirmar seu e-mail.\n\n"
	        + "Atenciosamente,\n"
	        + "Equipe Zendaa";

	    // Conteúdo HTML (para e-mails com formatação)
	    String htmlConteudo = "<html><body>"
	        + "<h2>Confirmação de inscrição</h2>"
	        + "<p>Olá <strong>" + tenant.getNome()+ "</strong>,</p>"
	        + "<p>Obrigado por se cadastrar na <strong>Zendaa</strong>!</p>"
	        + "<p>Seu código de confirmação é:</p>"
	        + "<h3 style='color: #2d89ef;'>" + tenant.getUser().getCodigoAuxiliar().getCodigo() + "</h3>"
	        + "<p>Insira este código na aplicação para confirmar seu e-mail.</p>"
	        + "<br>"
	        + "<p>Atenciosamente,<br>Equipe Zendaa</p>"
	        + "</body></html>";
		
		EmailData emailData = new EmailData("zendaa", "Equipe Zendaa", tenant.getUser().getLogin(), " ", "Confirmação de inscrição", textoSimples, htmlConteudo);
		
		emailService.sendEmail(emailData);
	}

}
