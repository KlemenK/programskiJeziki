package edu.klemen.rekreasist.database.poti;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import edu.klemen.rekreAsist.android.PodatkiZaPoti;



public class DBAdapterBazaPoti implements BaseColumns {
	public static final  String TAG="RekreacijskaPotPodatki";

	
//	public double pot;// razdalja
//	public int aktivnost;//tek, hoja, kolesarjenje
//	public long id;
//	public double Xkord;// koordinati X in Y
//	public double Ykord;
	
	public static final String IDPOTI="IdPoti";
	public static final String XKORD="Xkoordinata";
	public static final String YKORD="Ykoordinata";
	
	public static final  int POS__ID=0;
	public static final  int POS__IDPOTI=1;
	public static final  int POS__XKORD=2;
	public static final  int POS__YKORD=3;
	
	
	


	public static final  String TABLE="podatkipot";
	public static final  String TABELA_PODATKI="podatkipot1";


	private final Context context;
	
	private DatabaseHelperBazaPoti DBHelper;
	private SQLiteDatabase db;

	public DBAdapterBazaPoti(Context ctx) 
	{
		this.context = ctx;
		DBHelper = new DatabaseHelperBazaPoti(context);
	}


	//---opens the database---
	public DBAdapterBazaPoti open() throws SQLException 
	{
		db = DBHelper.getWritableDatabase();
		return this;
	}

	//---closes the database---    
	public void close() 
	{
		DBHelper.close();
	}

	//---insert a stevec
	public long insertPodatki(PodatkiZaPoti tmp) 
	{
		ContentValues initialValues = new ContentValues();
		
		initialValues.put(IDPOTI, tmp.id); 
		initialValues.put(XKORD, tmp.Xkord); 
		initialValues.put(YKORD, tmp.Ykord); 

		
		return db.insert(TABELA_PODATKI, null, initialValues);
		//return 1;
	}

	//---deletes a particular title---
	public boolean deletePodatek(long rowId) 
	{
		return db.delete(TABELA_PODATKI, _ID + "=" + rowId, null) > 0;
	}

	//---retrieves all the titles---
	public Cursor getAll() 
	{
		return db.query(TABELA_PODATKI, new String[] {
				_ID,       //POS__ID=0;
				    //POS_NAME=1
				IDPOTI,
				XKORD,
				YKORD//POS_VALUE =2
				},
				null, 
				null, 
				null,
				null, 
				null);
	}
	//vrne doloceno pot
	public Cursor getRoute(int index) 
	{
		return db.query(TABELA_PODATKI, new String[] {
				_ID,       //POS__ID=0;
				    //POS_NAME=1
				IDPOTI,
				XKORD,
				YKORD//POS_VALUE =2
				},
				IDPOTI + "=" +index, //vzamemo vse katere majo IDpoti enake kot podan index
				null, 
				null,
				null, 
				null);
	}

	//---retrieves a particular title---
	public Cursor getStevec(long rowId) throws SQLException 
	{
		Cursor mCursor =
			db.query(true, TABLE, new String[] {
					_ID,
					IDPOTI,
					XKORD,
					YKORD,
					}, 
					_ID + "=" + rowId, 
					null,
					null, 
					null, 
					null,
					null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	//---update---
	public boolean updateStevec() 
	{
		/*ContentValues args = new ContentValues();
		args.put(NAME, tmp.getIme());
		args.put(STANJE, tmp.getTock());
		return db.update(TABLE, args, 
				_ID + "=" + tmp.getId(), null) > 0;*/
		return false;
	}
}
