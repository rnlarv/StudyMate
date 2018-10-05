package com.mad.studymate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.mad.studymate.R;
import com.mad.studymate.db.QuizTableController;
import com.mad.studymate.jsons.JsonCrud;
import com.mad.studymate.validation.QAValidation;

public class QnCAsActivity extends AppCompatActivity {

    ActionBar actionBar;
    Button addQuizButton, deleteQuizBt;

    String title;

    EditText questionEt1;
    RadioButton radioButtonTrue1;
    RadioButton radioButtonFalse1;

    EditText questionEt2;
    RadioButton radioButtonTrue2;
    RadioButton radioButtonFalse2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qn_cas);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Add Questions and Correct Answers");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = extras.getString("quiz");
        }

        questionEt1 = findViewById(R.id.idQuestionET);
        radioButtonTrue1 = findViewById(R.id.idTrueRadio);
        radioButtonFalse1 = findViewById(R.id.idFalseRadio);

        questionEt2 = findViewById(R.id.idQuestionET2);
        radioButtonTrue2 = findViewById(R.id.idTrueRadio2);
        radioButtonFalse2 = findViewById(R.id.idFalseRadio2);

        addQuizButton = findViewById(R.id.idAddQuizButton);
        addQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //to check if true or false answer is selected
                boolean firstRadioGroup = radioButtonTrue1.isChecked() || radioButtonFalse1.isChecked();
                boolean secondRadioGroup = radioButtonTrue2.isChecked() || radioButtonFalse2.isChecked();

                //valudate before update QnAs
                QAValidation validator = new QAValidation(getApplicationContext());
                if (validator.isFieldsEmpty(questionEt1.getText().toString(),
                        questionEt2.getText().toString(),
                        firstRadioGroup, secondRadioGroup)) {
                    return;
                }

                JsonCrud jsonCrud = new JsonCrud(getApplicationContext());
                jsonCrud.insert(title, questionEt1, questionEt2,
                        radioButtonTrue1, radioButtonTrue2, radioButtonFalse1, radioButtonFalse2);
                Intent intent = new Intent(QnCAsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        deleteQuizBt = findViewById(R.id.idCloseQuizButton);
        deleteQuizBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuizTableController quizController = new QuizTableController(getApplicationContext());
                quizController.deleteQuiz(title, v);
                quizController.close();
                finish();
            }
        });
    }

    //to disable os back button
    @Override
    public void onBackPressed() {
    }
}
