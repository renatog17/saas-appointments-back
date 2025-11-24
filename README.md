# Appointment – Plataforma de Agendamentos

Sistema de agendamentos criado para resolver um problema real: organizar, automatizar e gerenciar os horários da minha cabeleireira com segurança, clareza e confiabilidade.  
O projeto segue princípios de arquitetura limpa, boas práticas e utiliza ferramentas amplamente usadas em produção.

---

## ✔️ Principais Recursos

### Backend (Spring Boot)
- **Spring Security**  
  - Autenticação JWT  
  - Sessões stateless  
  - Políticas seguras de senha  
- **Spring Data JPA**  
  - Repository pattern  
  - Hibernate
- **Spring Validation**  
  - Validação de DTOs  
  - Tratamento de erros consistente  
- **Perfis de ambiente**  
  - `dev`, `prod`  
  - Variáveis de ambiente para credenciais
- **Flyway Migrations**  
  - Versionamento de scripts SQL  
  - Evolução segura do schema
- **Envio de E-mail**  
  - Confirmação de agendamentos  
  - Lembretes automáticos (quando houver agendamentos de rotina)
- **Padrões de Projeto Utilizados**
  - DTO + Mapper 
  - Strategy (serviços de e-mail / cadastro de novos usuários e realização de agendamentos)  
- **Clean Code + Boas Práticas**
  - Arquitetura em camadas  
  - Princípios de responsabilidade única  
  - Uso de DTOs para evitar exposição direta do domínio
- **Banco de Dados PostgreSQL**

### DevOps
- **GitHub Actions CI/CD**
  - Pipeline de testes e build  
  - Deploy automático via SSH  
  - Variáveis de ambiente seguras
- **Serviço systemd**
  - Executa o jar em produção  
  - Logs via `journalctl`
- **NGINX como proxy reverso**
  - Redirecionamento para o backend  
  - HTTPS opcional

### Caso de Uso Real
Desenvolvido originalmente para a minha cabeleireira, visando resolver problemas concretos:
- Choque de horários  
- Falta de notificações  
- Agendamentos perdidos  
- Acompanhamento manual pouco confiável  
- Dificuldade em editar, confirmar ou visualizar clientes anteriores


