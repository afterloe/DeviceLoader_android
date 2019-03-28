package com.github.afterloe.pifinder;

import com.github.afterloe.pifinder.domain.Device;
import com.github.afterloe.pifinder.domain.ResponseObj;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    private Gson initGSON() {
        return new GsonBuilder()
                .setLenient()// json宽松
                .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
                .serializeNulls() //智能null
                .setPrettyPrinting()// 调教格式
                .disableHtmlEscaping().create(); //默认是GSON把HTML 转义的
    }

    @Test
    public void test_str2Int() {
        String str = "1.0";
        Integer val = Double.valueOf(str).intValue();
        System.out.println(val);
    }

    @Test
    public void test_json2obj() {
        String str = "{\"code\":200,\"data\":[{\"id\":1,\"name\":\"树莓派1\",\"remark\":\"获取电灯能耗数据\"},{\"id\":2,\"name\":\"树莓派2\",\"remark\":\"采集电脑能耗数据\"}],\"msg\":null}";
        ResponseObj<List<Device>> res = initGSON().fromJson(str, new TypeToken<ResponseObj<List<Device>>>(){}.getType());
        res.getData().stream().forEach(d -> {
            System.out.println(d.getId());
            System.out.println(d.getClass());
        });
        System.out.println(res.getData());
    }
}