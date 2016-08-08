package org.openlmis.example.extension;

import org.openlmis.example.extension.point.OrderQuantity;
import org.springframework.stereotype.Component;

@Component("AMCOrderQuantity")
public class AMCOrderQuantity implements OrderQuantity {

  public String getInfo() {
    return "I am extended method";
  }
}
