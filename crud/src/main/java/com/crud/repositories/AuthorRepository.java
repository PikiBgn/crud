package com.crud.repositories;

import com.crud.domain.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends CrudRepository<Author,Long> {

    List<Author> findAllByOrderByLastNameAscFirstNameAsc();

    public Long countById(Long id);
}
