package com.zph.annotation.dao;

import com.zph.annotation.bean.Student;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDao extends BaseDao<Student> {

    public void save() {
        System.out.println("保存学生");
    }
}
