import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioStocksRepository extends JpaRepository<PortfolioStocks, String> {
    // You can add custom queries or methods here if needed
}
