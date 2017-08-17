package demo.pratice.practiceproject.data;

import android.content.Context;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import demo.pratice.practiceproject.app.MainApplication;
import timber.log.Timber;


/**
 * Created by yb on 2017/8/15.
 */

public class CommonDAO {

    private DB db;

    private static CommonDAO ourInstance = new CommonDAO();

    public static CommonDAO getInstance() {
        return ourInstance;
    }

    protected CommonDAO() {
        createDb();
    }

    private void createDb() {
        Context context = MainApplication.getInstance().getApplicationContext();
        try {
            db = DBFactory.open(context);
        } catch (SnappydbException e) {
            Timber.e(e, "create db failed");
        }
    }

    public void saveString(String key, String value) {
        try {
            db.put(key, value);
        } catch (SnappydbException e) {
            Timber.e("Can't save value: key = %s, reason: %s", key, e.getMessage());
        }
    }

    public String getString(String key) {
        try {
            return db.get(key);
        } catch (SnappydbException e) {
            Timber.e("Can't get value: key = %s, reason: %s", key, e.getMessage());
        }
        return null;
    }

    public void saveInt(String key, int value) {
        try {
            db.putInt(key, value);
        } catch (SnappydbException e) {
            Timber.e("Can't save value: key = %s, reason: %s", key, e.getMessage());
        }
    }
    public void saveBool(String key, boolean value) {
        try {
            db.putBoolean(key, value);
        } catch (SnappydbException e) {
            Timber.e("Can't save value: key = %s, reason: %s", key, e.getMessage());
        }
    }

    public boolean getBool(String key) {
        try {
            return db.getBoolean(key);
        } catch (SnappydbException e) {
            Timber.e("Can't save value: key = %s, reason: %s", key, e.getMessage());
        }
        return false;
    }

    public int getInt(String key, int defaultValue) {
        try {
            return db.getInt(key);
        } catch (SnappydbException e) {
            Timber.e("Can't get value: key = %s, reason: %s", key, e.getMessage());
        }
        return defaultValue;
    }
    public void saveLong(String key, long value) {
        try {
            db.putLong(key, value);
        } catch (SnappydbException e) {
            Timber.e("Can't save value: key = %s, reason: %s", key, e.getMessage());
        }
    }

    public long getLong(String key, long defaultValue) {
        try {
            return db.getLong(key);
        } catch (SnappydbException e) {
            Timber.e("Can't get value: key = %s, reason: %s", key, e.getMessage());
        }
        return defaultValue;
    }

    public void saveStrings(String key, List<String> list) {
        if (list != null && list.size() > 0) {
            try {
                String[] array = new String[list.size()];
                db.put(key, list.toArray(array));
            } catch (SnappydbException e) {
                Timber.e("Can't save value: key = %s, reason: %s", key, e.getMessage());
            }
        }
    }

    public List<String> getStrings(String key) {
        try {
            String[] array = db.getArray(key, String.class);
            if (array != null) {
                return Arrays.asList(array);
            }
        } catch (SnappydbException e) {
            Timber.e("Can't get value, reason: %s", e.getMessage());
        }
        return null;
    }

    public void saveObject(String key, Object object) {
        try {
            db.put(key, object);
        } catch (SnappydbException e) {
            Timber.e("Can't save object: key = %s, reason: %s", key, e.getMessage());
        }
    }
    public <T> T getObject(String key, Class<T> tClass) {
        try {
          return db.getObject(key, tClass);
        } catch (SnappydbException e) {
            Timber.e("Can't get object: key = %s, reason: %s", key, e.getMessage());
        }
        return null;
    }

    public void saveObjects(String key, List<?> list) {
        if (list != null && list.size() >= 0) {
            try {
                db.put(key, list.toArray());
            } catch (SnappydbException e) {
                Timber.e("Can't save value: key = %s, reason: %s", key, e.getMessage());
            }
        }
    }

    public <T> List<T> getObjects(String key, Class<T> tClass) {
        try {
            T[] array = db.getObjectArray(key, tClass);
            if (array != null) {
                return Arrays.asList(array);
            }
        } catch (SnappydbException e) {
            Timber.e("Can't get value, reason: %s", e.getMessage());
        }
        return null;
    }


    public void saveSerializeObjects(String key, List<? extends Serializable> list) {
        if (list != null && list.size() >= 0) {
            try {
                db.put(key, list.toArray());
            } catch (SnappydbException e) {
                Timber.e("Can't save value: key = %s, reason: %s", key, e.getMessage());
            }
        }
    }

    public <T extends Serializable> List<T> getSerializeObjects(String key, Class<T> tClass) {
        try {
            T[] array = db.getArray(key, tClass);
            if (array != null) {
                return Arrays.asList(array);
            }
        } catch (SnappydbException e) {
            Timber.e("Can't get value, reason: %s", e.getMessage());
        }
        return null;
    }

    public void remove(String key) {
        try {
            db.del(key);
        } catch (SnappydbException e) {
         //   Timber.e("Can't delete cookies, reason: %s", e.getMessage());
        }
    }

    public void destroy() {
        try {
            db.destroy();
            createDb();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }
}
