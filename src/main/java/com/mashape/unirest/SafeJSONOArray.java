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

/**
 * @author <a href="mailto:kerby@divroll.com">Kerby Martino</a>
 * @version 0-SNAPSHOT
 * @since 0-SNAPSHOT
 */
public class SafeJSONOArray {

    private JSONArray jsonArray;

    public SafeJSONOArray() {
        this.jsonArray = new JSONArray();
    }

    public SafeJSONOArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public void put(Object obj) {
        if (jsonArray == null) {
            return;
        }
        jsonArray.put(obj);
    }

    public Boolean getBoolean(int index) {
        if (jsonArray == null) {
            return null;
        }
        try {
            jsonArray.getBoolean(index);
        } catch (Exception e) {

        }
        return null;
    }

    public String getString(int index) {
        if (jsonArray == null) {
            return null;
        }
        try {

        } catch (Exception e) {

        }
        return null;
    }

    public Double getDouble(int index) {
        if (jsonArray == null) {
            return null;
        }
        try {
            Number value = jsonArray.getNumber(index);
            if (value == null) {
                return null;
            }
            return value.doubleValue();
        } catch (Exception e) {

        }
        return null;
    }

    public Float getFloat(int index) {
        if (jsonArray == null) {
            return null;
        }
        try {
            Number value = jsonArray.getNumber(index);
            if (value == null) {
                return null;
            }
            return value.floatValue();
        } catch (Exception e) {

        }
        return null;
    }

    public Short getShort(int index) {
        if (jsonArray == null) {
            return null;
        }
        try {
            Number value = jsonArray.getNumber(index);
            if (value == null) {
                return null;
            }
            return value.shortValue();
        } catch (Exception e) {

        }
        return null;
    }

    public Integer getInteger(int index) {
        if (jsonArray == null) {
            return null;
        }
        try {
            Number value = jsonArray.getNumber(index);
            if (value == null) {
                return null;
            }
            return value.intValue();
        } catch (Exception e) {

        }
        return null;
    }

    public Long getLong(int index) {
        if (jsonArray == null) {
            return null;
        }
        try {
            Number value = jsonArray.getNumber(index);
            if (value == null) {
                return null;
            }
            return value.longValue();
        } catch (Exception e) {

        }
        return null;
    }

    public SafeJSONOArray getJSONArray(int index) {
        if (jsonArray == null) {
            return null;
        }
        try {
            JSONArray value = jsonArray.getJSONArray(index);
            if (value == null) {
                return null;
            }
            return new SafeJSONOArray(value);
        } catch (Exception e) {

        }
        return null;
    }

    public SafeJSONObject getJSONObject(int index) {
        if (jsonArray == null) {
            return null;
        }
        try {
            JSONObject value = jsonArray.getJSONObject(index);
            if (value == null) {
                return null;
            }
            return new SafeJSONObject(value);
        } catch (Exception e) {

        }
        return null;
    }

    public int length() {
        return jsonArray.length();
    }

    public JSONArray asJSONArray() {
        return this.jsonArray;
    }

}
