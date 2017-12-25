package adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import database.StoreDatabase;
import fragment.TabThreeFragment;
import minhpvn.com.swipedemo.MainActivity;
import minhpvn.com.swipedemo.R;
import model.Phone;

import static fragment.TabThreeFragment.storeDatabase;

/**
 * Created by smkko on 12/17/2017.
 */

public class PhoneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Phone> phoneList;
    private  Phone phone;
    boolean isEdit = false;
    StoreDatabase database ;
    MainActivity mainActivity;
    TabThreeFragment tabThreeFragment = new TabThreeFragment();

    public PhoneAdapter(Context context, List<Phone> phoneList) {
        this.context = context;
        this.phoneList = phoneList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_three_item, parent, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Phone phoneAdd;
        MyHolder myHolder = (MyHolder) holder;
        phone = phoneList.get(position);
        phoneAdd = phoneList.get(position);
        myHolder.phone_name.setText(phone.getName());
        myHolder.phone_descreption.setText(phone.getDescreption());
        myHolder.phone_price.setText(String.valueOf(phone.getPrice()));
        myHolder.button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabThreeFragment.DialogAdd(context, phoneAdd.getId(), phoneAdd.getName(), phoneAdd.getDescreption(), String.valueOf(phoneAdd.getPrice()),isEdit);
            }
        });
        myHolder.button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabThreeFragment.DialogDelete(phoneAdd.getId(),context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return phoneList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.phone_image)
        ImageView phone_image;
        @BindView(R.id.phone_name)
        TextView phone_name;
        @BindView(R.id.phone_price)
        TextView phone_price;
        @BindView(R.id.phone_descreption)
        TextView phone_descreption;
        @BindView(R.id.phone_quantity)
        TextView phone_quantity;
        @BindView(R.id.button_edit)
        ImageButton button_edit;
        @BindView(R.id.button_delete)
        ImageButton button_delete;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
