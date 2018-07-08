package org.wecancodeit.reviewsitefullstack;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class JpaMappingsTest {
	
	@Resource
	private TestEntityManager entityManager;
	
	@Resource
	private CategoryRepository categoryRepo;
	
	@Resource
	private TagRepository tagRepo;
	
	@Resource
	private ReviewRepository reviewRepo;
	
	@Test
	public void shouldSaveAndLoadCategory() {
		Category coffee = categoryRepo.save(new Category("coffee"));
		long coffeeId = coffee.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<Category> result = categoryRepo.findById(coffeeId);
		coffee = result.get();
		assertThat(coffee.getBeverage(), is("coffee"));
		assertThat(result, is(Optional.of(coffee)));
		assertTrue(result.isPresent());
	}
	
	@Test
	public void shouldGenerateCategoryId() {
		Category coffee = categoryRepo.save(new Category("coffee"));
		long coffeeId = coffee.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		assertThat(coffeeId, is(greaterThan(0L)));
	}
	
	@Test
	public void shouldSaveAndLoadATag() {
		Tag hot = tagRepo.save(new Tag("hot"));
		long hotId = hot.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<Tag> result = tagRepo.findById(hotId);
		assertThat(hot.getDescription(), is("hot"));
		assertTrue(result.isPresent());
	}
	
	@Test
	public void shouldGenerateTagId() {
		Tag hot = tagRepo.save(new Tag("hot"));
		long hotId = hot.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		assertThat(hotId, is(greaterThan(0L)));
	}
	
	@Test
	public void shouldSaveAndLoadAReview() {
		Category coffee = categoryRepo.save(new Category("coffee"));
		Review dunkin = reviewRepo.save(new Review("dunkin", "description", "", coffee));
		long dunkinId = dunkin.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<Review> result = reviewRepo.findById(dunkinId);
		assertThat(dunkin.getName(), is("dunkin"));
		assertThat(dunkin.getDescription(), is("description"));
		assertTrue(result.isPresent());
	}
	
	@Test
	public void shouldGenerateReviewId() {
		Category coffee = categoryRepo.save(new Category("coffee"));
		Review dunkin = reviewRepo.save(new Review("dunkin", "description", "", coffee));
		long dunkinId = dunkin.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		assertThat(dunkinId, is(greaterThan(0L)));
	}
	
	@Test
	public void shouldEstablishReviewToCategoryRelationship() {
		Category coffee = categoryRepo.save(new Category("coffee"));
		Review dunkin = reviewRepo.save(new Review("dunkin", "description", "", coffee));
		long dunkinId = dunkin.getId();
		
		entityManager.flush();
		entityManager.clear();

		Optional<Review> result = reviewRepo.findById(dunkinId);
		dunkin = result.get(); //this will drive generation of hash code and equals for entities
		assertThat(dunkin.getBeverage(), is(coffee));
	}
	
	@Test
	public void shouldEstablishCategoryToReviewRelationship() {
		Category coffee = categoryRepo.save(new Category("coffee"));
		Review dunkin = reviewRepo.save(new Review("dunkin", "description", "", coffee));
		Review starbucks = reviewRepo.save(new Review("starbucks", "description", "", coffee));
		long coffeeId = coffee.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<Category> result = categoryRepo.findById(coffeeId);
		coffee = result.get();
		assertThat(coffee.getReviews(), containsInAnyOrder(dunkin, starbucks));
	}
	
	@Test
	public void shouldEstablishReviewToTagRelationship() {
		Tag hot = tagRepo.save(new Tag("hot"));
		Tag cold = tagRepo.save(new Tag("cold"));
		Category coffee = categoryRepo.save(new Category("coffee"));
		Review dunkin = reviewRepo.save(new Review("dunkin", "description", "", coffee, hot, cold));
		long dunkinId = dunkin.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<Review> result = reviewRepo.findById(dunkinId);
		dunkin = result.get();
		assertThat(dunkin.getTags(), containsInAnyOrder(hot, cold));
	}
	
	@Test
	public void shouldEstablishTagToReviewRelationship() {
		Tag hot = tagRepo.save(new Tag("hot"));
		Tag cold = tagRepo.save(new Tag("cold"));
		Category coffee = categoryRepo.save(new Category("coffee"));
		Review dunkin = reviewRepo.save(new Review("dunkin", "description", "", coffee, hot, cold));
		Review starbucks = reviewRepo.save(new Review("starbucks", "description", "", coffee, hot, cold));
		long hotId = hot.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<Tag> result = tagRepo.findById(hotId);
		hot = result.get();
		assertThat(hot.getReviews(), containsInAnyOrder(dunkin, starbucks));
	}

}
