package com.crud.controllers;

import com.crud.Exceptions.BookNotFoundException;
import com.crud.domain.Book;
import com.crud.services.AuthorService;
import com.crud.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BookController {

    private BookService bookService;
    private AuthorService authorService;

    @Autowired
    public BookController(BookService bookService,AuthorService authorService){
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/books")
    public String showBookList(Model model){
        List<Book> listBooks = bookService.list();
        model.addAttribute("listBooks",bookService.list());
        return "Books/list";
    }
    @GetMapping("/books/new")
    public  String showNewForm(Model model){
        model.addAttribute("book", new Book());
        model.addAttribute("pageTitle","Add New Book");
        model.addAttribute("authors", authorService.list());
        return "Books/book_form";
    }
    @PostMapping("/books/save")
    public String save(Book book, RedirectAttributes ra){
        bookService.save(book);
        ra.addFlashAttribute("message","The book has been added succesfully!");
        return "redirect:/books";
    }
    @RequestMapping ("/books/edit/{id}")
    public String showEditForm(@PathVariable() Long id, Model model, RedirectAttributes ra){

        try{
            model.addAttribute("book", bookService.get(id));
            model.addAttribute("authors",authorService.list());
            model.addAttribute("pageTitle","Edit Book (ID: "+ id +")");
            return "Books/book_form";
        }
        catch (BookNotFoundException e){
            ra.addFlashAttribute("message",e.getMessage());
            return "redirect:/books";
        }
    }
    @RequestMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable Long id, RedirectAttributes ra){

        try{
            bookService.delete(id);
        }
        catch (BookNotFoundException e){
            ra.addFlashAttribute("message",e.getMessage());
        }
        return "redirect:/books";
    }

}
