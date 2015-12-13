package com.mio.jrdv.ghfincas.SQL;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mio.jrdv.ghfincas.R;
import com.mio.jrdv.ghfincas.modelParse.MessageParse;

import java.util.ArrayList;

/**
 * Created by invitado on 10/12/15.
 */
 public class NotificationAdapter extends BaseAdapter {


    Context context;
    ArrayList<MessageParse> listDataAdapter;
    private LayoutInflater inflater;




//constructor:

    public NotificationAdapter(Activity activity,Context mcontext,ArrayList<MessageParse> listData){
        this.context = mcontext;
        this.listDataAdapter = listData;

        /*
        MessageParse mensajeprueba=new MessageParse(0,"inicial desde Adapter",787878989);


        listDataAdapter.add(0,mensajeprueba);
        */

        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);



    }
    @Override
    public int getCount() {
        return listDataAdapter.size();
    }

    @Override
    public Object getItem(int position) {
        return listDataAdapter.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    //class para coger los 3 textview:?¿?

    class ViewHolder {
        private TextView textViewMessage,TextViewTimeStamp;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder = null;
        if(view == null){
            //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);lo saco del paramwtro activty pasado en consructtor
            view = inflater.inflate(R.layout.list_row_parse,null);
            viewHolder = new ViewHolder();
            viewHolder.textViewMessage = (TextView) view.findViewById(R.id.message);
            viewHolder.TextViewTimeStamp = (TextView) view.findViewById(R.id.timestamp);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        MessageParse notification = listDataAdapter.get(position);
        String Notificationmessage = notification.getMessage();
        long Notificationtimestamp=notification.getTimestamp();

        viewHolder.textViewMessage.setText(Notificationmessage);


        /*
        CharSequence ago = DateUtils.getRelativeTimeSpanString(message.getTimestamp(), System.currentTimeMillis(),
                    0L, DateUtils.FORMAT_ABBREV_ALL);

            txtTimestamp.setText(String.valueOf(ago));
         */


        CharSequence ago = DateUtils.getRelativeTimeSpanString(notification.getTimestamp(), System.currentTimeMillis(),
                0L, DateUtils.FORMAT_ABBREV_ALL);
        viewHolder.TextViewTimeStamp.setText(String.valueOf(ago));



        return view;
    }

}
