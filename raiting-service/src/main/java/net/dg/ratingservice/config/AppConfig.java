package net.dg.ratingservice.config;

import feign.Retryer;
import net.dg.ratingservice.feign.errordecoder.FeignErrorDecoder;
import net.dg.ratingservice.feign.retryer.CustomRetryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public FeignErrorDecoder feignErrorDecoder() {
        return new FeignErrorDecoder();
    }

    @Bean
    public Retryer retryer() {
        return new CustomRetryer();
    }

}
