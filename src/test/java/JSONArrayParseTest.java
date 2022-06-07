
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONException;
import junit.framework.TestCase;
import org.junit.Assert;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class JSONArrayParseTest extends junit.framework.TestCase {

    private String text;
    private List<Map<String, Integer>> array;
    private Map<String, Integer> map;

    public JSONArrayParseTest(String text){
        configure(text);
    }

    public void configure(String text){
        this.text=text;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {
                {"[[id:123}]"},{"p{i:178}]"}
        });
    }

    @org.junit.Test
    public void testArray() throws Exception {
        int pos=this.text.indexOf(":"), endPos=this.text.length()-2, i, startPos=this.text.indexOf("{");
        String idString, idNameString;
        char[] id, idName;
        try {
            idName = new char [pos-startPos-1];
            idNameString=String.valueOf(idName);
            this.array=JSON.parseObject(text, new TypeReference<List<Map<String, Integer>>>() {});
            System.out.println(array+"    "+this.array.get(0));
            this.map  = this.array.get(0);
            id = new char[endPos-pos];
            this.text.getChars(pos+1, endPos, id, 0);
            idString = String.valueOf(id);
            i = Integer.parseInt(idString);
            Assert.assertEquals(1, this.array.size());
            Assert.assertEquals(i, this.map.get(idNameString).intValue());
        }
        catch(Exception e) {
            boolean correct=true;
            if (this.text.indexOf("[")!=0||this.text.indexOf("{")!=1) correct=false;
            else if((this.text.substring(endPos))!="}]") correct=false;
            else if(pos==-1) correct=false;
            else{
                try{
                    id = new char[endPos-pos];
                    this.text.getChars(pos+1, endPos, id, 0);
                    idString = String.valueOf(id);
                    i = Integer.parseInt(idString);
                }
                catch(NumberFormatException ne){
                    correct=false;
                }
            }
            Assert.assertFalse(correct);

        }
    }


}
