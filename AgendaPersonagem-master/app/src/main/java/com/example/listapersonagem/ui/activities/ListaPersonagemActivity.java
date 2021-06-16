package com.example.listapersonagem.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listapersonagem.R;
import com.example.listapersonagem.dao.PersonagemDAO;
import com.example.listapersonagem.model.Personagem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.listapersonagem.ui.activities.ConstantesActivities.CHAVE_PERSONAGEM;

public class ListaPersonagemActivity<onCreateItemSelected> extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Lista de Personagens";

    private final PersonagemDAO dao = new PersonagemDAO();

    private ArrayAdapter<Personagem> adapter;





    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_personagem);
                                                                    //adciona um titulo
        setTitle(TITULO_APPBAR);
        configuraFacNovoPersonagem();
        configuraLista();


    }




    private void configuraFacNovoPersonagem() {
                                                                    //tem a função de pegar o botão
        FloatingActionButton botaoNovoPersonagem = findViewById(R.id.fab_novo_personagem);
        botaoNovoPersonagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreFormularioSalva();
            }
        });
    }




    private void abreFormularioSalva() {
        startActivity(new Intent(this, FormularioPersonagemActivity.class));
    }




                                                                    //impede os dados de serem deletados na hora de dar o back
    @Override
    protected void onResume() {
        super.onResume();
        atualizaPersonagem();
    }




    private void atualizaPersonagem() {
        adapter.clear();
        adapter.addAll(dao.todos());
    }





    private void remove(Personagem personagem) {
        dao.remove(personagem);
        adapter.remove(personagem);
                                                                    //se segurar o click excluí algo da lista
    }





    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.activity_lista_personagens_menu, menu);
    }




    public boolean onContextItemSelected(@NonNull MenuItem item) {
        configuraMenu(item);
        return super.onContextItemSelected(item);
    }




    private void configuraMenu(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.activity_lista_personagem_menu_remover) {

            new AlertDialog.Builder(this)
                    .setTitle("Removendo Personagem")
                    .setMessage("Tem certeza que deseja remover?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                            Personagem personagemEscolhido = adapter.getItem(menuInfo.position);
                            remove(personagemEscolhido);
                        }
                    })
                    .setNegativeButton("Não", null)
                    .show();
        }
    }




    private void configuraLista() {
        ListView listaDePersonagens = findViewById(R.id.lista_personagem);
        configuraAdapter(listaDePersonagens);
        configuraItemPorClique(listaDePersonagens);
                                                                        //Cria uma firmeza nos dados caso der o back
        registerForContextMenu(listaDePersonagens);
    }




    private void configuraItemPorClique(ListView listaDePersonagens) {

        listaDePersonagens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                abreFormularioModoEditar(adapterView, posicao);
            }
        });
    }




    private void abreFormularioModoEditar(AdapterView<?> adapterView, int posicao) {
        Personagem personagem = (Personagem) adapterView.getItemAtPosition(posicao);
        Intent vaiParaFormulario = new Intent(ListaPersonagemActivity.this, FormularioPersonagemActivity.class);
        vaiParaFormulario.putExtra(CHAVE_PERSONAGEM, personagem);
        startActivity(vaiParaFormulario);
                                                                        //seleciona items em uma certa posição especifica
    }




    private void configuraAdapter(ListView listaDePersonagens) {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listaDePersonagens.setAdapter(adapter);

    }

}
