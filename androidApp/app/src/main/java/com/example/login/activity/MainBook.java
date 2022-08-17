package com.example.login.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.login.R;

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

public class MainBook extends Activity {
    // https://run.mocky.io/v3/6e35600c-eca1-445e-8a4b-1835b8783a55
    private  static String JSON_url="https://run.mocky.io/v3/6e35600c-eca1-445e-8a4b-1835b8783a55";
    List<itembook> bookList;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_main);

        bookList=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerView);

        GetData getData=new GetData();
        getData.execute();
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
                    url=new URL(JSON_url);
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