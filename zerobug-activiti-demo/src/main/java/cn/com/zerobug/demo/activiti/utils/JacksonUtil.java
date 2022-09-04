package cn.com.zerobug.demo.activiti.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * jackson Util
 *
 * @author zhongxiaowei
 * @date 2022/3/20
 */
public class JacksonUtil {

    private static final Logger log = LoggerFactory.getLogger(JacksonUtil.class);

    /**
     * 默认时间格式
     **/
    public static final String DEFAULT_DATE_TIME_PATTEN = "yyyy-MM-dd HH:mm:ss";

    private static ObjectMapper  objectMapper  = new ObjectMapper();
    private static PrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
    private static MapType       hashMapType;
    private static MapType       stringStringHashMapType;

    static {
        //忽略null的字段
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //发现未知字段不抛异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 解析器支持解析单引号
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 解析器支持解析结束符
        objectMapper.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
        // 设置当前json格式日期格式
        objectMapper.setDateFormat(new SimpleDateFormat(DEFAULT_DATE_TIME_PATTEN));
        try {
            Class<?> clazz  = JavaTimeModule.class;
            Module   module = (Module) clazz.getDeclaredConstructor().newInstance();
            objectMapper.registerModule(module);
        } catch (Exception e) {
            log.error("Loading jackson-datatype-jsr310 failed", e);
        }

        hashMapType = getMapType(HashMap.class);
        stringStringHashMapType = getMapType(HashMap.class, String.class, String.class);
    }

    /**
     * 暴露ObjectMapper，用于修改配置
     **/
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /**
     * 转换成Json字符串
     */
    public static String toJsonString(Object object) {
        return toJsonString(object, false);
    }

    /**
     * 转换成Json字符串, pretty print
     **/
    public static String toPrettyJsonString(Object object) {
        return toJsonString(object, true);
    }

    /**
     * 转换成指定对象
     */
    public static <T> T parseObject(String content, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        if (typeReference == null) {
            return null;
        }
        try {
            return objectMapper.readValue(content, typeReference);
        } catch (Exception e) {
            log.error("ERROR", e);
        }
        return null;
    }

    /**
     * 转换成指定对象
     */
    public static <T> T parseObject(String content, Class<T> clazz) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        if (clazz == null) {
            return null;
        }
        try {
            return objectMapper.readValue(content, clazz);
        } catch (Exception e) {
            log.error("ERROR", e);
        }
        return null;
    }

    /**
     * json转换成数组
     */
    public static <T> T[] parseArray(String jsonString, Class<T> elementType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        ArrayType array = getArrayType(elementType);
        try {
            return objectMapper.readValue(jsonString, array);
        } catch (IOException e) {
            log.error("Error", e);
        }
        return null;
    }

    /**
     * json转换成list
     **/
    public static <T> List<T> parseList(String jsonString, Class<T> elementType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        CollectionType collectionType = getCollectionType(ArrayList.class, elementType);
        try {
            return objectMapper.readValue(jsonString, collectionType);
        } catch (IOException e) {
            log.error("Error", e);
        }
        return null;
    }

    /**
     * 转换成HashMap<String, String>
     */
    public static HashMap<String, String> parseStringHashMap(String content) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }

        try {
            return objectMapper.readValue(content, stringStringHashMapType);
        } catch (Exception e) {
            log.error("ERROR", e);
        }
        return null;
    }

    /**
     * 转换成HashMap<String,Object>
     **/
    public static HashMap<String, Object> parseHashMap(String content) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }

        try {
            return objectMapper.readValue(content, hashMapType);
        } catch (Exception e) {
            log.error("ERROR", e);
        }
        return null;
    }

    private static MapType getMapType(Class<? extends Map> mapClass) {
        return objectMapper.getTypeFactory().constructRawMapType(mapClass);
    }

    private static MapType getMapType(Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) {
        return objectMapper.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
    }

    private static <T> ArrayType getArrayType(Class<T> elementType) {
        return objectMapper.getTypeFactory().constructArrayType(elementType);
    }

    private static <T> CollectionType getCollectionType(Class<? extends Collection> collectionClass, Class<T> elementType) {
        return objectMapper.getTypeFactory().constructCollectionType(collectionClass, elementType);
    }

    /**
     * 转换成json字符串
     */
    private static String toJsonString(Object object, boolean isPretty) {
        if (object == null) {
            return null;
        }
        try {
            String content;
            if (isPretty) {
                content = objectMapper.writer(prettyPrinter).writeValueAsString(object);
            } else {
                content = objectMapper.writeValueAsString(object);
            }
            return content;
        } catch (Exception e) {
            log.error("ERROR", e);
        }
        return null;
    }

}
