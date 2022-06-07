import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import junit.framework.TestCase;
import org.junit.Assert;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

//@RunWith(Parameterized.class)
public class MapTest2 extends TestCase {

    private String object;
    Map<Object, Object> map;


    public MapTest2(){
        configure("{1:\"2\",\"3\":4,'5':6}");
    }

    public void configure(String object){
        this.object=object;
        this.map=JSON.parseObject(this.object, new TypeReference<Map<Object, Object>>() {
        });


    }

    @Test
    public void testObject(){
        Assert.assertEquals("2", map.get(1));
        Assert.assertEquals(4, map.get("3"));
        Assert.assertEquals(6, map.get("5"));
    }

    /*@Parameterized.Parameters
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {
                {"{1:\"2\",\"3\":4,'5':6}"}
        });
    }*/

}