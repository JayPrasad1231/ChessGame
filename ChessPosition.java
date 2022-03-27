public class ChessPosition {
    private int row;
    private int col;

    public ChessPosition(int r, int c)
    {
        this.row = r;
        this.col = c;
    }
    public int getRow()
    {
        return row;
    }

    public int getCol()
    {
        return col;
    }
    
    public void setRow(int r)
    {
        this.row = r;
    }

    public void setCol(int c)
    {
        this.col = c;
    }
}
