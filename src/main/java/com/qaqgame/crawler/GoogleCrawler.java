package com.qaqgame.crawler;


/**
 * Created by Administrator on 2018/1/4 0004.
 */
public class GoogleCrawler
{
    public static void main(String[] args)
    {
        String url="https://www.google.com/search?source=hp&ei=x_VNWozVOMjU0gLOrZSwAQ&q=网吧+电话&oq=网吧+电话";

        CommonCrawler commonCrawler = CommonCrawler.getNewInstance() ;
        commonCrawler.setProxy("127.0.0.1:1080");
        commonCrawler.addTaskUrl(url);
        commonCrawler.waitForWork(10);
    }
}
