package br.com.mitraconsignado.webview;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroActivity extends AppCompatActivity {

    private AlertDialog alerta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //chamar o button cadastrar
        Button btnCadastrar = (Button)findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Aqui chamei os txt do layout cadastro
                EditText txtNome = (EditText)findViewById(R.id.txtNome);
                EditText txtEmail = (EditText)findViewById(R.id.txtEmail);

                //
               /* String SQL = "INSERT INTO clientes (nome, email) VALUES ('"+txtNome.getText().toString()+"'," +
                        " '"+txtEmail.getText().toString()+"')";

                SQLiteDatabase db = openOrCreateDatabase("crud.db", SQLiteDatabase.CREATE_IF_NECESSARY,null);

                db.execSQL(SQL);*/


                SQLiteDatabase db = openOrCreateDatabase("crud.db", SQLiteDatabase.CREATE_IF_NECESSARY,null);

                ContentValues ctv = new ContentValues();
                ctv.put("nome", txtNome.getText().toString());
                ctv.put("email", txtEmail.getText().toString());

                db.insert("clientes","id",ctv);
                exemplo_simples();
            }
        });
    }
    private void exemplo_simples() {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Adiciona");
        //define a mensagem
        builder.setMessage("Deseja Adicionar o Cliente...");
        //define um botão como positivo
        builder.setPositiveButton("Positivo", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(getBaseContext(), "positivo=" + arg1, Toast.LENGTH_SHORT).show();
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Negativo", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(getBaseContext(), "negativo=" + arg1, Toast.LENGTH_SHORT).show();
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }
}
