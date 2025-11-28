package com.umitcoban.budgetmatebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.modulith.Modulithic;

@Modulithic(
		useFullyQualifiedModuleNames = false
)
@SpringBootApplication
public class BudgetmateBackendApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BudgetmateBackendApplication.class, args);
	}
	
}
