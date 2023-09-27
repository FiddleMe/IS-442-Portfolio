package com.BackendServices.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BackendServices.model.MySession;
import com.BackendServices.repository.MySessionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MySessionService {
    private final MySessionRepository sessionRepository;

    @Autowired
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
