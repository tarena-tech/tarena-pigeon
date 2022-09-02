/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tarena.mnmp.extentions.autoconfigurations.msgpack;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.msgpack.core.annotations.Nullable;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

public class MsgPackRedisSerializer implements RedisSerializer<Object> {

    static final byte[] EMPTY_ARRAY = new byte[0];
    static final byte[][] EMPTY_TWO_DIMENSION_ARRAY = new byte[0][0];
    private final ObjectMapper mapper;


    public MsgPackRedisSerializer() {
        this.mapper = new ObjectMapper(new MessagePackFactory());
        this.mapper.registerModule((new SimpleModule()).addSerializer(new NullValueSerializer(null)));
        this.mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        this.mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
    }

    /**
     * 序列化
     *
     * @param source
     * @return byte[]
     * @throws SerializationException
     */
    @Override
    public byte[] serialize(@Nullable Object source) throws SerializationException {
        if (source == null) {
            return EMPTY_ARRAY;
        } else {
            try {
                return this.mapper.writeValueAsBytes(source);
            } catch (JsonProcessingException ex) {
                throw new SerializationException("Could not write JSON: " + ex.getMessage(), ex);
            }
        }
    }


    public <T> byte[][] serialize(@Nullable Collection<T> sources) {
        if (CollectionUtils.isEmpty(sources)) {
            return EMPTY_TWO_DIMENSION_ARRAY;
        }
        byte bytes[][] = new byte[sources.size()][];
        int i = 0;
        for (T t : sources) {
            bytes[i++] = this.serialize(t);
        }
        return bytes;
    }


    @Nullable
    public <T> T deserialize(@Nullable Object o, Class<T> clazz) throws SerializationException {
        if (o == null) {
            return null;
        } else {
            try {
                return this.mapper.readValue(objectToBytes(o), clazz);
            } catch (Exception ex) {
                throw new SerializationException("Could not read JSON: " + ex.getMessage(), ex);
            }
        }
    }


    @Nullable
    public <T> T deserialize(@Nullable byte[] source, Class<T> clazz) throws SerializationException {
        Assert.notNull(clazz, "Deserialization type must not be null! Pleaes provide Object.class to make use of Jackson2 default typing.");
        if (source == null || source.length == 0) {
            return null;
        } else {
            try {
                return this.mapper.readValue(source, clazz);
            } catch (Exception ex) {
                throw new SerializationException("Could not read JSON: " + ex.getMessage(), ex);
            }
        }
    }


    @Override
    public Object deserialize(@Nullable byte[] source) throws SerializationException {
        return this.deserialize(source, Object.class);
    }


    @Nullable
    public <T> List<T> deserialize(@Nullable Collection<byte[]> sources, Class<T> clazz) {
        if (CollectionUtils.isEmpty(sources)) {
            return null;
        }
        List<T> result = new ArrayList<>();
        T t = null;
        for (byte[] bytes : sources) {
            if (bytes != null) {
                t = this.deserialize(bytes, clazz);
                if (t != null) {
                    result.add(t);
                }
            }
        }
        return result;
    }


    @Nullable
    public <T> Set<T> deserialize(@Nullable Set<?> sources, Class<T> clazz) {
        if (CollectionUtils.isEmpty(sources)) {
            return null;
        }
        Set<T> result = new HashSet<>();
        T t = null;
        for (Object source : sources) {
            if (source == null) {
                continue;
            }
            t = this.deserialize(source, clazz);
            if (t != null) {
                result.add(t);
            }
        }
        return result;
    }

    @Nullable
    public <T> List<T> deserialize(@Nullable List<Object> sources, Class<T> clazz) {
        if (CollectionUtils.isEmpty(sources)) {
            return null;
        }
        List<T> result = new ArrayList<>();
        T t = null;
        for (Object object : sources) {
            if (object != null) {
                t = this.deserialize((byte[]) object, clazz);
                if (t != null) {
                    result.add(t);
                }
            }
        }
        return result;
    }

    private static <T> byte[] objectToBytes(T obj) {
        byte[] bytes = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream sOut;
        try {
            sOut = new ObjectOutputStream(out);
            sOut.writeObject(obj);
            sOut.flush();
            bytes = out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }


}
