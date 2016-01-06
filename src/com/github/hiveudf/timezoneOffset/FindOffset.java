package com.github.hiveudf.timezoneOffset;
 
import java.util.TimeZone;
 
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.serde2.io.DoubleWritable;
 
@Description(name="FindOffset", value="Find offset in hours from time zone string in IANA format.", extended="Example:\n SELECT FindOffset(\"Europe/Berlin\", unix_timestamp()*1000) FROM src LIMIT 1; ")
public class FindOffset extends UDF {
      
       static final Log LOG = LogFactory.getLog(FindOffset.class.getName());
      
       public DoubleWritable evaluate(String tz, long runDate) {
              try {
                     return  new DoubleWritable((double)TimeZone.getTimeZone(tz.trim()).getOffset(runDate)/(1000D*60D*60D));
              }
              catch (Exception e) {
                     LogExceptions(LOG, e.toString());
                     return new DoubleWritable(Double.NaN);
              }
       }
      
       public static void LogExceptions(Log logger, String e)  {
           logger.error("IANA to Offset : Invalid arguments - one or more arguments are null."+e);
         }
//     Used for testing
     public static void main(String[] args) {
            FindOffset fo=new FindOffset();
            System.out.println("Europe/Berlin : Offset="+fo.evaluate("Europe/Berlin", System.currentTimeMillis()));
            System.out.println("Asia/Kolkata : Offset="+fo.evaluate("Asia/Kolkata", System.currentTimeMillis()));
            System.out.println("PST : Offset="+fo.evaluate("PST", System.currentTimeMillis()));
            System.out.println("IST : Offset="+fo.evaluate("IST", System.currentTimeMillis()));
     }
}
 
/*
 
add jar IANA_to_offset.jar
create temporary function toOffset as 'com.github.hiveudf.timezoneOffset.FindOffset';
 
SELECT timezone, toOffset(timezone, unix_timestamp()*1000) from table_abc;
 
*/