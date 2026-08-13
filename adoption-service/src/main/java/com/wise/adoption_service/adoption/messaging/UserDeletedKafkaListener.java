package com.wise.adoption_service.adoption.messaging;

import com.wise.adoption_service.adoption.service.DeleteApplicationsForDeletedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDeletedKafkaListener {

    private final DeleteApplicationsForDeletedUser deleteApplications;

    @KafkaListener(
            topics = "${app.kafka.topics.user-deleted}",
            groupId = "${app.kafka.groups.user-deletion}"
    )
    public void onUserDeleted(UserDeletedV1 event) {
        deleteApplications.deleteByApplicantId(event.userId());
    }
}
