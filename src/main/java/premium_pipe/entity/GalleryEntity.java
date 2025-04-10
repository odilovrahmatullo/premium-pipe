package premium_pipe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import premium_pipe.enums.FileType;

import java.time.LocalDateTime;

@Entity
@Table(name = "gallery")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GalleryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "image")
    private String image;
    @Enumerated(EnumType.STRING)
    @Column(name = "file_type")
    private FileType fileType;
    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;
}
