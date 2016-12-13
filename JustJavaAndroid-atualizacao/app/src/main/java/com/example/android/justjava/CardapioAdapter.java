package com.example.android.justjava;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by mathe on 10/12/2016.
 */

public class CardapioAdapter {
    private Context context;
    private ProdutoSQLadapter BD;
    private ProdutoAdapter adapter;
    private ArrayList<Produto> lista_de_produtos= new ArrayList<>();

    public CardapioAdapter(Context context){
        this.context = context;
        BD = new ProdutoSQLadapter(context);
        this.adapter = criarAdapter();
    }

    private ProdutoAdapter criarAdapter (){

       atualizarBD(this.BD);
        return   adapter = new ProdutoAdapter(this.context, this.lista_de_produtos);
    }

    private void atualizarBD(ProdutoSQLadapter BD){
        while(BD.buscarSQLProduto()== null){

        }
        this.lista_de_produtos = BD.buscarSQLProduto();
    }

    public ArrayList<Produto> getListaProdutos (){
        return this.lista_de_produtos;
    }

    public ProdutoAdapter getAdapter() {
        return adapter;
    }


}
