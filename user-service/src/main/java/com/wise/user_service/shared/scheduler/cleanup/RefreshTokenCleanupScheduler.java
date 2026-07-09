package com.wise.user_service.shared.scheduler.cleanup;

import com.wise.user_service.security.service.RefreshTokenCleanupService;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenCleanupScheduler {
    private final RefreshTokenCleanupService cleanupService;

    @Scheduled(cron = "${security.refresh-token.cleanup-cron}")
    @SchedulerLock(
            name = "refresh-token-cleanup",
            lockAtMostFor = "PT5M",
            lockAtLeastFor = "PT30S"
    )
    public void deleteExpiredTokens() {
        cleanupService.deleteExpiredTokens();
    }
}
