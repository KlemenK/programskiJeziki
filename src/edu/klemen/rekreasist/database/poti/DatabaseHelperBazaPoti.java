package edu.klemen.rekreasist.database.poti;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelperBazaPoti extends SQLiteOpenHelper 
{	
//	public static final String POT="Pot";
//	public static final String AKTIVNOST="Aktivnost";
//	public static final String XKORD="Xkoordinata";
//	public static final String YKORD="Ykoordinata";
	
	public static final  String TAG="DatabaseHelper";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "potiRekreacije";
	private static final String DATABASE_CREATE =

				"create table " +DBAdapterBazaPoti.TABELA_PODATKI + " ("+DBAdapterBazaPoti._ID+" integer primary key autoincrement,"
				+DBAdapterBazaPoti.IDPOTI + " INTEGER not null, "+DBAdapterBazaPoti.XKORD + " DECIMAL not null, "
				+DBAdapterBazaPoti.YKORD+" DECIMAL not null );";

	DatabaseHelperBazaPoti(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, 
			int newVersion) 
	{
		Log.w(TAG, "Upgrading database from version " + oldVersion 
				+ " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS "+DBAdapterBazaPoti.TABLE);
		onCreate(db);
	}

}
