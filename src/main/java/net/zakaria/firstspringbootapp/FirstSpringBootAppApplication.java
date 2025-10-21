package net.zakaria.firstspringbootapp;

import net.zakaria.firstspringbootapp.entities.Product;
import net.zakaria.firstspringbootapp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class FirstSpringBootAppApplication implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(FirstSpringBootAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // productRepository.save(new Product(null, "Computer", 6500, 12));
        // productRepository.save(new Product(null, "Printer", 1200, 15));
        // productRepository.save(new Product(null, "Smartphone", 1400, 20));

        List<Product> products = productRepository.findAll();
        products.forEach(p -> {
            System.out.println(p.toString());
        });

        Product product = productRepository.findById(Long.valueOf(1)).get();
        System.out.println("****************");
        System.out.println(product.getId());
        System.out.println(product.getName());
        System.out.println(product.getPrice());
        System.out.println(product.getQuantity());
        System.out.println("****************");

        System.out.println("------------------------");

        List<Product> productList = productRepository.findByNameContains("C");
        productList.forEach(p -> {
            System.out.println(p);
        });

        System.out.println("------------------------");

        List<Product> productList2 = productRepository.search("%S%");
        productList2.forEach(p -> {
            System.out.println(p);
        });

        System.out.println("------------------------");

        List<Product> productList3 = productRepository.searchByPrice(1200);
        productList3.forEach(p -> {
            System.out.println(p);
        });
    }
}
