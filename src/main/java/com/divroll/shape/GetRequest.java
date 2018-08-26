/**
 *
 * Copyright 2018 DIVROLL
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */
package com.divroll.shape;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.teavm.flavour.widgets.BackgroundWorker;
import org.teavm.jso.JSBody;
import org.teavm.jso.ajax.ReadyStateChangeHandler;
import org.teavm.jso.ajax.XMLHttpRequest;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 *
 */
public class GetRequest {

    static final Logger logger = Logger.getLogger(GetRequest.class.getName());

    private String url;
    private Multimap<String,String> headerMap;
    private Map<String, String> queryMap = null;
    private Map<String,Object> fields = null;

    private String authorization = null;
    private int TIMEOUT = 60000;

    public GetRequest(String url) {
        setUrl(url);
        headerMap = ArrayListMultimap.create();
    }

    public GetRequest header(String header, String value) {
        if(headerMap == null){
            headerMap = ArrayListMultimap.create();
        }
        if(value != null) {
            headerMap.put(header, value);
        }
        return this;
    }

    public GetRequest queryString(String name, String value){
        if(queryMap == null){
            queryMap = new LinkedHashMap<String,String>();
        }
        queryMap.put(name, value);
        return this;
    }

    public GetRequest basicAuth(String username, String password) {
        authorization = "Basic " + Base64.btoa(username + ":" + password);
        return this;
    }

    public void asJson(final AsyncCallback<String> callback){
        if(queryMap != null && !queryMap.isEmpty()){
            url = url + "?";
            url = url +  queries(queryMap);
        }
        BackgroundWorker background = new BackgroundWorker();
        final XMLHttpRequest xhr = XMLHttpRequest.create();
        xhr.open("GET", url);
        if(headerMap != null){
            // Set default first
            headerMap.put("Content-Type", "application/json");
            headerMap.put("accept", "application/json");
            for (Map.Entry<String,String> entry : headerMap.entries()) {
                if(entry.getKey() != null && entry.getValue() != null
                        && !entry.getKey().isEmpty() && !entry.getValue().isEmpty()) {
                    xhr.setRequestHeader(entry.getKey(), entry.getValue());
                }
            }
        }
        if(authorization != null){
            xhr.setRequestHeader("Authorization", authorization);
        }
        try {
            background.run(new Runnable() {
                public void run() {
                    xhr.setOnReadyStateChange(new ReadyStateChangeHandler() {
                        @Override
                        public void stateChanged() {
                            if(xhr.getReadyState() != XMLHttpRequest.DONE) {
                                return;
                            }
                            if(xhr.getStatus() >= 400) {
                                callback.onFailure(new HttpRequestException(xhr.getStatusText(), xhr.getStatus()));
                                return;
                            }
                            String responseText = xhr.getResponseText();
                            callback.onSuccess(responseText);
                        }
                    });
                    xhr.send();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            callback.onFailure(ex);
        }
    }

//    public void asJsonp(final AsyncCallback<String> callback) {
//        if(queryMap != null && !queryMap.isEmpty()){
//            url = url + "?";
//            url = url +  queries(queryMap);
//        }
//        JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
//        jsonp.requestObject(url, new AsyncCallback<JavaScriptObject>() {
//                    public void onFailure(Throwable throwable) {
//                        callback.onFailure(throwable);
//                    }
//                    public void onSuccess(JavaScriptObject response) {
//                        callback.onSuccess(JsonUtils.stringify(response));
//                    }
//                });
//    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String queries(Map<String,String> parmsRequest){
        StringBuilder sb = new StringBuilder();
        for ( String k: parmsRequest.keySet() ) {
            String vx = encodeURI( parmsRequest.get(k));
            if ( sb.length() > 0 ) {
                sb.append("&");
            }
            sb.append(k).append("=").append(vx);
        }
        return sb.toString();
    }

    public void setTimeout(int timeout) {
        this.TIMEOUT = timeout;
    }

    @JSBody(params = { "uri" }, script = "encodeURI(uri);")
    public static native String encodeURI(String uri);

}
