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
package com.mashape.unirest.http;

import com.mashape.unirest.Method;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;

/**
 * Fluent HTTP Client wrapper for TeaVM
 *
 * @author Kerby Martino
 * @version 0-SNAPSHOT
 * @since 0-SNAPSHOT
 */
public class Unirest {


    public static GetRequest get(String url) {
        return new GetRequest(url);
    }



    public static GetRequest head(String url) {
        return new GetRequest(url);
    }



    public static HttpRequestWithBody post(String url) {
        return new HttpRequestWithBody(url, Method.POST);
    }



    public static HttpRequestWithBody put(String url) {
        return new HttpRequestWithBody(url, Method.PUT);
    }



    public static HttpRequestWithBody delete(String url) {
        return new HttpRequestWithBody(url, Method.DELETE);
    }



    //public static HttpRequestWithBody patch(String url){
    //    return new HttpRequestWithBody(url, Method.PATCH);
    //};
    //public static HttpRequestWithBody options(String url){
    //    return new HttpRequestWithBody(url, Method.OPTIONS);
    //};
}
