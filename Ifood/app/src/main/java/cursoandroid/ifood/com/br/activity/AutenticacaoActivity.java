
package cursoandroid.ifood.com.br.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import cursoandroid.ifood.com.br.R;

public class AutenticacaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autenticacao);
        getSupportActionBar().hide();
    }
}
