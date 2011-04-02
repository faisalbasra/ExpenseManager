package com.thekyz.expensemanager;

import java.util.Date;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleCursorAdapter;

public class ExpenseManagerActivity extends ListActivity {
    private int mEntryNumber = 1;
    private EntryDbAdapter mDbHelper;
    private String mDateFormat = "dd/MM/yy";
	private static final int INSERT_ID = Menu.FIRST;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.expense_list);
        
        mDbHelper = new EntryDbAdapter(this);
        mDbHelper.open();
        
        fillData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        menu.add(0, INSERT_ID, 0, R.string.menu_insert);
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case INSERT_ID:
        	createEntry();
        	return true;
        }
        
        return super.onOptionsItemSelected(item);
    }

	private void createEntry() {
		String title = "Entry " + mEntryNumber++;
		String day = DateFormat.format(mDateFormat, new Date()).toString();
		String category = "";
		double amount = 0.0;
		int validated = 1;
		int cyclic = 1;
		String comment = "";
		
		mDbHelper.createEntry(title, day, category, amount, validated, cyclic, comment);
		
		fillData();
	}

	private void fillData() {
        // Get all of the notes from the database and create the item list
        Cursor c = mDbHelper.fetchAllNotes();
        startManagingCursor(c);

        String[] from = new String[] { EntryDbAdapter.KEY_TITLE };
        int[] to = new int[] { R.id.text1 };
        
        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter notes =
            new SimpleCursorAdapter(this, R.layout.expense_row, c, from, to);
        setListAdapter(notes);
		
	}
}