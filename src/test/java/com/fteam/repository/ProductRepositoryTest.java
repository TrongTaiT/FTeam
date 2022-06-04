package com.fteam.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.fteam.model.Product;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductRepositoryTest {

	@Autowired
	private ProductRepository repo;

	@Test
	public void getTop8ByDayOfManufacture() {
		List<Product> result = repo.listTop8ByDateOfManufacture();
		System.out.println(result);

		assertThat(result.size()).isGreaterThan(0);
	}

	@Test
	public void getTop8ByRandom() {
		List<Product> result = repo.listTop8ByRandom();
		result.forEach(System.out::println);

		assertThat(result.size()).isGreaterThan(0);
	}
	
	@Test
	public void listProductByCategory() {
//		Page<Product> page = 
	}
}
