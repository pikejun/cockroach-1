package com.qaqgame.crawler;


/**
 * Created by Administrator on 2018/1/4 0004.
 */
public class BaiduCrawler
{
    public static void main(String[] args)
    {
        String url="https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=网吧%20电话&oq=%25E7%25BD%2591%25E5%2590%25A7%2520%25E7%2594%25B5%25E8%25AF%259D";

        CommonCrawler commonCrawler =  CommonCrawler.getNewInstance();
        commonCrawler.addTaskUrl(url);
        commonCrawler.waitForWork(10);
    }
}
