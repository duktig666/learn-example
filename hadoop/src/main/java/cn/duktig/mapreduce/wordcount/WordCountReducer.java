package cn.duktig.mapreduce.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * description:
 * <p>
 * KEYIN， reduce阶段输入的key的类型: Text
 * VALUEIN, reduce阶段输入value类型: IntWritable
 * KEYOUT, reduce阶段输出的Key类型: Text
 * VALUEOUT, reduce阶段输出的value类型: IntWritable
 *
 * @author RenShiWei
 * Date: 2021/10/7 16:49
 * blog: https://duktig.cn/
 * github知识库: https://github.com/duktig666/knowledge
 **/
public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    IntWritable outV = new IntWritable();

    /**
     * 一个key执行一次
     */
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException,
            InterruptedException {
        // 1 累加求和 duktig,(1,1)
        int sum = 0;
        for (IntWritable count : values) {
            sum += count.get();
        }

        // 2 输出
        outV.set(sum);
        context.write(key, outV);
    }

}
