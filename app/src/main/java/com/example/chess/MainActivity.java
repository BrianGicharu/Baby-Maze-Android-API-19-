package com.example.chess;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private static String defaultOrientation = "Vertical";
    private static int cols;
    private static int rows;
    private final ImageView[][] gridLabels = new ImageView[13][16];
    private ImageView  compassBox;
    public int[] baby = {0,0};
    public TextView optionText, currentBabySquareBox, DirectionBox;
    public Button moveUp, moveDown, moveEast, moveWest, actButton, runButton, resetButton, exitButton, toggleVert, toggleHori, centerScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       initComponents();

    }

    public void initComponents() {

        optionText = findViewById(R.id.OrientationTextBox);
        optionText.setText(defaultOrientation);

        currentBabySquareBox = findViewById(R.id.currentBabySquare);
        currentBabySquareBox.setText("00");

        DirectionBox = findViewById(R.id.DirectionTextBox);
        DirectionBox.setText(" N");

        compassBox = findViewById(R.id.compassJLabel);
        compassBox.setBackgroundResource(R.drawable.compass_north);

        moveUp = findViewById(R.id.moveUpJButton);
        moveDown = findViewById(R.id.moveDownJButton);
        moveEast = findViewById(R.id.moveEastJButton);
        moveWest = findViewById(R.id.moveWestJButton);
        centerScroll = findViewById(R.id.centerJButton);
        centerScroll.setBackgroundResource(R.drawable.baby_dark);

        exitButton = findViewById(R.id.exitButton);

        actButton = findViewById(R.id.actJButton);

        runButton = findViewById(R.id.runJButton);

        resetButton = findViewById(R.id.ResetJButton);


        toggleVert = findViewById(R.id.toggleVerticalButton);
        toggleHori = findViewById(R.id.toggleHorizontalButton);


        cloneImageViewToXML();
        initLabelsGrid();

        //Action Listeners
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeApp();
            }
        });

        actButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonHaptics();
                babyAct();
            }
        });

        runButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonHaptics();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonHaptics();
                resetGrid();
            }
        });

        moveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    buttonHaptics();
                    BabyMoveNorth();
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        });

        moveDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    buttonHaptics();
                    BabyMoveSouth();
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        });

        moveEast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonHaptics();
                BabyMoveEast();
            }
        });


        moveWest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    buttonHaptics();
                    BabyMoveWest();
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        });

        // Orientation Buttons ActionListener Classes
        toggleVert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonHaptics();
                defaultOrientation = "Vertical";
                optionText.setText("Vertical");
                resetGrid();
            }
        });

        toggleHori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonHaptics();
                defaultOrientation = "Horizontal";
                optionText.setText("Horizontal");
                resetGrid();
            }
        });
        centerScroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonHaptics();
                if (centerScroll.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.baby_dark).getConstantState())) {
                    centerScroll.setBackgroundResource(R.drawable.baby_light);
                } else {
                    centerScroll.setBackgroundResource(R.drawable.baby_dark);
                }
            }
        });
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // This method moves the baby one move upwards
        public void BabyMoveNorth()
        {
            if(gridLabels[baby[0]-1][baby[1]].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.blank_space).getConstantState()))
            {
            baby[0]--;
            initLabelsGrid();
            DirectionBox.setText(" N");
            compassBox.setBackgroundResource(R.drawable.compass_north);
            currentBabySquareBox.setText(getBabySquare());
            }
        }

        // This method moves the baby one move to the Left
        public void BabyMoveEast()
        {
            if((baby[1]<15) && gridLabels[baby[0]][baby[1]+1].getBackground().getConstantState().equals( getResources().getDrawable(R.drawable.blank_space).getConstantState())) {
                baby[1]++;
                initLabelsGrid();
                DirectionBox.setText(" W");
                compassBox.setBackgroundResource(R.drawable.compass_east);
                currentBabySquareBox.setText(getBabySquare());
            }
        }

        // This method moves the baby one move to the Right
        public void BabyMoveWest()
        {
            if(gridLabels[baby[0]][baby[1]-1].getBackground().getConstantState().equals( getResources().getDrawable(R.drawable.blank_space).getConstantState())) {
                baby[1]--;
                initLabelsGrid();
                DirectionBox.setText(" E");
                compassBox.setBackgroundResource(R.drawable.compass_west);
                currentBabySquareBox.setText(getBabySquare());
            }
        }

        // This method moves the baby one move bottom-wise
        public void BabyMoveSouth()
        {
            if(gridLabels[baby[0]+1][baby[1]].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.blank_space).getConstantState()))
            {
                baby[0]++;
                initLabelsGrid();
                DirectionBox.setText(" S");
                compassBox.setBackgroundResource(R.drawable.compass_south);
                currentBabySquareBox.setText(getBabySquare());
            }
        }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    Runnable runner = new Runnable() {
        @Override
        public void run() {

        }
    };
    public void initLabelsGrid() {
        Random randy = new Random();
        int[] spaces = new int[16];
        int xp = 0;

        while (xp < 16) {
            spaces[xp] = randy.nextInt(13);
            System.out.println(spaces[xp]);
            xp++;
        }

        switch (defaultOrientation) {
            case "Vertical":
                for (rows = 0; rows < 13; rows++) {
                    for (cols = 0; cols < 16; cols++) {
                        if (rows== baby[0] && cols == baby[1]) {
                            gridLabels[rows][cols].setBackgroundResource(R.drawable.baby_light);
                            continue;
                        }
                        // Assigning white spaces(movable address) to the even indexed columns
                        if (cols % 2 == 0) {
                            gridLabels[rows][cols].setBackgroundResource(R.drawable.blank_space);

                        }
                        // Punching random spaces in the walls to allow the passage of baby
                        else if ((cols == 1 && rows == 8) || (cols == 3 && rows == 5) || (cols == 5 && rows == 12) || (cols == 7 && rows == 5)
                                || (cols == 9 && rows == 4) || (cols == 11 && rows == 10) || (cols == 13 && rows == 3) || (cols == 15 && rows == 0)) {
                            gridLabels[rows][cols].setBackgroundResource(R.drawable.blank_space);
                        } else {
                            gridLabels[rows][cols].setBackgroundResource(R.drawable.brick_wall);
                        }
                    }
                }
                break;

            case "Horizontal":
                defaultOrientation = "Horizontal";
                for (rows = 0; rows < 13; rows++) {
                    for (cols = 0; cols < 16; cols++) {
                        if (rows == baby[0] && cols == baby[1]) {
                            gridLabels[rows][cols].setBackgroundResource(R.drawable.baby_light);
                            continue;
                        }
                        // Creating blank spaces(The possible baby path)
                        if (rows % 2 == 0) {
                            gridLabels[rows][cols].setBackgroundResource(R.drawable.blank_space);
                        }
                        // Punching random spaces in the walls to allow the passage of baby
                        else if ((rows == 1 && cols == 8) || (rows == 3 && cols == 5) || (rows == 5 && cols == 12) || (rows == 7 && cols == 5)
                                || (rows == 9 && cols == 4) || (rows == 11 && cols == 10) || (rows == 13 && cols == 3) || (rows == 15 && cols == 0)) {
                            gridLabels[rows][cols].setBackgroundResource(R.drawable.blank_space);
                        } else {
                            gridLabels[rows][cols].setBackgroundResource(R.drawable.brick_wall);
                        }
                    }
                }
                break;
        }
    }

    // This method returns the 2D address of the baby in the maze in 1D format with index[0] as rows and index[1] as columns
    public int[] getBabyAddress()
    {
        int[] arr = new int[2];
        int x, y;
        for(x =0; x <13; x++)
        {
            for (y = 0; y < 16; y++)
            {
                if ((gridLabels[x][y].getBackground().getConstantState()).equals(getResources().getDrawable(R.drawable.baby_light).getConstantState()))
                {
                    arr[0] = x;
                    arr[1] = y;
                }
            }
        }
        return arr;
    }

    // This method returns the number of squares preceding the baby from array[0][0]
    public String getBabySquare()
    {
        int[] babySquare = getBabyAddress();
        return  String.format("%02d",(((babySquare[0])*16)+babySquare[1]));
    }


    public void cloneImageViewToXML() {
        for (rows = 0; rows < 13; rows++) {
            for (cols = 0; cols < 16; cols++) {
                gridLabels[rows][cols] = findViewById(getResources().getIdentifier((String.format("row%d_%d", rows, cols)), "id", getPackageName()));
            }
        }
    }

    public void resetGrid(){
        switch (defaultOrientation) {
            case "Vertical":
                defaultOrientation = "Vertical";
                for (rows = 0; rows < 13; rows++) {
                    for (cols = 0; cols < 16; cols++) {
                        if (rows == 0 && cols == 0) {
                            baby[0] = 0;
                            baby[1] = 0;
                            gridLabels[rows][cols].setBackgroundResource(R.drawable.baby_light);
                            continue;
                        }
                        // Assigning white spaces(movable address) to the even indexed columns
                        if (cols % 2 == 0) {
                            gridLabels[rows][cols].setBackgroundResource(R.drawable.blank_space);

                        }
                        // Punching random spaces in the walls to allow the passage of baby
                        else if ((cols == 1 && rows == 8) || (cols == 3 && rows == 5) || (cols == 5 && rows == 12) || (cols == 7 && rows == 5)
                                || (cols == 9 && rows == 4) || (cols == 11 && rows == 10) || (cols == 13 && rows == 3) || (cols == 15 && rows == 0)) {
                            gridLabels[rows][cols].setBackgroundResource(R.drawable.blank_space);
                        } else {
                            gridLabels[rows][cols].setBackgroundResource(R.drawable.brick_wall);
                        }
                    }
                }
                break;

            case "Horizontal":
                defaultOrientation = "Horizontal";
                for (rows = 0; rows < 13; rows++) {
                    for (cols = 0; cols < 16; cols++) {
                        if (cols == 0 && rows == 0) {
                            baby[0]=0;
                            baby[1]=0;
                            gridLabels[rows][cols].setBackgroundResource(R.drawable.baby_light);
                            continue;
                        }
                        // Creating blank spaces(The possible baby path)
                        if (rows % 2 == 0) {
                            gridLabels[rows][cols].setBackgroundResource(R.drawable.blank_space);
                        }
                        // Punching random spaces in the walls to allow the passage of baby
                        else if ((rows == 1 && cols == 8) || (rows == 3 && cols == 5) || (rows == 5 && cols == 12) || (rows == 7 && cols == 5)
                                || (rows == 9 && cols == 4) || (rows == 11 && cols == 10) || (rows == 13 && cols == 3) || (rows == 15 && cols == 0)) {
                            gridLabels[rows][cols].setBackgroundResource(R.drawable.blank_space);
                        } else {
                            gridLabels[rows][cols].setBackgroundResource(R.drawable.brick_wall);
                        }
                    }
                }
                break;
        }
    }

    //This method scans for spaces and moves the baby one step at a time on press of a button
    public void babyAct()
    {
        int index=0;
        int[] foundBlankOnPathAddress = new int[2];
        int[] currentBabyAddress = baby;
        int[] adjacentBabyAddress = new int[2];

        switch (defaultOrientation){
            case "Vertical":
                defaultOrientation = "Vertical";
                while(index<13)
                {
                    // Assigning new address on cell to the right
                    adjacentBabyAddress[0] = currentBabyAddress[0];
                    adjacentBabyAddress[1] = currentBabyAddress[1]+1;

                    // This code tests if a cell adjacent to the baby is a blank space
                    if(gridLabels[index][adjacentBabyAddress[1]].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.blank_space).getConstantState()))
                    {
                        foundBlankOnPathAddress[0]= index;
                        foundBlankOnPathAddress[1]= adjacentBabyAddress[1];
                        break;
                    }
                    index++;
                }
                if(gridLabels[currentBabyAddress[0]][currentBabyAddress[1]+1].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.blank_space).getConstantState()))
                {
                    BabyMoveEast();
                }
                else if(currentBabyAddress[0] < (foundBlankOnPathAddress[0]))
                {
                    BabyMoveSouth();
                }
                else if(currentBabyAddress[0] > foundBlankOnPathAddress[0])
                {
                    BabyMoveNorth();
                }
                else if(currentBabyAddress[0] == foundBlankOnPathAddress[0])
                {
                    BabyMoveEast();
                }
                break;

            case "Horizontal":
                while(index<16)
                {
                    // Assigning new address on cell to the cell below the baby
                    adjacentBabyAddress[0] = currentBabyAddress[0] + 1;
                    adjacentBabyAddress[1] = currentBabyAddress[1];

                    // This code tests if a cell adjacent to the baby is a blank space
                    if(gridLabels[adjacentBabyAddress[0]][index].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.blank_space).getConstantState()))
                    {
                        foundBlankOnPathAddress[0]= adjacentBabyAddress[0];
                        foundBlankOnPathAddress[1]= index;
                        break;
                    }
                    index++;
                }
                if(gridLabels[currentBabyAddress[0]+1][currentBabyAddress[1]].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.blank_space).getConstantState()))
                {
                    BabyMoveSouth();
                }
                else if(currentBabyAddress[1] < (foundBlankOnPathAddress[1]))
                {
                    BabyMoveEast();
                }
                else if(currentBabyAddress[1] == foundBlankOnPathAddress[1]-1)
                {
                    BabyMoveWest();
                }
                else if(currentBabyAddress[1] > foundBlankOnPathAddress[1])
                {
                    BabyMoveWest();
                }
                break;
        }
    }

    private void buttonHaptics() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(40);
    }

    public void closeApp() {
        Vibrator closingVibration = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        closingVibration.vibrate(100);
        finish();
    }

}