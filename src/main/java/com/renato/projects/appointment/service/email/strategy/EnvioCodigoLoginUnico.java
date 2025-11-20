package com.renato.projects.appointment.service.email.strategy;

import org.springframework.stereotype.Component;

import com.renato.projects.appointment.security.domain.User;
import com.renato.projects.appointment.service.email.EmailData;
import com.renato.projects.appointment.service.email.EmailService;

@Component
public class EnvioCodigoLoginUnico implements IEnviarEmailStrategy {

    private EmailService emailService;

    public EnvioCodigoLoginUnico(EmailService emailService) {
        super();
        this.emailService = emailService;
    }

    @Override
    public void enviarEmail(Object object) {
        User user = (User) object;

        // Conteúdo simples (texto plano)
        String textoSimples = "Olá " + user.getLogin() + ",\n\n"
            + "Recebemos uma solicitação de login com código único.\n"
            + "Seu código é: " + user.getCodigoAuxiliar().getCodigo() + "\n\n"
            + "Insira este código no aplicativo para prosseguir com a autenticação.\n\n"
            + "Se você não fez essa solicitação, por favor, ignore este e-mail.\n\n"
            + "Atenciosamente,\n"
            + "Equipe Zendaa";

        // Conteúdo HTML (para e-mails com formatação)
        String htmlConteudo = "<html><body>"
            + "<h2>Código de acesso único</h2>"
            + "<p>Olá <strong>" + user.getLogin() + "</strong>,</p>"
            + "<p>Recebemos uma solicitação de login com código único na <strong>Zendaa</strong>.</p>"
            + "<p>Seu código é:</p>"
            + "<h3 style='color: #2d89ef;'>" + user.getCodigoAuxiliar().getCodigo() + "</h3>"
            + "<p>Insira este código no aplicativo para prosseguir com a autenticação.</p>"
            + "<p style='color: gray; font-size: 12px;'>"
            + "Se você não fez essa solicitação, por favor, ignore este e-mail."
            + "</p>"
            + "<br>"
            + "<p>Atenciosamente,<br>Equipe Zendaa</p>"
            + "</body></html>";

        EmailData emailData = new EmailData(
            "zendaa",
            "Equipe Zendaa",
            user.getLogin(),
            " ",
            "Código de acesso único",
            textoSimples,
            htmlConteudo
        );

        emailService.sendEmail(emailData);
    }

}
