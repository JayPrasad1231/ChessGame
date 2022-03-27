public class King implements Chesspiece{
    String piecename = "King";
    ChessColor pieceColor;
    ChessPosition position;
    Boolean isCastle = false;
    Boolean ischecking = false;
    public King(ChessColor col)
    {
        pieceColor = col;
    }
    public ChessPosition getPosition() {
        return position;
    }
    public void setPosition(ChessPosition pos) {
        position = pos;
    }
    public ChessColor getColor() {
        return pieceColor;
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
    public boolean isValidMove(ChessPosition curPos, ChessPosition endPos) // Kings can only move one square
    {
        if (Math.abs(curPos.getRow() - endPos.getRow()) > 1)
            return false;
        if (Math.abs(curPos.getCol() - endPos.getCol()) > 1)
            return false;
        return true;
    }
    public ChessPosition[] getPath(ChessPosition curPos, ChessPosition endPos) // since they can only move one square, path is null
    {
        return null;
    }
}
