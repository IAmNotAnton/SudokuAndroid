package com.example.sudoku;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.example.sudoku.generate_sudoku.Sudoku;
import com.example.sudoku.sudoku_board.SudokuBoard;

public class ActivityGame extends AppCompatActivity implements Runnable {



    private SudokuBoard sudokuBoard;
    private TextView    textViewMistakeCount;
    private TextView    textViewTimer;
    private int countMistake;

    private int seconds;
    private boolean isStop;
    private Handler handlerTimer;
    private static final String FILE = "saving.json";


    private final String mistakes = "Mistake";
    //private final String mistake = findViewById(R.string.app_mistake).toString();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        countMistake = 0;
        seconds = 0;
        textViewMistakeCount = findViewById(R.id.textViewMistakeCount);
        textViewMistakeCount.setText(mistakes + " " + countMistake + "/" + 3);
        textViewTimer = findViewById(R.id.textViewTimer);
        textViewTimer.setText("00:00");

        sudokuBoard = findViewById(R.id.sudokuBoard);

        Intent intent = getIntent();



        handlerTimer = new Handler();
        handlerTimer.postDelayed(this::run, 1000);

    }

    public void onTick() {
        if(!isStop) {
            this.seconds++;
            int seconds = this.seconds % 60;
            int minutes = this.seconds / 60;
            textViewTimer.setText(String.format("%02d:%02d", minutes, seconds));
        }
    }

    @Override
    public void onStop() {
        isStop = true;
        super.onStop();
    }

    @Override
    public void onStart() {
        isStop = false;
        super.onStart();
    }


    @Override
    public void onBackPressed() {

        DialogInterface.OnClickListener listener = (dialog, which) -> {
            switch (which) {

                case Dialog.BUTTON_POSITIVE:
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;

                case Dialog.BUTTON_NEGATIVE:
                    break;


            }
        };

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setMessage(R.string.app_dialog_by_exit);
        adb.setIcon(android.R.drawable.ic_dialog_info);
        adb.setPositiveButton(R.string.app_yes, listener);
        adb.setNegativeButton(R.string.app_no,  listener);

        adb.create();
        adb.show();
    }

    private void showAlterDialogGameOver() {

        DialogInterface.OnClickListener listener = (dialog, which) -> {
            switch (which) {
                case Dialog.BUTTON_POSITIVE:
                    Intent intentAgain = new Intent(this, ActivityGame.class);
                    startActivity(intentAgain);
                    Sudoku.generateSudoku(Sudoku.getDifficult());
                    finish();
                    break;

                case Dialog.BUTTON_NEGATIVE:
                    Intent intentExit = new Intent(this, MainActivity.class);
                    startActivity(intentExit);
                    finish();
                    break;
            }

        };

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle(R.string.app_game_over);
        adb.setMessage(R.string.app_msg_game_again);
        adb.setPositiveButton(R.string.app_yes, listener);
        adb.setNegativeButton(R.string.app_no,  listener);
        adb.create();
        adb.show();
    }

    private void showAlterDialogWin() {

        DialogInterface.OnClickListener listener = (dialog, which) -> {
            switch (which) {
                case Dialog.BUTTON_POSITIVE:
                    Intent intentAgain = new Intent(this, ActivityGame.class);
                    startActivity(intentAgain);
                    Sudoku.generateSudoku(Sudoku.getDifficult());
                    finish();
                    break;

                case Dialog.BUTTON_NEGATIVE:
                    Intent intentExit = new Intent(this, MainActivity.class);
                    startActivity(intentExit);
                    finish();
                    break;
            }

        };

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle(R.string.app_win);
        adb.setMessage(R.string.app_msg_game_again);
        adb.setPositiveButton(R.string.app_yes, listener);
        adb.setNegativeButton(R.string.app_no,  listener);
        adb.create();
        adb.show();
    }

    private void setNum(int num) {
        countMistake = sudokuBoard.setNumInCell(num) ? countMistake: countMistake + 1;
        textViewMistakeCount.setText(mistakes + " " + countMistake + "/" + 3);
        if(countMistake > 2) showAlterDialogGameOver();
        if(Sudoku.checkWin()) {
            showAlterDialogWin();
        }
    }


    public void onClick_One(View view) {
        setNum(1);
    }

    public void onClick_Two(View view) {
        setNum(2);
    }


    public void onClick_Three(View view) {
        setNum(3);
    }


    public void onClick_Four(View view) {
        setNum(4);
    }

    public void onClick_Five(View view) {
        setNum(5);
    }

    public void onClick_Six(View view) {
        setNum(6);
    }

    public void onClick_Seven(View view) {
        setNum(7);
    }

    public void onClick_Eight(View view) {
        setNum(8);
    }

    public void onClick_Nine(View view) {
        setNum(9);
    }

    @Override
    public void run() {
        onTick();
        handlerTimer.postDelayed(this::run, 1000);

    }
}