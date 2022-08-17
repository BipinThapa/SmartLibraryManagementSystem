package com.example.login.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.login.R;

import java.util.List;

public class BookItemAdapter extends RecyclerView.Adapter<BookItemAdapter.MyViewHolder> {

    private Context mContext;
    private List<itembook> itbookData;

    public BookItemAdapter(Context mContext, List<itembook> itbookData) {
        this.mContext = mContext;
        this.itbookData = itbookData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater=LayoutInflater.from(mContext);
        v=inflater.inflate(R.layout.bookitem,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.book_title.setText(itbookData.get(position).getBookTitle());
        holder.book_isbn.setText(itbookData.get(position).getBookIsbn());
        holder.book_author.setText(itbookData.get(position).getBookAuthor());
        holder.book_publisher.setText(itbookData.get(position).getBookPublisher());
        holder.book_date.setText(itbookData.get(position).getBookDate());
        holder.book_batch.setText(itbookData.get(position).getBookBatch());
        //using Glide library to display the image
        Glide.with(mContext).load(itbookData.get(position).getBookimg()).into(holder.book_image);

    }

    @Override
    public int getItemCount() {
        return itbookData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView book_image;
        TextView book_title,book_isbn,book_author,book_publisher,book_date,book_batch;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            book_title=itemView.findViewById(R.id.book_title);
            book_isbn=itemView.findViewById(R.id.book_isbn);
            book_author=itemView.findViewById(R.id.book_author);
            book_publisher=itemView.findViewById(R.id.book_publisher);
            book_date=itemView.findViewById(R.id.book_date);
            book_batch=itemView.findViewById(R.id.book_batch);
            book_image=(ImageView)itemView.findViewById(R.id.book_image);
        }
    }
}
