package com.crud.services;

import com.crud.Exceptions.BookNotFoundException;
import com.crud.domain.Book;
import com.crud.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Service
@RequestMapping("books")
public class BookService {
    private BookRepository bookRepo;

    @Autowired
    public BookService(BookRepository bookRepo){this.bookRepo = bookRepo;}

    public List<Book> list(){ return bookRepo.findAllByOrderByWhenAddedDesc();}

    public Book save(Book book) {
       return bookRepo.save(book);
    }
    public Book get(Long id) throws BookNotFoundException{
        Optional<Book> result = bookRepo.findById(id);
        if (result.isPresent()){
            return result.get();
        }
        throw new BookNotFoundException("Could not find any books!");
    }
    public void delete(Long id) throws BookNotFoundException{
        Long count = bookRepo.countById(id);
        if(count == null || count == 0){
            throw new BookNotFoundException("Could not find any books!");
        }
        bookRepo.deleteById(id);

    }
}
