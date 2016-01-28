package com.mio.jrdv.ghfincas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ListaProveedoresFromWeb extends Activity {

    private TextView stickyView;
    private ListView listView;
    private View heroImageView;
    ProveedoresListViewAdapter adapter;

    private View stickyViewSpacer;

    private int MAX_ROWS = 20;

    ProgressDialog mProgressDialog;
    ArrayList<HashMap<String, String>> arraylist;
    static String TELEFONO = "Telefono";
    static String EMPRESA = "Empresa";
    static String DESCRIPCION = "Descripcion";

    //y en html
    static String DESCRIPCION_HTML = "Descripcionhtml";

    static String LOGO = "Logo";
    //TODO  URL Address
    String url = "https://jrdvsoft.wordpress.com/prueba-tabla/";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_proveedores_from_web);

        // Execute DownloadJSON AsyncTask
        new JsoupListView().execute();




        /* Initialise list view, hero image, and sticky view */
        listView = (ListView) findViewById(R.id.listView);
        heroImageView = findViewById(R.id.heroImageView);
        stickyView = (TextView) findViewById(R.id.stickyView);

        /* Inflate list header layout */
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listHeader = inflater.inflate(R.layout.list_header, null);
        stickyViewSpacer = listHeader.findViewById(R.id.stickyViewPlaceholder);

        /* Add list view header */
        listView.addHeaderView(listHeader);

        /* Handle list View scroll events */
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                /* Check if the first item is already reached to top.*/
                if (listView.getFirstVisiblePosition() == 0) {
                    View firstChild = listView.getChildAt(0);
                    int topY = 0;
                    if (firstChild != null) {
                        topY = firstChild.getTop();
                    }

                    int heroTopY = stickyViewSpacer.getTop();
                    stickyView.setY(Math.max(0, heroTopY + topY));

                    /* Set the image to scroll half of the amount that of ListView */
                    heroImageView.setY(topY * 0.5f);
                }
            }
        });




    }//oncreate


    // Title AsyncTask
    private class JsoupListView extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(ListaProveedoresFromWeb.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Descargando Datos Actualizados de Proveedores");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create an array
            arraylist = new ArrayList<HashMap<String, String>>();

            try {
                // Connect to the Website URL
                Document doc = Jsoup.connect(url).get();
                // Identify Table Class "ListaProveedores"
                for (Element table : doc.select("table[class=ListaProveedores]")) {

                    // Identify all the table row's(tr)
                    for (Element row : table.select("tr:gt(0)")) {
                        HashMap<String, String> map = new HashMap<String, String>();

                        // Identify all the table cell's(td)
                        Elements tds = row.select("td");

                        // Identify all img src's
                        Elements imgSrc = row.select("img[src]");
                        // Get only src from img src
                        String imgSrcStr = imgSrc.attr("src");

                        // Retrive Jsoup Elements
                        // Get the first td
                        map.put("Telefono", tds.get(0).text());
                        // Get the second td
                        map.put("Empresa", tds.get(1).text());
                        // Get the third td
                        map.put("Descripcion", tds.get(2).text());//lo paso comom hasta ahora en text
                        map.put("Descripcionhtml", tds.get(2).html());//y lo paso en html tambien
                        // Get the image src links
                        map.put("Logo", imgSrcStr);
                        // Set all extracted Jsoup Elements into the array
                        arraylist.add(map);
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Locate the listview in listview_main.xml
            listView = (ListView) findViewById(R.id.listView);
            // Pass the results into ListViewAdapter.java
            adapter = new ProveedoresListViewAdapter(ListaProveedoresFromWeb.this, arraylist);
            // Set the adapter to the ListView
            listView.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }
}
