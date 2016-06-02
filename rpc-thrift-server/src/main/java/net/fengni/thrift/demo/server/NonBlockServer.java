package net.fengni.thrift.demo.server;

import net.fengni.thrift.demo.core.StudentService;
import net.fengni.thrift.demo.service.StudentServiceImpl;
import net.fengni.util.zk.ZKUtil;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

/**
 * Created by fengni on 2016/6/1.
 */
public class NonBlockServer {
    public static final int SERVER_PORT = 8090;

    public void startServer() {
        try {
            System.out.println("HelloWorld TSimpleServer start ....");

            TProcessor tprocessor = new StudentService.Processor<StudentService.Iface>(new StudentServiceImpl());
            ZooKeeper zk = ZKUtil.getZK();

            zk.create("/net/fengni/thrift/demo/server/socket", "127.0.0.1:8090".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(SERVER_PORT);
            TNonblockingServer.Args tArgs = new TNonblockingServer.Args(serverTransport);
            tArgs.processor(tprocessor);
            tArgs.transportFactory(new TFramedTransport.Factory());
            tArgs.protocolFactory(new TCompactProtocol.Factory());
            // tArgs.protocolFactory(new TCompactProtocol.Factory());
            //tArgs.protocolFactory(new TJSONProtocol.Factory());
            TServer server = new TNonblockingServer(tArgs);

            server.serve();

        } catch (Exception e) {
            System.out.println("SimpleServer start error!!!");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        NonBlockServer server = new NonBlockServer();
        server.startServer();
    }

}
