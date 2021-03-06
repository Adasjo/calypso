package se.datasektionen.calypso.auth;

import facebook4j.Facebook;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("unused")
public class DAuthUserDetails implements UserDetails {
	private String user;
	private String token;
	private String firstName;
	private String lastName;
	private String email;
	private Collection<? extends GrantedAuthority> authorities;
	private Map<String, String> mandates;
	private Facebook facebook;

	public DAuthUserDetails(String user,
	                        String token,
	                        String firstName,
	                        String lastName,
	                        String email,
	                        Map<String, String> mandates,
	                        Collection<? extends GrantedAuthority> authorities) {
		this.user = user;
		this.token = token;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mandates = mandates;
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return token;
	}

	@Override
	public String getUsername() {
		return user;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getUser() {
		return user;
	}

	public String getToken() {
		return token;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return this.firstName + " " + this.lastName;
	}

	public Map<String, String> getMandates() {
		return mandates;
	}

	public Facebook getFacebook() {
		return facebook;
	}

	public void setFacebook(Facebook facebook) {
		this.facebook = facebook;
	}

	@Override
	public String toString() {
		return "DAuthUserDetails{" +
				"user='" + user + '\'' +
				", token='" + token + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				", authorities=" + authorities.toString() +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DAuthUserDetails that = (DAuthUserDetails) o;
		return Objects.equals(user, that.user) &&
				Objects.equals(firstName, that.firstName) &&
				Objects.equals(lastName, that.lastName) &&
				Objects.equals(email, that.email);
	}
}
