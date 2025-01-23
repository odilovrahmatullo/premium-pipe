package premium_pipe.util;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import premium_pipe.util.validator.JsonFieldConstraint;

import java.util.Map;

public class JsonFieldValidate
    implements ConstraintValidator<JsonFieldConstraint, Map<String, String>> {
  @Override
  public boolean isValid(Map map, ConstraintValidatorContext constraintValidatorContext) {
    String defaultLang = "uz";

    if (map == null) return true;

    Object value = map.get(defaultLang);

    return value != null && !value.equals("");
  }
}
