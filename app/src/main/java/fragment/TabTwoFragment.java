package fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import minhpvn.com.swipedemo.MainActivity;
import minhpvn.com.swipedemo.R;

/**
 * Created by smkko on 12/13/2017.
 */

public class TabTwoFragment extends Fragment {
    @BindView(R.id.lang_vn)
    Button lang_vn;
    @BindView(R.id.lang_en)
    Button lang_en;
    @BindView(R.id.tab_2_address)
    TextView tab_2_address;
    @BindView(R.id.tab_2_course_1)
    TextView tab_2_course_1;
    @BindView(R.id.tab_2_course_2)
    TextView tab_2_course_2;
    @BindView(R.id.tab_2_course_3)
    TextView tab_2_course_3;
    String lang,noidung;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_two, container, false);
        ButterKnife.bind(this, view);
        new ReadJSON().execute("https://khoapham.vn/KhoaPhamTraining/json/tien/demo3.json");
        lang_vn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLang("vn");
            }
        });
        lang_en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLang("en");
            }
        });
        setHasOptionsMenu(false);
        ((MainActivity) getActivity()).setActionBarTitle("Langs");
        //Volley
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        return view;
    }

    private void setLang(String s) {
        try {
            JSONObject jsonObject = new JSONObject(noidung);
            JSONObject jsonObjectLang = jsonObject.getJSONObject("language");
            JSONObject jsonObjectVN = jsonObjectLang.getJSONObject(s);
            String adress = jsonObjectVN.getString("address");
            String course1 = jsonObjectVN.getString("course1");
            String course2 = jsonObjectVN.getString("course2");
            String course3 = jsonObjectVN.getString("course3");
            tab_2_address.setText(adress);
            tab_2_course_1.setText(course1);
            tab_2_course_2.setText(course2);
            tab_2_course_3.setText(course3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class ReadJSON extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            noidung = s;
//            Toast.makeText(getContext(),s,Toast.LENGTH_SHORT).show();
        }
    }
}
