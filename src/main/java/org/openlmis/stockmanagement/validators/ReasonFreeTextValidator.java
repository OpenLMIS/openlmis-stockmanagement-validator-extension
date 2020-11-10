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

import static org.openlmis.stockmanagement.i18n.MessageKeys.ERROR_REASON_FREE_TEXT_NOT_ALLOWED;

import org.openlmis.stockmanagement.domain.reason.StockCardLineItemReason;
import org.openlmis.stockmanagement.dto.StockEventDto;
import org.openlmis.stockmanagement.dto.StockEventLineItemDto;
import org.openlmis.stockmanagement.exception.ValidationMessageException;
import org.openlmis.stockmanagement.extension.point.FreeTextValidator;
import org.openlmis.stockmanagement.util.Message;
import org.slf4j.profiler.Profiler;
import org.springframework.stereotype.Component;

@Component(value = "ReasonFreeTextValidator")
public class ReasonFreeTextValidator implements FreeTextValidator {

  @Override
  public void validate(StockEventDto stockEventDto) {
    XLOGGER.entry(stockEventDto);
    Profiler profiler = new Profiler("REASON_FREE_TEXT_VALIDATOR");
    profiler.setLogger(XLOGGER);
    if (!stockEventDto.hasLineItems()) {
      return;
    }
    profiler.start("CHECK_EVENT_LINE_ITEMS");
    for (StockEventLineItemDto eventLineItem : stockEventDto.getLineItems()) {
      checkReasonFreeText(stockEventDto, eventLineItem);
    }
    profiler.stop().log();
    XLOGGER.exit(stockEventDto);
  }

  private void checkReasonFreeText(StockEventDto event, StockEventLineItemDto lineItem) {
    if (!lineItem.hasReasonFreeText()) {
      return;//if there is no reason free text, then there is no need to validate
    }
    boolean reasonNotAllowFreeText = lineItem.hasReasonId()
        && !isFreeTextAllowed(event, lineItem);
    boolean hasNoReasonIdButHasFreeText = !lineItem.hasReasonId();
    if (reasonNotAllowFreeText || hasNoReasonIdButHasFreeText) {
      throwError(ERROR_REASON_FREE_TEXT_NOT_ALLOWED,
          lineItem.getReasonId(), lineItem.getReasonFreeText());
    }
  }

  private boolean isFreeTextAllowed(StockEventDto event, StockEventLineItemDto lineItem) {
    StockCardLineItemReason reason = event.getContext().findEventReason(lineItem.getReasonId());
    return reason != null && reason.getIsFreeTextAllowed();
  }

  private void throwError(String key, Object... params) {
    throw new ValidationMessageException(new Message(key, params));
  }
}
