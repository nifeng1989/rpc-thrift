package net.fengni.util.zk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * Created by fengni on 2016/6/1.
 */
public class ZKUtil {
    public static ZooKeeper getZK(){
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper("123.56.85.14:2181", 5000,new Watcher() {
                // 监控所有被触发的事件
                public void process(WatchedEvent event) {
                    System.out.println(event.getPath());
                    System.out.println(event.getType().name());
                    //System.out.println(event.getState().getIntValue());
                }
            });

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return zk;
    }

}
