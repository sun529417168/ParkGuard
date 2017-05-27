package com.linked.erfli.library.okhttps.builder;


import com.linked.erfli.library.application.LibApplication;
import com.linked.erfli.library.okhttps.request.PostStringRequest;
import com.linked.erfli.library.okhttps.request.RequestCall;
import com.linked.erfli.library.utils.MyUtils;
import com.linked.erfli.library.utils.NetWorkUtils;
import com.linked.erfli.library.utils.SharedUtil;

import okhttp3.MediaType;

/**
 * Created by zhy on 15/12/14.
 */
public class PostStringBuilder extends OkHttpRequestBuilder<PostStringBuilder> {
    private String content;
    private MediaType mediaType;


    public PostStringBuilder content(String content) {
        if (NetWorkUtils.getNetWorkState(LibApplication.getContent()) == 0) {
            int obj = content.getBytes().length;
            int size = SharedUtil.getInteger(LibApplication.getContent(), "WMSize", 0) + obj;
            SharedUtil.setInteger(LibApplication.getContent(), "WMSize", size);
            SharedUtil.setString(LibApplication.getContent(), "traffic", MyUtils.getFormatSize(size));
            int monthSize=SharedUtil.getInteger(LibApplication.getContent(), "monthWMSize", 0) + obj;
            SharedUtil.setInteger(LibApplication.getContent(), "monthWMSize", monthSize);
            SharedUtil.setString(LibApplication.getContent(), "monthTraffic", MyUtils.getFormatSize(monthSize));
        }
        this.content = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build() {
        return new PostStringRequest(url, tag, params, headers, content, mediaType, id).build();
    }


}
