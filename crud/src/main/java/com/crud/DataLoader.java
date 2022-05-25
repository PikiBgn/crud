package com.crud;

import com.crud.domain.Author;
import com.crud.domain.Book;
import com.crud.repositories.AuthorRepository;
import com.crud.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DataLoader {

    private BookRepository bookRepo;
    private AuthorRepository authorRepo;

    @Autowired
    public DataLoader(BookRepository bookRepo, AuthorRepository authorRepo){
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
    }
    @PostConstruct
    private void loadData(){
        bookRepo.deleteAll();
        authorRepo.deleteAll();
        Author dv = new Author("Dan","Vega");
        authorRepo.save(dv);

        registerAuthors();

        registerBooks(dv);
        System.out.println();


    }

    private void registerBooks(Author author){
        Date yesterday = null;
        try {
            yesterday = new SimpleDateFormat("dd/MM/yyyy").parse("18/12/2022");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Book book = new Book("Ogniem i mieczem");
        book.setAuthor(author);
        book.setWhenAdded(yesterday);
        bookRepo.save(book);

    }
    private void registerAuthors(){

        Author hs = new Author("Henryk","Sienkiewicz");
        authorRepo.save(hs);
    }

}
