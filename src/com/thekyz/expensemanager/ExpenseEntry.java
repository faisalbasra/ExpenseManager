package com.thekyz.expensemanager;

import java.util.Date;

import android.content.ContentValues;
import android.text.format.DateFormat;

public class ExpenseEntry {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DAY = "day";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_AMOUNT = "amount";
    public static final String KEY_VALIDATED = "validated";
    public static final String KEY_CYCLIC = "cyclic";
    public static final String KEY_COMMENT = "comment";
    
    public static final String[] KEYS_ARRAY = {
    	KEY_ROWID, KEY_TITLE, KEY_DAY, KEY_CATEGORY, KEY_AMOUNT, KEY_VALIDATED, KEY_CYCLIC, KEY_COMMENT
    };
    
    private static int entryNumber = 1;
    private static String dateFormat = "dd/MM/yy";

	private String mTitle;
	private String mDay;
	private String mCategory;
	private double mAmount;
	private int mValidated;
	private int mCyclic;
	private String mComment;
	
	public ExpenseEntry() {
		this.mTitle = "Entry " + entryNumber++;
		this.mDay = DateFormat.format(dateFormat, new Date()).toString();
		this.mCategory = "";
		this.mAmount = 0.0;
		this.mValidated = 0;
		this.mCyclic = 0;
		this.mComment = "";
	}
	
	public ExpenseEntry(String title, String day, String category, double amount,
			int validated, int cyclic, String comment) {
		this.mTitle = title;
		this.mDay = day;
		this.mCategory = category;
		this.mAmount = amount;
		this.mValidated = validated;
		this.mCyclic = cyclic;
		this.mComment = comment;
	}
	
	public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, mTitle);
        values.put(KEY_DAY, mDay);
        values.put(KEY_CATEGORY, mCategory);
        values.put(KEY_AMOUNT, mAmount);
        values.put(KEY_VALIDATED, mValidated);
        values.put(KEY_CYCLIC,mCyclic);
        values.put(KEY_COMMENT, mComment);
        
        return values;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		this.mTitle = title;
	}

	public String getDay() {
		return mDay;
	}

	public void setDay(String day) {
		this.mDay = day;
	}

	public String getCategory() {
		return mCategory;
	}

	public void setCategory(String category) {
		this.mCategory = category;
	}

	public double getAmount() {
		return mAmount;
	}

	public void setAmount(double amount) {
		this.mAmount = amount;
	}

	public int getValidated() {
		return mValidated;
	}

	public void setValidated(int validated) {
		this.mValidated = validated;
	}

	public int getCyclic() {
		return mCyclic;
	}

	public void setCyclic(int cyclic) {
		this.mCyclic = cyclic;
	}

	public String getComment() {
		return mComment;
	}

	public void setComment(String comment) {
		this.mComment = comment;
	}
}
