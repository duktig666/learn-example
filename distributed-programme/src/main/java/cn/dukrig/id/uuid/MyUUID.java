package cn.dukrig.id.uuid;

import cn.hutool.core.util.IdUtil;

import java.util.UUID;

/**
 * description:
 *
 * @author RenShiWei
 * Date: 2021/8/20 16:27
 **/
public class MyUUID {

    public static void main(String[] args) {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
        String simpleUUID = IdUtil.simpleUUID();
        System.out.println(simpleUUID);
    }

}

