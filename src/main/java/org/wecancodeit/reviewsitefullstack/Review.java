package org.wecancodeit.reviewsitefullstack;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Review {

	@Id
	@GeneratedValue
	private long id;

	private String name;
	private String description;
	private String image;

	@ManyToOne // EntityToDatatype
	private Category beverage;

	@ManyToMany
	private Collection<Tag> tags;

	@OneToMany(mappedBy = "review")
	private Collection<Comment> comments;

	public Review(String name, String description, String image, Category beverage, Tag... tags) {
		this.name = name;
		this.description = description;
		this.image = image;
		this.beverage = beverage;
		this.tags = new HashSet<>(Arrays.asList(tags));
	}

	public Review() {
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getImage() {
		return image;
	}

	public Category getBeverage() {
		return beverage;
	}

	public Collection<Tag> getTags() {
		return tags;
	}

	public Collection<Comment> getComments() {
		return comments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Review other = (Review) obj;
		if (id != other.id)
			return false;
		return true;
	}

	//allowing the collection to add this tag we created in our form
	public void addTag(Tag newTag) {
		tags.add(newTag);
	}

	//allowing the collection to have a tag removed
	public void removeTag(Tag tagToRemove) {
		tags.remove(tagToRemove);
	}

}
