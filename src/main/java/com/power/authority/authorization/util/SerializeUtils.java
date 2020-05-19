package com.power.authority.authorization.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.*;

/**
 * @program: authorization
 * @description: 自定义redis序列化工具
 * @author: xie ting
 * @create: 2020-05-11 15:40
 */
public class SerializeUtils implements RedisSerializer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 序列化方法
     * @param o
     * @return
     * @throws SerializationException
     */
    @Override
    public byte[] serialize(Object o) throws SerializationException {
        byte[] result = null;
        if (o == null) {
            throw new NullPointerException("Can't serialize null");
        }
        if (o == null) {
            return new byte[0];
        }
        try (
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream(128);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream)
        ){

            if (!(o instanceof Serializable)) {
                throw new IllegalArgumentException(SerializeUtils.class.getSimpleName() + " requires a Serializable payload " +
                        "but received an object of type [" + o.getClass().getName() + "]");
            }

            objectOutputStream.writeObject(o);
            objectOutputStream.flush();
            result =  byteStream.toByteArray();
        } catch (Exception ex) {
            logger.error("Failed to serialize",ex);
        }
        return result;
    }

    /**
     * 反序列化方法
     * @param bytes
     * @return
     * @throws SerializationException
     */
    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        Object result = null;

        if (bytes==null || bytes.length==0) {
            return null;
        }

        try (
                ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteStream)
        ){
            result = objectInputStream.readObject();
        } catch (Exception e) {
            logger.error("Failed to deserialize",e);
        }
        return result;
    }

    //序列化公共方法
    public static Object serializable(Object o){
        SerializeUtils serializeUtils = new SerializeUtils();
        return serializeUtils.serialize(o);
    }

    //反序列化公共方法
    public static Object unserializable(byte[] bytes){
        SerializeUtils serializeUtils = new SerializeUtils();
        return serializeUtils.deserialize(bytes);
    }

}
