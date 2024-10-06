package com.feng.shortlink.admin.remote;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.feng.shortlink.admin.common.convention.result.Result;
import com.feng.shortlink.admin.remote.dto.request.*;
import com.feng.shortlink.admin.remote.dto.response.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author FENGXIN
 */
public interface ShortLinkRemoteService {
    
    /**
     * 新增短链接
     */
    default Result<ShortLinkSaveRespDTO> saveShortLink (ShortLinkSaveReqDTO requestParam){
        String response = HttpUtil.post ("http://127.0.0.1:8001/api/fenglink/v1/shortlink" , JSON.toJSONString (requestParam));
        /* 自动确定要转换的类型 Result中含有泛型需要说明转换*/
        return JSON.parseObject (response ,new TypeReference<> () {});
    }
    
    /**
     * 处理修改短链接的请求。
     *
     * @param requestParam 包含有关要修改的短链接详细信息的请求参数
     */
    default void updateShortLink ( ShortLinkUpdateReqDTO requestParam) {
        HttpUtil.post ("http://127.0.0.1:8001/api/fenglink/v1/shortlink/update" , JSON.toJSONString (requestParam));
    }
    
    /**
     * 分页查询短链接
     */
    default Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParam){
        Map<String,Object> requestMap = new HashMap<> ();
        requestMap.put("gid", requestParam.getGid());
        requestMap.put("current", requestParam.getCurrent());
        requestMap.put("size", requestParam.getSize());
        String responsePage = HttpUtil.get ("http://127.0.0.1:8001/api/fenglink/v1/shortlink" , requestMap);
        return JSON.parseObject(responsePage, new TypeReference<> (){});
    }
    
    /**
     * 查询短链接组中短链接的数量
     */
    default Result<List<ShortLinkGroupQueryRespDTO>> listShortLinkGroup( List<String> requestParam){
        Map<String,Object> requestMap = new HashMap<> ();
        requestMap.put("requestParam", requestParam);
        String responsePage = HttpUtil.get ("http://127.0.0.1:8001/api/fenglink/v1/shortlink/group" , requestMap);
        return JSON.parseObject(responsePage, new TypeReference<> (){});
    }
    
    /**
     * 获取标题
     *
     * @param url 网址
     * @return {@code Result<String> 标题}
     */
    default Result<String> getTitle (String url) {
        String response = HttpUtil.get ("http://127.0.0.1:8001/api/fenglink/v1/title?url=" + url);
        /* 自动确定要转换的类型 Result中含有泛型需要说明转换*/
        return JSON.parseObject (response ,new TypeReference<> () {});
    }
    
    /**
     * 保存短链接到回收站
     */
    default void saveRecycleBin (ShortLinkRecycleBinSaveReqDTO requestParam) {
        HttpUtil.post ("http://127.0.0.1:8001/api/fenglink/v1/shortlink/recycle-bin" , JSON.toJSONString (requestParam));
    }
    
    /**
     * 分页查询回收站短链接
     */
    default Result<IPage<ShortLinkPageRespDTO>> pageRecycleBinShortLink(ShortLinkRecycleBinPageReqDTO requestParam){
        Map<String,Object> requestMap = new HashMap<> ();
        requestMap.put("gidList", requestParam.getGidList ());
        requestMap.put("current", requestParam.getCurrent());
        requestMap.put("size", requestParam.getSize());
        System.out.println (JSON.toJSONString (requestMap));
        String responsePage = HttpUtil.get ("http://127.0.0.1:8001/api/fenglink/v1/shortlink/recycle-bin" , requestMap);
        return JSON.parseObject(responsePage, new TypeReference<> (){});
    }
    
    /**
     * 恢复短链接
     */
    default void recoverRecycleBin (ShortLinkRecycleBinRecoverReqDTO requestParam){
        HttpUtil.post ("http://127.0.0.1:8001/api/fenglink/v1/shortlink/recycle-bin/recover" , JSON.toJSONString (requestParam));
    }
    
    /**
     * 移除短链接
     */
    default void removeRecycleBin (ShortLinkRecycleBinRemoveReqDTO requestParam){
        HttpUtil.post ("http://127.0.0.1:8001/api/fenglink/v1/shortlink/recycle-bin/remove" , JSON.toJSONString (requestParam));
    }
    
    /**
     * 获取单条短链接监控统计数据
     */
    default Result<ShortLinkStatsRespDTO> getShortLinkStats (ShortLinkStatsReqDTO requestParam){
        String responsePage = HttpUtil.get ("http://127.0.0.1:8001/api/short-link/v1/stats", BeanUtil.beanToMap(requestParam));
        return JSON.parseObject(responsePage, new TypeReference<> (){});
    }
    
    /**
     * 分页短链接监控统计
     */
    default Result<IPage<ShortLinkPageStatsRespDTO>> pageShortLinkStats (ShortLinkPageStatsReqDTO requestParam){
        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(requestParam, false, true);
        stringObjectMap.remove("orders");
        stringObjectMap.remove("records");
        String resultBodyStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/v1/stats/page", stringObjectMap);
        return JSON.parseObject(resultBodyStr, new TypeReference<>() {
        });
    }
    
    /**
     * 获取分组短链接监控统计数据
     */
    default Result<ShortLinkStatsRespDTO> groupShortLinkStats (ShortLinkStatsGroupReqDTO requestParam){
        String responsePage = HttpUtil.get ("http://127.0.0.1:8001/api/short-link/v1/stats/group", BeanUtil.beanToMap(requestParam));
        return JSON.parseObject(responsePage, new TypeReference<> (){});
    }
}
