package com.mobapphome.avtolowpenal;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mobapphome.avtolowpenal.other.ALArticle;
import com.mobapphome.avtolowpenal.other.ALChapter;
import com.mobapphome.avtolowpenal.other.ALChapterArticle;
import com.mobapphome.avtolowpenal.other.Constants;
import com.mobapphome.avtolowpenal.other.PParArtSubArt;
import com.mobapphome.avtolowpenal.other.PParentArticle;
import com.mobapphome.avtolowpenal.other.PSubArticle;

import java.util.LinkedList;
import java.util.List;

public class SqlMethods {


    static public List<ALChapterArticle> readALChapterArticles(SqlLiteHelper myDbHelper) {
        Log.i(Constants.TAG_SQL_LITE_DB_LOG, "ReadALChapterArticles");

        List<ALChapter> chapters = new LinkedList<>();
        List<ALChapterArticle> retItems = new LinkedList<>();

        SQLiteDatabase db = myDbHelper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT id, name, desc FROM chapters order by id ", null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    int id = c.getInt(c.getColumnIndex("id"));
                    String name = c.getString(c.getColumnIndex("name"));
                    String desc = c.getString(c.getColumnIndex("desc"));
                    chapters.add(new ALChapter(id, name, desc));
                } while (c.moveToNext());
            }
        }
        c.close();

        for (int i = 0; i < chapters.size(); ++i) {

            ALChapter chpater = chapters.get(i);
            retItems.add(new ALChapterArticle(null, chpater));

            Cursor cArticles = db.rawQuery("SELECT id, name, desc_url, type_id FROM articles where type_id = " + chpater.getId() + " order by id", null);
            if (cArticles != null) {
                if (cArticles.moveToFirst()) {
                    do {
                        int id = cArticles.getInt(cArticles.getColumnIndex("id"));
                        String name = cArticles.getString(cArticles.getColumnIndex("name"));
                        String desc_url = cArticles.getString(cArticles.getColumnIndex("desc_url"));
                        int type_id = cArticles.getInt(cArticles.getColumnIndex("type_id"));
                        retItems.add(new ALChapterArticle(new ALArticle(id, name, desc_url, type_id), null));
                    } while (cArticles.moveToNext());
                }
            }
            cArticles.close();
        }

        db.close();

        return retItems;
    }


    static public List<ALChapterArticle> searchALChapterArticles(SqlLiteHelper myDbHelper, String queryStr) {
        Log.i(Constants.TAG_SQL_LITE_DB_LOG, "ReadALChapterArticles");

        List<ALChapter> chapters = new LinkedList<>();
        List<ALChapterArticle> retItems = new LinkedList<>();

        SQLiteDatabase db = myDbHelper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT id, name, desc FROM chapters order by id ", null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    int id = c.getInt(c.getColumnIndex("id"));
                    String name = c.getString(c.getColumnIndex("name"));
                    String desc = c.getString(c.getColumnIndex("desc"));
                    chapters.add(new ALChapter(id, name, desc));
                } while (c.moveToNext());
            }
        }
        c.close();

        for (int i = 0; i < chapters.size(); ++i) {

            ALChapter chapter = chapters.get(i);

            boolean addParentArt = true;
            Cursor cs = db.rawQuery("select id, name, desc_url, type_id "
                    + " from articles where type_id = " + chapter.getId() + " order by id", null);
            int index = 0;
            if (cs != null) {
                if (cs.moveToFirst()) {
                    do {
                        int id = cs.getInt(cs.getColumnIndex("id"));
                        String name = cs.getString(cs.getColumnIndex("name"));
                        String desc = cs.getString(cs.getColumnIndex("desc_url"));
                        int typeId = cs.getInt(cs.getColumnIndex("type_id"));

                        if (name.toLowerCase().indexOf(queryStr.trim().toLowerCase()) > 0 || Utils.htmlToText(desc).toLowerCase().indexOf(queryStr.trim().toLowerCase()) > 0) {
                            if (addParentArt) {
                                retItems.add(new ALChapterArticle(null, chapter));
                                addParentArt = false;
                            }

                            retItems.add(new ALChapterArticle(new ALArticle(id, name, desc, typeId), null));
                        }
                    } while (cs.moveToNext());
                }
            }
            cs.close();
        }

        db.close();

        return retItems;
    }


    static public List<PParentArticle> readPParentArticles(SqlLiteHelper myDbHelper) {
        Log.i(Constants.TAG_SQL_LITE_DB_LOG, "ReadPParentArticles");
        List<PParentArticle> retItems = new LinkedList<>();
        SQLiteDatabase db = myDbHelper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT id, name FROM penal_arts order by id", null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    int id = c.getInt(c.getColumnIndex("id"));
                    String name = c.getString(c.getColumnIndex("name"));
                    retItems.add(new PParentArticle(id, name));
                } while (c.moveToNext());
            }
        }
        c.close();
        db.close();

        return retItems;
    }


    static public List<PSubArticle> readPenalSubArts(SqlLiteHelper myDbHelper, int pArtId) {
        Log.i(Constants.TAG_SQL_LITE_DB_LOG, "ReadPSubArticles");
        List<PSubArticle> retItems = new LinkedList<>();
        SQLiteDatabase db = myDbHelper.getReadableDatabase();

        Cursor cs = db.rawQuery("select id, name, desc, penal_count, penal_bal, parent_art_id "
                + " from penal_sub_arts where parent_art_id = " + pArtId + " order by id", null);
        if (cs != null) {
            if (cs.moveToFirst()) {
                do {
                    int id = cs.getInt(cs.getColumnIndex("id"));
                    String name = cs.getString(cs.getColumnIndex("name"));
                    String desc = cs.getString(cs.getColumnIndex("desc"));
                    String penalCount = cs.getString(cs.getColumnIndex("penal_count"));
                    String penalBal = cs.getString(cs.getColumnIndex("penal_bal"));
                    int parentArtId = cs.getInt(cs.getColumnIndex("parent_art_id"));
                    retItems.add(new PSubArticle(id, name, desc, penalCount, penalBal, parentArtId));
                } while (cs.moveToNext());
            }
        }
        cs.close();
        db.close();

        return retItems;
    }

    static public List<PParArtSubArt> readPParArtSubArts(SqlLiteHelper myDbHelper, String queryStr) {
        Log.i(Constants.TAG_SQL_LITE_DB_LOG, "ReadPParArtSubArts");

        List<PParentArticle> pArts = new LinkedList<>();
        List<PParArtSubArt> items = new LinkedList<>();

        SQLiteDatabase db = myDbHelper.getReadableDatabase();


        Cursor c = db.rawQuery("SELECT id, name FROM penal_arts order by id", null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    int id = c.getInt(c.getColumnIndex("id"));
                    String name = c.getString(c.getColumnIndex("name"));
                    pArts.add(new PParentArticle(id, name));
                } while (c.moveToNext());
            }
        }
        c.close();

        for (int i = 0; i < pArts.size(); ++i) {
            PParentArticle pArt = pArts.get(i);
            if (pArt.getName().toLowerCase().contains(queryStr.trim().toLowerCase())) {
                items.add(new PParArtSubArt(pArt, null));
                Cursor cs = db.rawQuery("select id, name, desc, penal_count, penal_bal, parent_art_id "
                        + " from penal_sub_arts where parent_art_id = " + pArt.getId() + " order by id", null);

                if (cs != null) {
                    if (cs.moveToFirst()) {
                        do {
                            int id = cs.getInt(cs.getColumnIndex("id"));
                            String name = cs.getString(cs.getColumnIndex("name"));
                            String desc = cs.getString(cs.getColumnIndex("desc"));
                            String penalCount = cs.getString(cs.getColumnIndex("penal_count"));
                            String penalBal = cs.getString(cs.getColumnIndex("penal_bal"));
                            int parentArtId = cs.getInt(cs.getColumnIndex("parent_art_id"));
                            items.add(new PParArtSubArt(null, new PSubArticle(id, name, desc, penalCount, penalBal, parentArtId)));
                        } while (cs.moveToNext());
                    }
                }
                cs.close();
            } else {
                boolean addParentArt = true;
                Cursor cs = db.rawQuery("select id, name, desc, penal_count, penal_bal, parent_art_id "
                        + " from penal_sub_arts where parent_art_id = " + pArt.getId() + " "
                        + "and (LOWER(name) like '%" + queryStr.trim().toLowerCase() + "%' or LOWER(desc) like '%" + queryStr.trim().toLowerCase() + "%') order by id", null);

                if (cs != null) {
                    if (cs.moveToFirst()) {
                        do {
                            if (addParentArt) {
                                items.add(new PParArtSubArt(pArt, null));
                                addParentArt = false;
                            }
                            int id = cs.getInt(cs.getColumnIndex("id"));
                            String name = cs.getString(cs.getColumnIndex("name"));
                            String desc = cs.getString(cs.getColumnIndex("desc"));
                            String penalCount = cs.getString(cs.getColumnIndex("penal_count"));
                            String penalBal = cs.getString(cs.getColumnIndex("penal_bal"));
                            int parentArtId = cs.getInt(cs.getColumnIndex("parent_art_id"));
                            items.add(new PParArtSubArt(null, new PSubArticle(id, name, desc, penalCount, penalBal, parentArtId)));
                        } while (cs.moveToNext());
                    }
                }
                cs.close();

            }
        }

        db.close();

        return items;
    }
}
