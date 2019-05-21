package com.example.root.re_presencas.estudante_activities._a.fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.re_presencas.R;
import com.example.root.re_presencas.all.notification.NotificationGenerator;
import com.example.root.re_presencas.controller.FingerPrintAuthenticator;
import com.example.root.re_presencas.estudante_activities._a.activities.EstPresenca;
import com.example.root.re_presencas.menus.EstudanteMenu;
import com.example.root.re_presencas.model.Alocacao;
import com.example.root.re_presencas.model.Aula;
import com.example.root.re_presencas.model.Disciplina;
import com.example.root.re_presencas.model.Inscricao;
import com.example.root.re_presencas.model.Marcacao;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class EstPresencasMarcacaoFragment extends Fragment {

    private static final DatabaseReference raiz = FirebaseDatabase.getInstance().getReference();
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private Switch swIsMarcado;
    private ImageView imgFinger;
    private static String idAula;
    private static String idInscricao;
    private Intent intent;
    private TextView tvCadeira, tvPeriodo, tvNome;

    public EstPresencasMarcacaoFragment() {
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_est_presencas_marcacao, container, false);

//        raiz = FirebaseDatabase.getInstance().getReference();
        try {
            fingerprintManager = (FingerprintManager) Objects.requireNonNull(getActivity()).getSystemService(
                    Context.FINGERPRINT_SERVICE);
            keyguardManager = (KeyguardManager) Objects.requireNonNull(getActivity()).getSystemService(Context.KEYGUARD_SERVICE);
            intent = Objects.requireNonNull(getActivity()).getIntent();
        } catch (NoClassDefFoundError | NullPointerException e) {
        }

        swIsMarcado = view.findViewById(R.id.sw_is_marcado);
        imgFinger = view.findViewById(R.id.img_finger);
        tvCadeira = view.findViewById(R.id.tv_cadeira_est_marc);
        tvPeriodo = view.findViewById(R.id.tv_periodo_est_marc);
        tvNome = view.findViewById(R.id.tv_nome_est_marc);

        //Starting FingerPrint here
        try {
            this.startFingerPrint();
            this.imgFingerClick();
            this.sessionOpened();
            this.getIdInscicao();
        } catch (NullPointerException e) {
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * Para recuperar o id da inscricao do estudante logado
     */
    private void getIdInscicao() {
        String idEstudante = intent.getStringArrayExtra(EstudanteMenu.EST_LOGADO)[0];
        Query query = raiz.child("inscricao").orderByChild("id_estudante").equalTo(idEstudante);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Inscricao inscricao = d.getValue(Inscricao.class);
                    assert inscricao != null;
                    idInscricao = inscricao.getId();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void sessionOpened() {
        final LinkedList<Aula> aulasDeHoje = new LinkedList<>();
        Query query = raiz.child("alocacao");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    final Alocacao alocacao = d.getValue(Alocacao.class);
                    assert alocacao != null;

                    Query query1 = raiz.child("aula").orderByChild("id_alocacao").equalTo(alocacao.getId());
                    query1.addValueEventListener(new ValueEventListener() {
                        @TargetApi(Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                final Aula aula = d.getValue(Aula.class);
                                assert aula != null;

                                int diaAula = Integer.parseInt(aula.getData().split("-")[0]);
                                int mesAula = Integer.parseInt(aula.getData().split("-")[1]);
                                int anoAula = Integer.parseInt(aula.getData().split("-")[2]);
                                boolean isSelado = aula.isIs_selado();

                                GregorianCalendar c = (GregorianCalendar) GregorianCalendar.getInstance();
                                int diaActual = c.get(GregorianCalendar.DAY_OF_MONTH);
                                int mesActual = c.get(GregorianCalendar.MONTH) + 1;
                                int anoActual = c.get(GregorianCalendar.YEAR);

                                //Adicionar intervalo de hora na verificacao para incluir dupla marcacao(duas aulas) num dia
                                if (diaAula == diaActual && mesAula == mesActual && anoAula == anoActual && !isSelado) {
                                    aulasDeHoje.add(aula);

                                    idAula = aula.getId();

                                    //Para verificar estudantes daquela turma
                                    Query query2 = raiz.child("inscricao");
                                    query2.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                                Inscricao inscricao = d.getValue(Inscricao.class);
                                                assert inscricao != null;

                                                if (inscricao.getId_disciplina().equals(alocacao.getId_disciplina())
                                                        && inscricao.getPeriodo().equals(alocacao.getPeriodo())
                                                        && inscricao.getAno() == Calendar.getInstance().get(Calendar.YEAR)) {
                                                    //Ja estamos perante estudantes de uma unica turma

                                                    String idEstudante = intent.getStringArrayExtra(EstudanteMenu.EST_LOGADO)[0];

                                                    //Verificacao para abrir a sessao para o estudante logado  android:exported="true"
                                                    if (idEstudante.equals(inscricao.getId_estudante())) {

                                                        try {
                                                            NotificationGenerator.openActivityNotification(
                                                                    Objects.requireNonNull(getActivity()).getApplicationContext(),
                                                                    "Sessão de marcação de presenças aberta",
                                                                    "Começar"
                                                            );
                                                        } catch (NullPointerException e) {
                                                        }

                                                        EstPresenca.disableTab(0, true, 0, true);

                                                        String nomeEstudante = intent.getStringArrayExtra(EstudanteMenu.EST_LOGADO)[1];
                                                        tvNome.setText(nomeEstudante);
                                                        tvPeriodo.setText(alocacao.getPeriodo());

                                                        //Para setar o nome da disciplina em cada estudante da turma
                                                        Query query3 = raiz.child("disciplina").orderByChild("id").equalTo(alocacao.getId_disciplina());
                                                        query3.addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                for (DataSnapshot d : dataSnapshot.getChildren()) {
                                                                    Disciplina disciplina = d.getValue(Disciplina.class);
                                                                    assert disciplina != null;
                                                                    tvCadeira.setText(disciplina.getDesignacao());
                                                                }
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                    }
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                } else if (diaAula == diaActual && mesAula == mesActual && anoAula == anoActual && isSelado) {
                                    //Para verificar estudantes daquela turma
                                    Query query2 = raiz.child("inscricao");
                                    query2.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                                Inscricao inscricao = d.getValue(Inscricao.class);
                                                assert inscricao != null;

                                                if (inscricao.getId_disciplina().equals(alocacao.getId_disciplina())
                                                        && inscricao.getPeriodo().equals(alocacao.getPeriodo())
                                                        && inscricao.getAno() == Calendar.getInstance().get(Calendar.YEAR)) {
                                                    //Ja estamos perante estudantes de uma unica turma

                                                    String idEstudante = intent.getStringArrayExtra(EstudanteMenu.EST_LOGADO)[0];

                                                    //Verificacao para fechar a sessao para o estudante logado
                                                    if (idEstudante.equals(inscricao.getId_estudante())) {
                                                        EstPresenca.disableTab(0, false, 1, false);
                                                    }
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    /**
     * Metodo para verificar estudantes que nao marcaram as presencas e atribuir-los faltas
     */
    public static void sessionClosed(final String aula_id) {
        Log.e("Entramos", aula_id);
        Query query = raiz.child("aula").orderByChild("id").equalTo(aula_id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Aula aula = d.getValue(Aula.class);
                    assert aula != null;

                    final LinkedList<String> idInscricoes = new LinkedList<>();
                    Query query1 = raiz.child("marcacao").orderByChild("id_aula").equalTo(aula_id);
                    query1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                Marcacao marcacao = d.getValue(Marcacao.class);
                                assert marcacao != null;

                                //adicionando id's das inscricoes que marcaram as presencas na sala
                                if (marcacao.getData().equals(getDataActual())) {
                                    idInscricoes.add(marcacao.getId_inscricao());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                    //pegando a alocacao(turma) em que o docente activou a sessao
                    Query query2 = raiz.child("alocacao").orderByChild("id").equalTo(aula.getId_alocacao());
                    query2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                final Alocacao alocacao = d.getValue(Alocacao.class);
                                assert alocacao != null;

                                Query query3 = raiz.child("inscricao");
                                query3.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                                            Inscricao inscricao = d.getValue(Inscricao.class);
                                            assert inscricao != null;

                                            //inscricoes que fazem parte daquela turma
                                            if (inscricao.getId_disciplina().equals(alocacao.getId_disciplina())
                                                    && inscricao.getPeriodo().equals(alocacao.getPeriodo())
                                                    && inscricao.getAno() == Calendar.getInstance().get(Calendar.YEAR)) {


                                                LinkedList<String> idsNaoMarcados = new LinkedList<>();
                                                boolean flag = false;
                                                for (String idInsc : idInscricoes) {//marco, silva
                                                    //verifica as inscricoes que nao marcaram presencas para poder DAR FALTA
                                                    if (idInsc.equals(inscricao.getId())) {
                                                        flag = true;
                                                    }
                                                }

                                                if (!flag) {
                                                    String idMarcacao = raiz.push().getKey();
                                                    Marcacao marcacao = new Marcacao(idMarcacao, aula_id, inscricao.getId(), false, getDataActual());
                                                    assert idMarcacao != null;
                                                    raiz.child("marcacao").child(idMarcacao).setValue(marcacao);
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void savePresenca() {
        String id = raiz.push().getKey();
        Marcacao marcacao = new Marcacao(id, idAula, idInscricao, true, getDataActual());
        assert id != null;
        raiz.child("marcacao").child(id).setValue(marcacao);

        Vibrator vibrator = (Vibrator) Objects.requireNonNull(getActivity()).getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
        swIsMarcado.setChecked(true);
        swIsMarcado.setText("Marcado");
        swIsMarcado.setHighlightColor(R.color.colorAccent);
        Toast.makeText(getContext(), "Presença marcada com sucesso!", Toast.LENGTH_SHORT).show();
    }

    private static String getDataActual() {
        GregorianCalendar c = (GregorianCalendar) GregorianCalendar.getInstance();
        int dia = c.get(GregorianCalendar.DAY_OF_MONTH);
        int mes = c.get(GregorianCalendar.MONTH) + 1;
        int ano = c.get(GregorianCalendar.YEAR);

        return dia + "-" + mes + "-" + ano;
    }

    private void imgFingerClick() {
        this.imgFinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Coloque o dedo no sensor para marcar a presença!", Toast.LENGTH_SHORT).show();
//                savePresenca();
            }
        });
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void startFingerPrint() {
        if (checkFingerPrintSettings()) {
            FingerPrintAuthenticator authenticator = FingerPrintAuthenticator.getInstanece();

            if (authenticator.cipherInit()) {
                FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(authenticator.getCipher());

                FingerPrintHandler fingerPrintHandler = new FingerPrintHandler();
                fingerPrintHandler.startAuthentication(cryptoObject);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private class FingerPrintHandler extends FingerprintManager.AuthenticationCallback {

        CancellationSignal signal;

        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            super.onAuthenticationError(errorCode, errString);
            Toast.makeText(getContext(), "Erro de autenticação.", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
            super.onAuthenticationHelp(helpCode, helpString);
        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            Toast.makeText(getContext(), "Falha no processo de autenticação.", Toast.LENGTH_SHORT).show();
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            savePresenca();
        }

        void startAuthentication(FingerprintManager.CryptoObject cryptoObject) {
            signal = new CancellationSignal();

            if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.USE_FINGERPRINT)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            fingerprintManager.authenticate(cryptoObject, signal, 0, this, null);
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    private boolean checkFingerPrintSettings() {
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.USE_FINGERPRINT)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        if (fingerprintManager.isHardwareDetected()) {
            if (fingerprintManager.hasEnrolledFingerprints()) {
                if (keyguardManager.isKeyguardSecure()) {
                    return true;
                }
            } else {
                Toast.makeText(getContext(), "Habilite o serviço de Impressão Digital primeiro!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Settings.ACTION_SECURITY_SETTINGS));
            }
        }
        return false;
    }

}
