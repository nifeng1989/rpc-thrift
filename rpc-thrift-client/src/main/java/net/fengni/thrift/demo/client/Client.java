package net.fengni.thrift.demo.client;

import net.fengni.thrift.demo.core.Service;
import net.fengni.thrift.demo.core.Student;
import net.fengni.util.zk.ZKUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * Created by fengni on 2016/6/1.
 */
public class Client {
    public static String SERVER_IP = "localhost";
    public static int SERVER_PORT = 8090;
    public static final int TIMEOUT = 30000;

    /**
     *
     * @param userName
     */
    public void startClient(String userName) {
        TTransport transport = null;
        try {
            String socket = new String(ZKUtil.getZK().getData("/net/fengni/thrift/demo/server/socket",true,null));
            System.out.println("socket=" + socket);
            String [] params = StringUtils.split(socket,":");
            transport = new TSocket(params[0], Integer.parseInt(params[1]), TIMEOUT);
            // 协议要和服务端一致
            TProtocol protocol = new TBinaryProtocol(transport);
            // TProtocol protocol = new TCompactProtocol(transport);
            // TProtocol protocol = new TJSONProtocol(transport);
            Service.Client client = new Service.Client(protocol);
            transport.open();
            Student student = new Student(1,userName,true,(short)25);
            String result = client.put(student);
            System.out.println("Thrify client result =: " + result);
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
        Client client = new Client();
        client.startClient("Michael");

    }
}
