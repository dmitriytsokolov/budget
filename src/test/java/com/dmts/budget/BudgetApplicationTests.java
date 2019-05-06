package com.dmts.budget;

import com.dmts.budget.entity.Role;
import com.dmts.budget.entity.User;
import com.dmts.budget.repository.RoleRepository;
import com.dmts.budget.repository.UserRepository;
import com.dmts.budget.service.UserService;
import com.dmts.budget.service.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class BudgetApplicationTests {

	@TestConfiguration
	static class UserServiceImplTestContextConfiguration {

		@Bean
		public UserService userService() {
			return new UserServiceImpl();
		}
	}

	@Before
	public void setUp() {
	}

	@Autowired
	private UserService userService;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private RoleRepository roleRepository;

	@MockBean
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Test(expected = AccessDeniedException.class)
	@WithMockUser(username="admin")
	public void performProvision_shouldPass() {
		User user = new User();
		user.setRole(new Role());
		user.setUsername("asdasd");
		user.setPassword("123?");
		userService.save(user);
	}

	@Test
	@WithMockUser(username="admin")
	public void performProvision_shouldPass2() {
		User user = new User();
		user.setRole(new Role());
		user.setUsername("asdasd");
		user.setPassword("123");
		Assert.assertTrue(userService.save(user));
	}

}
