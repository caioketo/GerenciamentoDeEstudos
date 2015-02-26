package com.caioketo.gerenciadordeestudos.classes;

import android.content.ContentValues;
import android.database.Cursor;

import com.caioketo.gerenciadordeestudos.database.TabelaMateria;

import org.json.JSONObject;

public class Base {
    private int id;
    private int jsonId;

    public void setJsonId(int id) {
        jsonId = id;
        setId(id);
    }

    public int getJsonId() {
        return jsonId;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int _id) {
        this.id = _id;
    }

    public ContentValues toContent() {
        ContentValues values = new ContentValues();
        values.put("id", id);
        return values;
    }

    public JSONObject toJson() {
        return new JSONObject();
    }

}
