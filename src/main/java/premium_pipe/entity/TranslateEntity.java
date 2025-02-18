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

    private String keyword;
    @Column(name = "key_uz")
    private String keyUz;
    @Column(name = "key_ru")
    private String keyRu;
    @Column(name = "key_en")
    private String keyEn;
}
