package com.sistemadegestaoagricola.conexao;

import android.os.StrictMode;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class RotaCadastrarPropriedade implements Callable<ConexaoAPI> {

    private String[] mensagensExceptions = null;

    public RotaCadastrarPropriedade(){

    }

    @Override
    public ConexaoAPI call() throws Exception {
        String rota = "cadastrar-api";
        String parametros = "";
        String metodo = "POST";
        Map<String,String> propriedades = new HashMap<String,String>();
        propriedades.put("Accept","application/json");
        propriedades.put("Authorization","Bearer " + ConexaoAPI.getToken());
        ConexaoAPI con = new ConexaoAPI(rota,parametros,metodo,propriedades);
        Log.d("testeX","status user: " + con.getCodigoStatus());

        return con;

    }
}