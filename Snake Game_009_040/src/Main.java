package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Main extends Application {
    static int stop = 0;
    static int speed = 3;
    static int food = 0;
    static int width = 25;
    static int height = 25;
    static int foodX = 0;
    static int foodY = 0;
    static int cornersize = 25;
    static List<Corner> snake = new ArrayList<>();
    static Dir direction = Dir.right;
    static boolean gameOver = false;
    static boolean pause = true;
    static Random rand = new Random();
    private static final String[] FOODS_IMAGE = new String[]{"/img/ic_orange.png", "/img/ic_apple.png", "/img/ic_cherry.png",
            "/img/ic_berry.png", "/img/ic_coconut_.png", "/img/ic_peach.png", "/img/ic_watermelon.png", "/img/ic_orange.png",
            "/img/ic_pomegranate.png", "/img/ic_pika.png", "/img/ic_durian.png"};

    public enum Dir {
        left, right, up, down
    }

    public static class Corner {
        int x;
        int y;

        public Corner(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }

    public void start(Stage primaryStage) {
        try {
            newFood();

            VBox root = new VBox();
            Canvas c = new Canvas((width + 1) * cornersize, height * cornersize);
            GraphicsContext gc = c.getGraphicsContext2D();
            root.getChildren().add(c);

            new AnimationTimer() {
                long lastTick = 0;

                public void handle(long now) {
                    if (lastTick == 0) {
                        lastTick = now;
                        tick(gc);
                        return;
                    }

                    if (now - lastTick > 1000000000 / speed) {
                        lastTick = now;
                        tick(gc);
                    }
                }

            }.start();

            Scene scene = new Scene(root, width * cornersize, height * cornersize);

            // control
            scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
                if (key.getCode() == KeyCode.W && direction != Dir.down) {
                    direction = Dir.up;
                }
                if (key.getCode() == KeyCode.A && direction != Dir.right) {
                    direction = Dir.left;
                }
                if (key.getCode() == KeyCode.S && direction != Dir.up) {
                    direction = Dir.down;
                }
                if (key.getCode() == KeyCode.D && direction != Dir.left) {
                    direction = Dir.right;
                }
                if (pause == false && key.getCode() == KeyCode.P) {
                    pause = true;
                }
                if (pause && key.getCode() == KeyCode.ENTER) {
                    pause = false;
                }
            }
        );

            // add start snake parts
            snake.add(new Corner(width / 2, height / 2));
            snake.add(new Corner(width / 2, height / 2));
            snake.add(new Corner(width / 2, height / 2));
            //use css style
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("SNAKE GAME");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // gameover
    public static void tick(GraphicsContext gc) {
        if (gameOver) {
            gc.setFill(Color.RED);
            gc.setFont(Font.font("comic sans ms", 50));
            gc.fillText("GAME OVER", (width*cornersize)/4, (height*cornersize)/2);
            return;
        }
        if (pause) {
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, width * cornersize, height * cornersize);

            gc.setFill(Color.WHITE);
            gc.setFont(Font.font("comic sans ms", 25));
            gc.fillText("Press Enter to Start the Game", (width*cornersize)/4, (height*cornersize)/2);
            return;
        }

        for (int i = snake.size() - 1; i >= 1; i--) {
            snake.get(i).x = snake.get(i - 1).x;
            snake.get(i).y = snake.get(i - 1).y;
        }

        switch (direction) {
        case up:
            snake.get(0).y--;
            if (snake.get(0).y < 0) {
                gameOver = true;
            }
            break;
        case down:
            snake.get(0).y++;
            if (snake.get(0).y > height) {
                gameOver = true;
            }
            break;
        case left:
            snake.get(0).x--;
            if (snake.get(0).x < 0) {
                gameOver = true;
            }
            break;
        case right:
            snake.get(0).x++;
            if (snake.get(0).x > (width - 2)) {
                gameOver = true;
            }
            break;

        }

        // eat food
        if (foodX == snake.get(0).x && foodY == snake.get(0).y) {
            snake.add(new Corner(-1, -1));        
            newFood();
        }

        // self destroy
        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(0).x == snake.get(i).x && snake.get(0).y == snake.get(i).y) {
                gameOver = true;
            }
        }

        //background
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width * cornersize, height * cornersize);

        // Score
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("comic sans ms", 30));
        gc.fillText("Score: " + (snake.size() - 3), 10, 30);

        // random food
        Image cc = new Image(FOODS_IMAGE[0]);

        switch (food) {
        case 0:
            cc = new Image(FOODS_IMAGE[0]);
            break;
        case 1:
            cc = new Image(FOODS_IMAGE[1]);
            break;
        case 2:
            cc = new Image(FOODS_IMAGE[2]);
            break;
        case 3:
            cc = new Image(FOODS_IMAGE[3]);
            break;
        case 4:
            cc = new Image(FOODS_IMAGE[4]);
            break;
        case 5:
            cc = new Image(FOODS_IMAGE[5]);
            break;
        case 6:
            cc = new Image(FOODS_IMAGE[6]);
            break;
        case 7:
            cc = new Image(FOODS_IMAGE[7]);
            break;
        case 8:
            cc = new Image(FOODS_IMAGE[8]);
            break;
        case 9:
            cc = new Image(FOODS_IMAGE[9]);
            break;
        case 10:
            cc = new Image(FOODS_IMAGE[10]);
            break;
        }
        
        gc.drawImage(cc, cornersize * foodX, cornersize * foodY);

        // snake
        for (Corner c : snake) {
            gc.setFill(Color.RED);
            gc.fillRect(c.x * cornersize, c.y * cornersize, cornersize - 1, cornersize - 1);
            gc.setFill(Color.PINK);
            gc.fillRect(c.x * cornersize, c.y * cornersize, cornersize - 2, cornersize - 2);
        }
    }

    // food
    public static void newFood() {
        start: while (true) {
            foodX = rand.nextInt(width);
            foodY = rand.nextInt(height);

            for (Corner c : snake) {
                if (c.x == foodX && c.y == foodY) {
                    continue start;
                }
            }
            food = rand.nextInt(11);
            if (food == 9 || food == 10) speed--;
            else speed++;
            break;

        }
    }

    public static void main(String[] args) {
        launch();
    }
}
