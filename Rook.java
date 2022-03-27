public class Rook implements Chesspiece{
    String piecename = "Rook";
    ChessColor color;
    ChessPosition position;
    Boolean ischecking = false;
    public Rook(ChessColor col)
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
    public boolean isValidMove(ChessPosition curPos, ChessPosition endPos) // checks theoretical validity of a move for a rook
    {
        if (Math.abs(curPos.getRow() - endPos.getRow()) >= 1 && (Math.abs(curPos.getCol() - endPos.getCol()) == 0))
            return true;
        if (Math.abs(curPos.getRow() - endPos.getRow()) == 0 && Math.abs(curPos.getCol() - endPos.getCol()) >= 1)
            return true;
        return false;
    }
    public ChessPosition[] getPath(ChessPosition curPos, ChessPosition endPos) // returns path for a rook
    {
        ChessPosition[] path = new ChessPosition[8];
        if (curPos.getRow() - endPos.getRow() == 0)
        {
            for (int i = 1; i < Math.abs(curPos.getCol() - endPos.getCol()); i++)
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
        if (curPos.getCol() - endPos.getCol() == 0)
        {
            for (int i = 1; i < Math.abs(curPos.getRow() - endPos.getRow()); i++)
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
        return path;
    }
}