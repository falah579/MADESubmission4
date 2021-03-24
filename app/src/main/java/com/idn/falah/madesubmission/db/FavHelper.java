package com.idn.falah.madesubmission.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.idn.falah.madesubmission.model.Favorite;

import java.util.ArrayList;

public class FavHelper {
    private static final String DATABASE_TABLE = DatabaseContract.TABLE_FAVORITE;

    private static DatabaseHelper databaseHelper;
    private static FavHelper INSTANCE;

    private static SQLiteDatabase database;

    private FavHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static FavHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }
    public void close() {
        databaseHelper.close();
        if (database.isOpen())
            database.close();
    }
    /* CRUD OPERATIONS */
    public ArrayList<Favorite> getAllFavs() {
        ArrayList<Favorite> arrayList = new ArrayList<>();
        Log.d("run", "till here -3");
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                BaseColumns._ID + " ASC",
                null);
        Log.d("run", "till here -2");
        cursor.moveToFirst();
        Favorite favorite;
        Log.d("run", "till here -1");
        if (cursor.getCount() > 0) {
            Log.d("run", "till here");
            do {
                Log.d("run", "till here2");
                favorite = new Favorite();
                favorite.setId(cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID)));
                favorite.setMovieId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.MOVIE_ID)));
                favorite.setMovieTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.MOVIE_TITLE)));
                favorite.setMovieDescription(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.MOVIE_DESCRIPTION)));
                favorite.setMoviePoster(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.MOVIE_POSTER)));
                favorite.setMovieDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.MOVIE_DATE)));
                favorite.setMovieRating(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.MOVIE_RATING)));
                favorite.setMovieEliminator(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.MOVIE_ELIMINATOR)));

                arrayList.add(favorite);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
            Log.d("run", "till here3");
        }
        Log.d("run", "till here4");
        cursor.close();
        return arrayList;
    }

    public long insertFav(Favorite favorite) {
        ContentValues args = new ContentValues();
        args.put(DatabaseContract.FavoriteColumns.MOVIE_ID, favorite.getMovieId());
        args.put(DatabaseContract.FavoriteColumns.MOVIE_TITLE, favorite.getMovieTitle());
        args.put(DatabaseContract.FavoriteColumns.MOVIE_DESCRIPTION, favorite.getMovieDescription());
        args.put(DatabaseContract.FavoriteColumns.MOVIE_POSTER, favorite.getMoviePoster());
        args.put(DatabaseContract.FavoriteColumns.MOVIE_RATING, favorite.getMovieRating());
        args.put(DatabaseContract.FavoriteColumns.MOVIE_DATE, favorite.getMovieDate());
        args.put(DatabaseContract.FavoriteColumns.MOVIE_ELIMINATOR, favorite.getMovieEliminator());

        Long result = database.insert(DATABASE_TABLE, null, args);


        Log.d("retro favhelperinsert", favorite.getMovieId() + ", title"+favorite.getMovieTitle() + " and result " + result);

        return result;
    }

    public int updateFav(Favorite favorite) {
        ContentValues args = new ContentValues();
        args.put(DatabaseContract.FavoriteColumns.MOVIE_ID, favorite.getMovieId());
        args.put(DatabaseContract.FavoriteColumns.MOVIE_TITLE, favorite.getMovieTitle());
        args.put(DatabaseContract.FavoriteColumns.MOVIE_DESCRIPTION, favorite.getMovieDescription());
        args.put(DatabaseContract.FavoriteColumns.MOVIE_POSTER, favorite.getMoviePoster());
        args.put(DatabaseContract.FavoriteColumns.MOVIE_RATING, favorite.getMovieRating());
        args.put(DatabaseContract.FavoriteColumns.MOVIE_DATE, favorite.getMovieDate());
        args.put(DatabaseContract.FavoriteColumns.MOVIE_ELIMINATOR, favorite.getMovieEliminator());

        return database.update(DATABASE_TABLE, args, BaseColumns._ID + "= '" + favorite.getId() + "'", null);
    }

    public int deletefav(int movieId) {
        return database.delete(DatabaseContract.TABLE_FAVORITE, DatabaseContract.FavoriteColumns.MOVIE_ID + " = '" + movieId + "'", null);
    }
}
