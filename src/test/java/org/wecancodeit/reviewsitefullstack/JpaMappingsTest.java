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
	
	@Resource
	private CommentRepository commentRepo;
	
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
	
	@Test
	public void shouldHaveTwoCommentsOnOneReview() {
		Category coffee = new Category("Coffee");
		Tag hot = new Tag("Hot");
		coffee = categoryRepo.save(coffee);
		hot = tagRepo.save(hot);
		Review review = new Review("Test Review", "Stuff about review", "", coffee, hot);
		review = reviewRepo.save(review);
		long reviewId = review.getId();
		
		Comment testComment1 = new Comment("Author", review, "Comment1");
		testComment1 = commentRepo.save(testComment1);
		long testComment1Id = testComment1.getId();
		
		Comment testComment2 = new Comment("Author", review, "Comment2");
		testComment2= commentRepo.save(testComment2);
		long testComment2Id = testComment2.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		Iterable<Comment> comments = commentRepo.findAll();
		assertThat(comments, containsInAnyOrder(testComment1, testComment2));
		
		Optional<Comment> testComment1Result = commentRepo.findById(testComment1Id);
		testComment1 = testComment1Result.get();
		
		Optional<Comment> testComment2Result = commentRepo.findById(testComment2Id);
		testComment2 = testComment2Result.get();
		
		Optional<Review> reviewResult = reviewRepo.findById(reviewId);
		review = reviewResult.get();
		
		assertThat(testComment1.getAuthor(), is("Author"));
		assertThat(testComment2.getAuthor(), is("Author"));
		assertThat(testComment1.getReview(), is(review));
		assertThat(testComment2.getReview(), is(review));
		assertThat(review.getComments(), containsInAnyOrder(testComment1, testComment2));
	}

}
