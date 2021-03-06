package com.thekyz.expensemanager;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleCursorAdapter;

public class ExpenseManagerActivity extends ListActivity {
    private EntryDbAdapter mDbHelper;
	private static final int INSERT_ID = Menu.FIRST;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.expense_list);
        mDbHelper = new EntryDbAdapter(this);
        mDbHelper.open();
        mDbHelper.flush();
        
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
		mDbHelper.createEntry(new ExpenseEntry().getContentValues());
		
		fillData();
	}

	private void fillData() {
        // Get all of the notes from the database and create the item list
        Cursor c = mDbHelper.fetchAllNotes();
        startManagingCursor(c);

        String[] from = new String[] {
        		ExpenseEntry.KEY_DAY, ExpenseEntry.KEY_TITLE,
        		ExpenseEntry.KEY_CATEGORY, ExpenseEntry.KEY_AMOUNT };
        int[] to = new int[] { R.id.day, R.id.title, R.id.category, R.id.amount };
        
        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter expenses =
            new SimpleCursorAdapter(this, R.layout.expense_row, c, from, to);

        setListAdapter(expenses);
	}
}