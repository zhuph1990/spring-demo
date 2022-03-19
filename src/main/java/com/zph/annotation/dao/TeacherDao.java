package com.zph.annotation.dao;

import com.zph.annotation.bean.Teacher;
import org.springframework.stereotype.Repository;

@Repository
public class TeacherDao extends BaseDao<Teacher>{
    public void save() {
        System.out.println("保存老师");
    }
}
