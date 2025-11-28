
@ApplicationModule(
		displayName = "Expenses",
		allowedDependencies = {
				"modules.userprofile",
				"modules.income",
				"modules.rulesengine"
		}
)
package com.umitcoban.budgetmatebackend.modules.expenses;

import org.springframework.modulith.ApplicationModule;