package com.zph.annotation.service;

import com.zph.annotation.dao.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService2 extends PersonService{

    @Autowired
    private PersonDao personDao;

    public void save(){
        System.out.println("personservice2...........");
        personDao.save();
    }

}
