import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AccessLogService {
    private final AccessLogRepository accessLogRepository;

    @Autowired
    public AccessLogService(AccessLogRepository accessLogRepository) {
        this.accessLogRepository = accessLogRepository;
    }

    public List<AccessLog> getAllAccessLogs() {
        return accessLogRepository.findAll();
    }

    public AccessLog createAccessLog(AccessLog accessLog) {
        return accessLogRepository.save(accessLog);
    }

    // Implement other access log-related methods as needed
}
