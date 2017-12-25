package adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import minhpvn.com.swipedemo.R;
import model.Book;

public class BookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<Book> data = Collections.emptyList();
    Book current;

    // create constructor to innitilize context and data sent from MainActivity
    public BookAdapter(Context context, List<Book> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.tab_one_item, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder = (MyHolder) holder;
        current = data.get(position);
        myHolder.txtAuthor.setText(current.getAuthor());
        myHolder.txtTitle.setText(current.getTitle());
        myHolder.txtPrice.setText(current.getPrice());
        myHolder.txtPrice.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));


    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        TextView txtAuthor;
        TextView txtPrice;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.title_item);
            txtAuthor = (TextView) itemView.findViewById(R.id.author_item);
            txtPrice = (TextView) itemView.findViewById(R.id.price_item);
        }

    }

}