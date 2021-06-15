package com.sistemadegestaoagricola.entidades;

import android.graphics.Bitmap;
import android.util.Base64;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Util {

    private static ArrayList<AgendamentoReuniao> agendamentos = new ArrayList<AgendamentoReuniao>();

    public static ArrayList<AgendamentoReuniao> getAgendamentos() {
        return agendamentos;
    }

    public static void setAgendamentos(ArrayList<AgendamentoReuniao> agendamentos) {
        Util.agendamentos = agendamentos;
    }

    public static void addAgendamentos(AgendamentoReuniao reuniao){
        agendamentos.add(reuniao);
    }

    public static void esvaziarAgendamentos(){
        Util.agendamentos.clear();
    }

    public static Date converterStringDate(String data) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date d1 = null;
        try {
            d1 = df.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d1;
    }

    public static String calendarioIntParaStringDia(int dia){
        String diaSemana = null;
        if(dia == 1){
            diaSemana = "Domingo";
        } else if(dia == 2){
            diaSemana = "Segunda";
        } else if(dia == 3){
            diaSemana = "Terça";
        } else if(dia == 4){
            diaSemana = "Quarta";
        } else if(dia == 5){
            diaSemana = "Quinta";
        } else if(dia == 6){
            diaSemana = "Sexta";
        } else if(dia == 7){
            diaSemana = "Sabado";
        }
        return diaSemana;
    }

    public static String calendarioIntParaStringMes(int mes){
        String mesDoAno = null;
        if(mes == 0){
            mesDoAno = "Janeiro";
        } else if(mes == 1){
            mesDoAno = "Fevereiro";
        } else if(mes == 2){
            mesDoAno = "Março";
        } else if(mes == 3){
            mesDoAno = "Abril";
        } else if(mes == 4){
            mesDoAno = "Maio";
        } else if(mes == 5){
            mesDoAno = "Junho";
        } else if(mes == 6){
            mesDoAno = "Julho";
        } else if(mes == 7){
            mesDoAno = "Agosto";
        } else if(mes == 8){
            mesDoAno = "Setembro";
        } else if(mes == 9){
            mesDoAno = "Outubro";
        } else if(mes == 10){
            mesDoAno = "Novembro";
        } else if(mes == 11){
            mesDoAno = "Dezembro";
        }
        return mesDoAno;
    }

    public static String converterBitmapParaString(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] bytesArray = stream.toByteArray();
        return Base64.encodeToString(bytesArray,Base64.DEFAULT);
    }

    /*
     * Criar máscara para o CEP
     *@param editText Campo a ser aplicado a máscara
     *@param key Código correspondente ao caracter capturado pelo setOnKeyListener
     *@autor Gleison Alves de Araujo
     */
    public static void mascaraCepOnKeyListener(EditText editText, int key){
        String strCep = editText.getText().toString();
        if(key != 67){
            strCep = strCep.replace("-","");
            int tamanho = strCep.length();
            if(tamanho >= 5){
                strCep = strCep.substring(0,5) + "-" + strCep.substring(5,strCep.length());
                editText.setText(strCep);
                editText.setSelection(strCep.length());
            }
        } else {
            int tamanho = strCep.length();
            if(tamanho < 5){
                strCep = strCep.replace("-","");
                editText.setText(strCep);
                editText.setSelection(strCep.length());
            }
        }
    }
}