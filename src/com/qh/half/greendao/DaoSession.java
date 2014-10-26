package com.qh.half.greendao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.qh.half.greendao.LoginUser;

import com.qh.half.greendao.LoginUserDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig loginUserDaoConfig;

    private final LoginUserDao loginUserDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        loginUserDaoConfig = daoConfigMap.get(LoginUserDao.class).clone();
        loginUserDaoConfig.initIdentityScope(type);

        loginUserDao = new LoginUserDao(loginUserDaoConfig, this);

        registerDao(LoginUser.class, loginUserDao);
    }
    
    public void clear() {
        loginUserDaoConfig.getIdentityScope().clear();
    }

    public LoginUserDao getLoginUserDao() {
        return loginUserDao;
    }

}
