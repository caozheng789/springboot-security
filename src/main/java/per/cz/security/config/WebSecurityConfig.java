package per.cz.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import per.cz.security.filter.TokenFilter;

import java.util.Arrays;

/**
 * Created by Administrator on 2020/5/23.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	@Autowired
	private LogoutSuccessHandler logoutSuccessHandler;
	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private TokenFilter tokenFilter;

//	//定义用户信息服务（查询用户信息）
//	@Bean
//	public UserDetailsService userDetailsService(){
//		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//		//创建用户
//		manager.createUser(UserDto.withUsername("zhangsan").password("123").authorities("p1").build());
//		manager.createUser(UserDto.withUsername("lisi").password("123").authorities("p2").build());
//
//		return manager;
//	}


	//密码编码器
//	@Bean
//	public PasswordEncoder passwordEncoder(){
//		return  NoOpPasswordEncoder.getInstance();
//	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();
		//指定允许跨域的请求(*所有)：http://wap.ivt.guansichou.com
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
		// setAllowCredentials(true) is important, otherwise:
		// The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
		configuration.setAllowCredentials(true);
		// setAllowedHeaders is important! Without it, OPTIONS preflight request
		// will fail with 403 Invalid CORS request
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "X-User-Agent", "Content-Type"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	//安全拦截机制
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		//禁用csrf
		http.csrf().disable()
		.cors().configurationSource(CorsConfigurationSource())
		.and().servletApi().disable().requestCache().disable();

		// 基于token，所以不需要session  禁用session
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		//登录配置
		http.formLogin()
				.loginProcessingUrl("/login")
				//登录成功配置 全部在SecurityHandlerConfig中
				.successHandler(authenticationSuccessHandler)
				//登录失败配置
				.failureHandler(authenticationFailureHandler).and()
				//异常配置
				.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
		//退出
		http.logout().logoutUrl("/logout")
				//退出成功处理器
				.logoutSuccessHandler(logoutSuccessHandler);
		// 解决不允许显示在iframe的问题
		http.headers().frameOptions().disable();
		http.headers().cacheControl();

		//拦截token
		http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);

//			http.authorizeRequests()
//					//需要权限
////					.antMatchers("/r/r1").hasAuthority("p1")
////					.antMatchers("/r/r2").hasAuthority("p2")
//					.antMatchers("/r/**")
//					.authenticated() //除了/r/** 其他放行
//					.anyRequest()
//					.permitAll()
//					.and()
//					.formLogin()//允许表单登录
//					.successForwardUrl("/login-success");//自定义登录成功的页面地址 ;
	}

	//授权
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//注入用户信息 配置查询用户信息
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}


	//配置跨域访问资源
	private CorsConfigurationSource CorsConfigurationSource() {
		CorsConfigurationSource source =   new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("*");	//同源配置，*表示任何请求都视为同源，若需指定ip和端口可以改为如“localhost：8080”，多个以“，”分隔；
		corsConfiguration.addAllowedHeader("*");//header，允许哪些header，本案中使用的是token，此处可将*替换为token；
		corsConfiguration.addAllowedMethod("*");	//允许的请求方法，PSOT、GET等
		((UrlBasedCorsConfigurationSource) source).registerCorsConfiguration("/**",corsConfiguration); //配置允许跨域访问的url
		return source;
	}
}
