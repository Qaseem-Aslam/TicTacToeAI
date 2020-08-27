package com.example.muhammadshoaib.tictactoeai;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int[] boxes;
    Button[] buttons;
    int count = 0;
    Spinner depth;
    ArrayAdapter<CharSequence> depthAdapter;
    boolean turn = true,canPlay = true;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttons = new Button[9];
        buttons[0] = findViewById(R.id.button1);
        buttons[1] = findViewById(R.id.button2);
        buttons[2] = findViewById(R.id.button3);
        buttons[3] = findViewById(R.id.button4);
        buttons[4] = findViewById(R.id.button5);
        buttons[5] = findViewById(R.id.button6);
        buttons[6] = findViewById(R.id.button7);
        buttons[7] = findViewById(R.id.button8);
        buttons[8] = findViewById(R.id.button9);

        depth = (Spinner) findViewById(R.id.depth);
        depthAdapter = ArrayAdapter.createFromResource(MainActivity.this,R.array.depthArray,
                android.R.layout.simple_spinner_item);
        depthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        depth.setAdapter(depthAdapter);
        depth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AlphaBeta.plyDepth = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        boxes = new int[9];
        for(int i=0;i<9;i++)
            boxes[i] = -1;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                AlphaBeta.arr[i][j] = -1;
            }
        }
        Random rand=new Random();
        markComp(rand.nextInt(3),rand.nextInt(3));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void putSign(View v){
        switch(v.getId()) {
            case R.id.button1:
                if(boxes[0] == -1) {
                    mark(v,0);
                }
                break;
            case R.id.button2:
                if(boxes[1]  == -1) {
                    mark(v,1);
                }
                break;
            case R.id.button3:
                if(boxes[2] == -1) {
                    mark(v,2);
                }
                break;
            case R.id.button4:
                if(boxes[3] == -1) {
                    mark(v,3);
                }
                break;
            case R.id.button5:
                if(boxes[4] == -1) {
                    mark(v,4);
                }
                break;
            case R.id.button6:
                if(boxes[5] == -1) {
                    mark(v,5);
                }
                break;
            case R.id.button7:
                if(boxes[6] == -1) {
                    mark(v,6);
                }
                break;
            case R.id.button8:
                if(boxes[7] == -1) {
                    mark(v,7);
                }
                break;
            case R.id.button9:
                if(boxes[8] == -1) {
                    mark(v,8);
                }
                break;
            case R.id.resetGame:
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    void mark(View view,int num){
        check();
        if (turn && canPlay) {
            boxes[num] = 2;
            count++;
            AlphaBeta.arr[num/3][num%3] = 2;
            view.setBackground(getResources().getDrawable(R.drawable.icon_cross));
            view.setAlpha(1f);
            turn = false;
        }
        check();
        if(canPlay)
            markComp();
        check();
    }
    void check(){
        int result = new Node(AlphaBeta.arr,getApplicationContext()).isLeaf(false);
        if(result == 1 || result == 2 ||  count == 9) {
            if(result != 0) {
                if(result == 1)
                    Toast.makeText(this, "Game Over. Computer won! Please reset game.", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Game Over. Human Won! Please reset game.", Toast.LENGTH_SHORT).show();
            }else
                Toast.makeText(this, "It's a Draw! Please reset game.", Toast.LENGTH_SHORT).show();
            canPlay = false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    void markComp(){
        AlphaBeta.play();
        AlphaBeta.arr[AlphaBeta.iGlobalMax][AlphaBeta.jGlobalMax] = 1;
        boxes[(AlphaBeta.iGlobalMax * 3) + AlphaBeta.jGlobalMax] = 1;
        buttons[(AlphaBeta.iGlobalMax * 3) + AlphaBeta.jGlobalMax].setBackground(getResources().getDrawable(R.drawable.icon_tick));
        buttons[(AlphaBeta.iGlobalMax * 3) + AlphaBeta.jGlobalMax].setAlpha(1f);
        count++;
        turn = true;
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    void markComp(int i,int j){
        AlphaBeta.arr[i][j] = 1;
        boxes[(i * 3) + j] = 1;
        buttons[(i * 3) + j].setBackground(getResources().getDrawable(R.drawable.icon_tick));
        buttons[(i * 3) + j].setAlpha(1f);
        count++;
        turn = true;
    }
}
