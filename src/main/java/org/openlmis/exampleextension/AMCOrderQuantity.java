package org.openlmis.exampleextension;

import org.openlmis.example.extensionpoint.OrderQuantity;

public class AMCOrderQuantity implements OrderQuantity {

  public String getInfo() {
    return "I am extended method";
  }
}
