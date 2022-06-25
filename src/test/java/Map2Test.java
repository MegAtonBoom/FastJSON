import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import junit.framework.TestCase;
import org.junit.Assert;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class Map2Test extends TestCase {

    private String object;
    private Map<Object, Object> map;
    private String expectedValue;
    private dataType expectedValueType;
    private String expectedKey;
    private dataType expectedKeyType;
    private boolean expectedExc;


    public Map2Test(String object, dataType expectedValueType, String expectedValue, dataType expectedKeyType, String expectedKey, boolean expectedExc){
        configure(object, expectedValueType, expectedValue, expectedKeyType, expectedKey, expectedExc);
    }

    public void configure(String object, dataType expectedValueType, String expectedValue, dataType expectedKeyType, String expectedKey, boolean expectedExc){
        this.expectedKey = expectedKey;
        this.expectedKeyType = expectedKeyType;
        this.expectedValue = expectedValue;
        this.expectedValueType = expectedValueType;
        this.expectedExc = expectedExc;

        this.object=object;

    }

    @Parameterized.Parameters
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {

                //object                    value type          value       key type            key     expected Exception
                {"{\"1\":\"2\"}",           dataType.STRING,    "2",        dataType.STRING,    "1",    false},
                {"{\"1\"\"2\"}",           dataType.STRING,    "2",        dataType.STRING,    "1",    true},
                {null,                      dataType.INT,       "3",        dataType.INT,       "2",    true},
                {"{\"1\":\"2\", 2:3}",      dataType.INT,       "3",        dataType.INT,       "2",    false},

        });
    }

    @Test
    public void testObject(){

        try {
            this.map = JSON.parseObject(this.object, new TypeReference<Map<Object, Object>>() {
            });

            if (expectedKeyType == dataType.STRING) {
                String key = expectedKey;
                if (expectedValueType == dataType.STRING) {
                    String res = expectedValue;
                    Assert.assertEquals(res, map.get(key));
                    return;
                } else {
                    Integer res = Integer.valueOf(expectedValue);
                    Assert.assertEquals(res, map.get(key));
                    return;
                }
            } else {
                Integer key = Integer.valueOf(expectedKey);
                if (expectedValueType == dataType.STRING) {
                    String res = expectedValue;
                    Assert.assertEquals(res, map.get(key));
                    return;
                } else {
                    Integer res = Integer.valueOf(expectedValue);
                    Assert.assertEquals(res, map.get(key));
                    return;
                }
            }
        }
        catch(Exception e){
            Assert.assertTrue(this.expectedExc);
        }


    }



    private enum dataType{
        STRING,
        INT
    }

}