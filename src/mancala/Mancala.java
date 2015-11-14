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
    //static int [] mancalaBoard = new int[14];
    static int player=0;
    static MancalaBoard mancalaBoard= new MancalaBoard();
    //123456M123456M
    
    public static void main(String[] args) {
        
//        initiateBoard();
        mancalaBoard.board[0]=2;
        mancalaBoard.board[1]=5;
        mancalaBoard.board[2]=1;
        mancalaBoard.board[3]=0;
        mancalaBoard.board[4]=0;
        mancalaBoard.board[5]=0;
        printBoard(mancalaBoard.getBoard());
        //( int[] mancalaBoard, int depth, Move a, Move b, int player , Pair<int[],Integer> p )
        Move choise=minimax(mancalaBoard, 2, new Move(Integer.MIN_VALUE, 0), new Move(Integer.MAX_VALUE, 0));
        
        mancalaBoard.doMove(choise.movePosition);
        debugPrint("bin played "+choise.movePosition+" with value "+choise.v);
        printBoard(mancalaBoard.getBoard());
        //System.out.println(choise.movePosition+" "+choise.v);
//        askPlayerMove(0);
//        askPlayerMove(0);
    }
    
//    public static void initiateBoard()
//    {
//        for (int i = 0; i < mancalaBoard.length; i++) {
//            if(i==mancalaBoard.length/2-1||i==mancalaBoard.length-1)
//                mancalaBoard[i]=0;
//            else
//                mancalaBoard[i]=4;
//        }
//    }
    
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
    
//    public static void askPlayerMove(int player)
//    {
//        System.out.println("Player: "+player);
//        System.out.println("Position to move?");
//        Scanner in = new Scanner(System.in);
//        int position = in.nextInt();
//         if (player==0)
//        {
//            position--;
//        }
//        else
//        {
//            position=position+(6-position)*2+1;
//        }
//        doMove(mancalaBoard,0,position);
//        printBoard(mancalaBoard);
//    }
    
    public static boolean doMove(int[] board ,int player,int position)
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
        return true;
        
    }
    
    public static int evalutation(MancalaBoard mancalaBoard,int player)
    {
        int score= mancalaBoard.getGemsInMancalaOfPlayer(player)-
                        mancalaBoard.getGemsInMancalaOfPlayer(mancalaBoard.getNextPlayerId(player));
//        if(player==0)
//        {
            return score;
//        }
//        else return -score;
        
    }
    
    
    
//    r.v
//            r.move
     
    public static Move minimax(MancalaBoard mancalaBoard, int depth, Move a, Move b/*, int player , Pair<int[],Integer> p*/ )
    {
        // Integer.MAX_VALUE
        Move v= new Move();
        if(depth==0 || mancalaBoard.isFinished())
        {
            int val=evalutation(mancalaBoard,(mancalaBoard.playerTurn));
            int movepos = mancalaBoard.lastBinPlayed;
            debugPrint("Entering depth 0"+mancalaBoard.isFinished()+" "+val+":"+movepos);
            //System.out.println("d"+evalutation(mancalaBoard,player*-1+1)+":"+p.second);
            return new Move(val,movepos);
        }
        if(mancalaBoard.playerTurn==0)
        {
            debugPrint("Entering max");
            v.v = new Integer(Integer.MIN_VALUE);
            List<MancalaBoard> childs;
            childs=mancalaBoard.generateChildren();
            for (MancalaBoard child : childs) {
                //v.movePosition=child.lastBinPlayed;
                //p.second=child.second;
                v=max(v, minimax(child, depth-1, a, b));
                a=max(a,v);
                //System.out.println("v"+v.movePosition+" "+v.v);
                if(b.v<=a.v)
                    break;
            }
            
        }
        else
        {
            debugPrint("Entering min");
            v.v = Integer.MAX_VALUE;
            List<MancalaBoard> childs;
            childs=mancalaBoard.generateChildren();
            for (MancalaBoard child : childs) {
                //v.movePosition=child.second;
                v=min(v, minimax(child, depth-1, a, b));
                a=min(b,v);
                if(b.v<=a.v)
                    break;
            }
        }
            
        return v;
    }
    
    public static void debugPrint(Object obj)
    {
        System.out.println(obj);
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

//    private static List<Pair<int[],Integer>> generateChildren(int[] mancalaBoard, int player) {
//        List<Pair<int[],Integer>> children = new LinkedList<Pair<int[],Integer>>();
//        for (int i = 0; i < mancalaBoard.length/2-1; i++) {
//            
//            int j=i;
//            if(player==1)
//                j=i+7;
//            if(mancalaBoard[j]!=0){
//            children.add(
//                    new Pair(
//                            (doMove(getCopyOfBoard(mancalaBoard), player, j))
//                               ,j
//                         )
//            )
//                    ;
//            }
//        }
//        return children;
//    }

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
    
    public static class MancalaBoard {
        int [] board;
        int playerTurn;
        int totalGems;
        int binsPerPlayer;
        int numOfPlayers;
        int lastBinPlayed=-1;
        public MancalaBoard() 
        {
          this(4, 6, 2, 0);
        }
        
        public MancalaBoard(int gemsInABin, int binsPerPlayer, int numOfPlayers,int firstPlayer) 
        {
            
            this.numOfPlayers=numOfPlayers;
            board=new int[binsPerPlayer*numOfPlayers+numOfPlayers];
            this.binsPerPlayer=binsPerPlayer;
            playerTurn=firstPlayer;
            this.totalGems=numOfPlayers*binsPerPlayer*gemsInABin;
            for (int i = 0; i < board.length/numOfPlayers-1; i++) {
                
                for (int j = 0; j < numOfPlayers; j++) {
                    board[i+j*binsPerPlayer+1*j]=gemsInABin;
                    //board[i+binsPerPlayer+1]=gemsInABin;
                }
            }
        }
        
        public int[] getBoard()
        {
            return board;
        }
        
        public void doMove(int position)
        {
            if(this.board[position]==0)
                throw new UnsupportedOperationException("Player:"+this.playerTurn+" plays empty bin"+position);
            this.lastBinPlayed=position;
            int gems=board[position];
            board[position]=0;
            int extra=0;
            
            int i;
            for (i = position+1;  i<= position+gems+extra; i++) {
                //if((this.playerTurn==0 &&  i==board.length-1)||(this.playerTurn==1 &&  i==board.length/2-1))
                if(isMancala(i)&&getPlayerMancalaIndex(this.playerTurn)!=i)
                {
                    extra++;
                   continue;
                }
                else
                    board[i%14]++;
                
            }
            
            if(isIndexPlayersBin(-1)&&board[i-1]==1)
            {
                board[getPlayerMancalaIndex(this.playerTurn)]+=board[i-1]+board[(getPlayerMancalaIndex(getNextPlayerId(this.playerTurn)))-((i-1)%binsPerPlayer)];
                
            }
            
            if(i-1!=getPlayerMancalaIndex(this.playerTurn))
            {
                playerTurn=(playerTurn+1)%numOfPlayers;
            }
            
        }
        
        public List<MancalaBoard> generateChildren() {
            List<MancalaBoard> children = new LinkedList<MancalaBoard>();
            
            for (int i = this.getPlayerMancalaIndex(this.playerTurn)-this.binsPerPlayer; i < this.getPlayerMancalaIndex(this.playerTurn); i++) {

                if(this.board[i]!=0){
                    MancalaBoard child =this.getCopy();
                    child.doMove(i);
                    children.add(child);
                }
            }
            return children;
        }
        
        public MancalaBoard getCopy()
        {
            //(int gemsInABin, int binsPerPlayer, int numOfPlayers,int firstPlayer)
            MancalaBoard copy= new MancalaBoard(0, this.binsPerPlayer, this.numOfPlayers, this.playerTurn);
            for (int i = 0; i < this.board.length; i++) {
                copy.board[i]= this.board[i];
                
            }
            return copy;
        }
        
        public int getPlayerMancalaIndex(int player)
        {
            return player*(binsPerPlayer+1)+binsPerPlayer;
        }
        
        public int getGemsInMancalaOfPlayer(int player)
        {
            return this.board[this.getPlayerMancalaIndex(player)];
        }
        
        public boolean isMancala(int index)
        {
            return index%(binsPerPlayer+1)==binsPerPlayer;
        }
        
        public boolean isIndexPlayersBin(int index)
        {
            return isIndexPlayersBin(index, this.playerTurn);
        }
        
        public boolean isIndexPlayersBin(int index,int player)
        {
            return(index>=((binsPerPlayer+1)*player)&&index<getPlayerMancalaIndex(player));
        }
        
      
        
        public int getNextPlayerId(int id)
        {
            return (id+1)%numOfPlayers;
        }

        private boolean isFinished() {
            int sum = 0;
            for (int i = 0; i < numOfPlayers; i++) {
                sum=board[getPlayerMancalaIndex(i)];
            }
            return false&&sum==this.totalGems;
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
