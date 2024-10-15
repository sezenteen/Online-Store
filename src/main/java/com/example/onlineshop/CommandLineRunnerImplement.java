package com.example.onlineshop;

import com.example.onlineshop.model.Customer;
import com.example.onlineshop.service.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

public class CommandLineRunnerImplement implements CommandLineRunner {
    private final CustomerService customerService;

    public CommandLineRunnerImplement(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void run(String... args) throws Exception {
//        List<Customer> customers = customerService.searchByName("Бат");
        List<Customer> customers = customerService.findByCustomerAddress("Говь-Алтай");

        for (Customer customer: customers) {
            System.out.println("Нэр: " + customer.getName() + "\nУтас: " + customer.getPhone() + "\nХаяг: " + customer.getAddress());
        }
    }
}
