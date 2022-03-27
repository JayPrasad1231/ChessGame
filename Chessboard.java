public class Chessboard {
    Chesspiece[][] gameboard;
    Chesspiece[] blackpieces;
    Chesspiece[] whitepieces;
    boolean blackcheckmate = false, whitecheckmate = false;
    public Chessboard()
    {
        this.gameboard = new Chesspiece[8][8];
        this.blackpieces = new Chesspiece[16];
        this.whitepieces = new Chesspiece[16];
        this.initializeBoard();
    }

    public void initializeBoard()
    {
        // Initialize board with starting position
        // add pieces to list of black and white pieces
        // set the position for all of the pieces
        for (int j = 0; j < 8; j++) 
        {
            gameboard[1][j] = new Pawn(ChessColor.WHITE);
            whitepieces[j] = gameboard[1][j];
            ChessPosition pawntemp = new ChessPosition(1, j);
            whitepieces[j].setPosition(pawntemp);

            gameboard[6][j] = new Pawn(ChessColor.BLACK);
            blackpieces[j] = gameboard[6][j];
            ChessPosition Bpawntemp = new ChessPosition(6, j);
            blackpieces[j].setPosition(Bpawntemp);
        }
        //White Rook & Black Rook
        gameboard[0][0] = new Rook(ChessColor.WHITE);
        whitepieces[8] = gameboard[0][0];
        ChessPosition WR = new ChessPosition(0, 0);
        whitepieces[8].setPosition(WR);

        gameboard[0][7] = new Rook(ChessColor.WHITE);
        whitepieces[9] = gameboard[0][7];
        ChessPosition WR2 = new ChessPosition(0, 7);
        whitepieces[9].setPosition(WR2);

        gameboard[7][0] = new Rook(ChessColor.BLACK);
        blackpieces[8] = gameboard[7][0];
        ChessPosition BR = new ChessPosition(7, 0);
        blackpieces[8].setPosition(BR);

        gameboard[7][7] = new Rook(ChessColor.BLACK);
        blackpieces[9] = gameboard[7][7];
        ChessPosition BR2 = new ChessPosition(7, 7);
        blackpieces[9].setPosition(BR2);
        //White and Black Knights
        gameboard[0][6] = new Knight(ChessColor.WHITE);
        whitepieces[10] = gameboard[0][6];
        ChessPosition WN = new ChessPosition(0, 6);
        whitepieces[10].setPosition(WN);

        gameboard[0][1] = new Knight(ChessColor.WHITE);
        whitepieces[11] = gameboard[0][1];
        ChessPosition WN2 = new ChessPosition(0, 1);
        whitepieces[11].setPosition(WN2);

        gameboard[7][6] = new Knight(ChessColor.BLACK);
        blackpieces[10] = gameboard[7][6];
        ChessPosition BN = new ChessPosition(7, 6);
        blackpieces[10].setPosition(BN);

        gameboard[7][1] = new Knight(ChessColor.BLACK);
        blackpieces[11] = gameboard[7][1];
        ChessPosition BN2 = new ChessPosition(7, 1);
        blackpieces[11].setPosition(BN2);
        //White and Black Bishops
        gameboard[0][2] = new Bishop(ChessColor.WHITE);
        whitepieces[12] = gameboard[0][2];
        ChessPosition WB = new ChessPosition(0, 2);
        whitepieces[12].setPosition(WB);

        gameboard[0][5] = new Bishop(ChessColor.WHITE);
        whitepieces[13] = gameboard[0][5];
        ChessPosition WB2 = new ChessPosition(0, 5);
        whitepieces[13].setPosition(WB2);

        gameboard[7][2] = new Bishop(ChessColor.BLACK);
        blackpieces[12] = gameboard[7][2];
        ChessPosition BB = new ChessPosition(7, 2);
        blackpieces[12].setPosition(BB);

        gameboard[7][5] = new Bishop(ChessColor.BLACK);
        blackpieces[13] = gameboard[7][5];
        ChessPosition BB2 = new ChessPosition(7, 5);
        blackpieces[13].setPosition(BB2);
        //White and Black Queens
        gameboard[0][4] = new Queen(ChessColor.WHITE);
        whitepieces[14] = gameboard[0][4];
        ChessPosition WQ = new ChessPosition(0, 4);
        whitepieces[14].setPosition(WQ);

        gameboard[7][4] = new Queen(ChessColor.BLACK);
        blackpieces[14] = gameboard[7][4];
        ChessPosition BQ = new ChessPosition(7, 4);
        blackpieces[14].setPosition(BQ);
        //White and Black Kings
        gameboard[0][3] = new King(ChessColor.WHITE);
        whitepieces[15] = gameboard[0][3];
        ChessPosition WK = new ChessPosition(0, 3);
        whitepieces[15].setPosition(WK);

        gameboard[7][3] = new King(ChessColor.BLACK);
        blackpieces[15] = gameboard[7][3];
        ChessPosition BK = new ChessPosition(7, 3);
        blackpieces[15].setPosition(BK);
    }

    public boolean movePiece(ChessPosition curPos, ChessPosition toPos)
    {
        Chesspiece piece = gameboard[curPos.getRow()][curPos.getCol()];
        Chesspiece tempPiece = gameboard[toPos.getRow()][toPos.getCol()];
        // if the user selects a space on the gameboard without a piece, return false
        if(piece == null)
            return false;
        // if the user selects a space on the gameboard with the same color as the beginning piece, return false
        if(isOccupied(toPos, piece.getColor())) 
            return false;
        if (piece.getName().equals("Pawn")) // special rules for pawns
        {
            Pawn p = (Pawn) piece;
            boolean val = p.isValidPawn(curPos, toPos, tempPiece, piece.getColor());
            if (!val)
                return false;
        }
        if(!piece.isValidMove(curPos, toPos)) // Check if the move is theoretically possible
           return false;
        ChessPosition[] path = piece.getPath(curPos, toPos);
        if (isBlocked(path)) // If there is something in the way, move is illegal
            return false;
        // If you capture a piece, set its position to empty.
        if (tempPiece != null)
        {
            if (!(tempPiece.getColor().equals(piece.getColor())))
                tempPiece.setPosition(null);
        }
        // Actually moving the pieces
        gameboard[curPos.getRow()][curPos.getCol()] = null;
        gameboard[toPos.getRow()][toPos.getCol()] = piece; 
        gameboard[toPos.getRow()][toPos.getCol()].setPosition(toPos);
        if (piece.getColor() == ChessColor.WHITE) // If the move results in a check for your color, it is illegal.
        {
            boolean val = discoveredChecks(ChessColor.BLACK); // returns true if there is a discovered check
            if (val)
            {
                // Change the piece back to its initial position (as if the move never occurred)
                gameboard[curPos.getRow()][curPos.getCol()] = piece;
                gameboard[toPos.getRow()][toPos.getCol()] = null;
                ChessPosition returnValue = new ChessPosition(toPos.getRow(), toPos.getCol());
                if (returnValue != null)
                {
                    tempPiece.setPosition(returnValue);
                }
                return false;
            }
        }
        // Same as what happens for white just if the Color is Black
        if (piece.getColor() == ChessColor.BLACK)
        {
            boolean val = discoveredChecks(ChessColor.WHITE);
            if (val)
            {
                gameboard[curPos.getRow()][curPos.getCol()] = piece;
                piece.setPosition(curPos);
                gameboard[toPos.getRow()][toPos.getCol()] = null;
                ChessPosition returnValue = new ChessPosition(toPos.getRow(), toPos.getCol());
                if (tempPiece != null)
                {
                    tempPiece.setPosition(returnValue);
                }
                return false;
            }
        }
        // If the move results in a check or a discovered check, there is a check
        if(isCheck(piece.getColor(), toPos) || discoveredChecks(piece.getColor()))
        {
            if (piece.getColor().equals(ChessColor.BLACK))
            {
                System.out.println("White is in Check!");
                boolean escapability = canEscape(ChessColor.WHITE);
                if (escapability) // If White can escape, they are not in checkmate
                    return true;
                boolean blockability = canBlock(ChessColor.WHITE); // If they can block, they are not in checkmate
                if (!(escapability) && !(blockability))
                {
                    System.out.println("White is In Checkmate! BLACK WINS!");
                    whitecheckmate = true;
                }
            }
            // Same rules for Black
            else
            {
                System.out.println("Black is in Check!");            
                boolean escapability = canEscape(ChessColor.BLACK);
                if (escapability)
                    return true;
                boolean blockability = canBlock(ChessColor.BLACK);
                if (!(escapability) && !(blockability))
                {
                    System.out.println("Black is In Checkmate! WHITE WINS!");
                    blackcheckmate = true;
                }
            }
        }
        if (tempPiece != null) // if you actually capture a piece, set its position to null
        {
            if (!(tempPiece.getColor().equals(piece.getColor())))
            {
                tempPiece.setPosition(null);
            }
        }
        return true;
    }
    public boolean isBlocked(ChessPosition[] path) // checks if there is something in the way of the move
    {
        if (path == null)
            return false;
        for(int i = 0; i < path.length - 1; i++)
        {
            // first part of path is always the current piece.
            ChessPosition temp = path[i+1];
            if (temp != null)
            {
                if (gameboard[temp.getRow()][temp.getCol()] != null) // if it not null, as in there is a piece, the path is blocked
                    return true;
            }
        }
        return false;
    }

    public boolean isOccupied(ChessPosition pos, ChessColor col) // checks if the ending square is occupied by a same color piece
    {
        Chesspiece piece = gameboard[pos.getRow()][pos.getCol()];
        if (piece != null && piece.getColor().equals(col)) // if it is occupied by a piece of the same color, return false
            return true;
        else
            return false;
      
    }
    public boolean isCheck(ChessColor color, ChessPosition endPos) // determines if there is a check
    {
        ChessPosition KingPos = null;
        if (color == ChessColor.WHITE)
        {
            for (int i = 0; i < blackpieces.length; i++)
            {
                if (blackpieces[i] != null)
                {
                    if (blackpieces[i].getName().equals("King"))
                    {
                        KingPos = blackpieces[i].getPosition(); // gets the King Position of Black
                    }
                }
            }
        }
        else {
            for (int i = 0; i < whitepieces.length; i++)
            {
                if (whitepieces[i] != null)
                {
                    if (whitepieces[i].getName().equals("King"))
                    {
                        KingPos = whitepieces[i].getPosition(); // gets the King Position of White
                    }
                }
            }
        }
        Chesspiece piece = gameboard[endPos.getRow()][endPos.getCol()];
        boolean ischeck = false;
        if (piece.isValidMove(endPos, KingPos)) // checks if there is a valid move between the piece that moved and the King
        {
            ChessPosition[] checkpath = piece.getPath(endPos, KingPos);
            if (checkpath == null)
            {
                if (piece.getName().equals("King")) // If you move a King you cannot cause a check to the other color
                {
                    ischeck = false;
                }
                else if (piece.getName().equals("Pawn")) // check if a pawn is causing a check
                {
                    if (piece.getColor() == ChessColor.WHITE)
                    {
                        if (endPos.getRow() + 1 < 8)
                        {
                            if (endPos.getCol() + 1 < 8)
                            {
                                if (gameboard[endPos.getRow() + 1][endPos.getCol() + 1] != null)
                                {
                                    if(gameboard[endPos.getRow() + 1][endPos.getCol() + 1].getName().equals("King"))
                                    {
                                        ischeck = true;
                                        piece.setCheck(true);
                                    }
                                }
                            }
                            if (endPos.getCol() - 1 >= 0)
                            {
                                if (gameboard[endPos.getRow() + 1][endPos.getCol() - 1] != null)
                                {
                                    if(gameboard[endPos.getRow() + 1][endPos.getCol() - 1].getName().equals("King"))
                                    {
                                        ischeck = true;
                                        piece.setCheck(true);
                                    }
                                }
                            }
                        }
                    }
                    else {
                        if (endPos.getRow() - 1 >= 0)
                        {
                            if (endPos.getCol() + 1 < 8)
                            {
                                if (gameboard[endPos.getRow() - 1][endPos.getCol() + 1] != null)
                                {
                                    if(gameboard[endPos.getRow() - 1][endPos.getCol() + 1].getName().equals("King"))
                                    {
                                        ischeck = true;
                                        piece.setCheck(true);
                                    }
                                }
                            }
                            if (endPos.getCol() - 1 >= 0)
                            {
                                if (gameboard[endPos.getRow() - 1][endPos.getCol() + 1] != null)
                                {
                                    if(gameboard[endPos.getRow() - 1][endPos.getCol() - 1].getName().equals("King"))
                                    {
                                        ischeck = true;
                                        piece.setCheck(true);
                                    }
                                }
                            }
                        }
                    }
                }
                else if (piece.getName().equals("Knight")) // knights always cause a check if it is a valid move, they aren't blocked by anything
                {
                    boolean isKingIntheWay = piece.isValidMove(endPos, KingPos);
                    if (isKingIntheWay)
                    {
                        ischeck = true;
                        piece.setCheck(true);
                    }
                    else
                        ischeck = false;
                }
            }
            else{ // for other pieces, check if the path is blocked; if not, the piece is causing check
                ischeck = (!(isBlocked(checkpath)));
                if (ischeck == true)
                    piece.setCheck(true);
            }
        }
        else
            ischeck = false;
        return ischeck;
    }
    public boolean discoveredChecks(ChessColor color)
    {
        // check all pieces for discovered checks, as in that piece is not moving
        ChessPosition KingPosition = null;
        if (color == ChessColor.WHITE)
        {
            for (int i = 0; i < blackpieces.length; i++)
            {
                if (blackpieces[i] != null)
                {
                    if (blackpieces[i].getName().equals("King"))
                    {
                        KingPosition = blackpieces[i].getPosition(); // gets the position of the BLACK king
                    }
                }
            }
        }
        else {
            for (int i = 0; i < whitepieces.length; i++)
            {
                if (whitepieces[i].getName().equals("King"))
                {
                    KingPosition = whitepieces[i].getPosition(); // gets the postion of the WHITE King
                }
            }
        }
        boolean discover = true;
        // checks the path between all possible pieces and the King, and if there is a path that is then returns true
        if (color == ChessColor.WHITE)
        {
            for (int i = 0; i < whitepieces.length; i++)
            {
                ChessPosition temp = whitepieces[i].getPosition();
                if (temp != null)
                {
                    Chesspiece piece = gameboard[temp.getRow()][temp.getCol()];
                    if (piece != null)
                    {
                        if (piece.isValidMove(piece.getPosition(), KingPosition))
                        {
                            ChessPosition[] checkpath = piece.getPath(temp, KingPosition);
                            if (checkpath != null)
                            {
                                for (int j = 0; j < checkpath.length - 1; j++)
                                {
                                    if (checkpath[j+1] != null)
                                    {
                                        ChessPosition tem = checkpath[j+1];
                                        if (gameboard[tem.getRow()][tem.getCol()] != null)
                                        {
                                            discover = false;
                                        }
                                    }
                                }
                            }
                            else { // if it is a Valid Move for a Pawn or Knight to a King, then it is a check.
                                piece.setCheck(true);
                                return true;
                            }
                            if (discover == true)
                            {
                                piece.setCheck(true);
                                return true;
                            }
                            discover = true;
                        }
                    }
                }
            }
        }
        // Do the same thing for black, as did for white
        else
        {
            for (int i = 0; i < blackpieces.length; i++)
            {
                ChessPosition temp = blackpieces[i].getPosition();
                if (temp != null)
                {
                    Chesspiece piece = gameboard[temp.getRow()][temp.getCol()];
                    if (piece != null)
                    {
                        if (piece.isValidMove(piece.getPosition(), KingPosition))
                        {
                            ChessPosition[] checkpath = piece.getPath(temp, KingPosition);
                            if (checkpath != null)
                            {
                                for (int j = 1; j < checkpath.length; j++)
                                {
                                    if (checkpath[j] != null)
                                    {
                                        ChessPosition tem = checkpath[j];
                                        if (gameboard[tem.getRow()][tem.getCol()] != null)
                                        {
                                            discover = false;
                                        }
                                    }
                                }
                            }
                            else { // if it is a Valid Move for a Pawn or Knight to a King, then it is a check.
                                piece.setCheck(true);
                                return true;
                            }
                            if (discover == true)
                            {
                                piece.setCheck(true);
                                return true;
                            }
                            discover = true;
                        }
    
                    }
                }
            }
        } 
        return false;
    }
    public boolean canEscape(ChessColor color) { // Checks if the King can escape to a different piece if there is a check
        ChessPosition KingPosition = null;
        Chesspiece kingpiece = null;
        if (color == ChessColor.WHITE)
        {
            for (int i = 0; i < whitepieces.length; i++)
            {
                if (whitepieces[i] != null)
                {
                    if (whitepieces[i].getName().equals("King"))
                    {
                        KingPosition = whitepieces[i].getPosition(); // King's Position
                        kingpiece = whitepieces[i]; // the actual King piece
                    }
                }
            }
        }
        if (color == ChessColor.BLACK)
        {
            for (int i = 0; i < blackpieces.length; i++)
            {
                if (blackpieces[i] != null)
                {
                    if (blackpieces[i].getName().equals("King"))
                    {
                        KingPosition = blackpieces[i].getPosition();
                        kingpiece = blackpieces[i];
                    }
                }
            }
        }
        ChessPosition[] possiblemoves = new ChessPosition[8]; // all the possible moves a King can make (since a King can only move to 8 squares at maximum)
        if (KingPosition.getRow() + 1 <= 7)
            possiblemoves[0] = new ChessPosition((KingPosition.getRow() + 1), (KingPosition.getCol()));
        if (KingPosition.getRow() - 1 >= 0)
            possiblemoves[1] = new ChessPosition(KingPosition.getRow() - 1, KingPosition.getCol());
        if (KingPosition.getCol() + 1 <= 7)
            possiblemoves[2] = new ChessPosition(KingPosition.getRow(), KingPosition.getCol() + 1);
        if (KingPosition.getCol() - 1 >= 0)
            possiblemoves[3] = new ChessPosition(KingPosition.getRow(), KingPosition.getCol() - 1);
        if (KingPosition.getRow() + 1 <= 7 && KingPosition.getCol() + 1 <= 7)
            possiblemoves[4] = new ChessPosition((KingPosition.getRow() + 1), (KingPosition.getCol() + 1));
        if (KingPosition.getRow() - 1 >= 0 && KingPosition.getCol() - 1 >= 0)
            possiblemoves[5] = new ChessPosition(KingPosition.getRow() - 1, KingPosition.getCol() - 1);
        if (KingPosition.getRow() + 1 <= 7 && KingPosition.getCol() - 1 >= 0)
            possiblemoves[6] = new ChessPosition((KingPosition.getRow() + 1), (KingPosition.getCol() - 1));
        if (KingPosition.getRow() - 1 >= 0 && KingPosition.getCol() + 1 <= 7)
            possiblemoves[7] = new ChessPosition(KingPosition.getRow() - 1, KingPosition.getCol() + 1);
        for (int i = 0; i < 8; i++) // Checks the validity to moving to each of those squares
        {
            if (possiblemoves[i] != null)
            {
                ChessPosition tempmove = possiblemoves[i];
                boolean val = kingpiece.isValidMove(KingPosition, possiblemoves[i]);
                if (val)
                {
                    if (gameboard[tempmove.getRow()][tempmove.getCol()] == null)
                    {
                        if (color == ChessColor.BLACK)
                        {
                            if (!(discoveredChecks(ChessColor.WHITE)))
                                return true;
                        }
                        else {
                            if (!(discoveredChecks(ChessColor.BLACK)))
                                return true;
                        }
                    }
                    else if(!(gameboard[tempmove.getRow()][tempmove.getCol()].getColor().equals(color)))
                    {
                        if (color == ChessColor.BLACK)
                        {
                            if (!(discoveredChecks(ChessColor.WHITE)))
                                return true;
                        }
                        else {
                            if (!(discoveredChecks(ChessColor.BLACK)))
                                return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    public boolean canBlock(ChessColor color) // Checks if the check can be blocked
    {
        ChessPosition KingPosition = null;
        Chesspiece checkingpiece = null;
        if (color == ChessColor.BLACK)
        {
            for (int i = 0; i < blackpieces.length; i++)
            {
                if (blackpieces[i] != null)
                {
                    if (blackpieces[i].getName().equals("King"))
                        KingPosition = blackpieces[i].getPosition();
                }
            }
            for (int i = 0; i < whitepieces.length; i++)
            {
                if (whitepieces[i] != null)
                {
                    if (whitepieces[i].getCheck())
                        checkingpiece = whitepieces[i];
                }
            }
            ChessPosition[] checkingPath = checkingpiece.getPath(checkingpiece.getPosition(), KingPosition); // returns the path from the piece that is checking to the king
            if (checkingPath == null)
            {
                // only way to counter this is to capture the piece.
                for (int i = 0; i < blackpieces.length; i++)
                {
                    Chesspiece temp = blackpieces[i];
                    if (temp.getPosition() != null)
                    {
                        if (temp.isValidMove(temp.getPosition(), checkingpiece.getPosition()))
                        {
                            if (temp.getName().equals("King"))
                            {
                                if (!(discoveredChecks(ChessColor.BLACK)))
                                    return true;
                            }
                        }
                    }
                }
            }
            else // the path is not null
            {
                for (int i = 0; i < blackpieces.length; i++)
                {
                    Chesspiece temp = blackpieces[i];
                    for (int j = 0; j < checkingPath.length; j++)
                    {
                        if (temp.getPosition() != null && checkingPath[j] != null)
                        {
                            if (temp.isValidMove(temp.getPosition(), checkingPath[j])) // checks the validity of a move to each of the positions on the path
                            {
                                ChessPosition[] path = temp.getPath(temp.getPosition(), checkingPath[j]);
                                if (path != null)
                                {
                                    if (!(this.isBlocked(path))) // if it is possible to block a position on the path, the check can be blocked
                                        return true;
                                }
                                else {
                                    if (blackpieces[i].getName().equals("Pawn"))
                                    {
                                        Pawn p = (Pawn) blackpieces[i];
                                        if(p.isValidPawn(temp.getPosition(), checkingPath[j], null, ChessColor.BLACK)) // special rules for a pawn
                                            return true;
                                    }
                                    if (blackpieces[i].getName().equals("Knight"))
                                    {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        // the same rules apply, but for the opposite color
        else {
            for (int i = 0; i < whitepieces.length; i++)
            {
                if (whitepieces[i] != null)
                {
                    if (whitepieces[i].getName().equals("King"))
                        KingPosition = whitepieces[i].getPosition();
                }
            }
            for (int i = 0; i < blackpieces.length; i++)
            {
                if (blackpieces[i] != null)
                {
                    if (blackpieces[i].getCheck())
                        checkingpiece = blackpieces[i];
                }
            }
            ChessPosition[] checkingPath = checkingpiece.getPath(checkingpiece.getPosition(), KingPosition);
            if (checkingPath == null)
            {
                // only way to counter this is to capture the piece.
                for (int i = 0; i < blackpieces.length; i++)
                {
                    Chesspiece temp = blackpieces[i];
                    if (temp.getPosition() != null)
                    {
                        if (temp.isValidMove(temp.getPosition(), checkingpiece.getPosition()))
                        {
                            if (temp.getName().equals("King"))
                            {
                                if (!(discoveredChecks(ChessColor.BLACK)))
                                    return true;
                            }
                        }
                    }
                }
            }
            else {
                for (int i = 0; i < whitepieces.length; i++)
                {
                    Chesspiece temp = whitepieces[i];
                    for (int j = 0; j < checkingPath.length; j++)
                    {
                        if (temp.getPosition() != null)
                        {
                            if (temp.isValidMove(temp.getPosition(), checkingPath[j]))
                            {
                                ChessPosition[] path = temp.getPath(temp.getPosition(), checkingPath[j]);
                                if (path != null)
                                {
                                    if (!(this.isBlocked(path)))
                                        return true;
                                }
                                else {
                                    if (whitepieces[i].getName().equals("Pawn"))
                                    {
                                        Pawn p = (Pawn) whitepieces[i];
                                        if(p.isValidPawn(temp.getPosition(), checkingPath[j], null, ChessColor.WHITE))
                                            return true;
                                    }
                                    if (whitepieces[i].getName().equals("Knight"))
                                    {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    public void display() // display the board on the terminal
    {
        int count = 0;
        for (int i = 7; i >= 0; i--)
        {
            System.out.print(8 + count + "[");
            for (int j = 7; j >= 0; j--)
            {
                if (gameboard[i][j] == null)
                    System.out.print("|  ");
                else if (gameboard[i][j].getName().equals("Rook") && (gameboard[i][j].getColor() == ChessColor.WHITE))
                    System.out.print("|WR");
                else if (gameboard[i][j].getName().equals("Knight") && (gameboard[i][j].getColor() == ChessColor.WHITE))
                    System.out.print("|WN");
                else if (gameboard[i][j].getName().equals("Pawn") && (gameboard[i][j].getColor() == ChessColor.WHITE))
                    System.out.print("|WP");
                else if (gameboard[i][j].getName().equals("Queen") && (gameboard[i][j].getColor() == ChessColor.WHITE))
                    System.out.print("|WQ");
                else if (gameboard[i][j].getName().equals("King") && (gameboard[i][j].getColor() == ChessColor.WHITE))
                    System.out.print("|WK");
                else if (gameboard[i][j].getName().equals("Bishop") && (gameboard[i][j].getColor() == ChessColor.WHITE))
                    System.out.print("|WB");
                else if (gameboard[i][j].getName().equals("Rook") && (gameboard[i][j].getColor() == ChessColor.BLACK))
                    System.out.print("|BR");
                else if (gameboard[i][j].getName().equals("Knight") && (gameboard[i][j].getColor() == ChessColor.BLACK))
                    System.out.print("|BN");
                else if (gameboard[i][j].getName().equals("Pawn") && (gameboard[i][j].getColor() == ChessColor.BLACK))
                    System.out.print("|BP");
                else if (gameboard[i][j].getName().equals("Queen") && (gameboard[i][j].getColor() == ChessColor.BLACK))
                    System.out.print("|BQ");
                else if (gameboard[i][j].getName().equals("King") && (gameboard[i][j].getColor() == ChessColor.BLACK))
                    System.out.print("|BK");
                else if (gameboard[i][j].getName().equals("Bishop") && (gameboard[i][j].getColor() == ChessColor.BLACK))
                    System.out.print("|BB");
            }
            System.out.print("]\n");
            count--;
        }
        System.out.println("    A  B  C  D  E  F  G  H");
    }
}