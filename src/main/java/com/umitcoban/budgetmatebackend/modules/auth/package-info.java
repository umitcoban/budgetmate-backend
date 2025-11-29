
@ApplicationModule(
		displayName = "Auth",
        allowedDependencies = {
                "modules.userprofile",
                "config"
        }
)
package com.umitcoban.budgetmatebackend.modules.auth;

import org.springframework.modulith.ApplicationModule;