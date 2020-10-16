package org.openlmis.stockmanagement.validators;

import static org.openlmis.stockmanagement.i18n.MessageKeys.ERROR_DESTINATION_FREE_TEXT_NOT_ALLOWED;
import static org.openlmis.stockmanagement.i18n.MessageKeys.ERROR_REASON_FREE_TEXT_NOT_ALLOWED;
import static org.openlmis.stockmanagement.i18n.MessageKeys.ERROR_SOURCE_DESTINATION_FREE_TEXT_BOTH_PRESENT;
import static org.openlmis.stockmanagement.i18n.MessageKeys.ERROR_SOURCE_FREE_TEXT_NOT_ALLOWED;

import java.util.UUID;
import org.openlmis.stockmanagement.domain.reason.StockCardLineItemReason;
import org.openlmis.stockmanagement.domain.sourcedestination.Node;
import org.openlmis.stockmanagement.dto.StockEventDto;
import org.openlmis.stockmanagement.dto.StockEventLineItemDto;
import org.openlmis.stockmanagement.exception.ValidationMessageException;
import org.openlmis.stockmanagement.extension.point.FreeTextValidator;
import org.openlmis.stockmanagement.util.Message;
import org.slf4j.profiler.Profiler;
import org.springframework.stereotype.Component;

@Component(value = "ExtensionFreeTextValidator")
public class ExtensionFreeTextValidator implements FreeTextValidator {

  @Override
  public void validate(StockEventDto stockEventDto) {
    XLOGGER.entry(stockEventDto);
    Profiler profiler = new Profiler("FREE_TEXT_VALIDATOR");
    profiler.setLogger(XLOGGER);
    if (!stockEventDto.hasLineItems()) {
      return;
    }
    profiler.start("CHECK_EVENT_LINE_ITEMS");
    for (StockEventLineItemDto eventLineItem : stockEventDto.getLineItems()) {
      checkSourceDestinationFreeTextBothPresent(eventLineItem);
      checkNodeFreeText(
          stockEventDto, eventLineItem.getSourceId(),
          eventLineItem.getSourceFreeText(),
          ERROR_SOURCE_FREE_TEXT_NOT_ALLOWED
      );
      checkNodeFreeText(
          stockEventDto, eventLineItem.getDestinationId(),
          eventLineItem.getDestinationFreeText(),
          ERROR_DESTINATION_FREE_TEXT_NOT_ALLOWED
      );
      checkReasonFreeText(stockEventDto, eventLineItem);
    }
    profiler.stop().log();
    XLOGGER.exit(stockEventDto);
  }

  private void checkSourceDestinationFreeTextBothPresent(StockEventLineItemDto eventLineItem) {
    if (eventLineItem.hasSourceFreeText() && eventLineItem.hasDestinationFreeText()) {
      throwError(ERROR_SOURCE_DESTINATION_FREE_TEXT_BOTH_PRESENT,
          eventLineItem.getSourceFreeText(), eventLineItem.getDestinationFreeText());
    }
  }

  private void checkNodeFreeText(StockEventDto event, UUID nodeId,
      String freeText, String errorKey) {
    if (nodeId != null) {
      Node node = event.getContext().findNode(nodeId);
      if (null != node && node.isRefDataFacility() && freeText != null) {
        throwError(errorKey, nodeId, freeText);
      }
    } else if (freeText != null) {
      //node free text exist but node id is null
      throwError(errorKey, nodeId, freeText);
    }
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
