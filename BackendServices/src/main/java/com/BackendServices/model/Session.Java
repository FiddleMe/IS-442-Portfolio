import javax.persistence.*;

@Entity
@Table(name = "Session")
public class Session {
    @Id
    @Column(name = "SessionID", length = 36, nullable = false)
    private String sessionId;

    @Column(name = "UserID", length = 36, nullable = false)
    private String userId;

    @Column(name = "Login_Datetime", nullable = false)
    private LocalDateTime loginDatetime;

    @Column(name = "Logout_Datetime")
    private LocalDateTime logoutDatetime;

    // Getters and setters
}
