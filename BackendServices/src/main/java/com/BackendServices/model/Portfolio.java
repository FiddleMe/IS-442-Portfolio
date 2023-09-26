import javax.persistence.*;

@Entity
@Table(name = "Portfolio")
public class Portfolio {
    @Id
    @Column(name = "PortfolioID", length = 36, nullable = false)
    private String portfolioId;

    @Column(name = "Name")
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "Capital_Amount")
    private BigDecimal capitalAmount;

    @Column(name = "UserID", length = 36)
    private String userId;

    @Column(name = "StockID", length = 36)
    private String stockId;

    @Column(name = "DateTime")
    private LocalDateTime dateTime;

    // Getters and setters
}
