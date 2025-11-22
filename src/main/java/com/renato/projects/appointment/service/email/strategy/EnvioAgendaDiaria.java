package com.renato.projects.appointment.service.email.strategy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.renato.projects.appointment.domain.Agendamento;
import com.renato.projects.appointment.domain.Procedimento;
import com.renato.projects.appointment.domain.Tenant;
import com.renato.projects.appointment.service.AgendamentoService;
import com.renato.projects.appointment.service.email.EmailData;
import com.renato.projects.appointment.service.email.EmailService;

@Service
public class EnvioAgendaDiaria implements IEnviarEmailStrategy {

	private final AgendamentoService agendamentoService;
	private final EmailService emailService;

	public EnvioAgendaDiaria(AgendamentoService agendamentoService, EmailService emailService) {
		super();
		this.agendamentoService = agendamentoService;
		this.emailService = emailService;
	}

	@Override
	public void enviarEmail(Object object) {
		Tenant tenant = (Tenant) object;
		String email = tenant.getUser().getUsername();

		List<Agendamento> agendamentos = new ArrayList<>();

		for (Procedimento proc : tenant.getProcedimentos()) {
			List<Agendamento> encontrados = agendamentoService.buscarAgendamentosPorProcedimentoEDataAtual(proc);
			agendamentos.addAll(encontrados);
		}
		System.out.println(agendamentos.size()+"-> quantidade de agendamentos");
		if(tenant.getReceberAgendaDiariaPorEmail()==false)
			return;
		if (agendamentos.isEmpty()) {
			return; // não envia nada se não houver agendamentos
		}

		// -------- TEXTO SIMPLES --------
		StringBuilder textoSimples = new StringBuilder();
		textoSimples.append("Olá ").append(tenant.getNome()).append(",\n\n");
		textoSimples.append("Aqui estão os agendamentos de hoje:\n\n");

		for (Agendamento a : agendamentos) {
			textoSimples.append("- ").append(a.getConsumidor().getNome()).append(" | ")
					.append(a.getProcedimento().getNome()).append(" | ").append(a.getDateTime().toLocalTime())
					.append("\n");
		}

		textoSimples.append("\nBons atendimentos!\nEquipe ZendaaVIP");

		// -------- HTML --------
		StringBuilder html = new StringBuilder();
		html.append("<!DOCTYPE html>");
		html.append("<html><head><meta charset='UTF-8'><style>")
				.append("body { font-family: Arial, sans-serif; background-color:#f9f9f9; padding:20px; }")
				.append(".container { max-width:600px; margin:auto; background:#fff; padding:30px; border-radius:10px; box-shadow:0 0 10px rgba(0,0,0,0.05); }")
				.append("h2 { color:#333; }").append("table { width:100%; border-collapse:collapse; margin-top:20px; }")
				.append("th, td { padding:10px; text-align:left; border-bottom:1px solid #ddd; }")
				.append("</style></head><body>");

		html.append("<div class='container'>");
		html.append("<h2>Agendamentos de hoje - ").append(tenant.getNome()).append("</h2>");
		html.append("<p>Segue sua lista de atendimentos do dia:</p>");

		html.append("<table>").append("<tr>").append("<th>Cliente</th>").append("<th>Procedimento</th>")
				.append("<th>Horário</th>").append("</tr>");

		for (Agendamento a : agendamentos) {
			html.append("<tr>").append("<td>").append(a.getConsumidor().getNome()).append("</td>").append("<td>")
					.append(a.getProcedimento().getNome()).append("</td>").append("<td>")
					.append(a.getDateTime().toLocalTime()).append("</td>").append("</tr>");
		}

		html.append("</table>");
		html.append("<p style='margin-top:30px;'>Bons atendimentos!<br>Equipe ZendaaVIP</p>");
		html.append("</div></body></html>");

		EmailData emailData = new EmailData(tenant.getSlug(), tenant.getNome(), email, tenant.getNome(),
				"📅 Agendamentos de hoje - " + tenant.getNome(), textoSimples.toString(), html.toString());

		emailService.sendEmail(emailData);
	}

}
