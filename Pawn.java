public class Pawn implements Chesspiece{
    String piecename = "Pawn";
    ChessColor color;
    ChessPosition position;
    Boolean hasmoved = false;
    Boolean ischecking = false;
    public Pawn(ChessColor col)
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
    public void setHasMoved(Boolean val) {
        hasmoved = val;
    }
    public void setCheck(boolean val)
    {
        ischecking = val;
    }
    public boolean getCheck()
    {
        return ischecking;
    }
    public boolean isValidMove(ChessPosition curPos, ChessPosition endPos) // checks if it would theoretically be possible to move to the ending position
    {
        if (!hasmoved)
        {
            if (Math.abs(curPos.getRow() - endPos.getRow()) == 2 && (curPos.getCol() == endPos.getCol()))
            {
                this.setHasMoved(true);
                return true;
            }
        }
        if (Math.abs(curPos.getRow() - endPos.getRow()) != 1)
        {
            return false;
        }
        if (Math.abs(curPos.getCol() - endPos.getCol()) > 1)
        {
            return false;
        }
        this.setHasMoved(true);
        return true;
    }
    public boolean isValidPawn(ChessPosition curPos, ChessPosition endPos, Chesspiece endingPiece, ChessColor color) // Rules only specific to pawns
    {
        if (Math.abs(curPos.getCol() - endPos.getCol()) == 1) // Pawns can only change columns if they are capturing a piece
        {
            if (endingPiece == null)
                return false;
        }
        if (Math.abs(curPos.getCol() - endPos.getCol()) == 0) // Pawns cannot move forward if there is a piece in front of them (i.e. they cannot capture moving forward)
        {
            if (endingPiece != null)
                return false;
        }
        if (color == ChessColor.WHITE) // Pawns can only move forward
        {
            if (!(endPos.getRow() - curPos.getRow() > 0))
                return false;
        }
        if (color == ChessColor.BLACK)
        {
            if (!(curPos.getRow() - endPos.getRow() > 0)) // Pawns can only move forward
                return false;
        }
        return true;
    }
    public ChessPosition[] getPath(ChessPosition curPos, ChessPosition endPos) // Pawns can only move one space at a time --> there path is null.
    {
        return null;
    } 
}