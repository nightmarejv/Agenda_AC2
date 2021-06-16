package com.example.listapersonagem.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listapersonagem.R;
import com.example.listapersonagem.dao.PersonagemDAO;
import com.example.listapersonagem.model.Personagem;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import static com.example.listapersonagem.ui.activities.ConstantesActivities.CHAVE_PERSONAGEM;

public class FormularioPersonagemActivity extends AppCompatActivity {




    private static final String TITULO_APPBAR_EDITA_PERSONAGEM = "Editar Personagem" ;

    private static final String TITULO_APPBAR_NOVO_PERSONAGEM = "Novo Personagem";

    private EditText campoNome;

    private EditText campoAltura;

    private EditText campoNascimento;

    private final PersonagemDAO dao = new PersonagemDAO();   //cria-se um banco de dados para o personagem

    private Personagem personagem;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_formulario_personagem_menu_salvar, menu);
        return super.onCreateOptionsMenu(menu);
                                                            //faz a imagem do check ficar em uma posição
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.activity_formulario_personagem_menu_salvar){
            finalizaFormulario();
        }
        return super.onOptionsItemSelected(item);
                                                            //Adciona a função de salvar no botao check
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_personagem);
                                                            //adciona um titulo


        inicializaCampos();
        configuraBotaoSalvar();       
        carregaPersonagem();
                                                             //Tem a função de otimizar o codigo
    }




    private void carregaPersonagem() {
        Intent dados = getIntent();
        if (dados.hasExtra(CHAVE_PERSONAGEM)) {
            setTitle(TITULO_APPBAR_EDITA_PERSONAGEM);
            personagem = (Personagem) dados.getSerializableExtra(CHAVE_PERSONAGEM);
            preencheCampos();
        } else {
            setTitle(TITULO_APPBAR_NOVO_PERSONAGEM);
            personagem = new Personagem();
        }
                                                            //quando clicado, carrega o personagem
    }




    private void preencheCampos() {
        campoNome.setText(personagem.getNome());
        campoAltura.setText(personagem.getAltura());
        campoNascimento.setText(personagem.getNascimento());
                                                            //Tem a função de preencher os campos do app
    }




    private void configuraBotaoSalvar() {
        Button botaoSalvar = findViewById(R.id.button_salvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalizaFormulario();                       //Faz encerrar e voltar para o inicio
            }
        });
    }




    private void finalizaFormulario() {
        preechePersonagem();

        if (personagem.idValido()) {
            dao.edita(personagem);
        } else {
            dao.salva(personagem);
        }
        finish();
                                                            //deixa uma acessibilidade na configuração dos atributos e volta para o menu inicial
    }




    private void inicializaCampos() {

                                                            //ativa um inicio no xmll
        campoNome = findViewById(R.id.eddittext_nome);
        campoAltura = findViewById(R.id.edittext_altura);   //Tem a função de pegar os Ids
        campoNascimento = findViewById(R.id.edittext_nascimento);

                                                            //faz a utilização de espaçamento e inicia a formatação
        SimpleMaskFormatter smfAltura = new SimpleMaskFormatter("N,NN");
        MaskTextWatcher mtwAltura = new MaskTextWatcher(campoAltura, smfAltura);
        campoAltura.addTextChangedListener(mtwAltura);

        SimpleMaskFormatter smfNascimento = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtwNascimento = new MaskTextWatcher(campoNascimento, smfNascimento);
        campoNascimento.addTextChangedListener(mtwNascimento);
    }




    private void preechePersonagem() {

        String nome = campoNome.getText().toString();
        String altura = campoAltura.getText().toString();
        String nascimento = campoNascimento.getText().toString();

        personagem.setNome(nome);
        personagem.setAltura(altura);
        personagem.setNascimento(nascimento);

                                                            //adciona textos nos campos do personagem

    }
}