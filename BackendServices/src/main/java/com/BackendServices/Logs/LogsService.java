package com.BackendServices.Logs;

import java.time.LocalDateTime;
import java.util.List;


import org.springframework.stereotype.Service;

@Service
public class LogsService {
    private final LogsRepository accessLogRepository;

    public LogsService(LogsRepository accessLogRepository) {
        this.accessLogRepository = accessLogRepository;
    }

    public List<Logs> getAllLogs() {
        return accessLogRepository.findAll();
    }

    public Logs createLogs(Logs accessLog) {
        return accessLogRepository.save(accessLog);
    }

    // Create a method to post a log to the database
    public Logs postLog(String userId, String action) {
        Logs logs = new Logs();
        logs.setUserId(userId);
        logs.setAction(action);
        logs.setTimestamp(LocalDateTime.now());
        return createLogs(logs);
    }

    // Implement other access log-related methods as needed
}
