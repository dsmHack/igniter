package com.dsmhack.igniter.services.user;

import com.dsmhack.igniter.models.User;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

@Service
public class EventBriteUserImportService extends AbstractCsvUserImportService {

  enum Headers {
    ORDER_ID,
    ORDER_DATE,
    FIRST_NAME,
    LAST_NAME,
    EMAIL,
    QUANTITY,
    TICKET_TYPE,
    ORDER_TYPE,
    TOTAL_PAID,
    EVENTBRITE_FEES,
    EVENTBRITE_PAYMENT_PROCESSING,
    ATTENDEE_STATUS,
    CELL_PHONE,
    ACCEPT_LIABILITY_RELEASE,
    ACCEPT_CODE_OF_CONDUCT,
    OVER_21,
    TSHIRT_SIZE,
    FOOD_SENSITIVITES,
    FOOD_SENSITIVITES_DESCRIPTION,
    IS_GITHUB_USER,
    GITHUB_USERNAME,
    IS_SLACK_USER,
    SLACK_EMAIL,
    SLACK_INVITE_EMAIL,
    CURRENT_ROLE,
    CURRENT_ROLE_DESCRIPTION,
    EVENT_ROLE,
    EVENT_EMAIL,
    FORMER_PARTICIPANT,
    YEARS_OF_PARTICIPATION,
    BILLING_ADDRESS_1,
    BILLING_ADDRESS_2,
    BILLING_CITY,
    BILLING_STATE,
    BILLING_ZIP,
    BILLING_COUNTRY,
    JOB_TITLE,
    COMPANY
  }

  public EventBriteUserImportService() {
    super(CSVFormat.EXCEL.withHeader(Headers.class));
  }

  @Override
  protected User createUser(CSVRecord record) {
    User user = new User();
    user.setFirstName(record.get(Headers.FIRST_NAME));
    user.setLastName(record.get(Headers.LAST_NAME));
    user.setEmail(record.get(Headers.EMAIL));
    user.setGithubUsername(record.get(Headers.GITHUB_USERNAME));
    return user;
  }
}
