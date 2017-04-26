package com.linked.erfli.library.okhttps.builder;

import java.util.Map;

/**
 * Created by zhy on 16/3/1.
 */
public interface HasParamsable
{
    OkHttpRequestBuilder params(Map<String, Object> params);
    OkHttpRequestBuilder addParams(String key, String val);
}
