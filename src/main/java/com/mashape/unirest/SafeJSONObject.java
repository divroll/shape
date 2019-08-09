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

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:kerby@divroll.com">Kerby Martino</a>
 * @version 0-SNAPSHOT
 * @since 0-SNAPSHOT
 */
public class SafeJSONObject {

    private JSONObject jsonObject;

    public SafeJSONObject() {
        this.jsonObject = new JSONObject();
    }

    public SafeJSONObject(String json) {
        try {
            this.jsonObject = new JSONObject(json);
        } catch (Exception e) {

        }
    }

    public SafeJSONObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public Object get(String key) {
        if (jsonObject == null) {
            return null;
        }
        try {
            Object value = jsonObject.get(key);
            return value;
        } catch (Exception e) {

        }
        return null;
    }

    public void put(String key, Object value) {
        if (value != null && jsonObject != null) {
            jsonObject.put(key, value);
        }
    }

    public Boolean getBoolean(String key) {
        if (jsonObject == null) {
            return null;
        }
        try {
            Boolean value = jsonObject.getBoolean(key);
            return value;
        } catch (Exception e) {

        }
        return null;
    }

    public String getString(String key) {
        if (jsonObject == null) {
            return null;
        }
        try {
            String value = jsonObject.getString(key);
            return value;
        } catch (Exception e) {

        }
        return null;
    }

    public Double getDouble(String key) {
        if (jsonObject == null) {
            return null;
        }
        try {
            Number value = jsonObject.getNumber(key);
            if (value == null) {
                return null;
            }
            return value.doubleValue();
        } catch (Exception e) {

        }
        return null;
    }

    public Float getFloat(String key) {
        if (jsonObject == null) {
            return null;
        }
        try {
            Number value = jsonObject.getNumber(key);
            if (value == null) {
                return null;
            }
            return value.floatValue();
        } catch (Exception e) {

        }
        return null;
    }

    public Short getShort(String key) {
        if (jsonObject == null) {
            return null;
        }
        try {
            Number value = jsonObject.getNumber(key);
            if (value == null) {
                return null;
            }
            return value.shortValue();
        } catch (Exception e) {

        }
        return null;
    }

    public Integer getInteger(String key) {
        if (jsonObject == null) {
            return null;
        }
        try {
            Number value = jsonObject.getNumber(key);
            if (value == null) {
                return null;
            }
            return value.intValue();
        } catch (Exception e) {

        }
        return null;
    }

    public Long getLong(String key) {
        if (jsonObject == null) {
            return null;
        }
        try {
            Number value = jsonObject.getNumber(key);
            if (value == null) {
                return null;
            }
            return value.longValue();
        } catch (Exception e) {

        }
        return null;
    }

    public SafeJSONOArray getJSONArray(String key) {
        if (jsonObject == null) {
            return null;
        }
        try {
            JSONArray value = jsonObject.getJSONArray(key);
            if (value == null) {
                return null;
            }
            return new SafeJSONOArray(value);
        } catch (Exception e) {

        }
        return null;
    }

    public SafeJSONObject getJSONObject(String key) {
        if (jsonObject == null) {
            return null;
        }
        try {
            JSONObject value = jsonObject.getJSONObject(key);
            if (value == null) {
                return null;
            }
            return new SafeJSONObject(value);
        } catch (Exception e) {

        }
        return null;
    }

    public Set<String> keySet() {
        if (jsonObject == null) {
            return new HashSet<>();
        }
        return jsonObject.keySet();
    }

    public JSONObject asJSONObject() {
        return this.jsonObject;
    }
}
