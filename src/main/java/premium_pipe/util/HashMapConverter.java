package premium_pipe.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HashMapConverter implements AttributeConverter<Map<String, String>, String> {
  ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String convertToDatabaseColumn(Map<String, String> stringObjectMap) {
    String value;

    if (stringObjectMap == null) return "";

    try {
      value = objectMapper.writeValueAsString(stringObjectMap);
    } catch (final JsonProcessingException e) {
      value = "";
    }

    return value;
  }

  @Override
  public Map<String, String> convertToEntityAttribute(String s) {
    Map<String, String> customerInfo;

    if (s == null) return null;

    try {
      customerInfo = objectMapper.readValue(s, new TypeReference<HashMap<String, String>>() {});
    } catch (final IOException e) {
      customerInfo = null;
    }
    return customerInfo;
  }
}
