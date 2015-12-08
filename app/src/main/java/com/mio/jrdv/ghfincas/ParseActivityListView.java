package com.mio.jrdv.ghfincas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mio.jrdv.ghfincas.modelParse.MessageParse;

import java.util.ArrayList;
import java.util.List;

public class ParseActivityListView extends AppCompatActivity {

    //para el parse
    private ListView listView;
    private List<MessageParse> listMessages = new ArrayList<>();
    private MessageAdapterParse adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_activity_list_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //con esto cambiamos el tiotulod el ActionBar
        getSupportActionBar().setTitle("COMUNICADOS");

        /*
        //no uso el fab button!!!

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);//CON ESTO QIOTADO NO SALE EL BOTN DE BACK EN BARRA ARIIBA
        */


        //referencias la listview

        listView = (ListView) findViewById(R.id.list_view_parse);
        adapter = new MessageAdapterParse(this);


        listView.setAdapter(adapter);

    }

    /*
    Here onNewIntent() method will be called whenever a new push message is received.
     We’ll add the new message to list data source and refresh the list view.
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String message = intent.getStringExtra("message");

        MessageParse m = new MessageParse(message, System.currentTimeMillis());
        listMessages.add(0, m);
        adapter.notifyDataSetChanged();
    }


    //calss del listview adapter
    private class MessageAdapterParse extends BaseAdapter {

        LayoutInflater inflater;

        public MessageAdapterParse(Activity activity) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return listMessages.size();
        }

        @Override
        public Object getItem(int position) {
            return listMessages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.list_row, null);
            }

            TextView txtMessage = (TextView) view.findViewById(R.id.message);
            TextView txtTimestamp = (TextView) view.findViewById(R.id.timestamp);

            MessageParse message = listMessages.get(position);
            txtMessage.setText(message.getMessage());

            CharSequence ago = DateUtils.getRelativeTimeSpanString(message.getTimestamp(), System.currentTimeMillis(),
                    0L, DateUtils.FORMAT_ABBREV_ALL);

            txtTimestamp.setText(String.valueOf(ago));

            return view;
        }
    }
}
