package ch.bluecare.springplayground.controller.consumingrest;

import ch.bluecare.springplayground.model.consumingrest.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

// @SpringBootApplication
public class ConsumingRestController {

  private static final Logger log = LoggerFactory.getLogger(ConsumingRestController.class);
  private static final String SERVICE_URL = "https://gturnquist-quoters.cfapps.io/api/random";

  /*
     public static void main(String[] args) {
         SpringApplication.run(ConsumingRestController.class, args);
     }

  */

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder.build();
  }

  @Bean
  public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
    return args -> {
      Quote quote = restTemplate.getForObject(SERVICE_URL, Quote.class);
      log.info(quote.toString());
    };
  }
}
