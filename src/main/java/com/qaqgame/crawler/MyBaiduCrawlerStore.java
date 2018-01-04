package com.qaqgame.crawler;

import com.zhangyingwei.cockroach.executer.response.TaskResponse;
import com.zhangyingwei.cockroach.store.IStore;

/**
 * Created by Administrator on 2018/1/4 0004.
 */
public class MyBaiduCrawlerStore implements IStore
{
    @Override
    public void store(TaskResponse response) throws Exception
    {
        System.out.println("url:"+response.getTask().getUrl());
        System.out.println("content:"+response.getContent());
    }
}
