package com.qh.half;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.qh.half.greendao.DaoMaster;
import com.qh.half.greendao.DaoSession;

/**
 * Created by Administrator on 14-4-23.
 */
public class DaoManager {
    private Context mContext ;
    private static DaoSession mDaoSession ;
    private static DaoManager instance;
    public static DaoManager getInstance(){
        if(instance==null){
            instance = new DaoManager() ;
        }
        return instance ;
    }
    private DaoManager(){
    }
    public void init(Context context){
        this.mContext = context ;
        SQLiteDatabase db = new DaoMaster.DevOpenHelper(context, "half", null).getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession= daoMaster.newSession();
    }
    public static DaoSession getDaoSession(){
        return  mDaoSession ;
    }
}
