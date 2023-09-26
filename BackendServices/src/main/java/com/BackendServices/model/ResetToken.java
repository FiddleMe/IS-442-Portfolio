import javax.persistence.*;

@Entity
@Table(name = "ResetToken")
public class ResetToken {
    @Id
    @Column(name = "TokenID", length = 36, nullable = false)
    private String tokenId;

    @Column(name = "UserID", length = 36, nullable = false)
    private String userId;

    @Column(name = "Expiry_Datetime", nullable = false)
    private LocalDateTime expiryDatetime;

    // Getters and setters
}
