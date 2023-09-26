import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<MySession, String> {
    // You can add custom queries or methods here if needed
}
