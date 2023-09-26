import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ResetTokenService {
    private final ResetTokenRepository resetTokenRepository;

    @Autowired
    public ResetTokenService(ResetTokenRepository resetTokenRepository) {
        this.resetTokenRepository = resetTokenRepository;
    }

    public List<ResetToken> getAllResetTokens() {
        return resetTokenRepository.findAll();
    }

    public ResetToken createResetToken(ResetToken resetToken) {
        return resetTokenRepository.save(resetToken);
    }

    // Implement other reset token-related methods as needed
}
