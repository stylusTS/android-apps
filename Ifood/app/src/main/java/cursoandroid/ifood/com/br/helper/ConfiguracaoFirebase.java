package cursoandroid.ifood.com.br.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ConfiguracaoFirebase {

    private static DatabaseReference referenciaFireBase;
    private static FirebaseAuth referenciaAutenticacao;
    private static StorageReference referenciaStorage;


    public static String getIdUsuario(){

        FirebaseAuth autenticacao = getFirebaseAutenticacao();
        return autenticacao.getCurrentUser().getUid();
    }


    public static DatabaseReference getFirebase(){

        if(referenciaFireBase == null){
            referenciaFireBase = FirebaseDatabase.getInstance().getReference();

        }
        return referenciaFireBase;
    }

    public static StorageReference getFirebaseStorage(){

        if(referenciaStorage == null){
            referenciaStorage = FirebaseStorage.getInstance().getReference();

        }
        return referenciaStorage;
    }

    public static FirebaseAuth getFirebaseAutenticacao(){

        if(referenciaAutenticacao == null){
            referenciaAutenticacao = FirebaseAuth.getInstance();

        }
        return referenciaAutenticacao;
    }






}


