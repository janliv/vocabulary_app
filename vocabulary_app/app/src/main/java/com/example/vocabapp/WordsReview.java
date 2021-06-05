package com.example.vocabapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class WordsReview extends AppCompatActivity {

    private TextView meaningView;
    private static List<String> wordList = new ArrayList<String>();
    private static List<String> meaningList = new ArrayList<String>();
    private static List<String> temp = new ArrayList<String>();

    private static List<Integer> shuffle = Arrays.asList(0, 1, 2, 3, 4); // Shuffle to get indexes above lists
    private static List<String> ans = new ArrayList<String>(); // Shuffle to get random A, B, C, D answer

    private ImageButton nextMeaning;
    private ImageButton tryAgain;
    private ImageButton backToLearn;

    private TextView ans_A, ans_B, ans_C, ans_D;

    private ImageView boxA, boxB, boxC, boxD;

    private int countCorrect = 0;
    private int countEXPs = 0;
    private int countTimesTryAgain = 0;

    private TextView title;

    private int currentIndex = 0;
    private int thisIndexInMeaningList = 0;

    private int thisIndexInWordList = 0;

    private List<String> commentsCorrect = Arrays.asList(
            "Totally correct!, + 100 EXPs",
            "Fast and furious huh?, + 100 EXPs",
            "Words master confirmed!, + 100 EXPs",
            "Really impressive!, + 100 EXPs",
            "Unstoppable!, + 100 EXPs");

    private List<String> commentsIncorrect = Arrays.asList(
            "Oh no, good luck next time!",
            "0% correct for sure!",
            "Don't give up buddy!",
            "Nice try but unlucky it's wrong!",
            "This time is red, next time must be green!");

    @Override
    public void onBackPressed() {
        Toast.makeText(WordsReview.this, " Please complete reviewing words first!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_review);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            wordList = bundle.getStringArrayList("wordsKey");
            meaningList = bundle.getStringArrayList("meaningKey");
        }

        meaningView = findViewById(R.id.meaningView);
        nextMeaning = findViewById(R.id.next_meaning);
        tryAgain = findViewById(R.id.shuffle);
        backToLearn = findViewById(R.id.backtolearn);

        ans_A = findViewById(R.id.ans_A);
        ans_B = findViewById(R.id.ans_B);
        ans_C = findViewById(R.id.ans_C);
        ans_D = findViewById(R.id.ans_D);

        boxA = findViewById(R.id.A_answer_box);
        boxB = findViewById(R.id.B_answer_box);
        boxC = findViewById(R.id.C_answer_box);
        boxD = findViewById(R.id.D_answer_box);

        title = findViewById(R.id.title);

        Collections.shuffle(shuffle);

        setDataTemp();
        setDataAns();

        reorderArray(meaningList);
        reorderArray(wordList);

        callNewMeaning();
        nextMeaning.setVisibility(View.INVISIBLE);
        tryAgain.setVisibility(View.INVISIBLE);
        backToLearn.setVisibility(View.INVISIBLE);

        nextMeaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNewMeaning();
                nextMeaning.setVisibility(View.INVISIBLE);
            }
        });

        tryAgain.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                Collections.shuffle(shuffle);
                reorderArray(meaningList);
                reorderArray(wordList);
                currentIndex = 0;
                callNewMeaning();
                tryAgain.setVisibility(View.INVISIBLE);
                backToLearn.setVisibility(View.INVISIBLE);
                countCorrect = 0;
                countEXPs = 0;
                countTimesTryAgain++;
                title.setText("Review");
                meaningView.setTextSize(20);
            }
        });

        backToLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WordsReview.this, Learn.class);
                startActivity(intent);
            }
        });

        // Handle click event each answer

        boxA.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (validateAns(ans_A.getText().toString())) {
                    boxA.setImageResource(R.drawable.a_correct);
                    countCorrect++;
                    Toast.makeText(WordsReview.this, commentsCorrect.get(randomInt(0, 4)), Toast.LENGTH_SHORT).show();
                } else {
                    if (validateAns(ans_B.getText().toString())) {
                        boxA.setImageResource(R.drawable.a_incorrect);
                        boxB.setImageResource(R.drawable.b_correct);
                    } else if (validateAns(ans_C.getText().toString())) {
                        boxA.setImageResource(R.drawable.a_incorrect);
                        boxC.setImageResource(R.drawable.c_correct);
                    } else if (validateAns(ans_D.getText().toString())) {
                        boxA.setImageResource(R.drawable.a_incorrect);
                        boxD.setImageResource(R.drawable.d_correct);
                    }
                    Toast.makeText(WordsReview.this, commentsIncorrect.get(randomInt(0, 4)), Toast.LENGTH_SHORT).show();
                }

                if (countTimesTryAgain == 0) {
                    countEXPs = countCorrect * 100;
                }

                boxA.setClickable(false);
                boxB.setClickable(false);
                boxC.setClickable(false);
                boxD.setClickable(false);

                nextMeaning.setVisibility(View.VISIBLE);
                if (currentIndex == 5) {
                    nextMeaning.setVisibility(View.INVISIBLE);
                    tryAgain.setVisibility(View.VISIBLE);
                    backToLearn.setVisibility(View.VISIBLE);
                    showResult();
                }
            }
        });

        boxB.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (validateAns(ans_B.getText().toString())) {
                    boxB.setImageResource(R.drawable.b_correct);
                    countCorrect++;
                    Toast.makeText(WordsReview.this, commentsCorrect.get(randomInt(0, 4)), Toast.LENGTH_SHORT).show();
                } else {
                    if (validateAns(ans_A.getText().toString())) {
                        boxB.setImageResource(R.drawable.b_incorrect);
                        boxA.setImageResource(R.drawable.a_correct);
                    } else if (validateAns(ans_C.getText().toString())) {
                        boxB.setImageResource(R.drawable.b_incorrect);
                        boxC.setImageResource(R.drawable.c_correct);
                    } else if (validateAns(ans_D.getText().toString())) {
                        boxB.setImageResource(R.drawable.b_incorrect);
                        boxD.setImageResource(R.drawable.d_correct);
                    }
                    Toast.makeText(WordsReview.this, commentsIncorrect.get(randomInt(0, 4)), Toast.LENGTH_SHORT).show();
                }

                if (countTimesTryAgain == 0) {
                    countEXPs = countCorrect * 100;
                }

                boxA.setClickable(false);
                boxB.setClickable(false);
                boxC.setClickable(false);
                boxD.setClickable(false);

                nextMeaning.setVisibility(View.VISIBLE);
                if (currentIndex == 5) {
                    nextMeaning.setVisibility(View.INVISIBLE);
                    tryAgain.setVisibility(View.VISIBLE);
                    backToLearn.setVisibility(View.VISIBLE);
                    showResult();
                }
            }
        });

        boxC.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (validateAns(ans_C.getText().toString())) {
                    boxC.setImageResource(R.drawable.c_correct);
                    countCorrect++;
                    Toast.makeText(WordsReview.this, commentsCorrect.get(randomInt(0, 4)), Toast.LENGTH_SHORT).show();
                } else {
                    if (validateAns(ans_A.getText().toString())) {
                        boxC.setImageResource(R.drawable.c_incorrect);
                        boxA.setImageResource(R.drawable.a_correct);
                    } else if (validateAns(ans_B.getText().toString())) {
                        boxC.setImageResource(R.drawable.c_incorrect);
                        boxB.setImageResource(R.drawable.b_correct);
                    } else if (validateAns(ans_D.getText().toString())) {
                        boxC.setImageResource(R.drawable.c_incorrect);
                        boxD.setImageResource(R.drawable.d_correct);
                    }
                    Toast.makeText(WordsReview.this, commentsIncorrect.get(randomInt(0, 4)), Toast.LENGTH_SHORT).show();
                }

                if (countTimesTryAgain == 0) {
                    countEXPs = countCorrect * 100;
                }

                boxA.setClickable(false);
                boxB.setClickable(false);
                boxC.setClickable(false);
                boxD.setClickable(false);

                nextMeaning.setVisibility(View.VISIBLE);
                if (currentIndex == 5) {
                    nextMeaning.setVisibility(View.INVISIBLE);
                    tryAgain.setVisibility(View.VISIBLE);
                    backToLearn.setVisibility(View.VISIBLE);
                    showResult();
                }
            }
        });

        boxD.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (validateAns(ans_D.getText().toString())) {
                    boxD.setImageResource(R.drawable.d_correct);
                    countCorrect++;
                    Toast.makeText(WordsReview.this, commentsCorrect.get(randomInt(0, 4)), Toast.LENGTH_SHORT).show();
                } else {
                    if (validateAns(ans_A.getText().toString())) {
                        boxD.setImageResource(R.drawable.d_incorrect);
                        boxA.setImageResource(R.drawable.a_correct);
                    } else if (validateAns(ans_B.getText().toString())) {
                        boxD.setImageResource(R.drawable.d_incorrect);
                        boxB.setImageResource(R.drawable.b_correct);
                    } else if (validateAns(ans_C.getText().toString())) {
                        boxD.setImageResource(R.drawable.d_incorrect);
                        boxC.setImageResource(R.drawable.c_correct);
                    }
                    Toast.makeText(WordsReview.this, commentsIncorrect.get(randomInt(0, 4)), Toast.LENGTH_SHORT).show();
                }

                if (countTimesTryAgain == 0) {
                    countEXPs = countCorrect * 100;
                }

                boxA.setClickable(false);
                boxB.setClickable(false);
                boxC.setClickable(false);
                boxD.setClickable(false);

                nextMeaning.setVisibility(View.VISIBLE);
                if (currentIndex == 5) {
                    nextMeaning.setVisibility(View.INVISIBLE);
                    tryAgain.setVisibility(View.VISIBLE);
                    backToLearn.setVisibility(View.VISIBLE);
                    showResult();
                }
            }
        });

    }

    private void callNewMeaning() {
        if (currentIndex < 5) {
            boxA.setImageResource(R.drawable.a_default);
            boxB.setImageResource(R.drawable.b_default);
            boxC.setImageResource(R.drawable.c_default);
            boxD.setImageResource(R.drawable.d_default);

            boxA.setClickable(true);
            boxB.setClickable(true);
            boxC.setClickable(true);
            boxD.setClickable(true);

            thisIndexInMeaningList = currentIndex;

            meaningView.setText(meaningList.get(currentIndex));

            ansArray(currentIndex);

            ans_A.setText(ans.get(0));
            ans_B.setText(ans.get(1));
            ans_C.setText(ans.get(2));
            ans_D.setText(ans.get(3));

            currentIndex++;
        }
    }

    private void reorderArray(List<String> arr) {
        for (int i = 0; i < 5; i++) {
            temp.set(shuffle.get(i), arr.get(i));
        }
        Collections.copy(arr, temp);
    }

    private void setDataTemp() {
        for (int i = 0; i < 5; i++) {
            temp.add(i, "");
        }
    }

    private void setDataAns() {
        for (int i = 0; i < 4; i++) {
            ans.add(i, "");
        }
    }

    private void ansArray(int index) {
        switch (index) {
            case 0:
            case 1:
                ans.set(0, wordList.get(index));
                ans.set(1, wordList.get(index + 1));
                ans.set(2, wordList.get(index + 2));
                ans.set(3, wordList.get(index + 3));
                break;
            case 2:
                ans.set(0, wordList.get(index));
                ans.set(1, wordList.get(index + 1));
                ans.set(2, wordList.get(index + 2));
                ans.set(3, wordList.get(index - 2));
                break;
            case 3:
                ans.set(0, wordList.get(index));
                ans.set(1, wordList.get(index + 1));
                ans.set(2, wordList.get(index - 3));
                ans.set(3, wordList.get(index - 2));
                break;
            case 4:
                ans.set(0, wordList.get(index));
                ans.set(1, wordList.get(index - 3));
                ans.set(2, wordList.get(index - 2));
                ans.set(3, wordList.get(index - 1));
                break;
        }
        Collections.shuffle(ans);
    }

    private boolean validateAns(String str) {
        for (int i = 0; i < 5; i++) {
            if (wordList.get(i).equals(str)) {
                thisIndexInWordList = i;
                break;
            }
        }
        return (thisIndexInWordList == thisIndexInMeaningList);
    }

    @SuppressLint("SetTextI18n")
    private void showResult() {
        title.setText("Result");
        meaningView.setTextSize(25);
        meaningView.setText("Congratulations!" + "\n\n");
        meaningView.append("Correct answers: " + countCorrect + "/5" + "\n");
        meaningView.append("Total EXPs gained: " + countEXPs);
    }

    private int randomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

}