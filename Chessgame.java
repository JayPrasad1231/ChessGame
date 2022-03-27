import java.util.*;

public class Chessgame {
    public void startGame()
    {
        System.out.println("GAME START: CHESS!");
        System.out.println("Enter your move in the following format: piece, location (ex. knight A6)");
        Chessboard board = new Chessboard();
        Scanner sc = new Scanner(System.in);
        board.display();
        boolean playing = true, legal = false;
        int player = 0; // If player is 0, it is white's turn, and if it 1 it is black's turn.
        while(playing)
        {
            while (!legal)
            {
                System.out.println("If You Want to Forfeit, enter Q followed by W or B, for that color forfeiting");
                System.out.println("Otherwise, enter your next move (beginning and ending position) Always have the column headers in CAPS: ex. (C2 C3) - if you want to move from C2 to C3");
                String first = sc.next();
                String second = sc.next();
                if (first.equals("Q")) // check if there is a forfeit
                {
                    if (second.equals("W"))
                    {
                        System.out.println("White has forfeited, Black Wins!\n");
                        playing = false;
                        System.out.println("Thanks for playing!");
                        return;
                    }
                    if (second.equals("B"))
                    {
                        System.out.println("Black has forfeited, White Wins!\n");
                        playing = false;
                        System.out.println("Thanks for playing!");
                        return;
                    }
                    else {
                        System.out.println("Invalid entry.");
                        continue;
                    }
                }
                // parse input into the correct chess position and filter out illegal moves
                int curPoscol = 0, endPoscol = 0;
                if (first.charAt(0) == 'H')
                    curPoscol = 0;
                else if (first.charAt(0) == 'G')
                    curPoscol = 1;
                else if (first.charAt(0) == 'F')
                    curPoscol = 2;
                else if (first.charAt(0) == 'E')
                    curPoscol = 3;
                else if (first.charAt(0) == 'D')
                    curPoscol = 4;
                else if (first.charAt(0) == 'C')
                    curPoscol = 5;
                else if (first.charAt(0) == 'B')
                    curPoscol = 6;
                else if (first.charAt(0) == 'A')
                    curPoscol = 7;
                else {
                        System.out.println("Move is illegal. Enter a new move");
                        continue;
                }
                if (first.length() > 2 || first.length() < 2)
                {
                    System.out.println("Move is illegal. Enter a new move.");
                    continue;
                }
                int curPosrow = Character.getNumericValue(first.charAt(1)) - 1;
                if (second.charAt(0) == 'H')
                    endPoscol = 0;
                else if (second.charAt(0) == 'G')
                    endPoscol = 1;
                else if (second.charAt(0) == 'F')
                    endPoscol = 2;
                else if (second.charAt(0) == 'E')
                    endPoscol = 3;
                else if (second.charAt(0) == 'D')
                    endPoscol = 4;
                else if (second.charAt(0) == 'C')
                    endPoscol = 5;
                else if (second.charAt(0) == 'B')
                    endPoscol = 6;
                else if (second.charAt(0) == 'A')
                    endPoscol = 7;
                else {
                    System.out.println("Move is illegal. Enter a new move");
                    continue;
                }
                if (second.length() > 2 || second.length() < 2)
                {
                    System.out.println("Move is illegal. Enter a new move.");
                    continue;
                }
                int endPosrow = Character.getNumericValue(second.charAt(1)) - 1;
                if (curPosrow < 0 || curPosrow > 7 || curPoscol < 0 || curPoscol > 7 || endPosrow < 0 || endPoscol > 7 || endPosrow < 0 || endPosrow > 7)
                {
                    System.out.println("Move is illegal. Enter a new move.");
                    continue;
                }
                if (board.gameboard[curPosrow][curPoscol] == null)
                {
                    System.out.println("Move is illegal. Enter a new move");
                    continue;
                }
                else {
                    if (board.gameboard[curPosrow][curPoscol].getColor() == ChessColor.WHITE && player == 1)
                    {
                        System.out.println("It is black's turn. Please have black make a move.");
                        continue;
                    }
                    if (board.gameboard[curPosrow][curPoscol].getColor() == ChessColor.BLACK && player == 0)
                    {
                        System.out.println("It is white's turn. Please have white make a move.");
                        continue;
                    }
                }
                ChessPosition curPos = new ChessPosition(curPosrow, curPoscol);
                ChessPosition toPos = new ChessPosition(endPosrow, endPoscol);
                legal = board.movePiece(curPos, toPos); // call chessboard class
                if (!legal)
                {
                    System.out.println("Move is illegal. Please enter a new move.");
                    continue;
                }
            }
            board.display();
            if (board.whitecheckmate || board.blackcheckmate)
            {
                playing = false;
            }
            if (player == 0)
            {
                System.out.println("It is now Black's turn: ");
                player = 1;
            }
            else 
            {
                System.out.println("It is now White's turn: ");
                player = 0;
            }
            legal = false;
        }
        sc.close();
    }
}
