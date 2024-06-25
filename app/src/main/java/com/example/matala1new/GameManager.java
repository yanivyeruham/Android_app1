package com.example.matala1new;

import static androidx.core.app.NotificationCompat.getVisibility;
import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import java.util.Random;

public class GameManager
{
    MainActivity ma;
    private static final long DELAY = 1000L;
    private int life = 3;

    private int[][] matrix = new int[5][3] ;
    private int [] car = new int[3];
    private int carIndex ;
    Random random = new Random();
    private Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run()
        {
            handler.postDelayed(this,DELAY);
            addNewLineToMatrix();
            updateMatrixRows();
            isCarCrushed();
            ma.updateUI();
        }



    };

    public GameManager(MainActivity v)
    {
        ma = v;
    }

    private void startThread()
    {
        handler.postDelayed(runnable,0);
    }
    private void stopThread()
    {
        handler.removeCallbacks(runnable);
    }


    private void setMatrix()
    {

        for(int i = 0; i < matrix.length ; i++)
        {
            for(int j = 0; j < matrix[0].length ; j++)
            {
                matrix[i][j] = 0;
            }
        }
    }

    private void updateMatrixRows()
    {
        for(int i = matrix.length - 1; i > 0 ; i--)
        {
            for(int j = 0; j < matrix[0].length ; j++)
            {
                matrix[i][j] = matrix[i - 1][j];
            }
        }
    }

    private void addNewLineToMatrix()
    {
        for(int i = 0; i < matrix[0].length; i++)
        {
            matrix[0][i] = 0;
        }
        int randomNumber = random.nextInt(3);
        matrix[0][randomNumber] = 1;
    }

    private void isCarCrushed()
    {
        if (matrix[matrix.length - 1][getCarIndex()] == 1)
        {
            life --;
        }
        if(life == 0)
        {
            stopThread();
        }
    }




    private void initBoard()
    {
        initCar();
        setMatrix();
        addNewLineToMatrix();
        ma.updateUI();
    }

    private void initCar()
    {
        carIndex = 1;
        car[0] = 0;
        car[1] = 1;
        car[2] = 0;
    }

    public int getCarIndex()
    {
        return carIndex;
    }

    public int[][] getMatrix()
    {
        return matrix;
    }

    public int[] getCar()
    {
        return car;
    }

    public int getLife()
    {
        return life;
    }

    public int moveLeft()
    {
        if(carIndex == 0)
        {
            return 0;
        }

        if(carIndex == 1)
        {
            car[0] = 1;
            car[1] = 0;
        }
        else if(carIndex == 2)
        {
            car[1]= 1;
            car[2]= 0;
        }
        carIndex--;
        return carIndex;
    }

    public int moveRight()
    {

        if(carIndex == 2)
        {
            return 2;
        }
        else if(carIndex == 1)
        {
            car[1] = 0;
            car[2] = 1;
        }
        else if(carIndex == 0)
        {
            car[1] = 1;
            car[0] = 0;
        }
        carIndex++;
        return carIndex;
    }


    public void startGame()
    {
        initBoard();
        startThread();
    }
}
