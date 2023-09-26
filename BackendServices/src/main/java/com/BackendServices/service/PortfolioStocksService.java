import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PortfolioStocksService {
    private final PortfolioStocksRepository portfolioStocksRepository;

    @Autowired
    public PortfolioStocksService(PortfolioStocksRepository portfolioStocksRepository) {
        this.portfolioStocksRepository = portfolioStocksRepository;
    }

    public List<PortfolioStocks> getAllPortfolioStocks() {
        return portfolioStocksRepository.findAll();
    }

    public PortfolioStocks createPortfolioStocks(PortfolioStocks portfolioStocks) {
        return portfolioStocksRepository.save(portfolioStocks);
    }

    // Implement other portfolio stocks-related methods as needed
}
