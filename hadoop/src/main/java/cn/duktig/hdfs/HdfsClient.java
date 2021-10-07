package cn.duktig.hdfs;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;


/**
 * description:Hadoop的 hdfs API测试
 *
 * @author RenShiWei
 * Date: 2021/10/6 21:47
 * blog: https://duktig.cn/
 * github知识库: https://github.com/duktig666/knowledge
 **/
public class HdfsClient {

    private FileSystem fs;

    @Before
    public void init() {
        // 1 获取文件系统
        Configuration configuration = new Configuration();
        // 代码配置文件的默认副本数量
        configuration.set("dfs.replication", "2");
        try {
            fs = FileSystem.get(new URI("hdfs://localhost:9000"), configuration, "rsw");
        } catch (IOException | InterruptedException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @After
    public void close() {
        // 3 关闭资源
        try {
            fs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试 创建目录
     */
    @Test
    public void testMkdir() {
        // 2 创建目录
        try {
            fs.mkdirs(new Path("/duktig/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试 上传文件
     * <p>
     * 参数优先级排序：（1）客户端代码中设置的值 >（2）ClassPath 下的用户自定义配置文件 >（3）然后是服务器的自定义配置（xxx-site.xml）>（4）服务器的默认配置（xxx-default.xml）
     */
    @Test
    public void testCopyFromLocalFile() {
        try {
            // 参数1：是否删除文件 参数2：是否允许覆盖 参数3：本地文件路径 参数4：上传目标路径
            fs.copyFromLocalFile(false, true, new Path("src/main/resources/sunwukong.txt"), new Path("/duktig"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试文件下载
     */
    @Test
    public void testCopyToLocalFile() {
        try {
            /*
                执行下载操作
                boolean delSrc 指是否将原文件删除
                Path src 指要下载的文件路径
                Path dst 指将文件下载到的路径
                boolean useRawLocalFileSystem 是否开启文件校验
             */
            fs.copyToLocalFile(false, new Path("/duktig/sunwukong.txt"),
                    new Path("src/main/resources/sunwukong2.txt"), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件/目录 重命名和移动
     * 参数1：源文件路径  参数2：目标文件路径
     */
    @Test
    public void testRename() {
        try {
            fs.rename(new Path("/duktig/sunwukong.txt"), new Path("/duktig/sunwukong-copy.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试 删除文件和目录
     * <p>
     * 参数1：删除的路径 参数2：是否递归删除（即删除目录）
     */
    @Test
    public void testDelete() {
        try {
            fs.delete(new Path("/duktig"), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件详情
     */
    @Test
    public void testListFiles() {
        // 获取文件详情
        RemoteIterator<LocatedFileStatus> listFiles = null;
        try {
            // 参数1：文件路径 参数2：是否递归获取
            listFiles = fs.listFiles(new Path("/duktig"), true);
            while (listFiles.hasNext()) {
                LocatedFileStatus fileStatus = listFiles.next();
                System.out.println("========" + fileStatus.getPath() + "=========");
                System.out.println("权限：" + fileStatus.getPermission());
                System.out.println("所有者：" + fileStatus.getOwner());
                System.out.println("组：" + fileStatus.getGroup());
                System.out.println("文件长度：" + fileStatus.getLen());
                System.out.println("修改时间：" + fileStatus.getModificationTime());
                System.out.println("分片数量：" + fileStatus.getReplication());
                System.out.println("块大小：" + fileStatus.getBlockSize());
                System.out.println("文件名：" + fileStatus.getPath().getName());

                // 获取块信息
                BlockLocation[] blockLocations = fileStatus.getBlockLocations();
                System.out.println("文件的块信息：" + Arrays.toString(blockLocations));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件和文件夹判断
     */
    @Test
    public void testListStatus() {
        // 判断是文件还是文件夹
        try {
            FileStatus[] listStatus = fs.listStatus(new Path("/duktig"));
            for (FileStatus fileStatus : listStatus) {
                // 如果是文件
                if (fileStatus.isFile()) {
                    System.out.println("f:" + fileStatus.getPath().getName());
                } else {
                    System.out.println("d:" + fileStatus.getPath().getName());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

