package com.renato.projects.appointment.security.domain;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.renato.projects.appointment.utils.GerarCodigoConfirmacaoEmail;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "users")
@Entity(name = "users")
public class User implements UserDetails {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String login;
	private String password;
	private UserRole role;
	private String codigoConfirmacaoEmail;
	private Boolean confirmacaoEmail;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String login, String password) {
		
		this.setConfirmacaoEmail(false);
		this.setCodigoConfirmacaoEmail(GerarCodigoConfirmacaoEmail.gerarCodigo());
		this.login = login;
		this.password = new BCryptPasswordEncoder().encode(password);
		this.role = UserRole.USER;
	}

	public Long getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public UserRole getRole() {
		return role;
	}
	
	

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (this.role == UserRole.ADMIN)
			return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
		else
			return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.login;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public String getCodigoConfirmacaoEmail() {
		return codigoConfirmacaoEmail;
	}

	public void setCodigoConfirmacaoEmail(String codigoConfirmacaoEmail) {
		this.codigoConfirmacaoEmail = codigoConfirmacaoEmail;
	}

	public Boolean getConfirmacaoEmail() {
		return confirmacaoEmail;
	}

	public void setConfirmacaoEmail(Boolean confirmacaoEmail) {
		this.confirmacaoEmail = confirmacaoEmail;
	}

}