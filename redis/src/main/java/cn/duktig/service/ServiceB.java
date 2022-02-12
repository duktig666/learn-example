package cn.duktig.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description:
 *
 * @author RenShiWei
 * Date: 2021/12/30 15:30
 **/
@Service
public class ServiceB {

    @Autowired
    private ServiceA serviceA;

}

