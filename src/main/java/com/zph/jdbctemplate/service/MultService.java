package com.zph.jdbctemplate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;

@Service
public class MultService {

    @Autowired
    private BookService bookService;

    @Transactional
    public void mult(){
        try {
            bookService.buyBook();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("----------");
        bookService.updatePrice();
        int i = 1/0;
    }

//    @Transactional
    public void buyBook() {
        bookService.buyBook();
    }
}
