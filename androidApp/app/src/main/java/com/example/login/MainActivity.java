package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.login.activity.BookItemAdapter;
import com.example.login.activity.RegisterActivity;
import com.example.login.activity.itembook;
import com.example.login.app.AppConfig;
import com.example.login.helper.SessionManager;
import com.example.login.helper.SQLiteHandler;
import com.example.login.activity.LoginActivity;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import com.example.login.R;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {


    List<itembook> bookList;
    private Button btnLogoutBook;
    private  EditText btnSearchBooks;
    RecyclerView recyclerView;
    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_main);

        bookList=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerView);
        btnLogoutBook = (Button) findViewById(R.id.logoutMain);
        btnSearchBooks = (EditText) findViewById(R.id.searchBooks);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());
        // session manager
        session = new SessionManager(getApplicationContext());
        if (!session.isLoggedIn()) {
            logoutUser();
        }

        GetData getData=new GetData();
        getData.execute();

        // Link to Register Screen
        // Logout button click event
        btnLogoutBook.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        btnSearchBooks.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
//                bookList.stream().filter(s);
            }
        });
    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public class GetData extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String...strings)
        {
            String current="";
            try {
                URL url;
                HttpURLConnection urlConnection=null;
                try {
                    url=new URL(AppConfig.URL_BOOKLIST);
                    urlConnection=(HttpURLConnection) url.openConnection();
                    InputStream is=urlConnection.getInputStream();
                    InputStreamReader isr=new InputStreamReader(is);
                    int data=isr.read();
                    while(data!=-1){
                        current +=(char)data;
                        data=isr.read();

                    }
                    return current;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(urlConnection!=null){
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return current;
        }


        @Override
        protected void onPostExecute(String s)
        {
            try {
                JSONObject jsonObject=new JSONObject(s);
                JSONArray jsonArray=jsonObject.getJSONArray("books");
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                    itembook book=new itembook();
                    book.setBookTitle(jsonObject1.getString("title"));
                    book.setBookIsbn(jsonObject1.getString("ISBN"));
                    book.setBookAuthor(jsonObject1.getString("author"));
                    book.setBookPublisher(jsonObject1.getString("publisher"));
                    book.setBookDate(jsonObject1.getString("publication_date"));
                    book.setBookBatch(jsonObject1.getString("year"));
                    book.setBookimg(jsonObject1.getString("thumbnailUrl"));
                    bookList.add(book);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PutDataIntoRecyclerView(bookList);
        }

    }

    private void  PutDataIntoRecyclerView(List<itembook> booklist){
        BookItemAdapter adapter=new BookItemAdapter(this,booklist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}