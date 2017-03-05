package br.com.mitraconsignado.webview;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //criar o banco de dados com o nome de crud.db
        db = openOrCreateDatabase("crud.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);

        //criar tabela clientes com id , nome e email...
        String clientes = "CREATE TABLE IF NOT EXISTS clientes (_id INTEGER PRIMARY KEY autoincrement," +
                "nome VARCHAR(50), email VARCHAR(50))";

        db.execSQL(clientes);

        Button btnCliente = (Button) findViewById(R.id.btnCliente);
        btnCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), CadastroActivity.class));

            }
        });

        final EditText txtBusca = (EditText) findViewById(R.id.txtBusca);
        txtBusca.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                String[] busca = new String[]{"%" + txtBusca.getText().toString() + "%"};

                //preencher o list view
                Cursor cursor = db.query("clientes", new String[]{"_id", "nome", "email"}, "nome LIKE ?", busca, null, null, "_id ASC", null);

                String[] campos = {"_id", "nome"};
                int[] ids = {R.id.txtId, R.id.txtNome};

                SimpleCursorAdapter adt = new SimpleCursorAdapter(getBaseContext(), R.layout.model_clientes, cursor, campos, ids, 0);
                ListView ltwDados = (ListView) findViewById(R.id.ltwDados);
                ltwDados.setAdapter(adt);

                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        db = openOrCreateDatabase("crud.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        //preencher o list view
        Cursor cursor = db.query("clientes", new String[]{"_id", "nome", "email"}, null, null, null, null, null);

        String[] campos = {"_id", "nome"};
        int[] ids = {R.id.txtId, R.id.txtNome};

        final SimpleCursorAdapter adt = new SimpleCursorAdapter(getBaseContext(), R.layout.model_clientes, cursor, campos, ids, 0);
        ListView ltwDados = (ListView) findViewById(R.id.ltwDados);
        ltwDados.setAdapter(adt);

        ltwDados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor retornoCursor = (Cursor) adt.getItem(position);
                Intent it = new Intent(getBaseContext(), EditarActivity.class);

                it.putExtra("codigo", retornoCursor.getInt(0));
                it.putExtra("nome", retornoCursor.getString(1));
                it.putExtra("email", retornoCursor.getString(retornoCursor.getColumnIndex("email")));

                startActivity(it);
            }
        });
    }

}
