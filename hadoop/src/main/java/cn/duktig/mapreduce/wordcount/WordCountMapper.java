package cn.duktig.mapreduce.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * description:
 * <p>
 * KEYIN,map阶段输入的key的类型: LongWritable
 * VALUEIN , map阶段输入value类型: Text
 * KEYOUT , map阶段输出的Key类型: Text
 * VALUEOUT , map阶段输出的value类型: IntWritable
 *
 * @author RenShiWei
 * Date: 2021/10/7 16:48
 * blog: https://duktig.cn/
 * github知识库: https://github.com/duktig666/knowledge
 **/
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    Text outK = new Text();
    IntWritable outV = new IntWritable(1);

    /**
     * 一行数据执行一次
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 1 获取一行: duktig duktig
        String line = value.toString();
        // 2 切割
        // duktig
        // duktig
        String[] words = line.split(" ");
        // 3 输出
        for (String word : words) {
            // outK 和 outV for循环执行多次，一行数据执行一次 map方法，避免频繁创建对象，故写成成员变量
            outK.set(word);
            context.write(outK, outV);
        }
    }

}

