package com.ldz.fpt.xmlprojectandroid.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ldz.fpt.xmlprojectandroid.model.BaCangModel;
import com.ldz.fpt.xmlprojectandroid.model.DeModel;
import com.ldz.fpt.xmlprojectandroid.model.LoModel;
import com.ldz.fpt.xmlprojectandroid.model.LoXien2Model;
import com.ldz.fpt.xmlprojectandroid.model.LoXien3Model;
import com.ldz.fpt.xmlprojectandroid.model.LoXien4Model;
import com.ldz.fpt.xmlprojectandroid.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linhdq on 6/30/17.
 */

public class DBContext extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "loto_db";

    // User table name
    private static final String TABLE_USER = "user";
    // De table name
    private static final String TABLE_DE = "de";
    // ba cang table name
    private static final String TABLE_BA_CANG = "ba_cang";
    // Lo table name
    private static final String TABLE_LO = "lo";
    // Lo xien 2 table name
    private static final String TABLE_LO_XIEN_2 = "lo_xien_2";
    // Lo xien 3 table name
    private static final String TABLE_LO_XIEN_3 = "lo_xien_3";
    // Lo xien 4 table name
    private static final String TABLE_LO_XIEN_4 = "lo_xien_4";
    // Price table name
    private static final String TABLE_PRICE = "price_table";

    // User Table Columns names
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String FULLNAME = "fullName";
    private static final String PHONENUMBER = "phoneNumber";
    private static final String ROLE = "role";
    private static final String IS_CURRENT = "is_current";

    //De table columns names
    private static final String NUMBER = "number";
    private static final String PRICE = "price";
    private static final String DATE = "date";
    //Lo table columns names
    private static final String POINT = "point";
    //Lo xien 2 table columns names
    private static final String NUMBER_1 = "number_1";
    private static final String NUMBER_2 = "number_2";
    //Lo xien 3 table columns names
    private static final String NUMBER_3 = "number_3";
    //Lo xien 4 table columns names
    private static final String NUMBER_4 = "number_4";

    //Create User table query
    private static final String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + USERNAME + " TEXT PRIMARY KEY," + PASSWORD + " TEXT,"
            + FULLNAME + " TEXT, " + PHONENUMBER + " TEXT, " + ROLE + " INTEGER, "
            + IS_CURRENT + " INTEGER)";
    //Create De table query
    private static final String CREATE_DE_TABLE = "CREATE TABLE " + TABLE_DE + " ("
            + USERNAME + " TEXT, " + NUMBER + " INTEGER, " + PRICE + " INTEGER, " + DATE + " TEXT)";
    //Create Ba cang table query
    private static final String CREATE_BA_CANG_TABLE = "CREATE TABLE " + TABLE_BA_CANG + " ("
            + USERNAME + " TEXT, " + NUMBER + " INTEGER, " + PRICE + " INTEGER, " + DATE + " TEXT)";
    //Create Lo table query
    private static final String CREATE_LO_TABLE = "CREATE TABLE " + TABLE_LO + " ("
            + USERNAME + " TEXT, " + NUMBER + " INTEGER, " + POINT + " FLOAT, " + DATE + " TEXT)";
    //Create Lo xien 2 table query
    private static final String CREATE_LO_XIEN_2_TABLE = "CREATE TABLE " + TABLE_LO_XIEN_2 + " ("
            + USERNAME + " TEXT, " + NUMBER_1 + " INTEGER, " + NUMBER_2 + " INTEGER, " + POINT + " FLOAT, "
            + DATE + " TEXT)";
    //Create Lo xien 3 table query
    private static final String CREATE_LO_XIEN_3_TABLE = "CREATE TABLE " + TABLE_LO_XIEN_3 + " ("
            + USERNAME + " TEXT, " + NUMBER_1 + " INTEGER, " + NUMBER_2 + " INTEGER, " + NUMBER_3 + " INTEGER, "
            + POINT + " FLOAT, " + DATE + " TEXT)";

    //Create Lo xien 4 table query
    private static final String CREATE_LO_XIEN_4_TABLE = "CREATE TABLE " + TABLE_LO_XIEN_4 + " ("
            + USERNAME + " TEXT, " + NUMBER_1 + " INTEGER, " + NUMBER_2 + " INTEGER, " + NUMBER_3 + " INTEGER, "
            + NUMBER_4 + " INTEGER, " + POINT + " FLOAT, " + DATE + " TEXT)";

    public DBContext(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_DE_TABLE);
        db.execSQL(CREATE_BA_CANG_TABLE);
        db.execSQL(CREATE_LO_TABLE);
        db.execSQL(CREATE_LO_XIEN_2_TABLE);
        db.execSQL(CREATE_LO_XIEN_3_TABLE);
        db.execSQL(CREATE_LO_XIEN_4_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        // Create tables again
        onCreate(db);
    }

    /**
     * User table
     */

    public void addUserModel(User model) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USERNAME, model.getUsername());
        values.put(PASSWORD, model.getPassword());
        values.put(FULLNAME, model.getFullName());
        values.put(PHONENUMBER, model.getPhoneNumber());
        values.put(ROLE, model.getRole());
        values.put(IS_CURRENT, model.getIsCurrent());

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection
    }

    public User getUserModel() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "Select * from " + TABLE_USER;
        Cursor cursor = db.rawQuery(sqlQuery, null);
        User model = null;
        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            model = new User();
            model.setUsername(cursor.getString(0));
            model.setPassword(cursor.getString(1));
            model.setFullName(cursor.getString(2));
            model.setPhoneNumber(cursor.getString(3));
            model.setRole(cursor.getInt(4));
        }
        return model;
    }

    public void deleteUserModel() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, null, null);
        db.close();
    }

    public void deleteUserByUsername(String username) {
        String sql = "delete from " + TABLE_USER + " where " + USERNAME + " = '" + username + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
    }

    public User checkLoginSuccess() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "Select * from " + TABLE_USER + " where " + IS_CURRENT + " = 1";
        Cursor cursor = db.rawQuery(sqlQuery, null);
        User model = null;
        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            model = new User();
            model.setUsername(cursor.getString(0));
            model.setPassword(cursor.getString(1));
            model.setFullName(cursor.getString(2));
            model.setPhoneNumber(cursor.getString(3));
            model.setRole(cursor.getInt(4));
            model.setIsCurrent(cursor.getInt(5));
        }
        return model;
    }

    public User checkUserIsExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "Select * from " + TABLE_USER + " where " + USERNAME + " = '" + username + "'";
        Cursor cursor = db.rawQuery(sqlQuery, null);
        User model = null;
        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            model = new User();
            model.setUsername(cursor.getString(0));
            model.setPassword(cursor.getString(1));
            model.setFullName(cursor.getString(2));
            model.setPhoneNumber(cursor.getString(3));
            model.setRole(cursor.getInt(4));
            model.setIsCurrent(cursor.getInt(5));
        }
        return model;
    }

    public int updateUser(User model) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PASSWORD, model.getPassword());
        values.put(FULLNAME, model.getFullName());
        values.put(PHONENUMBER, model.getPhoneNumber());
        values.put(ROLE, model.getRole());

        // updating row
        return db.update(TABLE_USER, values, USERNAME + " = ?",
                new String[]{String.valueOf(model.getUsername())});
    }

    public List<User> getAllAdmins() {
        List<User> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "Select * from " + TABLE_USER + " where " + ROLE + " = 1";
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                User model = new User();
                model.setUsername(cursor.getString(0));
                model.setPassword(cursor.getString(1));
                model.setFullName(cursor.getString(2));
                model.setPhoneNumber(cursor.getString(3));
                model.setRole(cursor.getInt(4));

                // Adding model to list
                list.add(model);
            } while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * De table
     */

    public void addDeModel(DeModel model) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USERNAME, model.getUsername());
        values.put(NUMBER, model.getNumber());
        values.put(PRICE, model.getPrice());
        values.put(DATE, model.getDate());

        // Inserting Row
        db.insert(TABLE_DE, null, values);
        db.close(); // Closing database connection
    }

    public List<DeModel> getAllDeModel() {
        List<DeModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "Select * from " + TABLE_DE;
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                DeModel model = new DeModel();
                model.setUsername(cursor.getString(0));
                model.setNumber(cursor.getInt(1));
                model.setPrice(cursor.getInt(2));
                model.setDate(cursor.getString(3));
                // Adding model to list
                list.add(model);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public List<DeModel> getAllDeModelByDate(String date) {
        List<DeModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "Select * from " + TABLE_DE + " where " + DATE + " = '" + date + "'";
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                DeModel model = new DeModel();
                model.setUsername(cursor.getString(0));
                model.setNumber(cursor.getInt(1));
                model.setPrice(cursor.getInt(2));
                model.setDate(cursor.getString(3));
                // Adding model to list
                list.add(model);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public void deleteDeByDate(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TABLE_DE, DATE + " = ?", new String[]{date});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public List<DeModel> getAllDeModelByNumberAndDate(int number, String date) {
        List<DeModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "Select * from " + TABLE_DE + " where " + NUMBER + " = " + number + " and " + DATE + " = '" + date + "'";
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                DeModel model = new DeModel();
                model.setUsername(cursor.getString(0));
                model.setNumber(cursor.getInt(1));
                model.setPrice(cursor.getInt(2));
                model.setDate(cursor.getString(3));
                // Adding model to list
                list.add(model);
            } while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * Ba cang table
     */

    public void addBaCangModel(BaCangModel model) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USERNAME, model.getUsername());
        values.put(NUMBER, model.getNumber());
        values.put(PRICE, model.getPrice());
        values.put(DATE, model.getDate());

        // Inserting Row
        db.insert(TABLE_BA_CANG, null, values);
        db.close(); // Closing database connection
    }

    public List<BaCangModel> getAllBaCangModel() {
        List<BaCangModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "Select * from " + TABLE_BA_CANG;
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                BaCangModel model = new BaCangModel();
                model.setUsername(cursor.getString(0));
                model.setNumber(cursor.getInt(1));
                model.setPrice(cursor.getInt(2));
                model.setDate(cursor.getString(3));
                // Adding model to list
                list.add(model);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public List<BaCangModel> getAllBaCangModelByDate(String date) {
        List<BaCangModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "Select * from " + TABLE_BA_CANG + " where " + DATE + " = '" + date + "'";
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                BaCangModel model = new BaCangModel();
                model.setUsername(cursor.getString(0));
                model.setNumber(cursor.getInt(1));
                model.setPrice(cursor.getInt(2));
                model.setDate(cursor.getString(3));
                // Adding model to list
                list.add(model);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public void deleteBaCangByDate(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TABLE_BA_CANG, DATE + " = ?", new String[]{date});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public List<BaCangModel> getAllBaCangModelByNumberAndDate(int number, String date) {
        List<BaCangModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "Select * from " + TABLE_BA_CANG + " where " + NUMBER + " = " + number + " and " + DATE + " = '" + date + "'";
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                BaCangModel model = new BaCangModel();
                model.setUsername(cursor.getString(0));
                model.setNumber(cursor.getInt(1));
                model.setPrice(cursor.getInt(2));
                model.setDate(cursor.getString(3));
                // Adding model to list
                list.add(model);
            } while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * Lo table
     */

    public void addLoModel(LoModel model) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USERNAME, model.getUsername());
        values.put(NUMBER, model.getNumber());
        values.put(POINT, model.getPoint());
        values.put(DATE, model.getDate());

        // Inserting Row
        db.insert(TABLE_LO, null, values);
        db.close(); // Closing database connection
    }

    public List<LoModel> getAllLoModel() {
        List<LoModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "Select * from " + TABLE_LO;
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                LoModel model = new LoModel();
                model.setUsername(cursor.getString(0));
                model.setNumber(cursor.getInt(1));
                model.setPoint(cursor.getFloat(2));
                model.setDate(cursor.getString(3));
                // Adding model to list
                list.add(model);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public List<LoModel> getAllLoModelByDate(String date) {
        List<LoModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "Select * from " + TABLE_LO + " where " + DATE + " = '" + date + "'";
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                LoModel model = new LoModel();
                model.setUsername(cursor.getString(0));
                model.setNumber(cursor.getInt(1));
                model.setPoint(cursor.getFloat(2));
                model.setDate(cursor.getString(3));
                // Adding model to list
                list.add(model);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public void deleteLoByDate(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TABLE_LO, DATE + " = ?", new String[]{date});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public List<LoModel> getAllLoModelByNumberAndDate(int number, String date) {
        List<LoModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "Select * from " + TABLE_LO + " where " + NUMBER + " = " + number + " and " + DATE + " = '" + date + "'";
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                LoModel model = new LoModel();
                model.setUsername(cursor.getString(0));
                model.setNumber(cursor.getInt(1));
                model.setPoint(cursor.getFloat(2));
                model.setDate(cursor.getString(3));
                // Adding model to list
                list.add(model);
            } while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * Lo xien 2 table
     */

    public void addLoXien2Model(LoXien2Model model) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USERNAME, model.getUsername());
        values.put(NUMBER_1, model.getNumber1());
        values.put(NUMBER_2, model.getNumber2());
        values.put(POINT, model.getPoint());
        values.put(DATE, model.getDate());

        // Inserting Row
        db.insert(TABLE_LO_XIEN_2, null, values);
        db.close(); // Closing database connection
    }

    public List<LoXien2Model> getAllLoXien2Model() {
        List<LoXien2Model> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "Select * from " + TABLE_LO_XIEN_2;
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                LoXien2Model model = new LoXien2Model();
                model.setUsername(cursor.getString(0));
                model.setNumber1(cursor.getInt(1));
                model.setNumber2(cursor.getInt(2));
                model.setPoint(cursor.getFloat(3));
                model.setDate(cursor.getString(4));
                // Adding model to list
                list.add(model);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public List<LoXien2Model> getAllLoXien2ModelByDate(String date) {
        List<LoXien2Model> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "Select * from " + TABLE_LO_XIEN_2 + " where " + DATE + " = '" + date + "'";
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                LoXien2Model model = new LoXien2Model();
                model.setUsername(cursor.getString(0));
                model.setNumber1(cursor.getInt(1));
                model.setNumber2(cursor.getInt(2));
                model.setPoint(cursor.getFloat(3));
                model.setDate(cursor.getString(4));
                // Adding model to list
                list.add(model);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public void deleteLoXien2ByDate(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TABLE_LO_XIEN_2, DATE + " = ?", new String[]{date});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public List<LoXien2Model> getAllLoXien2ModelByNumberAndDate(int number1, int number2, String date) {
        List<LoXien2Model> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "Select * from " + TABLE_LO_XIEN_2 + " where " + NUMBER_1 + " = " + number1 + " and "
                + NUMBER_2 + " = " + number2 + " and " + DATE + " = '" + date + "'";
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                LoXien2Model model = new LoXien2Model();
                model.setUsername(cursor.getString(0));
                model.setNumber1(cursor.getInt(1));
                model.setNumber2(cursor.getInt(2));
                model.setPoint(cursor.getFloat(3));
                model.setDate(cursor.getString(4));
                // Adding model to list
                list.add(model);
            } while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * Lo xien 3 table
     */

    public void addLoXien3Model(LoXien3Model model) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USERNAME, model.getUsername());
        values.put(NUMBER_1, model.getNumber1());
        values.put(NUMBER_2, model.getNumber2());
        values.put(NUMBER_3, model.getNumber3());
        values.put(POINT, model.getPoint());
        values.put(DATE, model.getDate());

        // Inserting Row
        db.insert(TABLE_LO_XIEN_3, null, values);
        db.close(); // Closing database connection
    }

    public List<LoXien3Model> getAllLoXien3Model() {
        List<LoXien3Model> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "Select * from " + TABLE_LO_XIEN_3;
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                LoXien3Model model = new LoXien3Model();
                model.setUsername(cursor.getString(0));
                model.setNumber1(cursor.getInt(1));
                model.setNumber2(cursor.getInt(2));
                model.setNumber3(cursor.getInt(3));
                model.setPoint(cursor.getFloat(4));
                model.setDate(cursor.getString(5));
                // Adding model to list
                list.add(model);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public List<LoXien3Model> getAllLoXien3ModelByDate(String date) {
        List<LoXien3Model> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "Select * from " + TABLE_LO_XIEN_3 + " where " + DATE + " = '" + date + "'";
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                LoXien3Model model = new LoXien3Model();
                model.setUsername(cursor.getString(0));
                model.setNumber1(cursor.getInt(1));
                model.setNumber2(cursor.getInt(2));
                model.setNumber3(cursor.getInt(3));
                model.setPoint(cursor.getFloat(4));
                model.setDate(cursor.getString(5));
                // Adding model to list
                list.add(model);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public void deleteLoXien3ByDate(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TABLE_LO_XIEN_3, DATE + " = ?", new String[]{date});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public List<LoXien3Model> getAllLoXien3ModelByNumberAndDate(int number1, int number2, int number3, String date) {
        List<LoXien3Model> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "Select * from " + TABLE_LO_XIEN_3 + " where " + NUMBER_1 + " = " + number1 + " and "
                + NUMBER_2 + " = " + number2 + " and " + NUMBER_3 + " = " + number3 + " and " + DATE + " = '" + date + "'";
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                LoXien3Model model = new LoXien3Model();
                model.setUsername(cursor.getString(0));
                model.setNumber1(cursor.getInt(1));
                model.setNumber2(cursor.getInt(2));
                model.setNumber3(cursor.getInt(3));
                model.setPoint(cursor.getFloat(4));
                model.setDate(cursor.getString(5));
                // Adding model to list
                list.add(model);
            } while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * Lo xien 4 table
     */

    public void addLoXien4Model(LoXien4Model model) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USERNAME, model.getUsername());
        values.put(NUMBER_1, model.getNumber1());
        values.put(NUMBER_2, model.getNumber2());
        values.put(NUMBER_3, model.getNumber3());
        values.put(NUMBER_4, model.getNumber4());
        values.put(POINT, model.getPoint());
        values.put(DATE, model.getDate());

        // Inserting Row
        db.insert(TABLE_LO_XIEN_4, null, values);
        db.close(); // Closing database connection
    }

    public List<LoXien4Model> getAllLoXien4Model() {
        List<LoXien4Model> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "Select * from " + TABLE_LO_XIEN_4;
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                LoXien4Model model = new LoXien4Model();
                model.setUsername(cursor.getString(0));
                model.setNumber1(cursor.getInt(1));
                model.setNumber2(cursor.getInt(2));
                model.setNumber3(cursor.getInt(3));
                model.setNumber4(cursor.getInt(4));
                model.setPoint(cursor.getFloat(5));
                model.setDate(cursor.getString(6));
                // Adding model to list
                list.add(model);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public List<LoXien4Model> getAllLoXien4ModelByDate(String date) {
        List<LoXien4Model> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "Select * from " + TABLE_LO_XIEN_4 + " where " + DATE + " = '" + date + "'";
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                LoXien4Model model = new LoXien4Model();
                model.setUsername(cursor.getString(0));
                model.setNumber1(cursor.getInt(1));
                model.setNumber2(cursor.getInt(2));
                model.setNumber3(cursor.getInt(3));
                model.setNumber4(cursor.getInt(4));
                model.setPoint(cursor.getFloat(5));
                model.setDate(cursor.getString(6));
                // Adding model to list
                list.add(model);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public void deleteLoXien4ByDate(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TABLE_LO_XIEN_4, DATE + " = ?", new String[]{date});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public List<LoXien4Model> getAllLoXien4ModelByNumberAndDate(int number1, int number2, int number3, int number4, String date) {
        List<LoXien4Model> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "Select * from " + TABLE_LO_XIEN_4 + " where " + NUMBER_1 + " = " + number1 + " and "
                + NUMBER_2 + " = " + number2 + " and " + NUMBER_3 + " = " + number3 + " and " + NUMBER_4 + " = " + number4 + " and " + DATE + " = '" + date + "'";
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                LoXien4Model model = new LoXien4Model();
                model.setUsername(cursor.getString(0));
                model.setNumber1(cursor.getInt(1));
                model.setNumber2(cursor.getInt(2));
                model.setNumber3(cursor.getInt(3));
                model.setNumber4(cursor.getInt(4));
                model.setPoint(cursor.getFloat(5));
                model.setDate(cursor.getString(6));
                // Adding model to list
                list.add(model);
            } while (cursor.moveToNext());
        }
        return list;
    }

//    public List<ImagePendingModel> getAllImagePendingModel() {
//        List<ImagePendingModel> list = new ArrayList<>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_IMAGE_PENDING + " WHERE " + IS_SUCCESS + " = 0";
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                ImagePendingModel model = new ImagePendingModel();
//                model.setBillId(cursor.getInt(0));
//                model.setBase64Image1(cursor.getString(1));
//                model.setBase64Image2(cursor.getString(2));
//                model.setBase64Image3(cursor.getString(3));
//                model.setBase64Image4(cursor.getString(4));
//                model.setHairModeId(cursor.getInt(5));
//                model.setNote(cursor.getString(6));
//                model.setTime(cursor.getString(7));
//                model.setDate(cursor.getString(8));
//                model.setWaitingForUpload(cursor.getInt(9) == 1);
//                model.setSuccess(cursor.getInt(10) == 1);
//                // Adding model to list
//                list.add(model);
//            } while (cursor.moveToNext());
//        }
//
//        // return list
//        return list;
//    }
//
}
