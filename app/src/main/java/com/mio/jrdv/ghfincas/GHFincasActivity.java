package com.mio.jrdv.ghfincas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

public class GHFincasActivity extends AppCompatActivity {

    //TODO web de ghfincas

    final public   String url = "http://www.ghfincas.es/";

    private String htmlText="<h1>Quienes somos</h1>\n" +
            "<p style=\"text-align: justify;\">Tras a&ntilde;os de experiencia,<span style=\"color: #3366ff;\"><strong> GH Fincas</strong></span> comienza su actividad centr&aacute;ndose especialmente en la prestaci&oacute;n de servicios de administraci&oacute;n y gesti&oacute;n de todo tipo de fincas, ofreciendo las mejores condiciones del&nbsp;mercado.</p>\n" +
            "<h5 style=\"text-align: center;\">EFICACIA Y COMPETITIVIDAD</h5>\n" +
            "<p style=\"text-align: justify;\">El equipo de<strong><span style=\"color: #3366ff;\"> GH Fincas </span></strong>y los acuerdos alcanzados con un grupo de profesionales expertos, nos confiere la posibilidad de ofrecer a nuestros clientes&nbsp;un servicio integral y de calidad,&nbsp;siendo la eficacia y competitividad en la gesti&oacute;n de nuestros procesos&nbsp;los pilares&nbsp;fundamentales de nuestro negocio.</p>\n" +
            "<h5 style=\"text-align: center;\">&iquest;EN QUE NOS DIFERENCIAMOS?</h5>\n" +
            "<p style=\"text-align: justify;\">Nuestra vocaci&oacute;n de servicio y la constante b&uacute;squeda por la formalidad y la transparencia, nos confiere&nbsp;un valor a&ntilde;adido a la hora de&nbsp;elegirnos. Destacamos algunas diferencias:</p>\n" +
            "<p style=\"text-align: justify;\">- Atenci&oacute;n personalizada y servicio de emergencias 24 horas, 365 dias</p>\n" +
            "<p style=\"text-align: justify;\">- Gesti&oacute;n integral para su Comunidad. Damos soluciones de gesti&oacute;n, administraci&oacute;n, financiera, jur&iacute;dica y aseguradoras</p>\n" +
            "<p style=\"text-align: justify;\">-&nbsp; Contrato de puertas abiertas: el cliente puede rescindir su acuerdo con<strong><span style=\"color: #3366ff;\"> GH Fincas </span></strong>en el momento que lo desee, sin penalizaciones</p>\n" +
            "<p style=\"text-align: justify;\">- Reclamaci&oacute;n a morosos en condiciones muy ventajosas para la comunidad</p>\n" +
            "<p style=\"text-align: justify;\">-Visitas peri&oacute;dicas a las comunidades para control del estado de conservaci&oacute;n de la finca.</p>\n" +
            "<p style=\"text-align: justify;\">- Y muchas m&aacute;s que puedes comprobar por ti mismo.</p> ";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghfincas);
         Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);
        //con esto cambiamos el tiotulod el ActionBar
        getSupportActionBar().setTitle("EMPRESA");


        //FLOATING  de WEB:
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabweb);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //que no pregniute y tengsmods que pulsar para ir ala web

                Snackbar snackbar = Snackbar.make(view, "Abrir nuestra pagina web?", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar snackbar1 = Snackbar.make(view, "Redirigiendo!", Snackbar.LENGTH_SHORT);
                                snackbar1.show();

                                //este abre una web

                                // String url = "http://www.ghfincas.es/";la declaramos public!!!
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                            }


                        });
                snackbar.show();
            }

        });


       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);//con esto quitado no sale el boton atras


        // get our html content
        String htmlAsString = htmlText;      // used by WebView
        //Spanned htmlAsSpanned = Html.fromHtml(htmlAsString); // used by TextView

        // set the html content on a TextView
       // TextView textView = (TextView) findViewById(R.id.textView);
       // textView.setText(htmlAsSpanned);

          WebView webView = (WebView) findViewById(R.id.textoEmpresa);
          webView.loadDataWithBaseURL(null, htmlAsString, "text/html", "utf-8", null);

    }

}
