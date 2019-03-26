package nl.han.ica.icss.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

//We use this google library, because it makes life so much easier when
//reading the examples icss files as packaged resource
import com.google.common.io.Resources;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import nl.han.ica.icss.Pipeline;
import nl.han.ica.icss.ast.AST;
import nl.han.ica.icss.checker.Checker;
import nl.han.ica.icss.checker.SemanticError;
import nl.han.ica.icss.generator.Generator;
import nl.han.ica.icss.parser.ASTListener;
import nl.han.ica.icss.parser.ICSSLexer;
import nl.han.ica.icss.parser.ICSSParser;
import nl.han.ica.icss.transforms.EvalExpressions;

import nl.han.ica.icss.transforms.RemoveNesting;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

@SuppressWarnings("restriction")
public class Main extends Application {

    private final static String title = "ICSS Tool Februari 2019";
    //Example files (for menu)
    private final static List<String> examples = Arrays.asList("level0.icss", "level1.icss", "level2.icss", "level3.icss");

    //UI Components
    private InputPane inputPane;
    private ASTPane astPane;
    private OutputPane outputPane;
    private FeedbackPane feedbackPane;

    //Toolbar buttons
    private Button parseButton;
    private Button checkButton;
    private Button transformButton;
    private Button generateButton;

    //Model
    private Pipeline pipeline;

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        //Setup pipeline
        pipeline = new Pipeline();

        //Setup UI
        stage.setTitle(title);

        inputPane = new InputPane();
        astPane = new ASTPane();
        outputPane = new OutputPane();
        feedbackPane = new FeedbackPane();

        //Reference for the callbacks
        final Main me = this;

        //Create buttons
        parseButton = new Button("Parse");
        parseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                me.parse();
            }
        });

        checkButton = new Button("Check");
        checkButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                me.check();
            }
        });
        transformButton = new Button("Transform");
        transformButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                me.transform();
            }
        });
        generateButton = new Button("Generate");
        generateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                me.generate();
            }
        });

        //Create menus
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        MenuItem loadInput = new MenuItem("Load input ICSS...");
        loadInput.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open input ICSS...");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("ICSS", "*.icss"));

                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    inputPane.setText(file);
                }
            }
        });
        Menu exampleFilesMenu = new Menu("Load example ICSS");

        //We load them as resources straight from the application's jar
        for (String level : examples) {

            MenuItem levelItem = new MenuItem(level);
            levelItem.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    try {
                        ClassLoader classLoader = Main.class.getClassLoader();
                        URL url = classLoader.getResource(level);
                        inputPane.setText(Resources.toString(url, Charset.defaultCharset()));
                    } catch (IOException ioe) {
                        feedbackPane.addLine(ioe.toString());
                    }
                }
            });
            exampleFilesMenu.getItems().add(levelItem);
        }

        MenuItem saveOutput = new MenuItem("Save generated CSS...");
        saveOutput.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //Create file dialog
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save generated CSS...");
                fileChooser.setInitialFileName("output.css");

                File file = fileChooser.showSaveDialog(stage);
                if (file != null) {
                    outputPane.writeToFile(file);
                }
            }
        });

        MenuItem quit = new MenuItem("Quit");
        quit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Platform.exit();
            }
        });

        fileMenu.getItems().addAll(loadInput, exampleFilesMenu, new SeparatorMenuItem(),
                saveOutput, new SeparatorMenuItem(), quit);
        menuBar.getMenus().addAll(fileMenu);

        //Layout components
        BorderPane main = new BorderPane();
        SplitPane center = new SplitPane();
        center.getItems().addAll(inputPane, astPane, outputPane);

        //Toolbar
        HBox toolbar = new HBox();
        toolbar.setPadding(new Insets(5, 5, 5, 5));
        toolbar.getChildren().addAll(new Label("Pipeline: "), parseButton, checkButton, transformButton, generateButton);
        updateToolbar();

        BorderPane bottom = new BorderPane();
        bottom.setPadding(new Insets(10, 10, 10, 10));
        bottom.setTop(toolbar);
        bottom.setCenter(feedbackPane);

        main.setTop(menuBar);
        main.setCenter(center);
        main.setBottom(bottom);

        Scene scene = new Scene(main, 1200, 600);
        scene.getStylesheets().add("gui.css");

        stage.setScene(scene);
        stage.show();
    }

    private void parse() {
        feedbackPane.clear();
        feedbackPane.addLine("Parsing...");

        pipeline.parseString(inputPane.getText());
        for (String e : pipeline.getErrors()) {
            feedbackPane.addLine(e);
        }
        astPane.update(pipeline.getAST());
        updateToolbar();
        feedbackPane.addLine("Done!");
    }

    private void check() {
        feedbackPane.clear();
        feedbackPane.addLine("Checking...");

        if (pipeline.check()) {
            feedbackPane.addLine("AST is ok!");
        } else {
            for (String e : pipeline.getErrors()) {
                feedbackPane.addLine(e);
            }
        }
        astPane.update(pipeline.getAST());
        updateToolbar();
    }

    private void transform() {
        feedbackPane.clear();
        feedbackPane.addLine("Applying transformations...");
        pipeline.transform();
        astPane.update(pipeline.getAST());
        updateToolbar();
        feedbackPane.addLine("Done!");
    }

    private void generate() {
        feedbackPane.clear();
        feedbackPane.addLine("Generating output...");

        outputPane.setText(pipeline.generate());
        updateToolbar();
        feedbackPane.addLine("Done!");
    }

    private void updateToolbar() {

        //Quick and ugly way...
        checkButton.setDisable(true);
        transformButton.setDisable(true);
        generateButton.setDisable(true);

        if (pipeline.isParsed()) {
            checkButton.setDisable(false);
            if (pipeline.isChecked()) {
                transformButton.setDisable(false);
                generateButton.setDisable(false);
            }
        }
    }

}
