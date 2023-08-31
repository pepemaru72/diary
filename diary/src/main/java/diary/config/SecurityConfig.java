package diary.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import diary.service.MyUserService;


@Configuration
@EnableMethodSecurity

public class SecurityConfig {
	
	 @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	        return authenticationConfiguration.getAuthenticationManager();
	    }
	
	@Autowired
	private MyUserService userService;
	
	
	// URLパス毎に制御
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/js/**", "/css/**", "/login").permitAll()
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/login/diary", true)
                        .failureUrl("/loginForm?error=true"))
                .logout(logout -> logout
                        .permitAll());
                		
        return http.build();
    }
	@Bean
	public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	 
	 @Bean
	 DataSource dataSource() {
	 	return new EmbeddedDatabaseBuilder()
	 		.setType(null)
	 		.addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
	 		.build();
	 }
	 @Bean
		public UserDetailsManager userDetailsManager() {
			return new JdbcUserDetailsManager(dataSource());
		}
	 
	
	
	//@Bean
	//public UserDetailsManager userDetailsManager() {
		//return new JdbcUserDetailsManager();
	//}
	//@Bean
    //public javax.sql.DataSource getDataSource() {
       // DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        //dataSourceBuilder.driverClassName("org.h2.Driver");
        //return dataSourceBuilder.build();
   // }
	
	
	/** @Bean
	    public DaoAuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	        authProvider.setUserDetailsService(userService);
	        authProvider.setPasswordEncoder(passwordEncoder());
	        return authProvider;
	    }
	
	//@Override
		 /** public void configure(AuthenticationManagerBuilder auth) throws Exception{
		  auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
		  } 
		//@Autowired
	    void configureAuthenticationManager(AuthenticationManagerBuilder auth) throws Exception{
	        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	    }
	    
	    * .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/js/**", "/css/**", "/login").permitAll()
                        .anyRequest().authenticated()
                		);
                var authManager = authenticationManager(http.getSharedObject(AuthenticationConfiguration.class));
                http.addFilter(new MyUsernamePasswordAuthenticationFilter(authManager));
                http.logout(logout -> logout
                        .permitAll());
	    **/
}




	
	
	


