package mybeans;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class DataSheetGraph extends JPanel {

    private static final long serialVersionUID = 1L;

    private DataSheet dataSheet;
    private boolean isConnected;
    private int deltaX;
    private int deltaY;
    private Color color;

    public DataSheetGraph() {
        super();
        initialize();
    }

    private void initialize() {
        isConnected = false;
        deltaX = 5;
        deltaY = 5;
        color = Color.red;
        this.setSize(300, 400);
    }


    public DataSheetGraph(DataSheet dataSheet){
        isConnected = false;
        deltaX = 5;
        deltaY = 5;
        color = Color.GREEN;
        this.dataSheet = dataSheet;
    }

    private double minX(){
        double result = 100000;
        for (Data data : dataSheet.getDataArray()){
            if (data.getX() < result)
                result = data.getX();
        }

        return result;
    }

    private double maxX(){
        double result = 0;
        for (Data data : dataSheet.getDataArray()){
            if (data.getX() > result)
                result = data.getX();
        }

        return result;
    }

    private double minY(){
        double result = 100000;
        for (Data data : dataSheet.getDataArray()){
            if (data.getY() < result)
                result = data.getY();
        }

        return result;
    }

    private double maxY(){
        double result = 0;
        for (Data data : dataSheet.getDataArray()){
            if (data.getY() > result)
                result = data.getY();
        }

        return result;
    }


    public DataSheet getDataSheet(){
        return dataSheet;
    }

    public void setDataSheet(DataSheet dataSheet){
        this.dataSheet = dataSheet;
    }

    public boolean isConnected(){
        return isConnected;
    }

    public void setConnected(boolean isConnected){
        this.isConnected = isConnected;
        repaint();
    }

    public int getDeltaX(){
        return deltaX;
    }

    public int getDeltaY(){
        return deltaY;
    }

    public void setDeltaX(int deltaX){
        this.deltaX = deltaX;
        repaint();
    }

    public void setDeltaY(int deltaY){
        this.deltaY = deltaY;
        repaint();
    }

    public Color getColor(){
        return color;
    }

    public void setColor(Color color){
        this.color = color;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        showGraph(graphics2D);
    }

    public void showGraph(Graphics2D gr){
        double xMin = minX() - deltaX;
        double yMin = minY() - deltaY;
        double xMax = maxX() + deltaX;
        double yMax = maxY() + deltaY;
        double width = getWidth();
        double height = getHeight();
        double xScale = width / (xMax - xMin);
        double yScale = height / (yMax - yMin);
        System.out.println("in");

        double x0 = -xMin*xScale;
        double y0 = yMax*xScale;

        Paint oldColor = gr.getPaint();
        gr.setPaint(Color.WHITE);
        gr.fill(new Rectangle2D.Double(0.0, 0.0, width, height));

        Stroke oldStroke = gr.getStroke();
        Font oldFont = gr.getFont();

        float[] dashPattern = {10, 10};
        gr.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dashPattern, 0));
        gr.setFont(new Font("Serif", Font.BOLD, 14));

        double xStep = 1;
        for (double dx = xStep; dx < width; dx += xStep){
            double x = x0 + dx*xScale;
            gr.setPaint(Color.LIGHT_GRAY);
            gr.draw(new Line2D.Double(x, 0, x, height));
            gr.setPaint(Color.BLACK);
            gr.drawString((int)(Math.round(dx/xStep)*xStep) + "", (int) x + 2, 15);
        }

        for (double dx = -xStep; dx >= xMin; dx -= xStep) {
            double x = x0 + dx*xScale;
            gr.setPaint(Color.LIGHT_GRAY);
            gr.draw(new Line2D.Double(x, 0, x, height));
            gr.setPaint(Color.BLACK);
            gr.drawString(Math.round(dx/xStep)*xStep+"", (int)x+2, 10);
        }

        double yStep = 1;
        for (double dy = yStep; dy < height; dy += yStep){
            double y = y0 - dy*yScale;
            gr.setPaint(Color.LIGHT_GRAY);
            gr.draw(new Line2D.Double(0, y, width, y));
            gr.setPaint(Color.BLACK);
            gr.drawString((int)(Math.round(dy/yStep)*yStep) + "", 2, (int)y - 2);
        }

        for (double dy = -yStep; dy >= yMin; dy -= yStep) {
            double y = y0 - dy*yScale;
            gr.setPaint(Color.LIGHT_GRAY);
            gr.draw(new Line2D.Double(0, y, width, y));
            gr.setPaint(Color.BLACK);
            gr.drawString(Math.round(dy/yStep)*yStep+"", 2, (int)y-2);
        }


        gr.setPaint(Color.BLACK);
        gr.setStroke(new BasicStroke(3.0f));
        gr.draw(new Line2D.Double(x0, 0, x0, height));
        gr.draw(new Line2D.Double(0, y0, width, y0));
        gr.drawString("X", (int)width - 10, (int)y0 - 2);
        gr.drawString("Y", (int)x0 + 2, 10);

        if (dataSheet != null){
            if (!isConnected){
                for (Data data : dataSheet.getDataArray()){
                    double x = x0 + data.getX()*xScale;
                    double y = y0 - data.getY()*yScale;
                    gr.setColor(Color.WHITE);
                    gr.fillOval((int)(x - 5 / 2), (int) (y - 5 / 2), 10, 10);
                    gr.setColor(color);
                    gr.fillOval((int) (x - 5 / 2), (int) (y - 5 / 2), 10, 10);
                }
            } else {
                gr.setPaint(color);
                gr.setStroke(new BasicStroke(2.0f));
                double xOld = x0 + dataSheet.getDataItem(0).getX() * xScale;
                double yOld = y0 - dataSheet.getDataItem(0).getY() * yScale;

                for (Data data : dataSheet.getDataArray()){
                    double x = x0 + data.getX()*xScale;
                    double y = y0 - data.getY()*yScale;
                    gr.draw(new Line2D.Double(xOld, yOld, (double)x, y));
                    xOld = x;
                    yOld = y;
                }
            }


            gr.setPaint(oldColor);
            gr.setStroke(oldStroke);
            gr.setFont(oldFont);
        }
    }

}
