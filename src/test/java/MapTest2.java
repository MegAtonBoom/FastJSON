import java.util.Map;

import org.junit.Assert;
import junit.framework.TestCase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.junit.Test;


public class MapTest2 extends TestCase {
    /*public void test_map () throws Exception {
        Map<Object, Object> map = JSON.parseObject("{1:\"2\",\"3\":4,'5':6}", new TypeReference<Map<Object, Object>>() {});
        Assert.assertEquals("2", map.get(1));
        Assert.assertEquals(4, map.get("3"));
        Assert.assertEquals(6, map.get("5"));
    }*/
    private String text;
    private Map<Object, Object> map;
    private char expected1;
    private int expected2;
    private int expected3;

    public void configure(String text, char exp1, int exp2, int exp3){
        this.text=text;
        this.map=JSON.parseObject(this.text, new TypeReference<Map<Object, Object>>() {});
        this.expected1=exp1;
        this.expected2=exp2;
        this.expected3=exp3;
    }

    public MapTest2(){
        configure("{1:\"2\",\"3\":4,'5':6}", '2', 4, 6);
    }

    @Test
    public void Test_Map(){
        Assert.assertEquals(this.expected1, this.map.get(1));
        Assert.assertEquals(this.expected2, this.map.get("3"));
        Assert.assertEquals(this.expected3, this.map.get("5"));
    }
}