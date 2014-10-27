package com.fantasy.security.userdetails;

import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.fantasy.framework.util.common.ObjectUtil;
import com.fantasy.security.bean.User;
import com.fantasy.security.service.UserService;

public class SimpleUserDetailsService implements UserDetailsService {

	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

	@Resource(name="fantasy.auth.UserService")
	private UserService userService;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findUniqueByUsername(username);
		if (ObjectUtil.isNull(user)) {
			throw new UsernameNotFoundException(messages.getMessage("JdbcDaoImpl.notFound", new Object[] { username }, "Username {0} not found", Locale.CANADA));
		}
		return new AdminUser(user);
	}

}