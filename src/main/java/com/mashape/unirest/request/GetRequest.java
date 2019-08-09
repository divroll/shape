/**
 * Copyright 2018 DIVROLL
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.mashape.unirest.request;

import com.mashape.unirest.Base64;
import com.mashape.unirest.exceptions.BadRequestException;
import com.mashape.unirest.exceptions.HttpRequestException;
import com.mashape.unirest.exceptions.NotFoundRequestException;
import com.mashape.unirest.exceptions.UnauthorizedRequestException;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonHttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.teavm.flavour.widgets.BackgroundWorker;
import org.teavm.interop.Async;
import org.teavm.jso.JSBody;
import org.teavm.jso.ajax.ReadyStateChangeHandler;
import org.teavm.jso.ajax.XMLHttpRequest;
import org.teavm.platform.async.AsyncCallback;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Kerby Martino
 * @version 0-SNAPSHOT
 * @since 0-SNAPSHOT
 */
public class GetRequest {

    static final Logger logger = Logger.getLogger(GetRequest.class.getName());

    private String url;
    private Multimap<String, String> headerMap;
    private Map<String, String> queryMap = null;

    private String authorization = null;

    public GetRequest(String url) {
        setUrl(url);
        headerMap = ArrayListMultimap.create();
    }

    @JSBody(params = {"uri"}, script = "return encodeURI(uri);")
    public static native String encodeURI(String uri);

    public GetRequest header(String header, String value) {
        if (headerMap == null) {
            headerMap = ArrayListMultimap.create();
        }
        if (value != null) {
            headerMap.put(header, value);
        }
        return this;
    }

    public GetRequest queryString(String name, String value) {
        if (queryMap == null) {
            queryMap = new LinkedHashMap<String, String>();
        }
        queryMap.put(name, value);
        return this;
    }

    public GetRequest basicAuth(String username, String password) {
        authorization = "Basic " + Base64.btoa(username + ":" + password);
        return this;
    }

    @Async
    public native HttpResponse<InputStream> asBinary() throws UnirestException;

    private void asBinary(final AsyncCallback<HttpResponse<InputStream>> callback) {

    }

    @Async
    public native HttpResponse<JsonNode> asJson() throws UnirestException;

    public void asJson(final AsyncCallback<HttpResponse<JsonNode>> callback) {
        if (queryMap != null && !queryMap.isEmpty()) {
            url = url + "?";
            url = url + queries(queryMap);
            System.out.println(url);
        }
        BackgroundWorker background = new BackgroundWorker();
        final XMLHttpRequest xhr = XMLHttpRequest.create();
        xhr.open("GET", url);
        if (headerMap != null) {
            // Set default first
            headerMap.put("Content-Type", "application/json");
            headerMap.put("accept", "application/json");
            for (Map.Entry<String, String> entry : headerMap.entries()) {
                if (entry.getKey() != null && entry.getValue() != null
                        && !entry.getKey().isEmpty() && !entry.getValue().isEmpty()) {
                    xhr.setRequestHeader(entry.getKey(), entry.getValue());
                }
            }
        }
        if (authorization != null) {
            xhr.setRequestHeader("Authorization", authorization);
        }
        background.run(new Runnable() {
            public void run() {
                xhr.setOnReadyStateChange(new ReadyStateChangeHandler() {
                    @Override
                    public void stateChanged() {
                        if (xhr.getReadyState() != XMLHttpRequest.DONE) {
                            return;
                        }
                        if (xhr.getStatus() == 400) {
                            callback.error(new BadRequestException(xhr.getStatusText(), xhr.getStatus()));
                            return;
                        } else if (xhr.getStatus() == 401) {
                            callback.error(new UnauthorizedRequestException(xhr.getStatusText(), xhr.getStatus()));
                            return;
                        } else if (xhr.getStatus() == 404) {
                            callback.error(new NotFoundRequestException(xhr.getStatusText(), xhr.getStatus()));
                            return;
                        } else if (xhr.getStatus() >= 400) {
                            callback.error(new HttpRequestException(xhr.getStatusText(), xhr.getStatus()));
                            return;
                        }
                        String responseText = xhr.getResponseText();
                        int status = xhr.getStatus();
                        String statusText = xhr.getStatusText();
                        callback.complete(new JsonHttpResponse(status, statusText, responseText));
                    }
                });
                xhr.send();
            }
        });
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String queries(Map<String, String> parmsRequest) {
        if (parmsRequest == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String k : parmsRequest.keySet()) {
            if (k != null) {
                String encodedURI = encodeURI(parmsRequest.get(k));
                if (sb.length() > 0) {
                    sb.append("&");
                }
                sb.append(k).append("=").append(encodedURI);
            }
        }
        return sb.toString();
    }

}
