package com.umitcoban.budgetmatebackend.modules.userprofile.infrastructure.listener;

import com.umitcoban.budgetmatebackend.modules.auth.domain.event.UserRegisteredEvent;
import com.umitcoban.budgetmatebackend.modules.userprofile.application.UserProfileService;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
public class UserRegisteredEventListener {

    private final UserProfileService userProfileService;

    public UserRegisteredEventListener(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @ApplicationModuleListener
    public void on(UserRegisteredEvent event) {
        userProfileService.createProfile(event.email(), event.email());
    }
}
