package com.zph.annotation.controller;

import com.zph.annotation.bean.Teacher;
import com.zph.annotation.dao.BaseDao;
import com.zph.annotation.dao.StudentDao;
import com.zph.annotation.dao.TeacherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {

    @Autowired
    TeacherDao teacherDao;

    @Autowired
    StudentDao studentDao;

    @Autowired
    BaseDao<Teacher> baseDao;

//    public void save(){
//        teacherDao.save();
//    }
//
//    public void save2(){
//        studentDao.save();
//    }

    public void save() {
        baseDao.save();
    }

}
