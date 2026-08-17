package com.wise.adoption_service.shared.scheduler.outbox;

import com.wise.adoption_service.adoption.messaging.ApplicationRevokedOutboxRelay;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationRevokedOutboxScheduler {

    private final ApplicationRevokedOutboxRelay relay;

    @Scheduled(fixedDelayString = "${app.outbox.application-revoked.publish.fixed-delay:1s}")
    @SchedulerLock(
            name = "application-revoked-outbox-publish",
            lockAtMostFor = "PT1M",
            lockAtLeastFor = "PT0.5S"
    )
    public void publishPending() {
        relay.publishPending();
    }

    @Scheduled(fixedDelayString = "${app.outbox.application-revoked.recovery.fixed-delay:5m}")
    @SchedulerLock(
            name = "application-revoked-outbox-recovery",
            lockAtMostFor = "PT10M"
    )
    public void recoverStuckEvents() {
        relay.recoverStuckEvents();
    }
}
