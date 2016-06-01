namespace java net.fengni.thrift.demo.core

struct Student{
    1:i32 sid,
    2:string name,
    3:bool sex,
    4:i16 age
}
service Service{
    string put(1:Student s)
}