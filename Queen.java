public class Queen implements Chesspiece{
    String piecename = "Queen";
    ChessColor color;
    ChessPosition position;
    Boolean ischecking = false;
    public Queen(ChessColor col)
    {
        color = col;
    }
    public ChessPosition getPosition() {
        return position;
    }
    public void setPosition(ChessPosition pos) {
        position = pos;
    }
    public ChessColor getColor() {
        return color;
    }
    public String getName() {
        return piecename;
    }
    public void setCheck(boolean val)
    {
        ischecking = val;
    }
    public boolean getCheck()
    {
        return ischecking;
    }
    public boolean isValidMove(ChessPosition curPos, ChessPosition endPos) // check theoretical validity of a move
    {
        if (Math.abs(curPos.getRow() - endPos.getRow()) == Math.abs(curPos.getCol() - endPos.getCol()))
            return true;
        if (Math.abs(curPos.getRow() - endPos.getRow()) >= 1 && (Math.abs(curPos.getCol() - endPos.getCol()) == 0))
            return true;
        if (Math.abs(curPos.getRow() - endPos.getRow()) == 0 && Math.abs(curPos.getCol() - endPos.getCol()) >= 1)
            return true;
        return false;
    }
    public ChessPosition[] getPath(ChessPosition curPos, ChessPosition endPos) // combination of checking path for a bishop and rook
    {
        ChessPosition[] path = new ChessPosition[8];
        // Path if moving up and down or left and right
        if (curPos.getRow() - endPos.getRow() == 0)
        {
            for (int i = 0; i < Math.abs(curPos.getCol() - endPos.getCol()); i++)
            {
                if (curPos.getCol() > endPos.getCol())
                {
                    ChessPosition temp = new ChessPosition(curPos.getRow(), curPos.getCol() - i);
                    path[i] = temp;
                }
                else {
                    ChessPosition temp = new ChessPosition(curPos.getRow(), curPos.getCol() + i);
                    path[i] = temp;
                }
            }
        }
        else if (curPos.getCol() - endPos.getCol() == 0)
        {
            for (int i = 0; i < Math.abs(curPos.getRow() - endPos.getRow()); i++)
            {
                if (curPos.getRow() > endPos.getRow())
                {
                    ChessPosition temp = new ChessPosition(curPos.getRow() - i, curPos.getCol());
                    path[i] = temp;
                }
                else {
                    ChessPosition temp = new ChessPosition(curPos.getRow() + i, curPos.getCol());
                    path[i] = temp;
                }
            }
        }
        // validity for a on a diagonal
        else {
            for (int i = 0; i < Math.abs(curPos.getRow() - endPos.getRow()); i++)
            {
                if (endPos.getRow() > curPos.getRow() && endPos.getCol() < curPos.getCol())
                {
                    ChessPosition temp = new ChessPosition(curPos.getRow() + i, curPos.getCol() - i);
                    path[i] = temp;
                }
                if (endPos.getRow() < curPos.getRow() && endPos.getCol() > curPos.getCol())
                {
                    ChessPosition temp = new ChessPosition(curPos.getRow() - i, curPos.getCol() + i);
                    path[i] = temp;
                }
                if (endPos.getRow() < curPos.getRow() && endPos.getCol() < curPos.getCol())
                {
                    ChessPosition temp = new ChessPosition(curPos.getRow() - i, curPos.getCol() - i);
                    path[i] = temp;
                }
                if (endPos.getRow() > curPos.getRow() && endPos.getCol() > curPos.getCol())
                {
                    ChessPosition temp = new ChessPosition(curPos.getRow() + i, curPos.getCol() + i);
                    path[i] = temp;
                }
            }
        }
        return path;
    }
}