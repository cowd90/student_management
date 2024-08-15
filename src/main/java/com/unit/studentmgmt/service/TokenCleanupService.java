package com.unit.studentmgmt.service;

import com.unit.studentmgmt.repository.InvalidatedTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenCleanupService {
    private final InvalidatedTokenRepository repository;

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void deleteExpiredTokens() {
        repository.deleteAll();
    }

}
