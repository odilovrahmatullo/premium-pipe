package premium_pipe.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import premium_pipe.util.HashMapConverter;
import premium_pipe.util.validator.JsonFieldConstraint;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "news")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true,columnDefinition = "TEXT")
    @Convert(converter = HashMapConverter.class)
    @JsonFieldConstraint
    private Map<String, String> title;

    @Convert(converter = HashMapConverter.class)
    @JsonFieldConstraint
    @Column(columnDefinition = "TEXT")
    private Map<String, String> description;

    @Column(name = "image")
    private String image;
    @Column(name = "slug",unique = true,nullable = false)
    private String slug;

    @Column(name = "view_count")
    @Builder.Default
    private Long viewCount = 0L;

    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;

}
