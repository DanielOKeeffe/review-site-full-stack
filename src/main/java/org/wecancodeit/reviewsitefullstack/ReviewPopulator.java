package org.wecancodeit.reviewsitefullstack;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ReviewPopulator implements CommandLineRunner {
	
	private Logger log = LoggerFactory.getLogger(ReviewPopulator.class);
	
	@Resource
	private ReviewRepository reviewRepo;
	
	@Resource
	private CategoryRepository categoryRepo;
	
	@Resource
	private TagRepository tagRepo;

	@Override
	public void run(String... args) throws Exception {
		Category coffee = new Category("coffee");
		categoryRepo.save(coffee);
		
		Tag hot = new Tag("hot");
		Tag cold = new Tag("cold");
		tagRepo.save(hot);
		tagRepo.save(cold);
		
		Review dunkin = new Review("Dunkin", "Delicious", "/static/images/coffee.jpg", coffee, hot, cold);
		reviewRepo.save(dunkin);
		
	}

}
