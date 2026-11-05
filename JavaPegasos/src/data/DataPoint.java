//Purpose: Creates a data point consisting of a message and a label (1/-1)
package data;

public class DataPoint

{
    private String text; 
    private int label; 

    public DataPoint(String text, int label)

    {
        this.text=text; 
        this.label=label;
    }

    public String getText()

    {
        return text; 
    }

    public int getLabel()

    {
        return label; 
    }
}
