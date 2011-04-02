package com.thekyz.expensemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Simple notes database access helper class. Defines the basic CRUD operations
 * for the notepad example, and gives the ability to list all notes as well as
 * retrieve or modify a specific note.
 */
public class EntryDbAdapter {

    private static final String TAG = "EntryDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE = "expenses";
    private static final int DATABASE_VERSION = 2;

    /**
     * Database creation SQL statement
     */
    private static final String DATABASE_CREATE =
        "create table " + DATABASE_TABLE + " ("
        + ExpenseEntry.KEY_ROWID + " integer primary key autoincrement, "
        + ExpenseEntry.KEY_TITLE + " text not null, "
        + ExpenseEntry.KEY_DAY + " text not null, "
        + ExpenseEntry.KEY_CATEGORY + " text not null, "
        + ExpenseEntry.KEY_AMOUNT + " real not null,"
        + ExpenseEntry.KEY_VALIDATED + " integer not null, "
        + ExpenseEntry.KEY_CYCLIC + " integer not null, "
        + ExpenseEntry.KEY_COMMENT + " text not null);";

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * 
     * @param ctx the Context within which to work
     */
    public EntryDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * Open the expense database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     * 
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public EntryDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }


    /**
     * Create a new entry using the values provided. If the entry is
     * successfully created return the new rowId for it, otherwise return
     * a -1 to indicate failure.
     * 
     * @param entry the entry's values to add
     * @return rowId or -1 if failed
     */
    public long createEntry(ContentValues entry) {
        return mDb.insert(DATABASE_TABLE, null, entry);
    }

    /**
     * Delete the entry with the given rowId
     * 
     * @param rowId id of entry to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteEntry(long rowId) {
        return mDb.delete(DATABASE_TABLE, ExpenseEntry.KEY_ROWID + "=" + rowId, null) > 0;
    }

    /**
     * Return a Cursor over the list of all entries in the database
     * 
     * @return Cursor over all entries
     */
    public Cursor fetchAllNotes() {

        return mDb.query(DATABASE_TABLE, ExpenseEntry.KEYS_ARRAY, null, null, null, null, null);
    }

    /**
     * Return a Cursor positioned at the entry that matches the given rowId
     * 
     * @param rowId id of entry to retrieve
     * @return Cursor positioned to matching entry, if found
     * @throws SQLException if entry could not be found/retrieved
     */
    public Cursor fetchNote(long rowId) throws SQLException {
        Cursor mCursor =
            mDb.query(true, DATABASE_TABLE, ExpenseEntry.KEYS_ARRAY,
            		ExpenseEntry.KEY_ROWID + "=" + rowId, null,
                    null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;
    }

    /**
     * Update the entry using the details provided. The entry to be updated is
     * specified using the rowId, and it is altered to use the parameter
     * values passed in
     * 
     * @param rowId id of note to update
     * @param entry the entry's values
     * @return true if the note was successfully updated, false otherwise
     */
    public boolean updateNote(long rowId, ContentValues entry) {
        return mDb.update(DATABASE_TABLE, entry, ExpenseEntry.KEY_ROWID + "=" + rowId, null) > 0;
    }
}
