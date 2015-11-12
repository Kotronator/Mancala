/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mancala;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author tsipiripo
 */
public class Mancala {

    /**
     * @param args the command line arguments
     */
    static int [] mancalaBoard = new int[14];
    static int player=0;
    //123456M123456M
    
    public static void main(String[] args) {
        
        initiateBoard();
        mancalaBoard[0]=2;
        mancalaBoard[1]=5;
        mancalaBoard[2]=1;
        mancalaBoard[3]=0;
        mancalaBoard[4]=0;
        mancalaBoard[5]=0;
        printBoard(mancalaBoard);
        //( int[] mancalaBoard, int depth, Move a, Move b, int player , Pair<int[],Integer> p )
        Move choise=minimax(mancalaBoard, 1, new Move(Integer.MIN_VALUE, 0), new Move(Integer.MAX_VALUE, 0), 0, new Pair<int[],Integer>(null,0));
        doMove(mancalaBoard, 0, choise.movePosition);
         printBoard(mancalaBoard);
        //System.out.println(choise.movePosition+" "+choise.v);
//        askPlayerMove(0);
//        askPlayerMove(0);
    }
    
    public static void initiateBoard()
    {
        for (int i = 0; i < mancalaBoard.length; i++) {
            if(i==mancalaBoard.length/2-1||i==mancalaBoard.length-1)
                mancalaBoard[i]=0;
            else
                mancalaBoard[i]=4;
        }
    }
    
    public static void printBoard(int [] board)
    {
        System.out.println("  ---BOARD---");
        System.out.print(" |");
        for (int i = 0; i < board.length/2-1; i++) {

            System.out.print((i+1)+"|");  
        }
        System.out.println();
        for (int i = 0; i < board.length; i++) {
           
            System.out.print("-");
            
        }
        
        System.out.println();
        //--------------------------------
        System.out.print(" |");
        for (int i = board.length-2; i > board.length/2-1; i--) {
            int c = board[i];
            System.out.print(c+"|");
            
        }
        //--------------------------------
        System.out.println();
        System.out.print(board[board.length-1]);
        for (int i = 0; i < (board.length/2-1)*2+1; i++) {
            System.out.print("-");
            
        }
        System.out.print(board[board.length/2-1]);
        System.out.println();
        //--------------------------------
        System.out.print(" |");
        for (int i = 0; i < board.length/2-1; i++) {
            int c = board[i];
            System.out.print(c+"|");
            
        }
        //--------------------------------
        System.out.println();
    }
    
    public static void askPlayerMove(int player)
    {
        System.out.println("Player: "+player);
        System.out.println("Position to move?");
        Scanner in = new Scanner(System.in);
        int position = in.nextInt();
         if (player==0)
        {
            position--;
        }
        else
        {
            position=position+(6-position)*2+1;
        }
        doMove(mancalaBoard,0,position);
        printBoard(mancalaBoard);
    }
    
    public static int[] doMove(int[] board ,int player,int position)
    {
       
        int gems=board[position];
        board[position]=0;
        int extra=0;
        for (int i = position+1;  i<= position+gems+extra; i++) {
            if((player==0 &&  i==board.length-1)||(player==1 &&  i==board.length/2-1))
            {
                extra++;
               continue;
            }
            else
                board[i%14]++;
        }
        //printBoard(board);
        return board;
        
    }
    
    public static int evalutation(int[] mancalaBoard,int player)
    {
        int score=mancalaBoard[mancalaBoard.length/2-1]-mancalaBoard[mancalaBoard.length-1];
        if(player==0)
        {
            return score;
        }
        else return -score;
        
    }
    
    
    
//    r.v
//            r.move
     
    public static Move minimax( int[] mancalaBoard, int depth, Move a, Move b, int player , Pair<int[],Integer> p )
    {
        // Integer.MAX_VALUE
        Move v= new Move();
        if(depth==0 || finalState(mancalaBoard))
        {
            //System.out.println("d"+evalutation(mancalaBoard,player*-1+1)+":"+p.second);
            return new Move(evalutation(mancalaBoard,player*-1+1),p.second);
        }
        if(player==0)
        {
            v.v = new Integer(Integer.MIN_VALUE);
            List<Pair<int[],Integer>> childs;
            childs=generateChildren(mancalaBoard, player);
            for (Pair<int[],Integer> child : childs) {
                //v.movePosition=child.second;
                p.second=child.second;
                v=max(v, minimax(child.first, depth-1, a, b, player+1,p));
                a=max(a,v);
                //System.out.println("v"+v.movePosition+" "+v.v);
                if(b.v<=a.v)
                    break;
            }
            
        }
        else
        {
            v.v = Integer.MAX_VALUE;
            List<Pair<int[],Integer>> childs;
            childs=generateChildren(mancalaBoard, player);
            for (Pair<int[],Integer> child : childs) {
                v.movePosition=child.second;
                v=min(v, minimax(child.first, depth-1, a, b, player-1,p));
                a=min(b,v);
                if(b.v<=a.v)
                    break;
            }
        }
            
        return v;
    }
    
    public static Move min(Move move1 , Move move2)
    {
        if(move1.v<=move2.v)
            return move1;
        else
            return move2;
    }
    
     public static Move max(Move move1 , Move move2)
    {
        if(move1.v>=move2.v)
            return move1;
        else
            return move2;
    }
//      if depth = 0 or node is a terminal node
//          return the heuristic value of node
//      if maximizingPlayer
//          v := -∞
//          for each child of node
//              v := max(v, alphabeta(child, depth - 1, α, β, FALSE))
//              α := max(α, v)
//              if β ≤ α
//                  break (* β cut-off *)
//          return v
//      else
//          v := ∞
//          for each child of node
//              v := min(v, alphabeta(child, depth - 1, α, β, TRUE))
//              β := min(β, v)
//              if β ≤ α
//                  break (* α cut-off *)
//          return v

    private static boolean finalState(int[] mancalaBoard) {
        return (mancalaBoard[mancalaBoard.length/2-1]+mancalaBoard[mancalaBoard.length-1]==48);
    }

    private static List<Pair<int[],Integer>> generateChildren(int[] mancalaBoard, int player) {
        List<Pair<int[],Integer>> children = new LinkedList<Pair<int[],Integer>>();
        for (int i = 0; i < mancalaBoard.length/2-1; i++) {
            
            int j=i;
            if(player==1)
                j=i+7;
            if(mancalaBoard[j]!=0){
            children.add(
                    new Pair(
                            (doMove(getCopyOfBoard(mancalaBoard), player, j))
                               ,j
                         )
            )
                    ;
            }
        }
        return children;
    }

    private static int[] getCopyOfBoard(int[] mancalaBoard) {
        int[] board = new int[mancalaBoard.length]; 
        for (int i = 0; i < board.length; i++) {
            board[i]= mancalaBoard[i];
            
        }
        return board;
    }

    private static class Move {
        int v,movePosition;
        public Move() {
        }

        private Move(int evalutation, int move) {
            this.v=evalutation;
            this.movePosition=move;
        }
    }
    
    public static class Pair<F, S> {
        private F first; //first member of pair
        private S second; //second member of pair

        public Pair(F first, S second) {
            this.first = first;
            this.second = second;
        }

        public void setFirst(F first) {
            this.first = first;
        }

        public void setSecond(S second) {
            this.second = second;
        }

        public F getFirst() {
            return first;
        }

        public S getSecond() {
            return second;
        }
    }
}