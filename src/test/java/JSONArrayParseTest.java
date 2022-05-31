
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import org.junit.Assert;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class JSONArrayParseTest extends TestCase {

    private String text;
    private List<Map<String, Integer>> array;
    private Map<String, Integer> map;

    public JSONArrayParseTest(String text){
        this.text=text;
        this.array=JSON.parseObject(text, new TypeReference<List<Map<String, Integer>>>() {});
        this.map  = this.array.get(0);
    }

    @Parameterized.Parameters
    public static Collection Configure(){
        return Arrays.asList(new Object[][]{{"[{id:123}]"}});
        /*this.text=text;
        this.array=JSON.parseObject(this.text, new TypeReference<List<Map<String, Integer>>>() {});
        this.map  = this.array.get(0);*/
    }

    @Test
    public void Test_array() throws Exception {
        String idString;
        int i=this.text.indexOf("}");
        char[] id=new char[i-5];
        this.text.getChars(5, i, id ,0);
        idString=String.valueOf(id);
        i=Integer.parseInt(idString);
        Assert.assertEquals(1, this.array.size());
        Assert.assertEquals(i, this.map.get("id").intValue());
    }
}
