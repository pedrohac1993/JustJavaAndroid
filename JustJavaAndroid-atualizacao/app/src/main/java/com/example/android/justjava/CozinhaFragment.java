package com.example.android.justjava;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 */
public class CozinhaFragment extends Fragment {
    private CozinhaAdapter adpter;
    private ListView listView;
    private ArrayList<Pedido> lista_dos_pedidos= new ArrayList<>();
    DatabaseReference dataBase = FirebaseDatabase.getInstance().getReference().child("Pedidos");

    public CozinhaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_cozinha, container,false);
    listView = (ListView) view.findViewById(R.id.listViewCozinha);

    FloatingActionButton fab = (FloatingActionButton)  getActivity().findViewById(R.id.fab);
    fab.hide();

    adpter = new CozinhaAdapter(getContext(), lista_dos_pedidos);
    listView.setAdapter(adpter);



    dataBase.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Iterator i = dataSnapshot.getChildren().iterator();
            Pedido pedido ;
            adpter.clear();
            while (i.hasNext()) {
                pedido = ((DataSnapshot)i.next()).getValue(Pedido.class);
                        if(pedido.getStatus_do_pedido()<2)
                        adpter.add(pedido);

            }

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });

    return view;
}


}
