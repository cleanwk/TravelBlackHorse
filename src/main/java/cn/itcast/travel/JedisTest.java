package cn.itcast.travel;

import org.junit.Test;
import redis.clients.jedis.Jedis;
//import sun.security.mscapi.CPublicKey;

/**
 * @Author: Wuk
 * @Company: jlu.edu.cn
 * @date: 2020/10/15.
 * @description:
 */

public class JedisTest {

    @Test
    public void test(){

        Jedis jedis = new Jedis("101.37.71.31", 6379);
        jedis.auth("wk4478200");

        jedis.set("myneme","zhgnsan");

        jedis.close();


    }
}
