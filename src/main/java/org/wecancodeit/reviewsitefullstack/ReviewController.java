package org.wecancodeit.reviewsitefullstack;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReviewController {

	@Resource
	ReviewRepository reviewRepo;

	@Resource
	CategoryRepository categoryRepo;

	@Resource
	TagRepository tagRepo;

	@RequestMapping("/review")
	public String findOneReview(@RequestParam(value = "id") long reviewId, Model model) throws ReviewNotFoundException {
		Optional<Review> review = reviewRepo.findById(reviewId);
		if (review.isPresent()) {
			model.addAttribute("reviewsModel", review.get());
			return "reviewTemplate";
		}
		throw new ReviewNotFoundException();
	}

	@RequestMapping("/category")
	public String findOneCategory(@RequestParam(value = "id") long categoryId, Model model) throws CategoryNotFoundException {
		Optional<Category> category = categoryRepo.findById(categoryId);
		if (category.isPresent()) {
			model.addAttribute("categoriesModel", category.get());
			return "categoryTemplate";
		}
		throw new CategoryNotFoundException();
	}

	@RequestMapping("/tag")
	public String findOneTag(@RequestParam(value = "id") long tagId, Model model) throws TagNotFoundException {
		Optional<Tag> tag = tagRepo.findById(tagId);
		if (tag.isPresent()) {
			model.addAttribute("tagsModel", tag.get());
			return "tagTemplate";
		}
		throw new TagNotFoundException();
	}

	@RequestMapping("/show-reviews")
	public String findAllReviews(Model model) {
		model.addAttribute("reviewsModel", reviewRepo.findAll());
		return ("reviewsTemplate");
	}

	@RequestMapping("/show-categories")
	public String findAllCategories(Model model) {
		model.addAttribute("categoriesModel", categoryRepo.findAll());
		return ("categoriesTemplate");
	}

	@RequestMapping("/show-tags")
	public String findAllTags(Model model) {
		model.addAttribute("tagsModel", tagRepo.findAll());
		return ("tagsTemplate");
	}
	
}
