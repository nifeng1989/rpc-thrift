package net.fengni.thrift.demo.client;

import net.fengni.thrift.demo.core.Student;
import net.fengni.thrift.demo.core.StudentService;
import net.fengni.util.zk.ZKUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.*;

import java.util.concurrent.CountDownLatch;

/**
 * Created by fengni on 2016/6/1.
 */
public class ASyncClient {
    public static final int TIMEOUT = 30000;

    /**
     *
     * @param userName
     */
    public void startClient(String userName) {
        TNonblockingTransport transport = null;
        try {
            String socket = new String(ZKUtil.getZK().getData("/net/fengni/thrift/demo/server/socket",true,null));
            System.out.println("socket=" + socket);
            String [] params = StringUtils.split(socket,":");
            transport = new TNonblockingSocket(params[0], Integer.parseInt(params[1]), TIMEOUT);
            // 协议要和服务端一致
            TAsyncClientManager asyncClientManager = new TAsyncClientManager();
            TCompactProtocol.Factory protocolFactory = new TCompactProtocol.Factory();
            // TProtocol protocol = new TCompactProtocol(transport);
            //TProtocol protocol = new TJSONProtocol(transport);
            StudentService.AsyncClient client = new StudentService.AsyncClient(protocolFactory,asyncClientManager,transport);
            //transport.open();
            Student student = new Student(1,userName,true,(short)25);
            client.addStudent(student, new AsyncMethodCallback<Object>() {
                public void onComplete(Object o) {
                    System.out.println("complete"+o);
                }

                public void onError(Exception e) {
                    System.out.println("error");
                }
            });
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        } finally{
            if (null != transport) {
                transport.close();
            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        ASyncClient client = new ASyncClient();
        client.startClient("Michael");

    }
}
