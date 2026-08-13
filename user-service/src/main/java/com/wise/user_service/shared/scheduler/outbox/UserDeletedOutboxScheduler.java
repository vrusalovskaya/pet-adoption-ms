package com.wise.user_service.shared.scheduler.outbox;

import com.wise.user_service.user.service.UserDeletedOutboxRelay;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDeletedOutboxScheduler {

    private final UserDeletedOutboxRelay relay;

    @Scheduled(fixedDelayString = "${app.outbox.user-deleted.publish.fixed-delay:1s}")
    @SchedulerLock(
            name = "user-deleted-outbox-publish",
            lockAtMostFor = "PT1M",
            lockAtLeastFor = "PT0.5S"
    )
    public void publishPending() {
        relay.publishPending();
    }

    @Scheduled(fixedDelayString = "${app.outbox.user-deleted.recovery.fixed-delay:5m}")
    @SchedulerLock(
            name = "user-deleted-outbox-recovery",
            lockAtMostFor = "PT10M"
    )
    public void recoverStuckEvents() {
        relay.recoverStuckEvents();
    }
}
