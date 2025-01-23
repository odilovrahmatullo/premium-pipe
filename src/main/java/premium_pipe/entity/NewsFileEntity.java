package premium_pipe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "news_file")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewsFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news")
    private NewsEntity news;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file")
    private FileEntity file;
    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;
    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;
}
