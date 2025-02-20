package premium_pipe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import premium_pipe.util.HashMapConverter;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "banner")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BannerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = HashMapConverter.class)
    @Column(columnDefinition = "TEXT")
    private Map<String, String> title;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = HashMapConverter.class)
    private Map<String, String> description;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = HashMapConverter.class)
    private Map<String, String> success;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = HashMapConverter.class)
    private Map<String, String> experience;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = HashMapConverter.class)
    private Map<String, String> projects;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = HashMapConverter.class)
    private Map<String, String> customers;

    @Column(name = "image")
    private String image;

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
