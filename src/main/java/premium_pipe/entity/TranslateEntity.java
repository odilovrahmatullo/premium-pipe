package premium_pipe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "translate")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TranslateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @ManyToOne
    @JoinColumn(name = "static_type")
    private StaticTypeEntity type;

    @Column(columnDefinition = "TEXT")
    private String keyword;
    @Column(name = "key_uz",columnDefinition = "TEXT")
    private String keyUz;
    @Column(name = "key_ru",columnDefinition = "TEXT")
    private String keyRu;
    @Column(name = "key_en",columnDefinition = "TEXT")
    private String keyEn;
}
