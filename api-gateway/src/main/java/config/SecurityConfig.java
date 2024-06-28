package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity.csrf(csrf -> csrf.disable()) //CSRF (Cross-Site Request Forgery), güvenlik saldırılarına karşı koruma sağlar, devre dışı bırakıldı
                .authorizeExchange(exchange -> exchange // Endpoint'lerin erişimini yapılandırır.
                        .pathMatchers("/eureka/**") //"eureka" path pattern'ine sahip olan isteklerin konfigürasyonunu yapılandırır.
                        .permitAll() ///eureka/** pattern'ine sahip  isteklerin kimlik doğrulaması olmadan erişimine izin verir.
                        .anyExchange() //Diğer tüm endpoint'ler için aynı konfigürasyonu belirtir.
                        .authenticated()) // Kimlik doğrulaması gerektiren tüm endpoint'ler için erişimi sınırlar
                .oauth2ResourceServer((oauth2) -> oauth2
                        .jwt(Customizer.withDefaults())); //gelen HTTP isteklerinde JWT'nin içeriğinde yer alan bilgiler doğrulandıktan sonra isteğin kaynak sunucu tarafından işlenmesine izin verilir

        return serverHttpSecurity.build();
    }
}
