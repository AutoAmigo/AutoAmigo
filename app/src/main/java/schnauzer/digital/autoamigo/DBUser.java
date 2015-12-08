package schnauzer.digital.autoamigo;

/**
 * Created by Valeria on 12/6/15.
 */

import android.os.AsyncTask;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DBUser {

    SimpleDateFormat dateFormat=null;
    private String url="http://0.0.0.0:3000/api/pckmupUsers"; //cambiarla cuando este hosteado !!

    private String firstName="",lastName="",userName="",institution="",handicap="",interests="",Email="";
    private Calendar birthDay = null;
    private String Password=""; //POST ONLY
    private String userid=""; //READ ONLY

    static JSONObject userdb;

    public DBUser (){
        //inicializando...
        dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        birthDay= Calendar.getInstance();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getInstitution() {
        return institution;
    }

    public String getHandicap() {
        return handicap;
    }

    public String getInterests() {
        return interests;
    }

    public String getEmail() {
        return Email;
    }

    public Calendar getBirthDay() {
        return birthDay;
    }

    public String getPassword() {
        return Password;
    }

    public String getUserid() {
        return userid;
    }

    //getById(){}

    //getRides(){}

    public void create(String firstname,String lastname,Calendar bday, String username,
                       String email, String password,String institution,String handicap, String interests){

        userdb = new JSONObject();
        try {
            userdb.put("FirstName",firstname);  this.firstName=firstname;
            userdb.put("LastName",lastname);    this.lastName=lastname;
            userdb.put("BirthDay",dateFormat.format(bday.getTime())); //ej. "2015-12-01"
                                                this.birthDay=bday;
            userdb.put("UserName",username);    this.userName=username;
            userdb.put("email",email); //debe ser tipo algo@algo.com , sino no funciona!! y no se debe repetir..
                                                this.Email=email;
            userdb.put("password",password);    this.Password=password;
            userdb.put("Institution",institution); this.institution=institution;
            userdb.put("Handicap",handicap);       this.handicap=handicap;
            userdb.put("Interests",interests);     this.interests=interests;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*{
          "FirstName": "V",
          "LastName": "C",
          "BirthDay": "2015-12-07",
          "UserName": "vc",
          "Institution": "algo",
          "Handicap": "no",
          "Interests": "me",
          "password":"1234",
          "email": "v@v.com"
        }*/

        new HttpAsyncTask().execute(url);
    }

    public void create(String firstname,String lastname,Calendar bday, String username,
                       String email, String password) {

        userdb = new JSONObject();
        try {
            userdb.put("FirstName",firstname);  this.firstName=firstname;
            userdb.put("LastName",lastname);    this.lastName=lastname;
            userdb.put("BirthDay",dateFormat.format(bday.getTime()));
                                                this.birthDay=bday;
            userdb.put("UserName",username);    this.userName=username;
            userdb.put("email",email);
                                                this.Email=email;
            userdb.put("password",password);    this.Password=password;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        new HttpAsyncTask().execute(url);
    }



    public static String POST(String url){

        String result = "";

        try {
            //
            URLConnection connection = new URL(url).openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setDoOutput(true); //se supone q post
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");

            OutputStream wr = connection.getOutputStream();
            wr.write(userdb.toString().getBytes("UTF-8"));
            wr.flush();
            wr.close();

            InputStream response = connection.getInputStream();

            // convert inputstream to string
            if(response != null)
                result = convertInputStreamToString(response);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", "El error"+e.toString());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return POST(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject json = new JSONObject(result);
                userid = json.getString("id");

            }catch (JSONException jex){
                Log.d("Json exception", jex.getLocalizedMessage());
            }
        }
    }

}
