package com.BackendServices.service;
import org.springframework.stereotype.Service;

import com.BackendServices.entity.MySession;
import com.BackendServices.repository.MySessionRepository;

import java.util.List;
@Service
public class MySessionService {
    private final MySessionRepository sessionRepository;


    public MySessionService(MySessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public List<MySession> getAllSessions() {
        return sessionRepository.findAll();
    }

    public MySession createSession(MySession session) {
        return sessionRepository.save(session);
    }

    // Implement other session-related methods as needed
}
