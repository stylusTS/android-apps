
package cursoandroid.ifood.com.br.activity;

import android.content.Intent;
//import android.support.annotation.NonNull;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

//import androidx.annotation.NonNull;
import cursoandroid.ifood.com.br.R;
import cursoandroid.ifood.com.br.helper.ConfiguracaoFirebase;

public class AutenticacaoActivity extends AppCompatActivity {

    private Button botaoAcessar;
    private EditText campoEmail, campoSenha;
    private Switch tipoAcesso;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autenticacao);
        getSupportActionBar().hide();

        inicializarComponentes();
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        //autenticacao.signOut();

        verificaUsuarioLogado();

        botaoAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = campoEmail.getText().toString();
                String senha = campoSenha.getText().toString();

                if (!email.isEmpty()) {
                    if (!senha.isEmpty()) {

                        if (tipoAcesso.isChecked()) { //Cadastro

                            autenticacao.createUserWithEmailAndPassword(email, senha)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {

                                                Toast.makeText(AutenticacaoActivity.this,
                                                        "Cadastro realizado com sucesso !",
                                                        Toast.LENGTH_SHORT).show();

                                                abrirTelaPrincipal();

                                            } else {

                                                String erroExcecao = "";

                                                try {
                                                    throw task.getException();
                                                } catch (FirebaseAuthWeakPasswordException e) {
                                                    erroExcecao = "Digite uma senha mais forte";
                                                } catch (FirebaseAuthInvalidCredentialsException e) {
                                                    erroExcecao = "Por favor digite um e-mail valido";
                                                } catch (FirebaseAuthUserCollisionException e) {
                                                    erroExcecao = "esta conta já foi criada";
                                                } catch (Exception e) {
                                                    erroExcecao = "Ao cadastrar usuario " + e.getMessage();
                                                    e.printStackTrace();
                                                }

                                                Toast.makeText(AutenticacaoActivity.this,
                                                        "Erro: " + erroExcecao,
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        } else { // Login

                            autenticacao.signInWithEmailAndPassword(
                                    email, senha
                            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {


                                        Toast.makeText(AutenticacaoActivity.this,
                                                "Logado com sucesso !",
                                                Toast.LENGTH_SHORT).show();

                                        abrirTelaPrincipal();

                                    } else {

                                        Toast.makeText(AutenticacaoActivity.this,
                                                "Erro ao fazer Login: " + task.getException(),
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });


                        }


                    } else {

                        Toast.makeText(AutenticacaoActivity.this,
                                "Preencha o Senha!",
                                Toast.LENGTH_SHORT).show();

                    }
                } else {

                    Toast.makeText(AutenticacaoActivity.this,
                            "Preencha o E-mail!",
                            Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    private void inicializarComponentes() {

        campoEmail = findViewById(R.id.editCadastroEmail);
        campoSenha = findViewById(R.id.editCadastroSenha);
        botaoAcessar = findViewById(R.id.buttonAcesso);
        tipoAcesso = findViewById(R.id.switchAcesso);

    }

    private void verificaUsuarioLogado() {

        FirebaseUser usuarioAtual = autenticacao.getCurrentUser();
        if (usuarioAtual != null) {
            abrirTelaPrincipal();
        }
    }

    private void abrirTelaPrincipal() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
    }
}
