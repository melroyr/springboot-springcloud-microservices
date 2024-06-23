package org.example.orderservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced //== yük dengesi,isteklerin mevcut hizmet örnekleri arasında dağıtılmasını sağlar , hangi hizmet örneğinin daha az yüklü olduğunu belirler ve isteği o örneğe yönlendirir.
    //bir mikro hizmetin birden fazla örneği (instance) çalışıyorsa, bu anotasyon sayesinde WebClient bu örnekleri keşfedebilir ve istekleri bu örnekler arasında dağıtabilir.
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }

}
