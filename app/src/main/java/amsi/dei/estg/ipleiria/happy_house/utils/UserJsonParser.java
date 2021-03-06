package amsi.dei.estg.ipleiria.happy_house.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import amsi.dei.estg.ipleiria.happy_house.modelos.User;

public class UserJsonParser {
    public static ArrayList<User> parserJsonUser(JSONArray response, Context context){
        System.out.println("--> PARSER LISTA USERS: " + response);
        ArrayList<User> tempUsers = new ArrayList<>();

        try{
            for(int i = 0; i < response.length(); i++) {

                JSONObject user = (JSONObject) response.get(i);
                int id = user.getInt("id");
                String username = user.getString("username");
                String password_hash = user.getString("password_hash");
                int telemovel = user.getInt("telemovel");
                String email = user.getString("email");
                int nif= user.getInt("nif");
                String favoritos = user.getString("favoritos");
                String auth_key = user.getString("auth_key");
                String created_at = user.getString("created_at");
                String updated_at = user.getString("updated_at");

                User auxUsers = new User(id, username, nif,email, password_hash,telemovel, favoritos, auth_key, created_at, updated_at);
                tempUsers.add(auxUsers);
            }

        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return tempUsers;
    }
    public static User parserJsonUser(String response, Context context){
        System.out.println("--> PARSER ADICIONAR: " + response);
        User auxUser = null;

        try{
            JSONObject user = new JSONObject(response);

            int id = user.getInt("id");
            String username = user.getString("username");
            String password_hash = user.getString("password");
            int telemovel = user.getInt("telemovel");
            int nif= user.getInt("nif");
            String email = user.getString("email");
            String favoritos = user.getString("favoritos");
            String auth_key = user.getString("auth_key");
            String created_at = user.getString("created_at");
            String updated_at = user.getString("updated_at");


            auxUser = new User(id, username, nif,email, password_hash,telemovel, favoritos, auth_key, created_at, updated_at);

        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return auxUser;
    }
    public static boolean isConnectionInternet (Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }
}
