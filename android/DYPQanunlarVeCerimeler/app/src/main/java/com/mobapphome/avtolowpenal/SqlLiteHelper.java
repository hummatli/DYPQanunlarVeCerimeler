package com.mobapphome.avtolowpenal;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.mobapphome.avtolowpenal.other.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

public class SqlLiteHelper extends SQLiteOpenHelper{

	//public SQLiteDatabase DB;
	public String DBPath;
	//DBName will be file name to store data
	public static String DBName = Constants.DB_NAME_FOR_VERSION;
	public static final int version = 1;
	public static Context currentContext;


	public SqlLiteHelper(Context context) {
		super(context, DBName, null, version);
		Log.i(Constants.TAG_SQL_LITE_DB_LOG, "SqlLiteHelper constructor");

		currentContext = context;
		DBPath = context.getDatabasePath(DBName).getAbsolutePath();
		Log.i(Constants.TAG_SQL_LITE_DB_LOG, "DBPath = " + DBPath);
		// DBPath will be = "/data/" + getPackageName() + "/databases/yourdatabasename". Default place to store our db files
		// To access to this folder you don't need "WRITE_EXTERNAL_STORAGE" or "READ_EXTERNAL_STORAGE" permission

		//This is for backup db from phone to sd card. When want to use add this permission to Manafest
		//<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
		//backupDB(DBPath+DBName, "currencybackup.db");

		createDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.i(Constants.TAG_SQL_LITE_DB_LOG, "Version changed");
		//1)Something goes wrong when I changing version with id. I could not anaze yet.
		//  Therefore I save version = 1 and change db version with file name. Later if I will have time I look it.

		//2)onUpgrade() calls when calls getReadableDatabase() or getWritableDatabase().
		copyDataBase();

	}

	/**
	 * Look file in DBPath exist , copy from your assets to it if does not exists
	 */
	private void createDatabase() {
		boolean dbExists = checkDbExists();
		Log.i(Constants.TAG_SQL_LITE_DB_LOG, "Check db is :" + dbExists);
		if (dbExists) {
			// do nothing
		} else {
			copyDataBase();
			//DB = currentContext.openOrCreateDatabase(DBName, 0, null);
			//Initialize your db if you need
		}
	}

	//It's for backup. I don't use it yet
	public void backupDB(String currentDBPath, String backupDBPath){
		try {
			File sd = Environment.getExternalStorageDirectory();
			File data = Environment.getDataDirectory();

			if (sd.canWrite()) {
				//String currentDBPath = "/data/" + getPackageName() + "/databases/yourdatabasename";
				//String backupDBPath = "backupname.db";
				File currentDB = new File(currentDBPath);
				File backupDB = new File(sd, backupDBPath);

				if (currentDB.exists()) {
					FileChannel src = new FileInputStream(currentDB).getChannel();
					FileChannel dst = new FileOutputStream(backupDB).getChannel();
					dst.transferFrom(src, 0, src.size());
					src.close();
					dst.close();
				}
			}
		} catch (Exception e) {

		}
	}

	private void copyDataBase()  {
		Log.i(Constants.TAG_SQL_LITE_DB_LOG, "called copyDataBase , start of");
		try {
			InputStream mInputStream = currentContext.getAssets().open(Constants.DB_NAME);
			String outFileName = DBPath;

			File fileDB = new File(outFileName);

			Log.i(Constants.TAG_SQL_LITE_DB_LOG, "In copyDataBase() - DB File exists as file = " + fileDB.exists());
			Log.i(Constants.TAG_SQL_LITE_DB_LOG, "In copyDataBase() - DB File parent psth = "+fileDB.getParentFile().getAbsolutePath() +" , exists  = " + fileDB.getParentFile().exists());


			if ( ! fileDB.getParentFile().exists() ) {
				fileDB.getParentFile().mkdir();
			}

			OutputStream mOutputStream = new FileOutputStream(fileDB);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = mInputStream.read(buffer)) > 0) {
				mOutputStream.write(buffer, 0, length);
			}
			mOutputStream.flush();
			mOutputStream.close();
			mInputStream.close();
			Log.i(Constants.TAG_SQL_LITE_DB_LOG, "DB has copied");

		} catch (Exception e) {
			Log.d(Constants.TAG_SQL_LITE_DB_LOG, "Error when copies", e);
			Crashlytics.logException(e);
			Toast.makeText(currentContext.getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
		}
	}


	private boolean checkDbExists() {
		SQLiteDatabase checkDB = null;

		try {
			String myPath = DBPath;
			File dbFile = new File(myPath);
			Log.i(Constants.TAG_SQL_LITE_DB_LOG, "DB File exists as file = " + dbFile.exists());

			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {
			// database does't exist yet.
			Log.d(Constants.TAG_SQL_LITE_DB_LOG, "Check db", e);
		}

		if (checkDB != null) {
			checkDB.close();
		}

		return checkDB != null ? true : false;
	}

	// closing database
	public void closeDB() {
		Log.i(Constants.TAG_SQL_LITE_DB_LOG, "SqlLiteHelper close called");
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen()) {
			db.close();
		}
		this.close();
	}
}