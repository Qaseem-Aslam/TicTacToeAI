package com.example.muhammadshoaib.tictactoeai;

import java.util.ArrayList;

public class AlphaBetaC {
    static int iGlobalMin = -1,jGlobalMin = -1,iGlobalMax = -1,jGlobalMax = -1,plyDepth=10;
    public static int alphaBetaPruning(Node node,int alpha,int beta,boolean maxPlaying,int depth){
        int res = node.isLeaf(false);
        if(res == 0 || res == 1 || res == 2){
            if(res == 1 && !maxPlaying){
                return 10-depth;
            }
            if(res == 2 && !maxPlaying){
                return 10-depth;
            }
            if(res == 1 && maxPlaying){
                return depth-10;
            }
            if(res == 2 && maxPlaying){
                return depth-10;
            }

            if(res == 0){
                return 0;
            }
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
                        if(alpha>=beta){
                            break;
                        }
                    }
                }
            }
            int maximum = alphas.get(0);
            iGlobalMax = iS.get(0);
            jGlobalMax = jS.get(0);
            for(int i=1;i<alphas.size();i++)
                if(alphas.get(i)>maximum){
                    maximum = alphas.get(i);
                    iGlobalMax = iS.get(i);
                    jGlobalMax = jS.get(i);
                }
            return alpha;
        }else{
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    if(node.getCell(i,j) == -1){
                        node.setCell(i,j,2);
                        int tempBeta = Math.min(beta,alphaBetaPruning(node,alpha,beta,true,depth+1));
                        if(tempBeta != beta){
                            beta = tempBeta;
                            iGlobalMin = i;
                            jGlobalMin = j;
                        }
                        node.setCell(i,j,-1);
                        if(beta<=alpha){
                            break;
                        }
                    }
                }
            }
            return beta;
        }
    }
    public static int[][] arr = { {-1,-1,-1}, {-1,-1,-1}, {-1,-1,-1}};
    public static void main(String[] args) {
        Node n = new Node(arr);

        iGlobalMax=jGlobalMax=-1;
        if(n.isLeaf(false) == 3){
            alphaBetaPruning(n,-99999999,99999999,true,0);
            n.setCell(iGlobalMax,jGlobalMax,1);
        }
    }
}
