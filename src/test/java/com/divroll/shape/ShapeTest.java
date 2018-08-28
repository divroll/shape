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
package com.divroll.shape;

import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.teavm.junit.SkipJVM;
import org.teavm.junit.TeaVMTestRunner;

import java.io.IOException;
import java.util.logging.Logger;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * Unit tests of {@link ShapeTest}
 *
 * @author Kerby Martino
 * @version 0-SNAPSHOT
 * @since 0-SNAPSHOT
 */
@RunWith(TeaVMTestRunner.class)
@SkipJVM
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShapeTest {

    static final Logger logger = Logger.getLogger(ShapeTest.class.getName());
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testGet() {
        System.out.println("ShapeTest - testGet");
        String response = Shape.get("https://httpbin.org/get")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .asJson();

        JSONObject json = new JSONObject(response);
        String url = json.getString("url");
        JSONObject headers = json.getJSONObject("headers");
        assertNotNull(json);
        assertNotNull(url);
        assertNotNull(headers);

        System.out.println(json.toString());
    }

    @Test
    public void testGetQuery() {
        System.out.println("ShapeTest - testGetQuery");
        String response = Shape.get("https://httpbin.org/get")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .queryString("name", "Mark")
                .asJson();

        JSONObject json = new JSONObject(response);
        String url = json.getString("url");
        JSONObject args = json.getJSONObject("args");
        JSONObject headers = json.getJSONObject("headers");

        assertNotNull(json);
        assertNotNull(url);
        assertNotNull(headers);
        assertNotNull(args);

        System.out.println(json.toString());
    }

    @Test
    public void testPostFormField() throws IOException {
        System.out.println("ShapeTest - testPostFormField");
        String response = Shape.post("https://httpbin.org/post")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .queryString("name", "Mark")
                .field("middle", "O")
                .field("last", "Polo")
                .asJson();

        System.out.println("Response: " + response);

        assertNotNull(response);

        JSONObject json = new JSONObject(response);
        String url = json.getString("url");
        JSONObject headers = json.getJSONObject("headers");
        JSONObject args = json.getJSONObject("args");

        assertNotNull(json);
        assertNotNull(url);
        assertNotNull(headers);
        assertNotNull(args);

        String name = args.getString("name");
        assertEquals("Mark", name);
    }

    @Test
    public void testPostJson() throws IOException {
        JSONObject payload = new JSONObject();
        payload.put("hello", "world");
        String response = Shape.post("https://httpbin.org/post")
                .queryString("name", "Mark")
                .body(payload.toString())
                .asJson();
        JSONObject json = new JSONObject(response);
        String url = json.getString("url");
        JSONObject headers = json.getJSONObject("headers");
        JSONObject jsonField = json.getJSONObject("json");

        assertNotNull(json);
        assertNotNull(url);
        assertNotNull(headers);
        assertNotNull(jsonField);

        String accept = headers.getString("Accept");
        String contentType = headers.getString("Content-Type");
        String hello = jsonField.getString("hello");

        assertEquals("application/json", accept);
        assertEquals("application/json", contentType);
        assertEquals("world", hello);
    }

    @Test
    public void testPut() throws IOException {
        JSONObject payload = new JSONObject();
        payload.put("hello", "world");

        String response = Shape.put("https://httpbin.org/put")
                .queryString("name", "Mark")
                .body(payload.toString())
                .basicAuth("john", "doe")
                .asJson();

        JSONObject json = new JSONObject(response);
        String url = json.getString("url");
        JSONObject headers = json.getJSONObject("headers");
        JSONObject jsonField = json.getJSONObject("json");

        assertNotNull(json);
        assertNotNull(url);
        assertNotNull(headers);
        assertNotNull(jsonField);

        String accept = headers.getString("Accept");
        String authorization = headers.getString("Authorization");
        String contentType = headers.getString("Content-Type");
        String hello = jsonField.getString("hello");

        assertEquals("application/json", accept);
        assertEquals("application/json", contentType);
        assertEquals("world", hello);

        String actual = "Basic " + Base64.btoa("john" + ":" + "doe");

        System.out.println(authorization);
        System.out.println(actual);

        assertEquals(actual, authorization);

    }

    public void testDelete() throws IOException {
        JSONObject payload = new JSONObject();
        payload.put("hello", "world");

        String response = Shape.delete("https://httpbin.org/delete")
                .queryString("name", "Mark")
                .body(payload)
                .basicAuth("john", "doe")
                .asJson();

        JSONObject json = new JSONObject(response);
        String url = json.getString("url");
        JSONObject headers = json.getJSONObject("headers");

        assertNotNull(json);
        assertNotNull(url);
        assertNotNull(headers);

        String accept = headers.getString("Accept");
        String authorization = headers.getString("Authorization");
        String contentType = headers.getString("Content-Type");

        assertEquals("application/json", accept);
        assertEquals("application/json", contentType);
        //assertEquals("world", hello);

        String actual = "Basic " + Base64.btoa("john" + ":" + "doe");
        assertEquals(actual, authorization);

    }


}
