/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2017 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Affero General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Affero General Public License for more details. You should have received a copy of
 * the GNU Affero General Public License along with this program. If not, see
 * http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

package org.openlmis.stockmanagement.validators;

import org.openlmis.stockmanagement.dto.StockEventDto;
import org.openlmis.stockmanagement.extension.point.AdjustmentReasonValidator;
import org.slf4j.profiler.Profiler;
import org.springframework.stereotype.Component;

/**
 * An adjustment should have a reason that is either DEBIT or CREDIT. And it should have a reason
 * category that is ADJUSTMENT
 */
@Component(value = "NoneValidator")
public class NoneValidator implements AdjustmentReasonValidator {

  @Override
  public void validate(StockEventDto stockEventDto) {
    XLOGGER.entry(stockEventDto);
    Profiler profiler = new Profiler("ADJUSTMENT_REASON_NONE_VALIDATOR");
    profiler.setLogger(XLOGGER);
    profiler.stop().log();
    XLOGGER.exit(stockEventDto);
  }
}
