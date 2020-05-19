package com.power.authority.authorization.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * @Auther: xieting
 * @Date: 2019-03-01
 * @Description:抽象类--序列化list
 * @Version:V1.0
 */
public abstract class SerializeTranscoder {

    public abstract byte[] serialize(Object value);

    public abstract Object deserialize(byte[] in) throws IOException;

    public void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
