/*
 * Divroll, Platform for Hosting Static Sites
 * Copyright 2018, Divroll, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package com.mashape.unirest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.teavm.flavour.json.tree.*;

import java.util.*;

/**
 * @author <a href="mailto:kerby@divroll.com">Kerby Martino</a>
 * @version 0-SNAPSHOT
 * @since 0-SNAPSHOT
 */
public class JSONHelper {

    private JSONHelper() {
    }

    public static String transformObjectFieldToArray(String json, List<String> fields) {
        if (json == null) {
            return null;
        }
        JSONObject jsonObject = new JSONObject(json);
        for (String field : fields) {
            try {
                JSONObject fieldObject = jsonObject.getJSONObject(field);
                JSONArray jsonArray = new JSONArray();
                jsonArray.put(fieldObject);
                jsonObject.put(field, jsonArray);
            } catch (Exception e) {

            }
        }
        return jsonObject.toString();
    }

    public static String transformObjectFieldToArray(String container, String json, List<String> fields) {
        if (json == null) {
            return null;
        }
        JSONObject jsonObject = new JSONObject(json);
        JSONObject entityObject = jsonObject.getJSONObject(container);
        for (String field : fields) {
            try {
                JSONObject fieldObject = entityObject.getJSONObject(field);
                JSONArray jsonArray = new JSONArray();
                jsonArray.put(fieldObject);
                entityObject.put(field, jsonArray);
            } catch (Exception e) {

            }
        }
        jsonObject.put(container, entityObject);
        return jsonObject.toString();
    }

    public static String toJSONString(List<Object> list) {
        String json = new JSONArray(list).toString();
        return json;
    }

    public static List<Object> toArray(Node node) {
        List<Object> list = new LinkedList<Object>();
        if (node.isArray()) {
            ArrayNode arrayNode = (ArrayNode) node;
            for (int i = 0; i < arrayNode.size(); i++) {
                Node n = arrayNode.get(i);
                if (n.isNull()) {
                    list.add(null);
                } else if (n.isObject()) {
                    Map<String, Object> map = toMap(n);
                    list.add(map);
                } else if (n.isArray()) {
                    List<Object> array = toArray(n);
                    list.add(array);
                } else if (n.isBoolean()) {
                    BooleanNode booleanNode = (BooleanNode) n;
                    list.add(booleanNode.getValue());
                } else if (n.isNumber()) {
                    NumberNode numberNode = (NumberNode) n;
                    list.add(numberNode.getValue());
                } else if (n.isString()) {
                    StringNode stringNode = (StringNode) n;
                    list.add(stringNode.getValue());
                }
            }
        } else {
            return null;
        }
        return list;
    }

    public static Map<String, Object> toMap(Node node) {
        if (!node.isObject()) {
            return null;
        }
        Map<String, Object> entityMap = new LinkedHashMap<String, Object>();
        if (node.isObject()) {
            ObjectNode objectNode = (ObjectNode) node;
            String[] keys = objectNode.allKeys();
            for (int i = 0; i < keys.length; i++) {
                String key = keys[i];
                Node n = objectNode.get(key);
                if (n.isNull()) {
                    entityMap.put(key, null);
                } else if (n.isObject()) {
                    toMap(n);
                } else if (n.isArray()) {
                    toArray(n);
                } else if (n.isBoolean()) {
                    BooleanNode booleanNode = (BooleanNode) n;
                    entityMap.put(key, booleanNode.getValue());
                } else if (n.isNumber()) {
                    NumberNode numberNode = (NumberNode) n;
                    entityMap.put(key, numberNode.getValue());
                } else if (n.isString()) {
                    StringNode stringNode = (StringNode) n;
                    entityMap.put(key, stringNode.getValue());
                }
            }
        }
        return entityMap;
    }

    public static SafeJSONOArray toJSONArray(List<Object> list) {
        if (list == null) {
            return null;
        }
        SafeJSONOArray jsonoArray = new SafeJSONOArray();
        for (Object obj : list) {
            if (obj instanceof JSONObject) {
                jsonoArray.put(obj);
            } else if (obj instanceof JSONArray) {
                jsonoArray.put(obj);
            } else if (obj instanceof Map) {
                SafeJSONObject safeJSONObject = JSONHelper.toJSONObject((Map<String, Object>) obj);
                jsonoArray.put(safeJSONObject.asJSONObject());
            } else if (obj instanceof List) {
                SafeJSONOArray safeJSONOArray = JSONHelper.toJSONArray((List<Object>) obj);
                jsonoArray.put(safeJSONOArray.asJSONArray());
            } else if (obj instanceof Boolean || boolean.class.getName().equals(obj.getClass().getName())) {
                jsonoArray.put(obj);
            } else if (obj instanceof String) {
                jsonoArray.put(obj);
            } else if (obj instanceof Double || double.class.getName().equals(obj.getClass().getName())) {
                jsonoArray.put(obj);
            } else if (obj instanceof Long || long.class.getName().equals(obj.getClass().getName())) {
                jsonoArray.put(obj);
            } else if (obj instanceof Short || short.class.getName().equals(obj.getClass().getName())) {
                jsonoArray.put(obj);
            } else if (obj instanceof Integer || int.class.getName().equals(obj.getClass().getName())) {
                jsonoArray.put(obj);
            } else {
                throw new IllegalArgumentException("Unsupported property value type " + obj.getClass().getName());
            }
        }
        return jsonoArray;
    }

    public static SafeJSONObject toJSONObject(Map<String, Object> map) {
        SafeJSONObject jsonObject = null;
        if (map != null) {
            Iterator<String> it = ((Map) map).keySet().iterator();
            while (it.hasNext()) {
                if (jsonObject == null) {
                    jsonObject = new SafeJSONObject();
                }
                String key = it.next();
                Object obj = ((Map) map).get(key);
                if (obj != null) {
                    if (obj instanceof JSONObject) {
                        jsonObject.put(key, obj);
                    } else if (obj instanceof JSONArray) {
                        jsonObject.put(key, obj);
                    } else if (obj instanceof Map) {
                        SafeJSONObject safeJSONObject = JSONHelper.toJSONObject((Map<String, Object>) obj);
                        jsonObject.put(key, safeJSONObject.asJSONObject());
                    } else if (obj instanceof List) {
                        SafeJSONOArray safeJSONOArray = JSONHelper.toJSONArray((List<Object>) obj);
                        jsonObject.put(key, safeJSONOArray.asJSONArray());
                    } else if (obj instanceof Boolean || boolean.class.getName().equals(obj.getClass().getName())) {
                        jsonObject.put(key, obj);
                    } else if (obj instanceof String) {
                        jsonObject.put(key, obj);
                    } else if (obj instanceof Double || double.class.getName().equals(obj.getClass().getName())) {
                        jsonObject.put(key, obj);
                    } else if (obj instanceof Long || long.class.getName().equals(obj.getClass().getName())) {
                        jsonObject.put(key, obj);
                    } else if (obj instanceof Short || short.class.getName().equals(obj.getClass().getName())) {
                        jsonObject.put(key, obj);
                    } else if (obj instanceof Integer || int.class.getName().equals(obj.getClass().getName())) {
                        jsonObject.put(key, obj);
                    } else {
                        throw new IllegalArgumentException("Unsupported property value type " + obj.getClass().getName());
                    }
                }
            }
        }
        return jsonObject;
    }

}
