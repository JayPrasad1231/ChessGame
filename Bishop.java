public class Bishop implements Chesspiece{
    String piecename = "Bishop";
    ChessColor color;
    ChessPosition position;
    public Bishop(ChessColor col)
    {
        color = col;
    }
    @Override
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
    Boolean ischecking = false;
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
        if (Math.abs(curPos.getRow() - endPos.getRow()) == Math.abs(curPos.getCol() - endPos.getCol()))
            return true;
        return false;
    }
    public ChessPosition[] getPath(ChessPosition curPos, ChessPosition endPos) // Bishops can only move diagonally, so the difference betweeen columns and rows would be the same for both the beginning and ending position
    {
        ChessPosition[] path = new ChessPosition[8];
        // Checks different scenarios the Bishop is moving (top Left, top Right, Bottom Left, Bottom Right)
        for (int i = 1; i < Math.abs(curPos.getRow() - endPos.getRow()); i++)
        {
            if (endPos.getRow() > curPos.getRow() && endPos.getCol() < curPos.getCol())
            {
                ChessPosition temp = new ChessPosition(curPos.getRow() + i, curPos.getCol() - i);
                path[i-1] = temp;
            }
            if (endPos.getRow() < curPos.getRow() && endPos.getCol() > curPos.getCol())
            {
                ChessPosition temp = new ChessPosition(curPos.getRow() - i, curPos.getCol() + i);
                path[i-1] = temp;
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
        return path;
    }
}