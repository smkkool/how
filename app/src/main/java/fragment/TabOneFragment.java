package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import adapter.BookAdapter;
import minhpvn.com.swipedemo.MainActivity;
import minhpvn.com.swipedemo.R;
import model.Book;

/**
 * Created by smkko on 12/13/2017.
 */

public class TabOneFragment extends Fragment {
    Book book,book1,book2,book3,book4,book5,book6,book7,book8,book9,book10;
    ArrayList<Book> arrayListBook;
    RecyclerView mRcvBook;
    BookAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_one, container, false);
        getData();
        // Setup and Handover data to recyclerview
        mRcvBook = (RecyclerView)view.findViewById(R.id.rcvBooks);
        mAdapter = new BookAdapter(getContext(), arrayListBook);
        mRcvBook.setAdapter(mAdapter);
        mRcvBook.setLayoutManager(new LinearLayoutManager(getContext()));
        setHasOptionsMenu(false);
        return view;

    }

    private void getData() {
        book = new Book("How ", "Dave", "150.000");
        book1 = new Book("How1 ", "Dave1", "150.000");
        book2 = new Book("How2 ", "Dave2", "150.000");
        book3 = new Book("How 3", "Dave3", "150.000");
        book4 = new Book("How 4", "Dave4", "150.000");
        book5 = new Book("How5 ", "Dave6", "150.000");
        book6 = new Book("How6 ", "Dave7", "150.000");
        book7 = new Book("How 7", "Dave7", "150.000");
        book8 = new Book("How 8", "Dave8", "150.000");
        book9 = new Book("How 9", "Dave9", "150.000");
        book10 = new Book("How 10", "Dave10", "150.000");
        arrayListBook = new ArrayList<>();
        arrayListBook.add(book);
        arrayListBook.add(book1);
        arrayListBook.add(book2);
        arrayListBook.add(book3);
        arrayListBook.add(book4);
        arrayListBook.add(book5);
        arrayListBook.add(book6);
        arrayListBook.add(book7);
        arrayListBook.add(book8);
        arrayListBook.add(book9);
        arrayListBook.add(book10);
    }
}
