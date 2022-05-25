package com.crud.services;

import com.crud.Exceptions.BookNotFoundException;
import com.crud.domain.Author;
import com.crud.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Service
@RequestMapping("authors")
public class AuthorService {

    private AuthorRepository authorRepo;

    @Autowired
    public AuthorService(AuthorRepository authorRepo){this.authorRepo = authorRepo;}

    public List<Author> list(){ return authorRepo.findAllByOrderByLastNameAscFirstNameAsc();}

    public Author save(Author author) {
        return authorRepo.save(author);
    }

    public Author get(Long id) throws BookNotFoundException {
        Optional<Author> result = authorRepo.findById(id);
        if (result.isPresent()){
            return result.get();
        }
        throw new BookNotFoundException("Could not find any authors!");
    }

    public void delete(Long id) throws BookNotFoundException{
        Long count = authorRepo.countById(id);
        if(count == null || count == 0){
            throw new BookNotFoundException("Could not find any authors!");
        }
        authorRepo.deleteById(id);

    }
}
