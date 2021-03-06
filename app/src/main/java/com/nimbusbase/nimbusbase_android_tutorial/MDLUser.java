package com.nimbusbase.nimbusbase_android_tutorial;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Will on 11/10/14.
 */
public class MDLUser implements PGRecord {
    final Long
            id;
    final String
            email,
            name;
    final Integer
            age,
            gender;

    public static final class Attribute {
        public static final String
                id = "id",
                email = "email",
                name = "name",
                age = "age",
                gender = "gender";
    }
    public static final String
            ENTITY_NAME = "User";
    public static final String
            SQL_CREATE_TABLE = "CREATE TABLE "+ ENTITY_NAME +" ( "
            + Attribute.id + " INTEGER PRIMARY KEY, "
            + Attribute.email + " VARCHAR NOT NULL, "
            + Attribute.name + " NVARCHAR, "
            + Attribute.age + " INTEGER, "
            + Attribute.gender + " TINYINT "
            + " ); ";
    public static final HashMap<String, Integer>
            ATTRIBUTE_TYPE_BY_NAME = new HashMap<String, Integer>(4) {{
        put(MDLUser.Attribute.name, Cursor.FIELD_TYPE_STRING);
        put(MDLUser.Attribute.email, Cursor.FIELD_TYPE_STRING);
        put(MDLUser.Attribute.age, Cursor.FIELD_TYPE_INTEGER);
        put(MDLUser.Attribute.gender, Cursor.FIELD_TYPE_INTEGER);
    }};
    public String getEntityName() {
        return ENTITY_NAME;
    }

    protected MDLUser(Cursor cursor) {
        this.id = cursor.getLong(cursor.getColumnIndex(Attribute.id));
        this.email = cursor.getString(cursor.getColumnIndex(Attribute.email));
        this.name = cursor.getString(cursor.getColumnIndex(Attribute.name));
        this.age = cursor.getInt(cursor.getColumnIndex(Attribute.age));
        this.gender = cursor.getInt(cursor.getColumnIndex(Attribute.gender));
    }

    public boolean delete(SQLiteDatabase writableDB) {
        return writableDB.delete(getEntityName(), Attribute.id + " == ? ", new String[]{id.toString(),}) > 0;
    }

    public static List<MDLUser> fetchAll(SQLiteDatabase readableDB) {
        final Cursor
                cursor = readableDB.query(
                ENTITY_NAME, null, null, null, null, null, null
        );
        final List<MDLUser>
                users = new ArrayList<MDLUser>(cursor.getCount());
        while (cursor.moveToNext()) {
            users.add(new MDLUser(cursor));
        }

        return users;
    }

    @Override
    public String getTitle() {
        return name != null ? name : id.toString();
    }

    @Override
    public String getSummary() {
        return email;
    }
}
