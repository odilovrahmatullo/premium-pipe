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
@Table(name = "category")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(nullable = false, unique = true)
        @Convert(converter = HashMapConverter.class)
        @JsonFieldConstraint
        private Map<String, String> name;
        @Convert(converter = HashMapConverter.class)
        @JsonFieldConstraint
        private Map<String, String> description;
        private String image;
        private String slug;
        @CreationTimestamp
        @Column(name = "created_date")
        private LocalDateTime createdDate;
        @Column(name = "deleted_date")
        private LocalDateTime deletedDate;
}
