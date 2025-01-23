package premium_pipe.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
@Entity
@Table(name = "admin")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AdminEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    @NotEmpty
    private String username;

    @NotNull @NotEmpty private String password;

    @NotNull private Boolean isActive;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = "ROLE_ADMIN";
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return getIsActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return getIsActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return getIsActive();
    }

    @Override
    public boolean isEnabled() {
        return getIsActive();
    }
}
