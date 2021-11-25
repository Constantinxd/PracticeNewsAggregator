package ru.vyatsu.koscheev.GUI;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.stage.Stage;
import ru.vyatsu.koscheev.OnCompletedHandler;
import ru.vyatsu.koscheev.OnNewDataHandler;
import ru.vyatsu.koscheev.lenta.ParserWorkerLenta;
import ru.vyatsu.koscheev.lenta.LentaParser;
import ru.vyatsu.koscheev.lenta.LentaSettings;
import ru.vyatsu.koscheev.model.News;

import java.io.IOException;
import java.util.ArrayList;

public class LentaGUI extends Application
        implements OnNewDataHandler<ArrayList<News>>, OnCompletedHandler {
    private ParserWorkerLenta<ArrayList<News>> parser;
    private VBox hBox;
    private TextField numOfBlocksTextField;
    private int count;

    @Override
    public void start(Stage stage) {
        count = 0;
        GridPane gridPane = new GridPane();

        Button startButton = new Button("Start");
        startButton.setMaxWidth(Double.MAX_VALUE);
        startButton.setMaxHeight(Double.MAX_VALUE);
        GridPane.setHgrow(startButton, Priority.ALWAYS);
        gridPane.add(startButton, 0,0);

        Button abortButton = new Button("Abort");
        abortButton.setMaxWidth(Double.MAX_VALUE);
        abortButton.setMaxHeight(Double.MAX_VALUE);
        GridPane.setHgrow(abortButton, Priority.ALWAYS);
        gridPane.add(abortButton, 1,0);

        HBox numOfBlocksHBox = new HBox();
        Label numOfBlocksLabel = new Label("Количество новостных блоков: ");
        numOfBlocksTextField = new TextField("1");
        numOfBlocksHBox.getChildren().addAll(numOfBlocksLabel, numOfBlocksTextField);
        numOfBlocksHBox.setAlignment(Pos.CENTER);
        gridPane.add(numOfBlocksHBox, 0,2, 2, 1);

        hBox = new VBox();

        ScrollPane scrollPane = new ScrollPane(hBox);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        GridPane.setHalignment(scrollPane, HPos.CENTER);
        GridPane.setVgrow(scrollPane, Priority.ALWAYS);
        gridPane.add(scrollPane, 0, 1, 2, 1);

        startButton.setOnAction(e -> {
            hBox.getChildren().clear();
            count = 0;

            int numOfBlocks = Integer.parseInt(numOfBlocksTextField.getText());

            parser = new ParserWorkerLenta<>(new LentaParser());

            parser.setParserSettings(new LentaSettings(numOfBlocks));
            parser.onCompletedList.add(this);
            parser.onNewDataList.add(this);

            try {
                parser.Start();
                Thread.sleep(5000);
                parser.Abort();
            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        abortButton.setOnAction(e -> {
            if (parser != null)
                parser.Abort();
        });


        stage.setScene(new Scene(gridPane));
        stage.setTitle("Lenta");
        stage.setMinWidth(600);
        stage.setMinHeight(600);
        stage.show();
    }

    @Override
    public void OnNewData(Object sender, ArrayList<News> args) {
        Text title;

        for (News a : args) {
            title = new Text(a.title);
            title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));

            hBox.getChildren().addAll(new Text("#" + ++count), new TextFlow(title), new Text(a.time));

            if (a.imageUrl != null)
                hBox.getChildren().add(new ImageView(new Image(a.imageUrl, 550, 400, false, false)));

            for (var content : a.content)
                hBox.getChildren().add(new TextFlow(new Text(content + "\n")));
        }
    }

    @Override
    public void OnCompleted(Object sender) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setContentText("Загрузка завершена");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
