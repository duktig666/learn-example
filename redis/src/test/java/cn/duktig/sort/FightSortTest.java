package cn.duktig.sort;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class FightSortTest {

    @Autowired
    private FightSort fightSort;

    @Test
    public void testFightSort() {
        fightSort.cacheFightData("123456");
        Set<Object> fightSortDesc = fightSort.getFightSortDesc("123456");
        System.out.println(fightSortDesc);
    }

}
