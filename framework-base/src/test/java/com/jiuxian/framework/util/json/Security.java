package com.jiuxian.framework.util.json;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

/**
 * Created by luqingrun on 16/8/26.
 */

public class Security {

    @Test
    public void test(){
        String key = "key";
        String conent = "value";
        System.out.println(DigestUtils.md5Hex(conent));

    }
}
