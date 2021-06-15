package com.ms.rediscacheboot;

import com.ms.rediscacheboot.entity.Product;
import com.ms.rediscacheboot.repository.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/product")
public class RediscachebootApplication {

	@Autowired
	public ProductDAO dao;

	@PostMapping
	public Product save(@RequestBody Product product){
		return dao.save(product);
	}

	@GetMapping("/")
	public List<Product> getProducts(){
		return dao.findAll();
	}

	@GetMapping("/{id}")
	@Cacheable(key = "#id", value = "Product", unless = "#result.price > 1000")
	public Product getProduct(@PathVariable int id){
		return dao.findProductById(id);
	}

	@DeleteMapping("/{id}")
	@CacheEvict(key = "#id", value = "Product")
	public String removeProduct(@PathVariable int id){
		return dao.deleteProduct(id);
	}

	public static void main(String[] args) {
		SpringApplication.run(RediscachebootApplication.class, args);
	}

}
