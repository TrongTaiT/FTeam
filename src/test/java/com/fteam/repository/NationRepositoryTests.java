package com.fteam.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.fteam.model.Nation;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class NationRepositoryTests {

	@Autowired
	private NationRepository repo;

	@Test
	public void testCreateNation() {
		Nation nationVietNam = new Nation("Việt Nam");

		Nation savedNation = repo.save(nationVietNam);

		assertThat(savedNation.getId()).isGreaterThan(0);
	}

	@Test
	public void testUpdateNation() {
		Nation nation = new Nation(1, "Việt Nam");
		String name = "Ả Rập Xê Út";
		nation.setName(name);

		Nation updatedNation = repo.save(nation);

		assertThat(updatedNation.getName()).isEqualTo(name);
	}

	@Test
	public void testGetNation() {
		Nation nation = repo.findById(1).get();
		
		assertThat(nation).isNotNull();
	}
	
	@Test
	public void testFindAll() {
		List<Nation> listNation = repo.findAll();
		
		assertThat(listNation.size()).isGreaterThan(0);
	}
	
	@Test
	public void testDeleteNation() {
		Integer id = 1;
		repo.deleteById(id);
		
		Optional<Nation> result = repo.findById(id);
		
		assertThat(result.isEmpty());
	}
}
