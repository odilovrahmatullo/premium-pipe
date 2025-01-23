package premium_pipe.model.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserContactRequest {
    @NotNull(message = "Enter name ")
    private String name;
    @Pattern(regexp = "^\\+998\\d{9}$", message = "Phone number must start with +998")
    @Size(min = 13, max = 13, message = "Phone number must be exactly 13 characters")
    @NotBlank(message = "Enter phone number")
    private String phoneNumber;
    @Email
    private String email;
    private String message;

}
