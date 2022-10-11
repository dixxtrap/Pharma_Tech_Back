package com.tuthan.pharma_tech_back;

import com.tuthan.pharma_tech_back.models.Pharmacie;
import com.tuthan.pharma_tech_back.models.Role;
import com.tuthan.pharma_tech_back.models.User;
import com.tuthan.pharma_tech_back.services.PharmacieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
@EnableSwagger2
public class PharmaTechBackApplication {

    public static void main(String[] args) {

        SpringApplication.run(PharmaTechBackApplication.class, args);

    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
                "Accept", "Jwt-Token", "Authorization", "Origin, Accept", "X-Requested-With",
                "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Jwt-Token", "Authorization",
                "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        return multipartResolver;
    }
//   @Bean
//    CommandLineRunner runner(UserService userService){
//        return args -> {
//            userService.saveRole(new Role(null,"Role_Admin"));
//            userService.saveRole(new Role(null,"Role_Pharmacien"));
//
//            userService.SaveUser(new User(null,"Salane","Djiga", "Djiga","1234",new ArrayList<>()));
//            userService.SaveUser(new User(null,"kone","Malick","Malick","1234",new ArrayList<>()));
//
//            userService.addRoleToUser("Djiga","Role_Admin");
//            userService.addRoleToUser("Malick","Role_Pharmacien");
//        };
//    }
//       @Bean
//    CommandLineRunner runner(PharmacieService pharmacieService){
//        return args -> {
//            pharmacieService.SavePharmacie(new Pharmacie(null,"Pharmacie L'islam","Mbao","12345","54123"));
//            pharmacieService.SavePharmacie(new Pharmacie(null,"Pharmacie Caty","Pikine","26578","198707"));
//        };
//   }




}
