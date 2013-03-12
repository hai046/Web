package com.taobaoke.www.controllers;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.taobaoke.cms.home.ConnectSessionHome;
import com.taobaoke.cms.home.DeviceConnectHome;
import com.taobaoke.cms.home.TItemHome;
import com.taobaoke.cms.home.WebItemHome;
import com.taobaoke.cms.model.TItem;
import com.taobaoke.www.service.ItemService;
import com.taobaoke.www.service.SendMessageService;
import com.taobaoke.www.utils.CookieUtils;
import com.taobaoke.www.utils.MatrixToImageWriter;
import com.taobaoke.www.utils.MsgTools;

@Component
@Path("/")
public class IndexController {
    private static final Log logger = LogFactory.getLog(IndexController.class);
    private static ItemService itemService = new ItemService();

    @Get("")
    public String index(Invocation inv) {
        HttpServletRequest request = inv.getRequest();
        Cookie connectIdCookie = CookieUtils.getConnectIdCookie(request);
        String connectId = "";
        if (connectIdCookie == null) {
            try {
                connectId = DeviceConnectHome.getInstance().generateNewConnectId() + "";
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            connectId = connectIdCookie.getValue();
        }

        inv.getResponse().addCookie(CookieUtils.generateConnectIdCookie(connectId));
        logger.info("aaaaaaaaaaaaaa" + connectId);
        request.setAttribute("connect_id", connectId);
        inv.addModel("connect_id", connectId);

        try {
            /*
             * if(
             * WebItemHome.getInstance().getCount(NumberUtils.toLong(connectId))
             * > 0){ return "r:./itemList"; }
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "index";
    }

    @Get("buy")
    public String buy(Invocation inv, @Param("url") String url) {
        inv.addModel("url", url);
//        return "r:" + url;
        return "buy";
    }

    @Get("itemList")
    public String itemList(Invocation inv) {
        HttpServletRequest request = inv.getRequest();
        Cookie connectIdCookie = CookieUtils.getConnectIdCookie(request);
        if (connectIdCookie == null) {
            return "r:./";
        }
        String connectId = connectIdCookie.getValue();

        inv.getResponse().addCookie(CookieUtils.generateConnectIdCookie(connectId));

        String deviceNo = "";
        try {
            deviceNo = DeviceConnectHome.getInstance().getDeviceNoByConnectId(NumberUtils.toLong(connectId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<TItem> itemList = itemService.getWebItemList(NumberUtils.toLong(connectId), deviceNo);
        if (itemList == null || itemList.size() < 1) {
            return "r:./";
        }
        inv.addModel("device_no", deviceNo);
        inv.addModel("connect_id", connectId);
        inv.addModel("itemList", itemList);

        return "itemList";
    }

    @Get("barcodepic")
    public String barcodepic(Invocation inv, @Param("size") int size) {
        try {
            inv.getResponse().setContentType("image/jpeg;charset=UTF-8");

            String connectId = "";
            Cookie connectIdCookie = CookieUtils.getConnectIdCookie(inv.getRequest());
            if (connectIdCookie == null) {
                connectId = DeviceConnectHome.getInstance().generateNewConnectId() + "";
            } else {
                connectId = connectIdCookie.getValue();
            }
            inv.getResponse().addCookie(CookieUtils.generateConnectIdCookie(connectId));
            // MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            if (size < 1)
                size = 200;
            if (size < 10)
                size = 10;
            Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hints.put(EncodeHintType.MARGIN, 1);
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            // BitMatrix bitMatrix = multiFormatWriter.encode("connect_id:"
            // + connectId, BarcodeFormat.QR_CODE, 200, 200, hints);
            // MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);
            // MatrixToImageWriter.writeToStream(, "jpg", inv
            // .getResponse().getOutputStream());
            MatrixToImageWriter.writeToStream("connect_id:" + connectId, 200, hints, inv.getResponse().getOutputStream());
            // MatrixToImageWriter.toBufferendColorQRImage("connect_id:"
            // + connectId, size, hints, inv.getResponse()
            // .getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Get("colorQR")
    public String colorQr(Invocation inv, @Param("size") int size, @Param("margin") int margin) {
        try {
            inv.getResponse().setContentType("image/jpeg;charset=UTF-8");
            String connectId = "";
            Cookie connectIdCookie = CookieUtils.getConnectIdCookie(inv.getRequest());
            if (connectIdCookie == null) {
                connectId = DeviceConnectHome.getInstance().generateNewConnectId() + "";
            } else {
                connectId = connectIdCookie.getValue();
            }
            inv.getResponse().addCookie(CookieUtils.generateConnectIdCookie(connectId));
            // MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            if (size < 1)
                size = 200;
            if (size < 10)
                size = 10;

            if (margin < 1)
                margin = 1;
            Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hints.put(EncodeHintType.MARGIN, margin);
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            MatrixToImageWriter.toBufferendColorQRImage("connect_id:" + connectId, size, hints, inv.getResponse().getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Get("sendMessage")
    @Post("sendMessage")
    public String sendMessage(Invocation inv, @Param("method") String method, @Param("numIid") long numIid, @Param("connect_id") long connectId, @Param("device_no") String deviceNo) {
        if (StringUtils.isEmpty(method)) {
            return "@" + MsgTools.createErrorJSON("1", "method name is empty");
        }
        if ("addDevice".equals(method)) {
            if (connectId < 1 || StringUtils.isEmpty(deviceNo)) {
                return "@" + MsgTools.createErrorJSON("3", "invalid connect_id or device_no");
            }
            try {
                DeviceConnectHome.getInstance().insert(deviceNo, connectId, DeviceConnectHome.STATUS_OK);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return "@" + MsgTools.createOKJSON(null);
        }
        if ("sendMsg".equals(method)) {
            if (connectId < 1) {
                return "@" + MsgTools.createErrorJSON("4", "二维码识别失败，请重试");//invalid connect_id
            }
            try {
                if (!StringUtils.equals(DeviceConnectHome.getInstance().getDeviceNoByConnectId(connectId), deviceNo)) {
                    DeviceConnectHome.getInstance().insert(deviceNo, connectId, DeviceConnectHome.STATUS_OK);
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            String sessionId = "";
            try {
                sessionId = ConnectSessionHome.getInstance().getSessionId(connectId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (StringUtils.isEmpty(sessionId)) {
                return "@" + MsgTools.createErrorJSON("5", "游览器不支持或网页已关闭");//not online
            }
            if (numIid < 1) {
                return "@" + MsgTools.createErrorJSON("6", "此商品不存在");//invalid numIid
            }
            // TItem tItem = TItemHome.getInstance().getByNumId(numIid);
            // if( tItem == null ){
            //
            // }
            // sendMessageService.sendMessage(connectId, tItem.getClickUrl());

            /*
             * add by haizhu 把deviceNo传入口更加设备号来查找用户当前的收藏
             */
            try {
                TItem tItem = TItemHome.getInstance().getByNumId(numIid);
                if (tItem == null) {
                    return "@" + MsgTools.createErrorJSON("7", "此商品不存在");//item not exist
                }

                String pcClickUrl = StringUtils.defaultIfEmpty(itemService.getClickUrl(numIid), tItem.getClickUrl());
                tItem.setClickUrl(pcClickUrl);

                // if( WebItemHome.getInstance().get(connectId, numIid,
                // WebItemHome.TYPE_ITEM) < 1 ){
                WebItemHome.getInstance().insert(deviceNo, numIid, WebItemHome.TYPE_ITEM);
                SendMessageService.getInstance().sendMessage(connectId, tItem, deviceNo);
                // }
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return "@" + MsgTools.createOKJSON(null);
        }
        return "@" + MsgTools.createErrorJSON("2", "invalid method name");
    }
}