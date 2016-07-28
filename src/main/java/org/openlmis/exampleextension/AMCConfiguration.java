package org.openlmis.exampleextension;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AMCConfiguration {

  @Bean
  AMCOrderQuantity AMCOrderQuantity() {
    return new AMCOrderQuantity();
  }

}