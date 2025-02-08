package premium_pipe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "partner")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PartnerEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String image;
    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;
}

