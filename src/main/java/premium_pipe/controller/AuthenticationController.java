package premium_pipe.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import premium_pipe.model.request.AuthenticationRequest;
import premium_pipe.model.response.AuthResponse;
import premium_pipe.service.AuthService;

@RequestMapping("/api/login")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody final AuthenticationRequest request){
        try{
            return ResponseEntity.ok(authService.login(request));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(
            HttpServletRequest request){
        try {
            ResponseEntity<?> obj = authService.refreshToken(request);
            return ResponseEntity.ok().body(obj);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
