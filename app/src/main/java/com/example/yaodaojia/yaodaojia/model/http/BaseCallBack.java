package com.example.yaodaojia.yaodaojia.model.http;
import com.google.gson.internal.$Gson$Types;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by asus on 2016/10/17.
 */

public abstract class BaseCallBack<T> {

    Type mType;
    //下面方法是把T转换为type对象
    static Type getSuperclassParameter(Class<?> subclass){
        Type superclass=subclass.getGenericSuperclass();
        if(superclass instanceof Class){
            throw new RuntimeException("Missing type parameter");

        }
        ParameterizedType parameterized= (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }
    public BaseCallBack(){
        mType=getSuperclassParameter(getClass());
    }
    public abstract void onRequsetBefore(Request request);


    public abstract void onFailure(Call call, IOException e);

    public abstract void onSuccess( Response response ,T t) ;
    public abstract void onError( Response response,int code,Exception e) ;
}
