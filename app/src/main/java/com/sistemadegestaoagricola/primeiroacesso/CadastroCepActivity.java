package com.sistemadegestaoagricola.primeiroacesso;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sistemadegestaoagricola.entidades.CarregarDialog;
import com.sistemadegestaoagricola.R;
import com.sistemadegestaoagricola.conexao.CepAPI;
import com.sistemadegestaoagricola.entidades.Propriedade;
import com.sistemadegestaoagricola.entidades.Util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CadastroCepActivity extends AppCompatActivity implements Runnable{

    private EditText edtCep;
    private Button btPular;
    private Button btBuscar;
    private String cep = "";
    private boolean bloquearBotao = true;
    private int status;
    private AlertDialog buscando;
    private Thread thread;
    private static final ExecutorService threadpool = Executors.newFixedThreadPool(3);
    private String[] mensagensExceptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cep);

        edtCep = findViewById(R.id.edtCepCadastroCep);
        btPular = findViewById(R.id.btPularCadastroCep);
        btBuscar = findViewById(R.id.btBuscarCadastroCep);

        CarregarDialog carregarDialog = new CarregarDialog(CadastroCepActivity.this);
        buscando = carregarDialog.criarDialogBuscarCep();

        edtCep.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                String strCep = edtCep.getText().toString();
                Log.d("testeX", "tecla " +i);
                if(i == 66){
                    if(!strCep.isEmpty()){
                        if(strCep.length() == 9){
                            bloquearBotao = false;
                            btBuscar.setClickable(true);
                            btBuscar.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.white));

                        } else {
                            bloquearBotao = true;
                            btBuscar.setClickable(false);
                            btBuscar.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.cinza_escuro));
                            Toast.makeText(CadastroCepActivity.this, "O CEP deve possui 8 números", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        bloquearBotao = true;
                        btBuscar.setClickable(false);
                        btBuscar.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.cinza_escuro));
                        Toast.makeText(CadastroCepActivity.this, "Digite o CEP", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Util.mascaraCepOnKeyListener(edtCep,i);
                }

                return false;
            }
        });

        btPular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), CadastroLocalizacaoActivity.class);
                startActivity(intent);
            }
        });

        btBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!bloquearBotao){
                    cep = edtCep.getText().toString();
                    //Removendo mascara
                    cep = cep.replace("-","");
                    if(cep.length() == 8){
                        buscando.show();
                        thread = new Thread(CadastroCepActivity.this);
                        thread.start();
                    }
                }
            }
        });
    }

    private void imprimirPropriedade(){
        System.out.println(Propriedade.getCep());
        System.out.println(Propriedade.getLogradouro());
        System.out.println(Propriedade.getBairro());
        System.out.println(Propriedade.getCidade());
        System.out.println(Propriedade.getEstado());
    }

    @Override
    public void run() {
        CepAPI cepAPI = new CepAPI(cep);
        boolean sucesso  = cepAPI.buscar();
        Intent intent  = new Intent(getApplicationContext(), CadastroLocalizacaoActivity.class);
        startActivity(intent);

        buscando.dismiss();

//        if(sucesso && cepAPI.encontrouCidade()){
//            CarregarDialog dialog = new CarregarDialog(CadastroCepActivity.this);
//            runOnUiThread(new Runnable() {
//               @Override
//               public void run() {
//                   AlertDialog alertDialog = dialog.criarDialogContinuarCadastroLocalizacao();
//                   alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Sim", new DialogInterface.OnClickListener() {
//                       @Override
//                       public void onClick(DialogInterface dialogInterface, int i) {
//                           Intent intent  = new Intent(getApplicationContext(), CadastroLocalizacaoActivity.class);
//                           startActivity(intent);
//                       }
//                   });
//                   alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Não", new DialogInterface.OnClickListener() {
//                       @Override
//                       public void onClick(DialogInterface dialogInterface, int i) {
////                           CarregarDialog carregarDialog = new CarregarDialog(CadastroCepActivity.this);
////                           salvando = carregarDialog.criarDialogSalvarInformacoes();
////                           salvando.show();
//                           salvarCadastroAPI();
//                           Intent intent  = new Intent(getApplicationContext(), HomeActivity.class);
//                           startActivity(intent);
//                       }
//                   });
//                   alertDialog.show();
//                   imprimirPropriedade();
//               }
//            });
//        } else {
//            Intent intent  = new Intent(getApplicationContext(), CadastroCidadeActivity.class);
//            startActivity(intent);
//        }
    }

//    public void salvarCadastroAPI(){
//        RotaCadastrarPropriedade rotaCadastrarPropriedade = new RotaCadastrarPropriedade();
//        Future<ConexaoAPI> future = threadpool.submit(rotaCadastrarPropriedade);
//
//        while(!future.isDone()){
//            //Aguardando
//        }
//
//        ConexaoAPI conexao = null;
//        try{
//            conexao = future.get();
//
//            mensagensExceptions = conexao.getMensagensExceptions();
//
//            if(mensagensExceptions == null){
//                //Sem erro de conexão
//                status = conexao.getCodigoStatus();
//                Log.d("testeX","statusSalvar =" + status);
//                if(status == 200){
//                    //Salvo com sucesso
//                    Toast.makeText(this, "Dados salvos com sucesso", Toast.LENGTH_SHORT).show();
//                } else {
//                    //Erro ao salvar
//                    Toast.makeText(this, "Dados não puderam ser salvos", Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                Erro(mensagensExceptions[0],mensagensExceptions[1]);
//            }
//
//        } catch (InterruptedException | ExecutionException e){
//            e.printStackTrace();
//            Erro("Falha na conexão","Tente novamente em alguns minutos");
//        } finally {
//            conexao.fechar();
//        }
//    }
//
//    private void Erro(String titulo, String subtitulo){
//        Intent intent = new Intent(this, ErroActivity.class);
//        intent.putExtra("TITULO", titulo);
//        intent.putExtra("SUBTITULO", subtitulo);
//        intent.putExtra("ACTIVITY","MainActivity");
//        this.startActivity(intent);
//    }
}