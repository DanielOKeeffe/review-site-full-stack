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
		Category beer = new Category("Beer");
		Category coffee = new Category("Coffee");
		Category tea = new Category("Tea");
		Category wine = new Category("Wine");
		categoryRepo.save(beer);
		categoryRepo.save(coffee);
		categoryRepo.save(tea);
		categoryRepo.save(wine);
		
		Tag alcoholic = new Tag("Alcoholic");
		Tag cold = new Tag("Cold");
		Tag dark = new Tag("Dark");
		Tag green = new Tag("Green");
		Tag hot = new Tag("Hot");
		Tag nonAlcoholic = new Tag("Non-Alcoholic");
		Tag red = new Tag("Red");
		Tag white = new Tag("White");
		tagRepo.save(alcoholic);
		tagRepo.save(cold);
		tagRepo.save(dark);
		tagRepo.save(green);
		tagRepo.save(hot);
		tagRepo.save(nonAlcoholic);
		tagRepo.save(red);
		tagRepo.save(white);
		
		Review coorsLight = new Review("Coors Light Beer", "Light and crisp", "./static/images/", beer, alcoholic, cold);
		Review guinness = new Review("Guinness Beer", "Dark and beautiful", "./static/images/guinness.jpg", beer, alcoholic, cold, dark);
		reviewRepo.save(coorsLight);
		reviewRepo.save(guinness);
		
		Review dunkin = new Review("Dunkin Donuts Coffee", "Is forever", "./static/images/coffee.jpg", coffee, hot, cold, nonAlcoholic);
		Review starbucks = new Review("Starbucks Coffee", "Wonderful", "./static/images/coffee.jpg", coffee, hot, cold, nonAlcoholic);
		reviewRepo.save(dunkin);
		reviewRepo.save(starbucks);
		
		Review matchaTea = new Review("Matcha Tea", "Refreshing", "./static/images/tea.jpg", tea, cold, green, hot, nonAlcoholic);
		Review peppermintTea = new Review("Peppermint Tea", "Delicious", "./static/images/tea.jpg", tea, cold, hot, nonAlcoholic);
		reviewRepo.save(matchaTea);
		reviewRepo.save(peppermintTea);
		
		Review redWine = new Review("Red Wine", "Is for long days", "./static/images/wine.jpg", wine, alcoholic, cold, red);
		Review whiteWine = new Review("White Wine", "Relaxing", "./static/images/wine.jpg", wine, alcoholic, cold, white);
		reviewRepo.save(redWine);
		reviewRepo.save(whiteWine);
	}

}
