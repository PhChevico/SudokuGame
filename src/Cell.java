public class Cell extends SharedEnvironmentConsumer {
    private String value; //number in the cell
    private String row;
    private String column;


    public void setValue(String value) {
       this.value = value;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public void setColumn(String column) {
       this.column = column;

    }

    public int getValue() {
        return Integer.parseInt(value);
    }

    public int getRow() {
        return Integer.parseInt(row);
    }

    public int getColumn() {
        return Integer.parseInt(column);
    }

    @Override
    public String toString() {
        return "Cell{" +
                "value=" + value +
                ", row=" + row +
                ", column=" + column +
                '}';
    }
}
