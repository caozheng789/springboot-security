package per.cz.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by Administrator on 2020/5/23.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer{

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("redirect:/login");
	}


//	@Bean
//	public CorsFilter corsFilter() {
//		final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new      UrlBasedCorsConfigurationSource();
//		final CorsConfiguration corsConfiguration = new CorsConfiguration();
//		corsConfiguration.setAllowCredentials(true);
//		corsConfiguration.addAllowedOrigin("*");
//		corsConfiguration.addAllowedHeader("*");
//		corsConfiguration.addAllowedMethod("*");
//		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
//		return new CorsFilter(urlBasedCorsConfigurationSource);
//	}





}
