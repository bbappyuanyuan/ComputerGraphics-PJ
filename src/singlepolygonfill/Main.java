package singlepolygonfill;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Vector;

public class Main extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group group = new Group();
        Scene scene = new Scene(group);
        primaryStage.setScene(scene);
        final Canvas canvas = new Canvas(WIDTH, HEIGHT);
        Vector<MyPoint> myPolygon = new Vector<MyPoint>();
        group.getChildren().addAll(canvas);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.setLineWidth(1);
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new MyMouseClickedEvent(myPolygon, graphicsContext));

        primaryStage.show();
    }

    class MyMouseClickedEvent implements EventHandler<MouseEvent> {

        GraphicsContext graphicsContext;
        Vector<MyPoint> myPolygon;
        boolean doneInput;

        public MyMouseClickedEvent(Vector<MyPoint> myPolygon, GraphicsContext graphicsContext) {
            this.myPolygon = myPolygon;
            this.graphicsContext = graphicsContext;
            doneInput = false;
        }

        @Override
        public void handle(MouseEvent event) {
            if (doneInput) return;
            System.out.println("clicked " + event.getSceneX() + ", " + event.getSceneY());
            double x = event.getSceneX();
            double y = event.getSceneY();
            if (!myPolygon.isEmpty()) {
                if (MyPointUtils.distance(myPolygon.firstElement(), new MyPoint(x, y)) <= 20) {
                    x = myPolygon.firstElement().x;
                    y = myPolygon.firstElement().y;
                    doneInput = Boolean.TRUE;
                    Vector<MyHorizontalLine> lines = MyPolygonUtils.getInterior(myPolygon, WIDTH, HEIGHT);
                    for (MyHorizontalLine line : lines)
                        graphicsContext.strokeLine(line.x1, line.y, line.x2, line.y);
                }
                graphicsContext.strokeLine(myPolygon.lastElement().x, myPolygon.lastElement().y, x, y);
            }
            myPolygon.add(new MyPoint(x, y));
        }
    }
}
