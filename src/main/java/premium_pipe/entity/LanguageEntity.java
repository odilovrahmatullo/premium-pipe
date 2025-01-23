package premium_pipe.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "language")
public class LanguageEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    @Column(unique = true)
    private String code;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_default")
    private Boolean isDefault;

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

}