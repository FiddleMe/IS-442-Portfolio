import org.springframework.data.jpa.repository.JpaRepository;

public interface ResetTokenRepository extends JpaRepository<ResetToken, String> {
    // You can add custom queries or methods here if needed
}
