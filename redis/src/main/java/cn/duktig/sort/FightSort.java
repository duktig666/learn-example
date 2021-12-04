package cn.duktig.sort;

import cn.duktig.common.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;

/**
 * description:模拟对战
 *
 * @author RenShiWei
 * Date: 2021/12/4 15:25
 **/
@Service
@RequiredArgsConstructor
public class FightSort {

    private final RedisUtils redisUtils;

    /**
     * 缓存对战数据
     */
    public void cacheFightData(String fightNumber) {
        String key = "fight-sort::" + fightNumber;
        redisUtils.zaddByTimeAsc(key, 1, 490, Instant.now().toEpochMilli());
        redisUtils.zaddByTimeAsc(key, 2, 450, Instant.now().toEpochMilli());
        redisUtils.zaddByTimeAsc(key, 3, 496, Instant.now().toEpochMilli());
        redisUtils.zaddByTimeAsc(key, 5, 436, Instant.now().toEpochMilli());
        redisUtils.zaddByTimeAsc(key, 7, 455, Instant.now().toEpochMilli());
        redisUtils.zaddByTimeAsc(key, 3, 455, Instant.now().toEpochMilli());
        redisUtils.zaddByTimeAsc(key, 1, 422, Instant.now().toEpochMilli());
    }

    public Set<Object> getFightSortDesc(String fightNumber) {
        String key = "fight-sort::" + fightNumber;
        return redisUtils.zreverseRangeAll(key);
    }

}

