package com.example.android.justjava;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by mathe on 12/12/2016.
 */

public class CozinhaAdapter extends ArrayAdapter<Pedido> {
    static private DatabaseReference dataBase = FirebaseDatabase.getInstance().getReference().child("Pedidos");
    Context context;
    public CozinhaAdapter (Context context, ArrayList<Pedido> produtos) {
        super(context, 0, produtos);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Pedido pedido = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cozinha
                    , parent, false);
        }
        // Lookup view for data population
        TextView tvStatusPedido = (TextView) convertView.findViewById(R.id.tv_status_pedido);
        TextView tvNomeCliente = (TextView) convertView.findViewById(R.id.tv_nome_cliente);
        LinearLayout layout = (LinearLayout) convertView.findViewById((R.id.tv_bloco_pedido));
        Button mButton = (Button) convertView.findViewById(R.id.btAdiantar);

        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pedido.setStatus_do_pedido(pedido.getStatus_do_pedido()+1);
                dataBase.child(pedido.getChave()).child("status_do_pedido").setValue(pedido.getStatus_do_pedido());

            }
        });

        layout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog alerta;
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                //define o titulo
                builder.setTitle("Pedido");

                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.finalizar_pedido, null);
                ListView listViewPedido = (ListView) view.findViewById(R.id.listViewPedido);

                ProdutoAdapter adapter_item_pedido = new ProdutoAdapter(context, pedido.getItem_pedido());
                listViewPedido.setAdapter(adapter_item_pedido);

                builder.setView(view);
                builder.setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
                //cria o AlertDialog
                alerta = builder.create();
                //Exibe
                alerta.show();
            }
            });

        if(pedido.status_do_pedido == 0) {
            tvStatusPedido.setText("Pendente");

            layout.setBackgroundColor(Color.parseColor("#9E9E9E"));
        }
        else if (pedido.status_do_pedido == 1) {
            tvStatusPedido.setText("Em andamento");
            layout.setBackgroundColor(Color.parseColor("#FFEB3B"));

        }
        else {
            tvStatusPedido.setText("Pronto! :D");
            layout.setBackgroundColor(Color.parseColor("#4CAF50"));
        }

        tvNomeCliente.setText(pedido.getNome_cliente());

        // Return the completed view to render on screen
        return convertView;
    }
}
