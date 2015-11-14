package singlepolygonfill;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.Vector;

public class WindowController {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public Canvas canvas;
    public MenuItem fillMenuItem;
    Vector<MyPoint> myPolygon = new Vector<MyPoint>();

    public void initCanvas(ActionEvent event) {
        System.out.println("initCanvas");
        myPolygon.clear();
        fillMenuItem.setDisable(true);
        canvas.getGraphicsContext2D().setFill(Color.WHITE);
        canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        canvas.getGraphicsContext2D().setStroke(new Color(Math.random(), Math.random(), Math.random(), 0.8));
        canvas.getGraphicsContext2D().setLineWidth(10);
        canvas.getGraphicsContext2D().strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());
        canvas.getGraphicsContext2D().setStroke(Color.BLACK);
        canvas.getGraphicsContext2D().setLineWidth(1);
        canvas.setOnMouseClicked(new MyMouseClickedEventHandler());
    }

    public void fillPolygon(ActionEvent event) {
        System.out.println("fillPolygon");
        Vector<MyHorizontalLine> lines = MyPolygonUtils.getInterior(myPolygon, WIDTH, HEIGHT);
        for (MyHorizontalLine line : lines)
            canvas.getGraphicsContext2D().strokeLine(line.x1, line.y, line.x2, line.y);
    }

    public void setBlack(ActionEvent event) {
        canvas.getGraphicsContext2D().setStroke(Color.BLACK);
    }

    public void setRed(ActionEvent event) {
        canvas.getGraphicsContext2D().setStroke(Color.RED);
    }

    public void setGreen(ActionEvent event) {
        canvas.getGraphicsContext2D().setStroke(Color.GREEN);
    }

    public void setBlue(ActionEvent event) {
        canvas.getGraphicsContext2D().setStroke(Color.BLUE);
    }

    public void quit(ActionEvent event) {
        System.exit(0);
    }

    class MyMouseClickedEventHandler implements EventHandler<MouseEvent> {

        Boolean doneInput;

        public MyMouseClickedEventHandler() {
            doneInput = false;
        }

        @Override
        public void handle(MouseEvent event) {
            if (doneInput) return;
            double x = event.getX();
            double y = event.getY();
            System.out.println("clicked " + x + ", " + y);
            if (!myPolygon.isEmpty()) {
                if (MyPointUtils.distance(myPolygon.firstElement(), new MyPoint(x, y)) <= 20) {
                    System.out.println("clicked first");
                    x = myPolygon.firstElement().x;
                    y = myPolygon.firstElement().y;
                    doneInput = Boolean.TRUE;
                    fillMenuItem.setDisable(false);
                }
                canvas.getGraphicsContext2D().strokeLine(myPolygon.lastElement().x, myPolygon.lastElement().y, x, y);
            }
            if (!doneInput) myPolygon.add(new MyPoint(x, y));
        }
    }
}
