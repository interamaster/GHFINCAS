package com.mio.jrdv.ghfincas;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by joseramondelgado on 05/12/15.
 */
public class ProveedoresListViewAdapter extends BaseAdapter {

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    ImageLoader imageLoader;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public ProveedoresListViewAdapter(Context context,
                                      ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
        imageLoader = new ImageLoader(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        // Declare Variables
        //TextView TELEFONOPROVEEDOR;//no lo pongo
        TextView EMPRESAPROVEEDOR;
        TextView DESCRIPCIONPROVEEDOR;
        ImageView LOGOPROVEEDOR;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.list_row, parent, false);
        // Get the position
        resultp = data.get(position);

        // Locate the TextViews in listview_item.xml
        //TELEFONOPROVEEDOR = (TextView) itemView.findViewById(R.id.TELEFONOPROVEEDOR);//no lo pongo
        EMPRESAPROVEEDOR = (TextView) itemView.findViewById(R.id.EMPRESAPROVEEDOR);
        DESCRIPCIONPROVEEDOR = (TextView) itemView.findViewById(R.id.DESCRIPCIONPROVEEDOR);

        // Locate the ImageView in listview_item.xml
        LOGOPROVEEDOR = (ImageView) itemView.findViewById(R.id.LOGOPROVEEDOR);

        // Capture position and set results to the TextViews
       // TELEFONOPROVEEDOR.setText(resultp.get(JsoupGetFromWebActivityMain.TELEFONO));//no lo pongo
        EMPRESAPROVEEDOR.setText(resultp.get(ListaProveedoresFromWeb.EMPRESA));
        DESCRIPCIONPROVEEDOR.setText(resultp.get(ListaProveedoresFromWeb.DESCRIPCION));
        // Capture position and set results to the ImageView
        // Passes flag images URL into ImageLoader.class
        imageLoader.DisplayImage(resultp.get(ListaProveedoresFromWeb.LOGO), LOGOPROVEEDOR);
        // Capture ListView item click
        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get the position
                resultp = data.get(position);

                /*
                Intent intent = new Intent(context, SingleItemView.class);
                // Pass all data rank
                intent.putExtra("rank", resultp.get(MainActivity.RANK));
                // Pass all data country
                intent.putExtra("country", resultp.get(MainActivity.COUNTRY));
                // Pass all data population
                intent.putExtra("population",resultp.get(MainActivity.POPULATION));
                // Pass all data flag
                intent.putExtra("flag", resultp.get(MainActivity.FLAG));
                // Start SingleItemView Class
                context.startActivity(intent);

                */
                //TODO lo que habria que hacer era llamr por telefono!!!

                 //String TelefonoProveedorElegido = "tel:123456789";//la declaramos public!!!

                //lo sacamos de la fila

                String TelefonoProveedorElegido ="tel:"+ resultp.get(ListaProveedoresFromWeb.TELEFONO);



             Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse(TelefonoProveedorElegido));//asi no funciona



                context.startActivity(i);//se Hace con context!!!


            }
        });
        return itemView;
    }
}
