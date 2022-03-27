public class Knight implements Chesspiece {
    String piecename = "Knight";
    ChessColor color;
    ChessPosition position;
    Boolean ischecking = false;
    public Knight(ChessColor col)
    {
        color = col;
    }

    public ChessPosition getPosition(){
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
    public boolean isValidMove(ChessPosition curPos, ChessPosition endPos) // check theoretical validity of a move for a knight
    {
        if (Math.abs(curPos.getRow() - endPos.getRow()) == 2 && Math.abs(curPos.getCol() - endPos.getCol()) == 1)
            return true;
        if (Math.abs(curPos.getRow() - endPos.getRow()) == 1 && Math.abs(curPos.getCol() - endPos.getCol()) == 2)
            return true;
        return false;
    }
    public ChessPosition[] getPath(ChessPosition curPos, ChessPosition endPos) // Knights can hop over pieces, so the path is null
    {
        return null;
    }
}
