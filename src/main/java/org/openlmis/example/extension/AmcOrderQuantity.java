package org.openlmis.example.extension;

import org.openlmis.example.extension.point.OrderQuantity;
import org.springframework.stereotype.Component;

@Component("AmcOrderQuantity")
public class AmcOrderQuantity implements OrderQuantity {

  public String getInfo() {
    return "I am extended method";
  }
}
