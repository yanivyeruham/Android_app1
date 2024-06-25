package com.example.matala1new;

import static android.view.View.INVISIBLE;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity
{

    private AppCompatImageView[] main_IMG_car;
    private AppCompatImageView[] main_IMG_cube;
    private MaterialButton main_BTN_right;
    private MaterialButton main_BTN_left ;
    private   AppCompatImageView[] main_IMG_hearts;

    // creating GameManager class
    private GameManager gm = new GameManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        SetInitViewState();
        initViews();
        gm.startGame();
    }
    private void findViews()
    {
        main_BTN_right = findViewById(R.id.main_BTN_right);
        main_BTN_left = findViewById(R.id.main_BTN_left);

        main_IMG_cube = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_cube1),
                findViewById(R.id.main_IMG_cube6),
                findViewById(R.id.main_IMG_cube11),
                findViewById(R.id.main_IMG_cube2),
                findViewById(R.id.main_IMG_cube7),
                findViewById(R.id.main_IMG_cube12),
                findViewById(R.id.main_IMG_cube3),
                findViewById(R.id.main_IMG_cube8),
                findViewById(R.id.main_IMG_cube13),
                findViewById(R.id.main_IMG_cube4),
                findViewById(R.id.main_IMG_cube9),
                findViewById(R.id.main_IMG_cube14),
                findViewById(R.id.main_IMG_cube5),
                findViewById(R.id.main_IMG_cube10),
                findViewById(R.id.main_IMG_cube15),
        };

        main_IMG_car = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_car1),
                findViewById(R.id.main_IMG_car2),
                findViewById(R.id.main_IMG_car3),
        };


        main_IMG_hearts = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)
        };


    }

    protected void updateUI()
    {
        int[][] matrix = gm.getMatrix();

        int totalColumns = matrix[0].length;
        int totalRows = matrix.length;

        for (int row = 0; row < totalRows; row++)
        {
            for (int col = 0; col < totalColumns; col++)
            {
                int index = row * totalColumns + col;

                if (matrix[row][col] == 1)
                {
                    main_IMG_cube[index].setVisibility(View.VISIBLE);
                } else
                {
                    main_IMG_cube[index].setVisibility(View.INVISIBLE);
                }
            }
        }
        updateHearts(matrix, gm.getCar());

    }
    private void SetInitViewState()
    {
        for(int i=0; i<15;i++)
        {
            main_IMG_cube[i].setVisibility(View.INVISIBLE);
        }
        main_IMG_car[0].setVisibility(View.INVISIBLE);
        main_IMG_car[2].setVisibility(View.INVISIBLE);
    }
    private void initViews()
    {
        main_BTN_left.setOnClickListener(v->moveLeft());
        main_BTN_right.setOnClickListener(v->moveRight());

    }

    private void moveLeft()
    {
        int visibleCarIndex = gm.moveLeft() + 1;

        if(visibleCarIndex == 0)
        {
            return;
        }
        else if(visibleCarIndex == 1)
        {
            main_IMG_car[0].setVisibility(View.VISIBLE);
            main_IMG_car[1].setVisibility(View.INVISIBLE);
        }
        else if(visibleCarIndex == 2)
        {
            main_IMG_car[1].setVisibility(View.VISIBLE);
            main_IMG_car[2].setVisibility(View.INVISIBLE);
        }


    }

    private void moveRight()
    {
        int visibleCarIndex = gm.moveRight() - 1;

        if(visibleCarIndex == 2)
        {
            return;
        }
        else if(visibleCarIndex == 1)
        {
            main_IMG_car[1].setVisibility(View.INVISIBLE);
            main_IMG_car[2].setVisibility(View.VISIBLE);
        }
        else if(visibleCarIndex == 0)
        {
            main_IMG_car[1].setVisibility(View.VISIBLE);
            main_IMG_car[0].setVisibility(View.INVISIBLE);
        }

    }
    private void updateHearts(int[][] matrix, int[] car)
    {
        int life = gm.getLife();
        for(int i = 0; i < matrix[0].length; i++ )
        {
            if (matrix[matrix.length - 1][gm.getCarIndex()] == 1)
            {
                main_IMG_hearts[life].setVisibility(INVISIBLE);
                toast("crush!!!!");
                vibrate();

            }

        }
    }
    private void toast(String text)
    {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    private void vibrate()
    {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
    }
}