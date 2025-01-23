package premium_pipe.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import premium_pipe.entity.AdminEntity;
import premium_pipe.exception.AuthBadException;
import premium_pipe.model.request.AuthenticationRequest;
import premium_pipe.model.response.AuthResponse;
import premium_pipe.repository.AdminRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AdminRepository adminRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public AuthResponse login(AuthenticationRequest request) {
        AdminEntity admin = getAdminByUsername(request.username());
        if(!bCryptPasswordEncoder.matches(request.password(),admin.getPassword())){
            throw new AuthBadException("username or password wrong");
        }
        String accessToken = jwtService.generateAccessToken(admin.getUsername(),"ROLE_ADMIN");
        String refreshToken = jwtService.generateRefreshToken(admin.getUsername(),"ROLE_ADMIN");
        return new AuthResponse(accessToken,refreshToken);
    }

    public ResponseEntity<?> refreshToken(HttpServletRequest request){
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(header == null || !header.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token =header.substring(7).trim();
        String username = jwtService.decode(token).username();
        if(username==null){
            throw new UsernameNotFoundException("Admin not found");
        }
        AdminEntity admin = getAdminByUsername(username);

        if(jwtService.isValidRefreshToken(token,admin)){
            String accessToken = jwtService.generateAccessToken(admin.getUsername(),"ROLE_ADMIN");
            String refreshToken = jwtService.generateRefreshToken(admin.getUsername(),"ROLE_ADMIN");
            return new ResponseEntity<>(new AuthResponse(accessToken, refreshToken), HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public AdminEntity getAdminByUsername(String username){
        return adminRepository.getByUsername(username).orElseThrow(
                ()-> new AuthBadException("username or password wrong"));
    }
}
