package thread.aqs;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * description: 读写锁缓存的实现（ReentrantReadWriteLock示例）
 *
 * @author RenShiWei
 * Date: 2021/10/16 17:30
 * blog: https://duktig.cn/
 * github知识库: https://github.com/duktig666/knowledge
 **/
public class ReadWriteLockCache {

    static Map<String, Object> map = new HashMap<String, Object>();
    static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    static Lock r = rwl.readLock();
    static Lock w = rwl.writeLock();

    /**
     * 获取一个key对应的value
     */
    public static Object get(String key) {
        r.lock();
        try {
            return map.get(key);
        } finally {
            r.unlock();
        }
    }

    /**
     * 设置key对应的value，并返回旧的value
     *
     * @param key   /
     * @param value /
     * @return 旧的value
     */
    public static Object put(String key, Object value) {
        w.lock();
        try {
            return map.put(key, value);
        } finally {
            w.unlock();
        }
    }

    /**
     * 清空所有的内容
     */
    public static void clear() {
        w.lock();
        try {
            map.clear();
        } finally {
            w.unlock();
        }
    }

}

