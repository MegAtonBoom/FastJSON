
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONException;
import org.junit.Assert;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class JSONArrayParseTest extends junit.framework.TestCase {

    private String text;
    private List<Map<String, Integer>> list;
    private Map<String, Integer> map;

    boolean correctFormat;
    boolean hasBracket;

    public JSONArrayParseTest(String text){
        configure(text);
    }

    public void configure(String text){
        this.text=text;


        if(text!=null) {
            this.text = this.text.replaceAll("\\s", "");
        }
        this.correctFormat=correctFormat();

    }

    @Parameterized.Parameters
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {

                //parameter string
                {"[{i:123}]"},
                {null},
                {"[{id3:123l"},
                {"[{id3:123, id5:321}]"},

        });
    }

    @Test
    public void testArray() throws Exception {

        String current=null;
        if(this.text!=null)
        current = new String(this.text);

        boolean isFinished=false;

        try{
            this.list=JSON.parseObject(text, new TypeReference<List<Map<String, Integer>>>() {});
            this.map  = this.list.get(0);
            Assert.assertEquals(1, this.list.size());
        }
        catch(JSONException je){
            Assert.assertFalse(this.correctFormat);
            return;
        }
        catch(NullPointerException npe){
            Assert.assertFalse(this.correctFormat);
            return;
        }
        while(!isFinished){

            int pos=current.indexOf(":");
            int endPos=current.indexOf(",");

            if(endPos==-1) {
                endPos=current.length()-2;
                isFinished=true;
            }
            int startPos=current.indexOf("{");
            if(startPos==-1) startPos=0;
            else startPos++;
            int i;
            String idString, idNameString;
            idNameString=current.substring(startPos, pos);
            idString=current.substring(pos+1,endPos);
            if(isFinished)
                Assert.assertEquals(Integer.valueOf(idString), this.map.get(idNameString));
            if(!isFinished)
                current=current.substring(endPos+1);
        }


    }

    private boolean correctFormat(){

        if(this.text==null)
            return false;

        //boolean firstIter=true;
        String current=new String(this.text);
        String value;


            int pos1 = this.text.indexOf("[");
            int pos2 = this.text.indexOf("{");
            int pos3 = this.text.indexOf("}");
            int pos4 = this.text.indexOf("]");
            if (pos1 == -1 || pos2 == -1 || pos4 == -1) return false;
            if (pos3 == -1) this.hasBracket = false;
            else this.hasBracket = true;

            int pos6 = 0;

            if (pos2 < pos1) return false;
            if (pos1 > pos4 || pos2 > pos4) return false;
            if (this.hasBracket) {
                if (pos1 > pos3 || pos2 > pos3) return false;
                if (pos4 < pos3) return false;
            }

            while (pos6 != -1) {


                int pos5 = current.indexOf(":");
                if (pos5 == -1) {
                    return false;
                }
                pos6 = current.indexOf(",");
                if (pos6 < pos5 && pos6 != -1) return false;

                if (pos6 != -1) {

                    value = current.substring(pos5 + 1, pos6);
                    try {
                        Integer.valueOf(value);
                    } catch (NumberFormatException nfe) {
                        return false;
                    }
                    current = current.substring(pos6 + 1);
                } else {
                    if (this.hasBracket)
                        value = current.substring(pos5 + 1, current.length() - 2);
                    else
                        value = current.substring(pos5 + 1, current.length() - 1);
                    try {
                        Integer.valueOf(value);
                    } catch (NumberFormatException nfe) {
                        return false;
                    }

                    break;
                }


            }


        return true;

    }


}
