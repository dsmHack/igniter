package com.dsmhack.igniter.services.user;

import com.dsmhack.igniter.models.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

@Service
public class JsonUserImportService implements UserImportService {

  private final ObjectMapper mapper;

  public JsonUserImportService(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public List<User> getUsers(Reader reader) throws UserImportException {
    try {
      return mapper.readValue(reader, new TypeReference<List<User>>(){});
    } catch (IOException e) {
      throw new UserImportException("Unable to import users from json.", e);
    }
  }
}
