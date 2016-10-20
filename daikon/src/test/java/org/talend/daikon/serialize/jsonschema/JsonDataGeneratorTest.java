package org.talend.daikon.serialize.jsonschema;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

import org.apache.avro.SchemaBuilder;
import org.junit.Test;
import org.talend.daikon.avro.SchemaConstants;

public class JsonDataGeneratorTest {

    @Test
    public void genData() throws Exception {
        String jsonStr = JsonUtilTest.readJson("FullExampleJsonData.json");
        FullExampleProperties properties = new FullExampleProperties("properties");
        properties.stringProp.setValue("abc");
        properties.integerProp.setValue(1);
        properties.hideStringPropProp.setValue(false);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.000'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        properties.dateProp.setValue(df.parse("2016-10-05T01:23:45.000Z"));
        properties.schema.setValue(
                SchemaBuilder.builder().record("test").prop(SchemaConstants.INCLUDE_ALL_FIELDS, "true").fields().endRecord());

        properties.commonProp.colEnum.setValue(FullExampleProperties.CommonProperties.ColEnum.FOO);
        properties.commonProp.colBoolean.setValue(true);
        properties.commonProp.colString.setValue("common_abc");

        properties.tableProp.colListBoolean.setValue(Arrays.asList(new Boolean[] { true, false, true }));
        properties.tableProp.colListEnum.setValue(Arrays
                .asList(new FullExampleProperties.TableProperties.ColEnum[] { FullExampleProperties.TableProperties.ColEnum.FOO,
                        FullExampleProperties.TableProperties.ColEnum.BAR, FullExampleProperties.TableProperties.ColEnum.FOO }));
        properties.tableProp.colListString.setValue(Arrays.asList(new String[] { "a", "b", "c" }));

        JsonDataGenerator generator = new JsonDataGenerator();
        assertEquals(jsonStr, generator.genData(properties).toString());
    }

}