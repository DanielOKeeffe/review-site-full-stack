package org.wecancodeit.reviewsitefullstack;

import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, Long> {

	void deleteTagById(Long tagId);

	Tag findByDescription(String tagName); //query your tags to see if they exist

}
