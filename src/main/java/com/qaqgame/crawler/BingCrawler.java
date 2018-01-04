package com.qaqgame.crawler;


/**
 * Created by Administrator on 2018/1/4 0004.
 */
public class BingCrawler
{
    public static void main(String[] args)
    {
        String url="https://cn.bing.com/search?q=%E7%BD%91%E5%90%A7+%E6%B8%A0%E9%81%93&qs=n&form=QBLH&sp=-1&pq=%E7%BD%91%E5%90%A7+%E6%B8%A0%E9%81%93&sc=8-6&sk=&cvid=AC8D9C7A30C848F49C4FAA4A54CD6702";
        CommonCrawler commonCrawler = CommonCrawler.getNewInstance();
        commonCrawler.addTaskUrl(url);
        commonCrawler.waitForWork(10);
    }
}
