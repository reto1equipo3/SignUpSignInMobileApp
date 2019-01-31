package activities.signupsigninmobileapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Observable;
import android.media.session.MediaController;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import Beans.Cliente;
import Beans.Pedido;
import businessLogic.PedidoLogic;
import businessLogic.PedidoLogicFactory;


public class OrderConfirm extends Activity implements View.OnClickListener {


    private TableLayout tabla_pedidos;
    private TableRow fila_tabla_pedido;
    private TextView columna_producto;
    private TextView columna_cantidad;
    private TextView columna_precio;
    private Button  atras;
    private Button confirmar;
    // private Pedido pedido;
    private Button fecha;
    private Button hora;
    private TextView textoFecha;
    private TextView textoHora;
    private int dia, mes, ano, horita, minutos;
    private String dbHora;
    private String dbFecha;

    private Observable<Pedido> pedidoData;
    private Cliente cliente;
    private PedidoLogic pedidoLogic;




  /*  public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }*/





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);

        columna_producto=findViewById(R.id.colProducto);
        columna_cantidad=findViewById(R.id.colCantidad);
        columna_precio = findViewById(R.id.colPrecio);
        atras=findViewById(R.id.btnAtras);
        confirmar=findViewById(R.id.btnConfirmar);
        tabla_pedidos = findViewById(R.id.tablaPedido);
        confirmar.setOnClickListener(this);
        atras.setOnClickListener(this);

        //Hora y fecha
        fecha =findViewById(R.id.btnFecha);
        hora = findViewById(R.id.btnHora);
        textoFecha = findViewById(R.id.txtFecha);
        textoHora = findViewById(R.id.txtHora);

        fecha.setOnClickListener(this);
        hora.setOnClickListener(this);

        //Pedido

        //PedidoLogicFactory pedidoFactory = new PedidoLogicFactory();
        try {
            pedidoLogic = PedidoLogicFactory.createPedidoLogic("REST_WEB_CLIENT");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // confirmar.setEnabled(false);

       /* if(!textoFecha.toString().isEmpty()){
            confirmar.setEnabled(true);
        }*/

      /*   if(textoFecha.toString().trim().length()!=0 || textoHora.toString().trim().length()!=0){

            confirmar.setEnabled(true);
        }*/


        //Desactivar el Strechable para que no nos lleve las columnas a los lados.
        tabla_pedidos.setColumnStretchable(0, false);

        //List<Pedido> pedidoProductos;

        //pedidoData = FXCollections.obser

        ArrayList<Pedido> pedidos = new ArrayList<>();

        for(int i=0; i<pedidos.size(); i++) {
            //Crear la linea de la tabla
            TableRow oTableRow = new TableRow(this);
            oTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            oTableRow.setPadding(20, 10, 20, 10);


            //-- Crear el TextView con el producto.
            TextView oProducto = new TextView(this);
            oProducto.setText("");//Codigo del pedido  //Aqui saldria la base de datos el QR
            oProducto.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            oProducto.setPadding(3, 0, 3, 0);

            //-- Crear el TextView con el cantidad.
            TextView oCantidad = new TextView(this);
            oCantidad.setText("2");  //Aqui saldria la base de datos la cantidad del producto
            oCantidad.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            oCantidad.setPadding(3, 0, 3, 0);

            //-- Crear el TextView con el precio.
            TextView oPrecio = new TextView(this);
            oPrecio.setText("34");  //Aqui saldria la base de datos el precio Unidad
            oPrecio.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            oPrecio.setPadding(3, 0, 3, 0);

            //Añadir los elementos a la linea

            oTableRow.addView(oProducto);
            oTableRow.addView(oCantidad);
            oTableRow.addView(oPrecio);

            //Añadir la linea a la tabla
            tabla_pedidos.addView(oTableRow);
        }




    }

    @Override
    public void onClick(View v) {

        if(v==fecha){

            final Calendar c = Calendar.getInstance();
            dia= c.get(Calendar.DAY_OF_MONTH);
            mes=c.get(Calendar.MONTH);
            ano=c.get(Calendar.YEAR);

            DatePickerDialog dialogDate = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    dbFecha=( (Integer.toString(dayOfMonth)) +"/"+(Integer.toString(month))+"/"+ (Integer.toString(year)));
                    textoFecha.setText(dbFecha);
                }
            }
                    ,dia, mes, ano);
            dialogDate.show();
        }
        if(v==hora){
            final Calendar c = Calendar.getInstance();
            horita= c.get(Calendar.HOUR_OF_DAY);
            minutos=c.get(Calendar.MINUTE);

            TimePickerDialog dialogHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    if(minute<10){
                        dbHora=Integer.toString(hourOfDay) +":0"+Integer.toString(minute);
                    }else{
                        dbHora=Integer.toString(hourOfDay) +":"+Integer.toString(minute);
                    }

                    textoHora.setText(dbHora);
                }
            }, horita, minutos,false);
            dialogHora.show();
        }

        if(atras.isPressed()){

            Intent intentAddProducto = new Intent (this, AddNewProduct.class);
            startActivity(intentAddProducto);


        }
        if (confirmar.isPressed()){
            //Alert de confirmar y cuando le de ha si que envie los datos a la base de datos


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirmación de pedido.");
            builder.setMessage("Se ha realizado la compra con éxito. :)");
            builder.show();



           /* Intent intentUser = new Intent (this, UserViewActivity.class);
            startActivity(intentUser);*/

        }

/*
        try {
            //tenemos que mandarle las cosas
            pedido.setEstado(EstadoPedido.TRAMITADO);

            java.util.Date fecha = new Date();
            pedido.setFechaTramitado(fecha);
            pedido.setFechaPedido(dbFecha);
            pedido.setHoraPedido(dbHora);
            pedido.setCliente(4);
            pedidoLogic.añadirPedido(pedido);
        } catch (BusinessLogicException e) {
            e.printStackTrace();
        }
        */

    }
}
