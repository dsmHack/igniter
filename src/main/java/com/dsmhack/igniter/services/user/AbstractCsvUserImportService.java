package com.dsmhack.igniter.services.user;

import com.dsmhack.igniter.models.User;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class AbstractCsvUserImportService implements UserImportService {

  private final CSVFormat csvFormat;

  public AbstractCsvUserImportService(CSVFormat csvFormat) {
    this.csvFormat = csvFormat;
  }

  protected abstract User createUser(CSVRecord record);

  @Override
  public List<User> getUsers(Reader reader) throws UserImportException {
    return StreamSupport.stream(getRecords(reader).spliterator(), false)
        .skip(1)
        .map(this::createUser)
        .collect(Collectors.toList());
  }

  private Iterable<CSVRecord> getRecords(Reader reader) throws UserImportException {
    try {
      return csvFormat.parse(reader);
    } catch (IOException e) {
      throw new UserImportException("Unable to import users.", e);
    }
  }
}
