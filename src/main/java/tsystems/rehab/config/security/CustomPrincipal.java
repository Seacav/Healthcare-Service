package tsystems.rehab.config.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import tsystems.rehab.entity.User;

public class CustomPrincipal implements UserDetails{
	
	private User user;
	
	public User getUser() {
		return user;
	}
	
	public CustomPrincipal(final User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> grntAuth = new HashSet<>();
		if (user!=null) {
			String role = user.getRole();
			grntAuth.add(new SimpleGrantedAuthority(role));
		}
		return grntAuth;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
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

	@Override
	public String toString() {
		return "CustomPrincipal [user=" + user + "]";
	}

}
