package com.robin.microservices.inventory_service;

import com.robin.microservices.inventory_service.model.InventoryModel;
import com.robin.microservices.inventory_service.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {


		@Autowired
		private InventoryRepository inventoryRepository;

		public static void main(String[] args) {
			SpringApplication.run(InventoryServiceApplication.class, args);
		}

		@Bean
		public CommandLineRunner loadData() {
			return args -> {
				// Check if data exists to prevent duplicate inserts
				if (inventoryRepository.count() == 0) {
					inventoryRepository.save(new InventoryModel("iphone_15", 200));
					inventoryRepository.save(new InventoryModel("galaxy_5", 150));
					inventoryRepository.save(new InventoryModel("iphone_12", 70));
					inventoryRepository.save(new InventoryModel("pixel_8", 250));
					inventoryRepository.save(new InventoryModel("oneplus_12", 50));
					System.out.println("Inventory data inserted successfully!");
				} else {
					System.out.println("Inventory data already exists. Skipping insertion.");
				}
			};
		}

}
