package net.fengni.thrift.demo.server;

import net.fengni.thrift.demo.core.StudentService;
import net.fengni.thrift.demo.service.StudentServiceImpl;
import net.fengni.util.zk.ZKUtil;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.Executors;

/**
 * Created by fengni on 2016/6/1.
 */
public class ThreadServer {
    public static final int SERVER_PORT = 8090;

    public void startServer() {
        try {
            System.out.println("HelloWorld TSimpleServer start ....");

            TProcessor tprocessor = new StudentService.Processor<StudentService.Iface>(new StudentServiceImpl());
            ZooKeeper zk = ZKUtil.getZK();

            zk.create("/net/fengni/thrift/demo/server/socket","127.0.0.1:8090".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            // 简单的单线程服务模型，一般用于测试
            TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
            TThreadPoolServer.Args tArgs = new TThreadPoolServer.Args(serverTransport);
            tArgs.executorService(Executors.newFixedThreadPool(10));
            tArgs.processor(tprocessor);
            //tArgs.protocolFactory(new TBinaryProtocol.Factory());
            // tArgs.protocolFactory(new TCompactProtocol.Factory());
             tArgs.protocolFactory(new TJSONProtocol.Factory());
            TServer server = new TThreadPoolServer(tArgs);

            //TServer server = new TSimpleServer(tArgs);

            server.serve();

        } catch (Exception e) {
            System.out.println("SimpleServer start error!!!");
            e.printStackTrace();
        }
    }
        public static void main(String[] args) {
            ThreadServer server = new ThreadServer();
            server.startServer();
        }

    }
