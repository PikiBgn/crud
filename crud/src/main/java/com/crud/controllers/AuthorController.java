package com.crud.controllers;

import com.crud.Exceptions.BookNotFoundException;
import com.crud.domain.Author;
import com.crud.domain.Book;
import com.crud.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AuthorController {

    private AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }
    @GetMapping("/authors")
    public String showAuthorList(Model model){
        List<Author> listAuthors = authorService.list();
        model.addAttribute("listAuthors",authorService.list());
        return "Authors/list";
    }

    @GetMapping("/authors/new")
    public  String showNewForm(Model model){
        model.addAttribute("author", new Author());
        model.addAttribute("pageTitle","Add New Author");
        return "Authors/author_form";
    }

    @PostMapping("/authors/save")
    public String save(Author author, RedirectAttributes ra){
        authorService.save(author);
        ra.addFlashAttribute("message","The author has been added succesfully!");
        return "redirect:/authors";
    }

    @RequestMapping("/authors/edit/{id}")
    public String showEditForm(@PathVariable() Long id, Model model, RedirectAttributes ra){

        try{
            model.addAttribute("author", authorService.get(id));
            model.addAttribute("pageTitle","Edit Author (ID: "+ id +")");
            return "Authors/author_form";
        }
        catch (BookNotFoundException e){
            ra.addFlashAttribute("message",e.getMessage());
            return "redirect:/authors";
        }
    }

    @RequestMapping("/authors/delete/{id}")
    public String deleteAuthor(@PathVariable Long id, RedirectAttributes ra){

        try{
            authorService.delete(id);
        }
        catch (BookNotFoundException e){
            ra.addFlashAttribute("message",e.getMessage());
        }
        return "redirect:/authors";
    }

}
