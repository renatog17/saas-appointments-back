package com.renato.projects.appointment.service.email.strategy;

import org.springframework.stereotype.Service;

import com.renato.projects.appointment.domain.Agendamento;

import com.renato.projects.appointment.service.email.EmailData;
import com.renato.projects.appointment.service.email.EmailService;

@Service
public class InformarCancelamento implements IEnviarEmailStrategy {

    private final EmailService emailService;

    public InformarCancelamento(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void enviarEmail(Object object) {
        Agendamento agendamento = (Agendamento) object;

        String nomeConsumidor = agendamento.getConsumidor().getNome();
        String emailConsumidor = agendamento.getConsumidor().getEmail();
        String nomeProcedimento = agendamento.getProcedimento().getNome();
        String nomeProfissional = agendamento.getProcedimento().getTenant().getNome();
        String slug = agendamento.getProcedimento().getTenant().getSlug();

        // Texto simples
        String textoSimples =
                "Olá " + nomeConsumidor + ",\n\n" +
                "Seu agendamento foi cancelado.\n\n" +
                "Detalhes:\n" +
                "- Procedimento: " + nomeProcedimento + "\n" +
                "- Profissional: " + nomeProfissional + "\n" +
                "- Data: " + agendamento.getDateTime().toLocalDate() + "\n" +
                "- Horário: " + agendamento.getDateTime().toLocalTime() + "\n\n" +
                "Se este cancelamento não foi feito por você ou se precisar reagendar, entre em contato conosco.\n\n" +
                "Atenciosamente,\nEquipe ZendaaVIP";

        // HTML bonito
        String htmlConteudo =
                "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "  <meta charset=\"UTF-8\">" +
                "  <style>" +
                "    body { font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 20px; }" +
                "    .container { max-width: 600px; margin: auto; background: #fff; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.05); }" +
                "    h2 { color: #d9534f; }" +  // vermelho para cancelamento
                "    p { color: #555; font-size: 16px; line-height: 1.5; }" +
                "    .details { background-color: #f1f1f1; padding: 15px; border-radius: 5px; margin: 20px 0; }" +
                "    .details p { margin: 5px 0; }" +
                "  </style>" +
                "</head>" +
                "<body>" +
                "<div class=\"container\">" +
                "  <h2>Agendamento cancelado</h2>" +
                "  <p>Olá, " + nomeConsumidor + ".</p>" +
                "  <p>Seu agendamento foi cancelado. Confira os detalhes:</p>" +
                "  <div class=\"details\">" +
                "    <p><strong>Procedimento:</strong> " + nomeProcedimento + "</p>" +
                "    <p><strong>Profissional:</strong> " + nomeProfissional + "</p>" +
                "    <p><strong>Data:</strong> " + agendamento.getDateTime().toLocalDate() + "</p>" +
                "    <p><strong>Horário:</strong> " + agendamento.getDateTime().toLocalTime() + "</p>" +
                "  </div>" +
                "  <p>Se você não solicitou este cancelamento ou deseja agendar novamente, estamos à disposição.</p>" +
                "  <p style=\"margin-top: 30px;\">Atenciosamente,<br>Equipe ZendaaVIP</p>" +
                "</div>" +
                "</body>" +
                "</html>";

        // Cria o objeto de email
        EmailData emailData = new EmailData(
                slug,
                nomeProfissional,
                emailConsumidor,
                nomeConsumidor,
                "❌ Seu agendamento foi cancelado",
                textoSimples,
                htmlConteudo
        );

        emailService.sendEmail(emailData);
    }
}
