package com.qaqgame.crawler;

import com.zhangyingwei.cockroach.CockroachContext;
import com.zhangyingwei.cockroach.annotation.*;
import com.zhangyingwei.cockroach.config.CockroachConfig;
import com.zhangyingwei.cockroach.config.CockroachConfigBuilder;
import com.zhangyingwei.cockroach.executer.Task;
import com.zhangyingwei.cockroach.http.client.okhttp.COkHttpClient;
import com.zhangyingwei.cockroach.queue.TaskQueue;

/**
 * Created by Administrator on 2018/1/4 0004.
 */
@AppName("BaiduCrawler")
@AutoClose(true)
@CookieConfig("")
@EnableAutoConfiguration
@HttpConfig(COkHttpClient.class)
@TaskErrorHandlerConfig()
@ThreadConfig(num=10,sleep=1000)
@ProxyConfig("127.0.0.1:80,106.12.15.162:9001")
@Store(MyBaiduCrawlerStore.class)
public class BaiduCrawler
{
    public static void main(String[] args)
    {
        String url="https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=网吧%20电话&oq=%25E7%25BD%2591%25E5%2590%25A7%2520%25E7%2594%25B5%25E8%25AF%259D";

        try
        {
            CockroachConfigBuilder configBuilder = new CockroachConfigBuilder(
                    BaiduCrawler.class.getAnnotations());
            CockroachConfig cockroachConfig = configBuilder.bulid();

            CockroachContext cockroachContext = new CockroachContext(
                    cockroachConfig);

            TaskQueue taskQueue = TaskQueue.of();
            taskQueue.push(new Task(url));

            cockroachContext.start(taskQueue);
        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }
}
