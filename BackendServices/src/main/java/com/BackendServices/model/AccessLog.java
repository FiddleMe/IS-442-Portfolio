import javax.persistence.*;

@Entity
@Table(name = "AccessLog")
public class AccessLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LogID", nullable = false)
    private Long logId;

    @Column(name = "UserID", length = 36, nullable = false)
    private String userId;

    @Column(name = "Action", length = 50)
    private String action;

    @Column(name = "Timestamp")
    private LocalDateTime timestamp;

    // Getters and setters
}

