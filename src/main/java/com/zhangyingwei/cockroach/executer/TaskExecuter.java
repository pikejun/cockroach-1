package com.zhangyingwei.cockroach.executer;

import com.zhangyingwei.cockroach.executer.response.TaskErrorResponse;
import com.zhangyingwei.cockroach.executer.response.TaskResponse;
import com.zhangyingwei.cockroach.http.client.HttpClient;
import com.zhangyingwei.cockroach.http.handler.ITaskErrorHandler;
import com.zhangyingwei.cockroach.queue.CockroachQueue;
import com.zhangyingwei.cockroach.store.IStore;
import com.zhangyingwei.cockroach.utils.NameUtils;
import org.apache.log4j.Logger;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangyw on 2017/8/10.
 * 任务执行器，主要工作是从队列中取出任务然后执行任务
 */
public class TaskExecuter implements Runnable {
    private final ITaskErrorHandler errorHandler;
    private Logger logger = Logger.getLogger(TaskExecuter.class);
    private CockroachQueue queue;
    private HttpClient httpClient;
    private IStore store;
    private String id;
    private boolean autoClose;
    private int sleep;

    public TaskExecuter(CockroachQueue queue, HttpClient httpClient, IStore store, ITaskErrorHandler errorHandler, int sleep, boolean autoClose) {
        this.queue = queue;
        this.httpClient = httpClient;
        this.store = store;
        this.id = NameUtils.name(TaskExecuter.class);
        this.errorHandler = errorHandler;
        this.autoClose = autoClose;
        this.sleep = sleep;
    }

    volatile boolean flag = true;

    public void shutdown()
    {
        flag = false;
    }

    @Override
    public void run()
    {
        int nullTaskCnt=0;

        loop:while (flag)
        {
            try {
                Task task = null;
                task = this.queue.poll();

                if(task == null)
                {
                    if(autoClose&&nullTaskCnt++>10)
                    {
                        flag = false;
                        logger.info(this.getId()+" auto closed - "+task);
                        break loop;
                    }

                    Thread.sleep(1000);

                    continue ;
                }


                TimeUnit.MILLISECONDS.sleep(sleep);
                logger.info(this.getId()+" GET - "+task);
                TaskResponse response = this.httpClient.proxy().doGet(task);
                response.setQueue(this.queue);

                if(response.isEmpty())
                {
                    this.errorHandler.error(new TaskErrorResponse(response));
                }
                else
                {
                    this.store.store(response);
                }

                response.getResponse().close();

            } catch (Exception e) {
                logger.error(this.getId()+" - "+ e.getLocalizedMessage());
            }
        }

        logger.info(id+" : over");
    }

    public String getId() {
        return id;
    }
}
