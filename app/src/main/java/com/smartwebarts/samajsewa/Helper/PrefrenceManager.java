package com.smartwebarts.samajsewa.Helper;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefrenceManager {
    SharedPreferences sharedPreferences;
    Context context;
    SharedPreferences.Editor editor;


    public PrefrenceManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences("Samaj Sewa",Context.MODE_PRIVATE);
        this.context = context;
        this.editor = sharedPreferences.edit();
    }

    public void setMobileno(String mobile){
        editor.putString( "mobile",mobile );
        editor.commit();

    }
    public String getmobilno(){
        return sharedPreferences.getString( "mobile","0" );
    };

   public void setrefcode(String refcode){
        editor.putString( "refcode",refcode );
        editor.commit();

    }
    public String getrefcode(){
        return sharedPreferences.getString( "refcode","0" );
    };


    public void setUserName(String userName){
        editor.putString( "username",userName );
        editor.commit();

    }
    public String getUserName(){
        return sharedPreferences.getString( "username","" );
    };



    public void setCity(String city){
        editor.putString( "city",city );
        editor.commit();

    }

    public String getCity(){

        return sharedPreferences.getString( "city","" );

    };

    public void setUserid(String userid){
        editor.putString( "userid",userid );
        editor.commit();

    }

    public String getUserid(){
        return sharedPreferences.getString( "userid","" );
    };


    public void setphoto(String photo){
        editor.putString( "photo",photo );
        editor.commit();

    }

    public String getPhoto(){
        return sharedPreferences.getString( "photo","" );
    };


    public String getCitynamess(){
        return sharedPreferences.getString( "citynamess","" );
    };


    public void setCitynamess(String  citynamess){
        editor.putString( "citynamess",citynamess );
        editor.commit();

    }

    public void setEmail(String email){
        editor.putString( "email",email );
        editor.commit();

    }


    public String getToken(){
        return sharedPreferences.getString( "token","" );
    };


    public void setToken(String  token){
        editor.putString( "token",token );
        editor.commit();

    }
    public String getEmail(){
        return sharedPreferences.getString( "email","" );
    };
    public void logout(){
        editor.clear();
        editor.apply();
        editor.commit();
    }

}
