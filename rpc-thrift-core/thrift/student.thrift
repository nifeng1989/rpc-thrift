namespace java net.fengni.thrift.demo.core

struct Student{
    1:i32 sid,
    2:string name,
    3:bool sex,
    4:i16 age
}
service StudentService{
    i32 addStudent(1:Student s),
    Student getStudent(1:i32 sid),
    i32 updateStudent(1:Student s),
    i32 deleteStudentById(1:i32 sid),
    i32 deleteStudent(1:Student s)
}