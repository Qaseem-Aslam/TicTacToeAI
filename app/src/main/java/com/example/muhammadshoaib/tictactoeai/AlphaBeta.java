package com.example.muhammadshoaib.tictactoeai;

import java.util.ArrayList;

public class AlphaBeta {
    static int iGlobalMin = -1,jGlobalMin = -1,iGlobalMax = -1,jGlobalMax = -1,plyDepth=10;
    static boolean heuristic = true;
    private static int getCountRow(int row,int key){
        int count=0;
        for(int i=0;i<3;i++)
            if(arr[row][i] == key)
                count = 1;
            else if(arr[row][i] != -1){
                count = 0;
                break;
            }
        return count;
    }
    private static int getCountCol(int col,int key){
        int count=0;
        for(int i=0;i<3;i++)
            if(arr[i][col] == key)
                count = 1;
            else if(arr[i][col] != -1){
                count = 0;
                break;
            }
        return count;
    }

    private static int getCountDiag(int key){
        int diagMain = 0,diagSecond = 0;
        for(int i=0;i<3;i++){
            if(arr[i][i] == key)
                diagMain = 1;
            else if(arr[i][i] != -1){
                diagMain = 0;
                break;
            }
        }
        for(int i=0;i<3;i++){
            if(arr[i][2-i] == key)
                diagSecond = 1;
            else if(arr[i][2-i] != -1){
                diagSecond = 0;
                break;
            }
        }
        return diagSecond+diagMain;
    }

    public static int alphaBetaPruning(Node node,int alpha,int beta,boolean maxPlaying,int depth){
        int res = node.isLeaf(false);
        if(res == 0 || res == 1 || res == 2 || depth == plyDepth){
            if(res == 1)
                return 1;
            if(res == 2)
                return -1;
            if(res == 0)
                return 0;
            if(maxPlaying){
                int value = 0;
                for(int i=0;i<3;i++)
                    value += (getCountRow(i,2) + getCountCol(i,2));
                value+=getCountDiag(2);
                return value * -1;
            }

            int value = 0;
            for(int i=0;i<3;i++)
                value += (getCountRow(i,1) + getCountCol(i,1));
            value+=getCountDiag(1);
            return value;

        }
        if(maxPlaying){
            ArrayList<Integer> alphas = new ArrayList<>();
            ArrayList<Integer> iS = new ArrayList<>();
            ArrayList<Integer> jS = new ArrayList<>();
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    if(node.getCell(i,j) == -1){
                        node.setCell(i,j,1);
                        int tempAlpha = Math.max(alpha,alphaBetaPruning(node,alpha,beta,false,depth+1));
                        alphas.add(tempAlpha);
                        iS.add(i);
                        jS.add(j);
                        if(tempAlpha != alpha){
                            alpha = tempAlpha;
                            iGlobalMax = i;
                            jGlobalMax = j;
                        }
                        node.setCell(i,j,-1);
                        if(alpha>=beta)
                            break;
                    }
                }
            }
            if(alphas.size() != 0){
                int maximum = alphas.get(0);
                iGlobalMax = iS.get(0);
                jGlobalMax = jS.get(0);
                for(int i=1;i<alphas.size();i++)
                    if(alphas.get(i)>maximum){
                        maximum = alphas.get(i);
                        iGlobalMax = iS.get(i);
                        jGlobalMax = jS.get(i);
                    }
                return maximum;
            }
            return alpha;
        }else{
            ArrayList<Integer> betas = new ArrayList<>();
            ArrayList<Integer> iS = new ArrayList<>();
            ArrayList<Integer> jS = new ArrayList<>();
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    if(node.getCell(i,j) == -1){
                        node.setCell(i,j,2);
                        int tempBeta = Math.min(beta,alphaBetaPruning(node,alpha,beta,true,depth+1));
                        betas.add(tempBeta);
                        iS.add(i);
                        jS.add(j);
                        if(tempBeta != beta){
                            beta = tempBeta;
                            iGlobalMin = i;
                            jGlobalMin = j;
                        }
                        node.setCell(i,j,-1);
                        if(beta<=alpha)
                            break;
                    }
                }
            }
            if(betas.size() != 0){
                int min = betas.get(0);
                iGlobalMin = iS.get(0);
                jGlobalMin = jS.get(0);
                for(int i=1;i<betas.size();i++)
                    if (betas.get(i) < min) {
                        min = betas.get(i);
                        iGlobalMin = iS.get(i);
                        jGlobalMin = jS.get(i);
                    }
                return min;
            }
            return beta;
        }
    }
    public static int[][] arr = {{-1,-1,-1},{-1,-1,-1},{-1,-1,-1}};
    public static void play() {

        Node n = new Node(arr);

        iGlobalMax=jGlobalMax=-1;
        if(n.isLeaf(false) == 3){
            alphaBetaPruning(n,-99999999,99999999,true,0);
            n.setCell(iGlobalMax,jGlobalMax,1);
        }
    }
}
