package com.renato.projects.appointment.service.email;

public class EmailData {

	private String fromEmail;
    private String fromName;
    
    private String toEmail;
    private String toNome;
    
    private String assunto;
    
    private String textoSimples;
    private String htmlConteudo;
    
	public EmailData(String fromEmail, String fromName, String toEmail, String toNome, String assunto,
			String textoSimples, String htmlConteudo) {
		super();
		this.fromEmail = fromEmail;
		this.fromName = fromName;
		this.toEmail = toEmail;
		this.toNome = toNome;
		this.assunto = assunto;
		this.textoSimples = textoSimples;
		this.htmlConteudo = htmlConteudo;
	}
	public String getFromEmail() {
		return fromEmail;
	}
	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public String getToEmail() {
		return toEmail;
	}
	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}
	public String getToNome() {
		return toNome;
	}
	public void setToNome(String toNome) {
		this.toNome = toNome;
	}
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	public String getTextoSimples() {
		return textoSimples;
	}
	public void setTextoSimples(String textoSimples) {
		this.textoSimples = textoSimples;
	}
	public String getHtmlConteudo() {
		return htmlConteudo;
	}
	public void setHtmlConteudo(String htmlConteudo) {
		this.htmlConteudo = htmlConteudo;
	}
}
