package com.taobaoke.cms.crawlers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Formatter;
import java.util.List;

import net.paoding.rose.scanning.context.RoseAppContext;

import org.apache.commons.lang.math.NumberUtils;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TaobaokeItemsGetRequest;
import com.taobao.api.response.TaobaokeItemsGetResponse;
import com.taobaoke.cms.home.TCategoryHome;
import com.taobaoke.cms.model.TCategory;

public class SingleCrawlerCount {

    static String url = "http://gw.api.taobao.com/router/rest";
    // 正式环境需要设置为:http://gw.api.taobao.com/router/rest
    protected static String appkey = "21267618";
    protected static String appSecret = "b41ba667ae9fe43f44355c8d816a0af9";

    public static final long PAGE_COUNT = 40;
    TaobaoClient client = new DefaultTaobaoClient(url, appkey, appSecret);

    public void start(TCategory parent, FileOutputStream fos) throws SQLException, IOException {
        List<TCategory> list = TCategoryHome.getInstance().getAllSubList(TCategoryHome.TYPE_MOBILE, TCategoryHome.STATUS_OK, parent.getId(), 0, Integer.MAX_VALUE);
        System.out.println( parent.getName() );
//        fw.write(parent.getName());
        fos.write((parent.getName()+"\n)").getBytes("utf-8"));
//        fw.newLine();
//        Formatter formatter = new Formatter(fw);
        for (TCategory tCategory : list) {
            int i = 0;
            System.out.println("    " + tCategory.getName() );
            fos.write(("    " + tCategory.getName() + "\n").getBytes("utf-8") );
            while (true) {
                String rate = (100 * i++) + "";
                long totalCount = saveTaobaokeItemCount(tCategory, rate);
                StringBuilder sb = new StringBuilder("    ");
                Formatter formatter = new Formatter(sb); 
                formatter.format("%1$5s || %2$9s || %3$5d  || %4$9s ", rate, tCategory.getName(), totalCount, tCategory.getCid());
                fos.write((sb.toString()+"\n").getBytes("utf-8"));
//                fw.newLine();
                
//                System.out.printf("%1$5s || %2$9s || %3$5d", rate, tCategory.getName(), totalCount);
//                System.out.println();
                System.out.println( sb.toString() );
                if (totalCount < 1) {
                    break;
                }

            }
        }
        fos.write("\n\n\n".getBytes("utf-8"));
//        fw.write("\n");
//        fw.write("\n");
//        fw.write("\n");
        fos.flush();
        // }
    }

    private long saveTaobaokeItemCount(TCategory tCategory, String startCommissionRate) {

        TaobaokeItemsGetRequest req = new TaobaokeItemsGetRequest();

        String[] cids = tCategory.getCid().split(",");
        long totalCount = 0;
        for (String cidStr : cids) {
            req.setFields("desc,num_iid,nick,title,price,item_location,seller_id,seller_credit_score,click_url,shop_click_url,pic_url,commission,commission_rate,commission_num,commission_volume,shop_click_url,item_location,volume,shop_type");
            req.setNick("iterry1985");
            long cid = NumberUtils.toLong(cidStr.trim());
            if (cid == 0) {
                continue;
            }
            req.setCid(cid);
            req.setPageSize(PAGE_COUNT);
            req.setStartCredit("1diamond");
            req.setEndCredit("5goldencrown");
            req.setStartCommissionRate(startCommissionRate);
            req.setEndCommissionRate("20000");
            req.setSort("commissionNum_desc");
            long pageNo = 0;

            req.setPageNo(pageNo);
            req.setOuterCode("iterry1985");
            req.setIsMobile(true);
            TaobaokeItemsGetResponse response;
            try {
                response = client.execute(req);
                totalCount += response.getTotalResults();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        return totalCount;
        // testRefound(list);
    }

    public static void main(String[] args) {
//        RoseAppContext context = new RoseAppContext();
//        TCategoryHome home = context.getBean(TCategoryHome.class);
//        try {
//            List<TCategory> list = home.getAllParentList(TCategoryHome.TYPE_MOBILE, TCategoryHome.STATUS_OK, 0, Integer.MAX_VALUE);
//            SingleCrawlerCount scc = new SingleCrawlerCount();
//            BufferedWriter fw = new BufferedWriter(new FileWriter("/Users/koutosei/crawler.text"));
//            FileOutputStream fos = new FileOutputStream("/Users/koutosei/crawler.txt");
//            fos.write("我哈哈".getBytes("utf-8"));
//            fos.flush();
//            Formatter formatter = new Formatter(fw);
//            for( TCategory tc : list ){
//                scc.start(tc, fos);
//            }
        // }

    }
}
