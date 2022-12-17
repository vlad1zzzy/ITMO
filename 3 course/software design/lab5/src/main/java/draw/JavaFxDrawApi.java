package draw;

import draw.util.Point;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.util.ArrayList;

public record JavaFxDrawApi(int width, int height) implements DrawApi {
    private static final ArrayList<Shape> shapes = new ArrayList<>();

    @Override
    public int getDrawingAreaWidth() {
        return width;
    }

    @Override
    public int getDrawingAreaHeight() {
        return height;
    }

    @Override
    public void addCircle(final Point center, final double radius) {
        shapes.add(new Circle(center.x(), center.y(), radius));
    }

    @Override
    public void addLine(final Point first, final Point second) {
        shapes.add(new Line(first.x(), first.y(), second.x(), second.y()));
    }

    public static class App extends Application {
        @Override
        public void start(final Stage stage) {
            final Group root = new Group();
            final Canvas canvas = new Canvas(1000, 1000);
            final GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setFill(Color.GREEN);

            shapes.forEach(shape -> {
                if (shape instanceof Circle circle) {
                    final double radius = circle.getRadius();
                    gc.fillOval(circle.getCenterX() - radius, circle.getCenterY() - radius, 2 * radius, 2 * radius);
                }

                if (shape instanceof Line line) {
                    gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
                }
            });

            root.getChildren().add(canvas);
            stage.setScene(new Scene(root));
            stage.show();
        }
    }


    @Override
    public void paint() {
        App.launch(App.class);
    }
}
