
package com.example.ministery.Learn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.example.ministery.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Locale;


public class SignActivity extends AppCompatActivity {
    ArrayList<ImageView> alphabet = new ArrayList<> ();
    ImageButton mSpeakBtn ;
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private static final int RESULT_SPEECH = 200;
    private TextInputEditText srcTextView ;
    public static boolean cheked=false;
    public ToggleButton targetSyncButton ;


    Integer res;
    int a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.sign );
        srcTextView = findViewById ( R.id.sourceText );
        mSpeakBtn = findViewById ( R.id.mic );
        targetSyncButton = findViewById(R.id.buttonSignTarget);
        targetSyncButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {  //eng-fren
                    srcTextView.setText ( "" );
                    cheked=true;
                    frenEng();
                } else { //arab
                    srcTextView.setText ( "" );
                    cheked=false;
                    arab();
                }
            }
        });

        if(cheked)   frenEng();
        else    arab();
    }

    private void startVoiceInput() {
        Intent intent = new Intent ( RecognizerIntent.ACTION_RECOGNIZE_SPEECH );
        intent.putExtra ( RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM );
        intent.putExtra ( RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault () );
        intent.putExtra ( RecognizerIntent.EXTRA_PROMPT, "We are listening to you :D" );
        try {
            startActivityForResult ( intent, REQ_CODE_SPEECH_INPUT );
        } catch (ActivityNotFoundException a) {

        }





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case REQ_CODE_SPEECH_INPUT:
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra ( RecognizerIntent.EXTRA_RESULTS );
                    srcTextView.setText ( result.get ( 0 ) );
                }
                break;

            default:
                super.onActivityResult ( requestCode, resultCode, data );
                break;
        }

    }


    public void frenEng()

    {
        for(int t=0;t<alphabet.size ();t++)
        {
            alphabet.get ( t ).clearColorFilter ();
        }
        //         srcTextView.setText ( "" );
        alphabet.clear ();

        alphabet.add ( (ImageView) findViewById ( R.id.img1 ));
        res = getResources ().getIdentifier ( "@drawable/ea", null, getPackageName () );
        alphabet.get ( 0 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img2 ) );
        res = getResources ().getIdentifier ( "@drawable/eb", null, getPackageName () );
        alphabet.get ( 1 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img3 ) );
        res = getResources ().getIdentifier ( "@drawable/ec", null, getPackageName () );
        alphabet.get ( 2 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img4 ) );
        res = getResources ().getIdentifier ( "@drawable/ed", null, getPackageName () );
        alphabet.get ( 3 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img5 ) );
        res = getResources ().getIdentifier ( "@drawable/eee", null, getPackageName () );
        alphabet.get ( 4 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img6 ) );
        res = getResources ().getIdentifier ( "@drawable/ef", null, getPackageName () );
        alphabet.get ( 5 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img7 ) );
        res = getResources ().getIdentifier ( "@drawable/eg", null, getPackageName () );
        alphabet.get ( 6 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img8 ) );
        res = getResources ().getIdentifier ( "@drawable/eh", null, getPackageName () );
        alphabet.get ( 7 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img9 ) );
        res = getResources ().getIdentifier ( "@drawable/ei", null, getPackageName () );
        alphabet.get ( 8 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img10 ) );
        res = getResources ().getIdentifier ( "@drawable/ej", null, getPackageName () );
        alphabet.get ( 9 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img11 ) );
        res = getResources ().getIdentifier ( "@drawable/ek", null, getPackageName () );
        alphabet.get ( 10 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img12 ) );
        res = getResources ().getIdentifier ( "@drawable/el", null, getPackageName () );
        alphabet.get ( 11).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img13 ) );
        res = getResources ().getIdentifier ( "@drawable/em", null, getPackageName () );
        alphabet.get ( 12).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img14 ) );
        res = getResources ().getIdentifier ( "@drawable/en", null, getPackageName () );
        alphabet.get ( 13).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img15 ) );
        res = getResources ().getIdentifier ( "@drawable/eo", null, getPackageName () );
        alphabet.get ( 14).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img16 ) );
        res = getResources ().getIdentifier ( "@drawable/ep", null, getPackageName () );
        alphabet.get ( 15 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img17 ) );
        res = getResources ().getIdentifier ( "@drawable/eq", null, getPackageName () );
        alphabet.get ( 16).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img18 ) );
        res = getResources ().getIdentifier ( "@drawable/er", null, getPackageName () );
        alphabet.get ( 17 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img19 ) );
        res = getResources ().getIdentifier ( "@drawable/es", null, getPackageName () );
        alphabet.get ( 18 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img20 ) );
        res = getResources ().getIdentifier ( "@drawable/et", null, getPackageName () );
        alphabet.get ( 19 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img21 ) );
        res = getResources ().getIdentifier ( "@drawable/eu", null, getPackageName () );
        alphabet.get ( 20 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img22 ) );
        res = getResources ().getIdentifier ( "@drawable/ev", null, getPackageName () );
        alphabet.get ( 21 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img23 ) );
        res = getResources ().getIdentifier ( "@drawable/ew", null, getPackageName () );
        alphabet.get ( 22 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img24 ) );
        res = getResources ().getIdentifier ( "@drawable/ex", null, getPackageName () );
        alphabet.get ( 23 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img25 ) );
        res = getResources ().getIdentifier ( "@drawable/ey", null, getPackageName () );
        alphabet.get ( 24 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img26 ) );
        res = getResources ().getIdentifier ( "@drawable/ez", null, getPackageName () );
        alphabet.get ( 25 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img27 ) );
        alphabet.add ( (ImageView) findViewById ( R.id.img28 ) );
        alphabet.get ( 26 ).setVisibility ( View.GONE );
        alphabet.get ( 27 ).setVisibility ( View.GONE );

        srcTextView.addTextChangedListener ( new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                for (int j = 0; j < 26; j++) {

                    alphabet.get ( j ).setColorFilter ( getResources ().getColor ( R.color.white ) );

                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                a = 0;
                for (int i = 0; i < s.length (); i++) {
                    char c = s.charAt ( i );
                    switch (c) {
                        case 'a' :case 'A':
                            res = getResources ().getIdentifier ( "@drawable/ea", null, getPackageName () );
                            if (alphabet.size () > a) {
                                alphabet.get ( a ).setImageResource ( res );
                                alphabet.get ( a ).clearColorFilter ();

                            }


                            break;
                        case 'b'  :case'B':
                            res = getResources ().getIdentifier ( "@drawable/eb", null, getPackageName () );
                            if (alphabet.size () > a) {

                                alphabet.get ( a ).setImageResource ( res );

                                alphabet.get ( a ).clearColorFilter ();
                            }

                            break;
                        case 'c'  :case'C':
                            res = getResources ().getIdentifier ( "@drawable/ec", null, getPackageName () );
                            if (alphabet.size () > a) {
                                alphabet.get ( a ).setImageResource ( res );
                                alphabet.get ( a ).clearColorFilter ();

                            }


                            break;
                        case 'd'  :case'D':
                            res = getResources ().getIdentifier ( "@drawable/ed", null, getPackageName () );
                            if (alphabet.size () > a) {

                                alphabet.get ( a ).setImageResource ( res );

                                alphabet.get ( a ).clearColorFilter ();
                            }

                            break;

                        case 'e'  :case'E':
                            res = getResources ().getIdentifier ( "@drawable/eee", null, getPackageName () );
                            if (alphabet.size () > a) {
                                alphabet.get ( a ).setImageResource ( res );
                                alphabet.get ( a ).clearColorFilter ();

                            }


                            break;
                        case 'f'  :case'F':
                            res = getResources ().getIdentifier ( "@drawable/ef", null, getPackageName () );
                            if (alphabet.size () > a) {

                                alphabet.get ( a ).setImageResource ( res );

                                alphabet.get ( a ).clearColorFilter ();
                            }

                            break;

                        case 'G'  :case'g':
                            res = getResources ().getIdentifier ( "@drawable/eg", null, getPackageName () );
                            if (alphabet.size () > a) {
                                alphabet.get ( a ).setImageResource ( res );
                                alphabet.get ( a ).clearColorFilter ();

                            }


                            break;
                        case 'h'  :case'H':
                            res = getResources ().getIdentifier ( "@drawable/eh", null, getPackageName () );
                            if (alphabet.size () > a) {

                                alphabet.get ( a ).setImageResource ( res );

                                alphabet.get ( a ).clearColorFilter ();
                            }

                            break;

                        case 'i'  :case'I':
                            res = getResources ().getIdentifier ( "@drawable/ei", null, getPackageName () );
                            if (alphabet.size () > a) {
                                alphabet.get ( a ).setImageResource ( res );
                                alphabet.get ( a ).clearColorFilter ();

                            }


                            break;
                        case 'j'  :case'J':
                            res = getResources ().getIdentifier ( "@drawable/ej", null, getPackageName () );
                            if (alphabet.size () > a) {

                                alphabet.get ( a ).setImageResource ( res );

                                alphabet.get ( a ).clearColorFilter ();
                            }

                            break;

                        case 'k'  :case'K':
                            res = getResources ().getIdentifier ( "@drawable/ek", null, getPackageName () );
                            if (alphabet.size () > a) {
                                alphabet.get ( a ).setImageResource ( res );
                                alphabet.get ( a ).clearColorFilter ();

                            }


                            break;
                        case 'l'  :case'L':
                            res = getResources ().getIdentifier ( "@drawable/el", null, getPackageName () );
                            if (alphabet.size () > a) {

                                alphabet.get ( a ).setImageResource ( res );

                                alphabet.get ( a ).clearColorFilter ();
                            }

                            break;

                        case 'm'  :case'M':
                            res = getResources ().getIdentifier ( "@drawable/em", null, getPackageName () );
                            if (alphabet.size () > a) {
                                alphabet.get ( a ).setImageResource ( res );
                                alphabet.get ( a ).clearColorFilter ();

                            }


                            break;
                        case 'n'  :case'N':
                            res = getResources ().getIdentifier ( "@drawable/en", null, getPackageName () );
                            if (alphabet.size () > a) {

                                alphabet.get ( a ).setImageResource ( res );

                                alphabet.get ( a ).clearColorFilter ();
                            }

                            break;

                        case 'o'  :case'O':
                            res = getResources ().getIdentifier ( "@drawable/eo", null, getPackageName () );
                            if (alphabet.size () > a) {
                                alphabet.get ( a ).setImageResource ( res );
                                alphabet.get ( a ).clearColorFilter ();

                            }


                            break;
                        case 'p'  :case 'P':
                            res = getResources ().getIdentifier ( "@drawable/ep", null, getPackageName () );
                            if (alphabet.size () > a) {

                                alphabet.get ( a ).setImageResource ( res );

                                alphabet.get ( a ).clearColorFilter ();
                            }

                            break;

                        case 'q'  :case'Q':
                            res = getResources ().getIdentifier ( "@drawable/eq", null, getPackageName () );
                            if (alphabet.size () > a) {
                                alphabet.get ( a ).setImageResource ( res );
                                alphabet.get ( a ).clearColorFilter ();

                            }


                            break;
                        case 'r' :case'R':
                            res = getResources ().getIdentifier ( "@drawable/er", null, getPackageName () );
                            if (alphabet.size () > a) {

                                alphabet.get ( a ).setImageResource ( res );

                                alphabet.get ( a ).clearColorFilter ();
                            }

                            break;

                        case 's'  :case'S':
                            res = getResources ().getIdentifier ( "@drawable/es", null, getPackageName () );
                            if (alphabet.size () > a) {
                                alphabet.get ( a ).setImageResource ( res );
                                alphabet.get ( a ).clearColorFilter ();

                            }


                            break;
                        case 't' :case'T':
                            res = getResources ().getIdentifier ( "@drawable/et", null, getPackageName () );
                            if (alphabet.size () > a) {

                                alphabet.get ( a ).setImageResource ( res );

                                alphabet.get ( a ).clearColorFilter ();
                            }

                            break;

                        case 'u'  :case'U':
                            res = getResources ().getIdentifier ( "@drawable/eu", null, getPackageName () );
                            if (alphabet.size () > a) {
                                alphabet.get ( a ).setImageResource ( res );
                                alphabet.get ( a ).clearColorFilter ();

                            }


                            break;
                        case 'v'  :case'V':
                            res = getResources ().getIdentifier ( "@drawable/ev", null, getPackageName () );
                            if (alphabet.size () > a) {

                                alphabet.get ( a ).setImageResource ( res );

                                alphabet.get ( a ).clearColorFilter ();
                            }

                            break;

                        case 'w'  :case'W':
                            res = getResources ().getIdentifier ( "@drawable/w", null, getPackageName () );
                            if (alphabet.size () > a) {

                                alphabet.get ( a ).setImageResource ( res );

                                alphabet.get ( a ).clearColorFilter ();
                            }

                            break;


                        case 'x'  :case'X':
                            res = getResources ().getIdentifier ( "@drawable/ex", null, getPackageName () );
                            if (alphabet.size () > a) {

                                alphabet.get ( a ).setImageResource ( res );

                                alphabet.get ( a ).clearColorFilter ();
                            }

                            break;


                        case 'y' :case'Y':
                            res = getResources ().getIdentifier ( "@drawable/ey", null, getPackageName () );
                            if (alphabet.size () > a) {

                                alphabet.get ( a ).setImageResource ( res );

                                alphabet.get ( a ).clearColorFilter ();
                            }

                            break;

                        case 'z'  :case'Z':
                            res = getResources ().getIdentifier ( "@drawable/ez", null, getPackageName () );
                            if (alphabet.size () > a) {

                                alphabet.get ( a ).setImageResource ( res );

                                alphabet.get ( a ).clearColorFilter ();
                            }

                            break;

                        default:

                            a = a - 1;

                            break;


                    }
                    a++;


                }
            }
        } );


        /**************************************************************************
         *
         *
         * translate voice
         *
         **************************************************************************/

        //    mSpeakBtn = (ImageButton) findViewById ( R.id.mi );
        mSpeakBtn.setOnClickListener ( new View.OnClickListener () {

            @Override
            public void onClick(View v) {
                startVoiceInput ();


            }
        } );

    }


    public void arab()

    {
        srcTextView.setText ( "" );

        for(int t=0;t<alphabet.size ();t++)
        {
            alphabet.get ( t ).clearColorFilter ();
        }
        alphabet.clear ();


        alphabet.add ( (ImageView) findViewById ( R.id.img7 ));
        res = getResources ().getIdentifier ( "@drawable/aaa", null, getPackageName () );
        alphabet.get ( 0 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img6 ) );
        res = getResources ().getIdentifier ( "@drawable/ab", null, getPackageName () );
        alphabet.get ( 1 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img5 ) );
        res = getResources ().getIdentifier ( "@drawable/ac", null, getPackageName () );
        alphabet.get ( 2 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img4 ) );
        res = getResources ().getIdentifier ( "@drawable/ad", null, getPackageName () );
        alphabet.get ( 3 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img3 ) );
        res = getResources ().getIdentifier ( "@drawable/ae", null, getPackageName () );
        alphabet.get ( 4 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img2 ) );
        res = getResources ().getIdentifier ( "@drawable/af", null, getPackageName () );
        alphabet.get ( 5 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img1 ) );
        res = getResources ().getIdentifier ( "@drawable/ag", null, getPackageName () );
        alphabet.get ( 6 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img14 ) );
        res = getResources ().getIdentifier ( "@drawable/ah", null, getPackageName () );
        alphabet.get ( 7 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img13) );
        res = getResources ().getIdentifier ( "@drawable/ai", null, getPackageName () );
        alphabet.get ( 8 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img12) );
        res = getResources ().getIdentifier ( "@drawable/aj", null, getPackageName () );
        alphabet.get ( 9 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img11 ) );
        res = getResources ().getIdentifier ( "@drawable/ak", null, getPackageName () );
        alphabet.get ( 10 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img10 ) );
        res = getResources ().getIdentifier ( "@drawable/al", null, getPackageName () );
        alphabet.get ( 11).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img9 ) );
        res = getResources ().getIdentifier ( "@drawable/am", null, getPackageName () );
        alphabet.get ( 12).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img8 ) );
        res = getResources ().getIdentifier ( "@drawable/an", null, getPackageName () );
        alphabet.get ( 13).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img21 ) );
        res = getResources ().getIdentifier ( "@drawable/ao", null, getPackageName () );
        alphabet.get ( 14).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img20 ) );
        res = getResources ().getIdentifier ( "@drawable/ap", null, getPackageName () );
        alphabet.get ( 15 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img19 ) );
        res = getResources ().getIdentifier ( "@drawable/aq", null, getPackageName () );
        alphabet.get ( 16).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img18 ) );
        res = getResources ().getIdentifier ( "@drawable/ar", null, getPackageName () );
        alphabet.get ( 17 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img17 ) );
        res = getResources ().getIdentifier ( "@drawable/as", null, getPackageName () );
        alphabet.get ( 18 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img16 ) );
        res = getResources ().getIdentifier ( "@drawable/at", null, getPackageName () );
        alphabet.get ( 19 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img15 ) );
        res = getResources ().getIdentifier ( "@drawable/au", null, getPackageName () );
        alphabet.get ( 20 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img28 ) );
        res = getResources ().getIdentifier ( "@drawable/av", null, getPackageName () );
        alphabet.get ( 21 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img27 ) );
        res = getResources ().getIdentifier ( "@drawable/aw", null, getPackageName () );
        alphabet.get ( 22 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img26 ) );
        res = getResources ().getIdentifier ( "@drawable/ax", null, getPackageName () );
        alphabet.get ( 23 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img25 ) );
        res = getResources ().getIdentifier ( "@drawable/ay", null, getPackageName () );
        alphabet.get ( 24 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img24 ) );
        res = getResources ().getIdentifier ( "@drawable/az", null, getPackageName () );
        alphabet.get ( 25 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img23 ) );
        res = getResources ().getIdentifier ( "@drawable/ayy", null, getPackageName () );
        alphabet.get ( 26 ).setImageResource ( res );
        alphabet.add ( (ImageView) findViewById ( R.id.img22 ) );
        res = getResources ().getIdentifier ( "@drawable/azz", null, getPackageName () );
        alphabet.get ( 27 ).setImageResource ( res );




        srcTextView.addTextChangedListener ( new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                for (int j = 0; j < 28; j++) {

                    alphabet.get ( j ).setColorFilter ( getResources ().getColor ( R.color.white ) );

                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                a = 0;
                for (int i = 0; i < s.length (); i++) {
                    char c = s.charAt ( i );
                    switch (c) {
                        case  'خ':
                            res = getResources ().getIdentifier ( "@drawable/ag", null, getPackageName () );
                            if (alphabet.size () > a) {
                                alphabet.get ( a ).setImageResource ( res );
                                alphabet.get ( a ).clearColorFilter ();

                            }


                            break;
                        case 'ح' :
                            res = getResources ().getIdentifier ( "@drawable/af", null, getPackageName () );
                            if (alphabet.size () > a) {

                                alphabet.get ( a ).setImageResource ( res );

                                alphabet.get ( a ).clearColorFilter ();
                            }

                            break;
                        case 'ج' :
                            res = getResources ().getIdentifier ( "@drawable/ae", null, getPackageName () );
                            if (alphabet.size () > a) {
                                alphabet.get ( a ).setImageResource ( res );
                                alphabet.get ( a ).clearColorFilter ();

                            }


                            break;
                        case 'ث':
                            res = getResources ().getIdentifier ( "@drawable/ad", null, getPackageName () );
                            if (alphabet.size () > a) {

                                alphabet.get ( a ).setImageResource ( res );

                                alphabet.get ( a ).clearColorFilter ();
                            }

                            break;

                        case 'ت' :case 'ة':
                            res = getResources ().getIdentifier ( "@drawable/ac", null, getPackageName () );
                            if (alphabet.size () > a) {
                                alphabet.get ( a ).setImageResource ( res );
                                alphabet.get ( a ).clearColorFilter ();

                            }


                            break;
                        case 'ب' :
                            res = getResources ().getIdentifier ( "@drawable/ab", null, getPackageName () );
                            if (alphabet.size () > a) {

                                alphabet.get ( a ).setImageResource ( res );

                                alphabet.get ( a ).clearColorFilter ();
                            }

                            break;

                        case 'ا' :case 'ئ': case 'إ': case 'أ':case 'ء':
                            res = getResources ().getIdentifier ( "@drawable/aaa", null, getPackageName () );
                            if (alphabet.size () > a) {
                                alphabet.get ( a ).setImageResource ( res );
                                alphabet.get ( a ).clearColorFilter ();

                            }


                            break;
                        case 'ص' :
                            res = getResources ().getIdentifier ( "@drawable/an", null, getPackageName () );
                            if (alphabet.size () > a) {

                                alphabet.get ( a ).setImageResource ( res );

                                alphabet.get ( a ).clearColorFilter ();
                            }

                            break;

                        case 'ش' :
                            res = getResources ().getIdentifier ( "@drawable/am", null, getPackageName () );
                            if (alphabet.size () > a) {
                                alphabet.get ( a ).setImageResource ( res );
                                alphabet.get ( a ).clearColorFilter ();

                            }


                            break;
                        case 'س' :
                            res = getResources ().getIdentifier ( "@drawable/al", null, getPackageName () );
                            if (alphabet.size () > a) {

                                alphabet.get ( a ).setImageResource ( res );

                                alphabet.get ( a ).clearColorFilter ();
                            }

                            break;

                        case 'ز':
                            res = getResources ().getIdentifier ( "@drawable/ak", null, getPackageName () );
                            if (alphabet.size () > a) {
                                alphabet.get ( a ).setImageResource ( res );
                                alphabet.get ( a ).clearColorFilter ();

                            }


                            break;
                        case 'ر':
                            res = getResources ().getIdentifier ( "@drawable/aj", null, getPackageName () );
                            if (alphabet.size () > a) {

                                alphabet.get ( a ).setImageResource ( res );

                                alphabet.get ( a ).clearColorFilter ();
                            }

                            break;

                        case 'ذ' :
                            res = getResources ().getIdentifier ( "@drawable/ai", null, getPackageName () );
                            if (alphabet.size () > a) {
                                alphabet.get ( a ).setImageResource ( res );
                                alphabet.get ( a ).clearColorFilter ();

                            }


                            break;
                        case 'د' :
                            res = getResources ().getIdentifier ( "@drawable/ah", null, getPackageName () );
                            if (alphabet.size () > a) {

                                alphabet.get ( a ).setImageResource ( res );

                                alphabet.get ( a ).clearColorFilter ();
                            }

                            break;

                        case 'ق':
                            res = getResources ().getIdentifier ( "@drawable/au", null, getPackageName () );
                            if (alphabet.size () > a) {
                                alphabet.get ( a ).setImageResource ( res );
                                alphabet.get ( a ).clearColorFilter ();

                            }


                            break;
                        case 'ف':
                            res = getResources ().getIdentifier ( "@drawable/at", null, getPackageName () );
                            if (alphabet.size () > a) {

                                alphabet.get ( a ).setImageResource ( res );

                                alphabet.get ( a ).clearColorFilter ();
                            }

                            break;

                        case 'غ' :
                            res = getResources ().getIdentifier ( "@drawable/as", null, getPackageName () );
                            if (alphabet.size () > a) {
                                alphabet.get ( a ).setImageResource ( res );
                                alphabet.get ( a ).clearColorFilter ();

                            }


                            break;
                        case 'ع' :
                            res = getResources ().getIdentifier ( "@drawable/ar", null, getPackageName () );
                            if (alphabet.size () > a) {

                                alphabet.get ( a ).setImageResource ( res );

                                alphabet.get ( a ).clearColorFilter ();
                            }

                            break;

                        case 'ظ' :
                            res = getResources ().getIdentifier ( "@drawable/aq", null, getPackageName () );
                            if (alphabet.size () > a) {
                                alphabet.get ( a ).setImageResource ( res );
                                alphabet.get ( a ).clearColorFilter ();

                            }


                            break;
                        case 'ط' :
                            res = getResources ().getIdentifier ( "@drawable/ap", null, getPackageName () );
                            if (alphabet.size () > a) {

                                alphabet.get ( a ).setImageResource ( res );

                                alphabet.get ( a ).clearColorFilter ();
                            }

                            break;

                        case 'ض':
                            res = getResources ().getIdentifier ( "@drawable/ao", null, getPackageName () );
                            if (alphabet.size () > a) {
                                alphabet.get ( a ).setImageResource ( res );
                                alphabet.get ( a ).clearColorFilter ();

                            }


                            break;
                        case 'ي': case 'ى':
                            res = getResources ().getIdentifier ( "@drawable/azz", null, getPackageName () );
                            if (alphabet.size () > a) {

                                alphabet.get ( a ).setImageResource ( res );

                                alphabet.get ( a ).clearColorFilter ();
                            }

                            break;

                        case 'و':case 'ؤ':
                            res = getResources ().getIdentifier ( "@drawable/ayy", null, getPackageName () );
                            if (alphabet.size () > a) {

                                alphabet.get ( a ).setImageResource ( res );

                                alphabet.get ( a ).clearColorFilter ();
                            }

                            break;


                        case 'ه':
                            res = getResources ().getIdentifier ( "@drawable/az", null, getPackageName () );
                            if (alphabet.size () > a) {

                                alphabet.get ( a ).setImageResource ( res );

                                alphabet.get ( a ).clearColorFilter ();
                            }

                            break;


                        case 'ن' :
                            res = getResources ().getIdentifier ( "@drawable/ay", null, getPackageName () );
                            if (alphabet.size () > a) {

                                alphabet.get ( a ).setImageResource ( res );

                                alphabet.get ( a ).clearColorFilter ();
                            }

                            break;

                        case 'م':
                            res = getResources ().getIdentifier ( "@drawable/ax", null, getPackageName () );
                            if (alphabet.size () > a) {

                                alphabet.get ( a ).setImageResource ( res );

                                alphabet.get ( a ).clearColorFilter ();
                            }
                            break;
                        case 'ل':
                            res = getResources ().getIdentifier ( "@drawable/aw", null, getPackageName () );
                            if (alphabet.size () > a) {

                                alphabet.get ( a ).setImageResource ( res );

                                alphabet.get ( a ).clearColorFilter ();
                            }
                            break;
                        case 'ك' :
                            res = getResources ().getIdentifier ( "@drawable/av", null, getPackageName () );
                            if (alphabet.size () > a) {

                                alphabet.get ( a ).setImageResource ( res );

                                alphabet.get ( a ).clearColorFilter ();
                            }

                            break;

                        default:

                            a = a - 1;

                            break;


                    }
                    a++;


                }
            }
        } );


        /**************************************************************************
         *
         *
         * translate voice
         *
         **************************************************************************/

        //    mSpeakBtn = (ImageButton) findViewById ( R.id.mi );
        mSpeakBtn.setOnClickListener ( new View.OnClickListener () {

            @Override
            public void onClick(View v) {
                startVoiceInputaARAB ();


            }
        } );

    }


    public void startVoiceInputaARAB(){

        Intent intent = new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        // intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "ar-AE");
        //intent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{"ar-AE"});
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ar-DZ");
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"tk.oryx.voice");
        intent.putExtra ( RecognizerIntent.EXTRA_PROMPT, "يمكنك التكلم الان D:" );



        try {
            startActivityForResult(intent,REQ_CODE_SPEECH_INPUT);
            srcTextView.setText("");
        } catch (ActivityNotFoundException a) {
            Toast t = Toast.makeText(getApplicationContext(),
                    "Opps! Your device doesn't support Arabic Speech to Text",
                    Toast.LENGTH_SHORT);
            t.show();
        }
    }
}