package premium_pipe.util.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import premium_pipe.util.JsonFieldValidate;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = JsonFieldValidate.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonFieldConstraint {
    String message() default "This field is required";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}