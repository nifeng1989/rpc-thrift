package net.fengni.thrift.demo.server;

import net.fengni.thrift.demo.core.Service;
import net.fengni.thrift.demo.core.Student;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by fengni on 2016/6/1.
 */
public class ServiceImpl implements Service.Iface {
    private static Logger logger = LoggerFactory.getLogger(ServiceImpl.class);
    public String put(Student s) throws TException {
        System.out.println("put|"+ s.toString());
        return s.getName();
    }
}
