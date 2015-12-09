# tz-offset
Hive UDF to find the timezone offset in hours for a particular timezone and date.

Usage:
Compile it to a jar/use the already uploaded jar. Use the following from Hive.

add jar tz-offset.jar

create temporary function toOffset as 'com.github.hiveudf.timezoneOffset.FindOffset';

SELECT timezone, toOffset(timezone, unix_timestamp()*1000) from table_abc;
-- The timezone field has the name of the timezone in IANA format.
 

Note: This can be tweaked easily to find offset in minutes, etc. This uses the Java TimeZone library (java.util.TimeZone) to find the offset.
