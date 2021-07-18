package cn.dukrig.id.snowflake;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * description:Hutool的雪花算法实现
 * <p>
 * 可以使用Hutool默认提供的方法 IdUtil.getSnowflake 实现雪花算法，一般使用此即可。可根据情况传入 5位dataCenterId 和 5位workerId
 * <p>
 * 如果使用 IdUtil.createSnowflake 使用雪花算法，需要自行维护单例模式（不同的Snowflake对象创建的ID可能会有重复）。
 * 一个比较好的选择是交由 Spring 管理（默认单例）
 *
 * @author RenShiWei
 * Date: 2021/7/18 17:48
 **/
@Slf4j
@Component
public class HutoolSnowflake {

    public static void main(String[] args) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        for (int i = 0; i < 1000; i++) {
            long id = snowflake.nextId();
            System.out.println(id);
        }
    }

}

