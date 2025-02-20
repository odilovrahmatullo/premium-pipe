package premium_pipe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import premium_pipe.util.HashMapConverter;
import premium_pipe.util.validator.JsonFieldConstraint;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "home")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = HashMapConverter.class)
    private Map<String, String> title;

    @Convert(converter = HashMapConverter.class)
    @Column(columnDefinition = "TEXT")
    private Map<String, String> description;

    @Column(name = "description_2", columnDefinition = "TEXT")
    @Convert(converter = HashMapConverter.class)
    private Map<String, String> description2;

    @Convert(converter = HashMapConverter.class)
    @Column(name = "opened_date",columnDefinition = "TEXT")
    private Map<String, String> openedDate;

    @Convert(converter = HashMapConverter.class)
    @Column(name = "employees_count",columnDefinition = "TEXT")
    private Map<String, String> numberOfEmployees;

    @Column(name = "neighbor_countries",columnDefinition = "TEXT")
    @Convert(converter = HashMapConverter.class)
    private Map<String, String> neighborCountries;

    @Column(name = "image")
    private String image;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

}
