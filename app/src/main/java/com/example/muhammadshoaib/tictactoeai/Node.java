package com.example.muhammadshoaib.tictactoeai;

import android.content.Context;
import android.widget.Toast;

class Node{
    int[][] arr;
    Context c = null;
    Node(int[][] data){
        arr = new int[3][3];
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                arr[i][j] = data[i][j];
    }
    Node(int[][] data,Context co){
        arr = new int[3][3];
        for(int i=0;i<3;i++)
           for(int j=0;j<3;j++)
                arr[i][j] = data[i][j];
        c = co;
    }
    boolean checkRow(int num, int val){
        return arr[num][0] == arr[num][1] && arr[num][1] == arr[num][2] && arr[num][0] == val;
    }
    boolean checkCol(int num, int val){
        return arr[0][num] == arr[1][num] && arr[1][num] == arr[2][num] && arr[0][num] == val;
    }
    public int isLeaf(boolean debug){
        String s = "";
        for(int i=0;i<3;i++) {
            for (int j = 0; j < 3; j++)
                s += arr[i][j] + "";
            s+="\n";
        }
        if(debug)
            Toast.makeText(c, s, Toast.LENGTH_SHORT).show();
        if(checkRow(0,1) || checkRow(1,1) || checkRow(2,1))
            return 1;
        if(checkCol(0,1) || checkCol(1,1) || checkCol(2,1))
            return 1;
        if((arr[0][0] == arr[1][1] && arr[1][1] == arr[2][2] && arr[0][0] == 1) ||
                (arr[0][2] == arr[1][1] && arr[1][1] == arr[2][0] && arr[0][2] == 1))
            return 1;
        if(checkRow(0,2) || checkRow(1,2) || checkRow(2,2))
            return 2;
        if(checkCol(0,2) || checkCol(1,2) || checkCol(2,2))
            return 2;
        if( (arr[0][0] == arr[1][1] && arr[1][1] == arr[2][2] && arr[0][0] == 2) ||
                (arr[0][2] == arr[1][1] && arr[1][1] == arr[2][0] && arr[0][2] == 2))
            return 2;


        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                if(arr[i][j] == -1)
                    return 3;

        return 0;
    }
    int getCell(int r,int c){
        return arr[r][c];
    }
    void setCell(int r,int c,int val){
        arr[r][c] = val;
    }
}