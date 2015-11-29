package com.mio.jrdv.ghfincas;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ListaProveedores extends ActionBarActivity {

    private TextView stickyView;
    private ListView listView;
    private View heroImageView;

    private View stickyViewSpacer;

    private int MAX_ROWS = 20;





    // Array of strings storing country names
    String[] countries = new String[] {
            "Pepito Perez Antenas",
            "Juan Gomez Ascensores",
            "OTIS Ascensores",
            "Benito y compañia",
            "Susrmanos SA",
            "Sevilla FC",
            "Hermanso Gomez Antenistas",
            "Antenas Garcia",
            "Scheindler",
            "Herreros Delgado"

    };

    // Array of integers points to images stored in /res/drawable-ldpi/
    int[] flags = new int[]{
            R.drawable.reparar,
            R.drawable.averias,
            R.drawable.reparar,
            R.drawable.reparar,
            R.drawable.reparar,
            R.drawable.averias,
            R.drawable.reparar,
            R.drawable.reparar,
            R.drawable.averias,
            R.drawable.averias
    };

    // Array of strings to store currencies
    String[] currency = new String[]{
            "Arreglo 24 h  de antenas colectivas e individuales",
            "Su ascensor en buenas manos , empresa con mas de 50 años de antiguedad",
            "LA mejor de la mejor",
            "Los artistas de l barrio han llegado a su dominilio",
            "Ay los apyos lo vais a flipar!!!!",
            "Arreglo 24 h  de antenas colectivas e individuales",
            "Arreglo 24 h  de antenas colectivas e individuales",
            "Arreglo 24 h  de antenas colectivas e individuales",
            "Arreglo 24 h  de antenas colectivas e individuales",
            "Arreglo 24 h  de antenas colectivas e individuales"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_proveedores);



        // Each row in the list stores country name, currency and flag
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<10;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("txt", "Empresa: " + countries[i]);
            hm.put("cur","Detalles: " + currency[i]);
            hm.put("flag", Integer.toString(flags[i]) );
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = { "flag","txt","cur" };

        // Ids of views in listview_layout
        int[] to = { R.id.flag,R.id.txt,R.id.cur};



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


        /* Populate the ListView with sample data */
        /*
        List<String> modelList = new ArrayList<>();
        for (int i = 0; i < MAX_ROWS; i++) {
            modelList.add("List item " + i);
        }

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_row, modelList);
        listView.setAdapter(adapter);

*/

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.list_row, from, to);

        // Getting a reference to listview of main.xml layout file
        // ListView listView = ( ListView ) findViewById(R.id.listView);//ya hecho antes


        // Setting the adapter to the listView
        listView.setAdapter(adapter);


        // Item Click Listener for the listview
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
                // Getting the Container Layout of the ListView
                LinearLayout linearLayoutParent = (LinearLayout) container;

                // Getting the inner Linear Layout
                LinearLayout linearLayoutChild = (LinearLayout ) linearLayoutParent.getChildAt(1);

                // Getting the Country TextView
                TextView tvCountry = (TextView) linearLayoutChild.getChildAt(0);

                Toast.makeText(getBaseContext(), tvCountry.getText().toString(), Toast.LENGTH_SHORT).show();
            }


        };//onitemclicklistener


        // Setting the item click listener for the listview
        listView.setOnItemClickListener(itemClickListener);



    }//oncreate
}