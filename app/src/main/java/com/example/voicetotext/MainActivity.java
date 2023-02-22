package com.example.voicetotext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Activity {

    // costante che rappresenta il codice della richiesta di riconoscimento vocale
    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;

    // riferimento al TextView in cui verrà visualizzato il testo riconosciuto
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // recupero il riferimento al TextView definito nel layout
        mTextView = findViewById(R.id.textView_output);

        // recupero il riferimento al pulsante definito nel layout
        Button btnSpeechToText = findViewById(R.id.btn_speech_to_text);

        // imposto un listener sul pulsante per avviare il riconoscimento vocale
        btnSpeechToText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSpeechToText();
            }
        });
    }

    // metodo che avvia l'attività di riconoscimento vocale
    private void startSpeechToText() {
        // creo un intent per avviare l'attività di riconoscimento vocale
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        // imposto il modello linguistico per il riconoscimento
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        // imposto la lingua utilizzata per il riconoscimento
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        // imposto il testo visualizzato durante la registrazione
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak something...");

        try {
            // avvio l'attività di riconoscimento vocale e passo il codice della richiesta
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            // in caso di errore, mostro un messaggio Toast
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // metodo chiamato quando l'attività di riconoscimento vocale termina
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // controllo che il codice della richiesta sia quello corretto
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            // controllo che il riconoscimento sia stato effettuato con successo e che ci siano dati di risultato
            if (resultCode == RESULT_OK && data != null) {
                // recupero i risultati del riconoscimento sotto forma di ArrayList di stringhe
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                // recupero la prima stringa della lista, che dovrebbe contenere il testo riconosciuto
                String recognizedText = result.get(0);

                // visualizzo il testo riconosciuto nel TextView
                mTextView.setText(recognizedText);
            }
        }
    }
}
