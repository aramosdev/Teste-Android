package br.com.aramosdev.testeandroid.core;

import com.google.gson.Gson;

import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * Created by Alberto.Ramos on 12/11/17.
 */

public class UnitTestUtil {

    private static UnitTestUtil sInstance;

    public static UnitTestUtil getInstance() {
        if (sInstance == null) sInstance = new UnitTestUtil();
        return sInstance;
    }

    public <Z> Z readFile(Type clazz, String fileName){
        final InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName);
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        if(s.hasNext()) {
            return new Gson().fromJson(s.next(), clazz);
        }
        return null;
    }

}
