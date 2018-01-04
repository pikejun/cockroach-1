package com.qaqgame.crawler;

import com.zhangyingwei.cockroach.CockroachContext;
import com.zhangyingwei.cockroach.config.CockroachConfig;
import com.zhangyingwei.cockroach.executer.Task;
import com.zhangyingwei.cockroach.queue.TaskQueue;
import org.apache.log4j.Logger;

/**
 * Created by Administrator on 2018/1/4 0004.
 */
public class CommonCrawler
{
    private Logger logger = Logger.getLogger(CommonCrawler.class);

    public static CommonCrawler getNewInstance()
    {
        return new CommonCrawler();
    }

    public boolean isEmpty()
    {
        return taskQueue.isEmpty();
    }

    public void shutdown()
    {
        cockroachContext.executerList.stream().forEach(taskExecuter -> {taskExecuter.shutdown();});
    }

    private  boolean isInited=false;

    private  CockroachContext cockroachContext;
    private CockroachConfig cockroachConfig;
    private TaskQueue taskQueue = TaskQueue.of(100);

    private String proxyIps="";

    public CockroachContext getCockroachContext()
    {
        return cockroachContext;
    }

    public void setCockroachContext(
            CockroachContext cockroachContext)
    {
        this.cockroachContext = cockroachContext;
    }

    public CockroachConfig getCockroachConfig()
    {
        return cockroachConfig;
    }

    public void setCockroachConfig(
            CockroachConfig cockroachConfig)
    {
        this.cockroachConfig = cockroachConfig;
    }

    public TaskQueue getTaskQueue()
    {
        return taskQueue;
    }

    public void setTaskQueue(TaskQueue taskQueue)
    {
        this.taskQueue = taskQueue;
    }

    public void setProxy(String proxy)
    {
        proxyIps=proxy;
    }

    public void waitForWork(int seconds)
    {
        int cnt=seconds;
        while(!isEmpty()&&cnt>0)
        {
            try
            {
                Thread.sleep(1000);
                logger.info("....................waitForWork:."+cnt--);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        shutdown();
    }

    public void addTaskUrl(String url)
    {
        init();
        try
        {
            taskQueue.push(new Task(url));
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    private CommonCrawler()
    {
    }

    private void init()
    {
        if(!isInited)
        {
            isInited=true;

            cockroachConfig= new CockroachConfig().setAppName("CommonCrawler").setAutoClose(true).setProxys(proxyIps).setStore(
                    MyBaiduCrawlerStore.class).setThread(1,1000);

            cockroachContext = new CockroachContext(
                    cockroachConfig);

            try
            {
                cockroachContext.start(taskQueue);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
