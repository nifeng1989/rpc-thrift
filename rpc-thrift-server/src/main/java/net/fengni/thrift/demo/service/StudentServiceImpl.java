package net.fengni.thrift.demo.service;

import net.fengni.thrift.demo.core.Student;
import net.fengni.thrift.demo.core.StudentService;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by fengni on 2016/6/1.
 */
public class StudentServiceImpl implements StudentService.Iface {
    private static Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private AtomicInteger id = new AtomicInteger(1);
    public int addStudent(Student s) throws TException {
        s.setSid(id.getAndIncrement());
        System.out.println(s.toString());
        return s.getSid();
    }

    public Student getStudent(int sid) throws TException {
        Student student = new Student(sid,"zhansan",true,(short)12);
        return student;
    }

    public int updateStudent(Student s) throws TException {
        System.out.println(s);
        return 1;
    }

    public int deleteStudentById(int sid) throws TException {
        System.out.println(sid);
        return 1;
    }

    public int deleteStudent(Student s) throws TException {
        System.out.println(s);
        return 1;
    }
}
