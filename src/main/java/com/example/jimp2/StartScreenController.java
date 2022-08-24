package com.example.jimp2;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//TODO: refaktoryzacja w przyszłości


public class StartScreenController extends Grafexe implements Initializable {
    @FXML
    private CheckBox ifGenerate;
    @FXML
    private CheckBox ifRead;
    @FXML
    private CheckBox ifBFS;
    private boolean BFS;

    @FXML
    private CheckBox IfConst;

    @FXML
    private Label Costs;

    @FXML
    private Slider HowMuchConnections;
    public double howMuchConnections;

    @FXML
    private Label HowMuch;

    @FXML
    private TextField FileNameGen;
    public String fileNameGen;
    private boolean FileNameGenWrong;

    @FXML
    private TextField RowNum;
    private int rowNum;
    private boolean RowNumWrong;

    @FXML
    private TextField ColNum;
    private int colNum;
    private boolean ColNumWrong;


    @FXML
    private TextField FromBoundery;
    private Double fromBoundery;
    private boolean FromBounderyWrong;

    @FXML
    private TextField ToBoundery;
    private Double toBoundery;
    private boolean ToBounderyWrong;

    @FXML
    private TextField FileNameRead;
    private String fileNameRead;
    private boolean FileNameReadWrong;

    @FXML
    private CheckBox IfSave;

    @FXML
    private Label Weights;

    @FXML
    private CheckBox BFSresult;

    @FXML
    private TextArea messages;

    @FXML
    private Pane myPane;
    private double size = 10;
    private double vX;
    private double vY;
    private boolean ifRoadPrinted;
    private Dijkstra dijkstra;
    private GraphGenerator graphGenerator;
    private Reader reader;

    public StartScreenController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        BFSresult.setDisable(true);
        FileNameGen.setStyle("-fx-background-color: #fff;");
        FileNameGen.setEditable(false);
        RowNum.setStyle("-fx-background-color: #fff;");
        RowNum.setEditable(false);
        ColNum.setStyle("-fx-background-color: #fff;");
        ColNum.setEditable(false);
        ToBoundery.setStyle("-fx-background-color: #fff;");
        ToBoundery.setEditable(false);
        FromBoundery.setStyle("-fx-background-color: #fff;");
        FromBoundery.setEditable(false);
        FileNameRead.setStyle("-fx-background-color: #fff;");
        FileNameRead.setEditable(false);
        IfConst.setDisable(true);
        IfSave.setDisable(true);
        messages.setText("");

        HowMuchConnections.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {

                howMuchConnections = HowMuchConnections.getValue();
                HowMuch.setText(howMuchConnections +"%");
            }
        });
        HowMuchConnections.setValue(50);
        HowMuchConnections.setDisable(true);
        HowMuch.setText("");


    }
    EventHandler<MouseEvent> handler = this::handleEvent;

    public double getX(int node){
        int colN = node%(colNum);
        if(colN == 0)
            colN = colNum;
        return ((colN-1)*4*size + 2*size);
    }

    public double getY(int node){
        int rowN;
        if(node%colNum == 0) {
            rowN = node / (colNum);
        } else {
            rowN = node /  colNum;
            rowN++;
        }
        return (((rowN-1)*4*size) + 0.85*size);
    }

    public void addHorizontallyRectangle(int fromNode,double cost){
        Rectangle rectangle = new Rectangle();
        rectangle.setX(getX(fromNode));
        rectangle.setY(getY(fromNode));
        rectangle.setWidth(2*size);
        rectangle.setHeight(0.3*size);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(0.05*size);
        rectangle.setFill(getColor(cost));
        myPane.getChildren().add(rectangle);
    }

    public void addVerticalRectangle(int fromNode,double cost){
        Rectangle rectangle = new Rectangle();
        rectangle.setX(getX(fromNode)-1.15*size);
        rectangle.setY(getY(fromNode)+1.15*size);
        rectangle.setWidth(0.3*size);
        rectangle.setHeight(2*size);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(0.05*size);
        rectangle.setFill(getColor(cost));
        myPane.getChildren().add(rectangle);
    }

    private int getSwitch(double cost){
        double scale = toBoundery - fromBoundery;
        if(cost-fromBoundery < 0.1 * scale)
            return 1;
        if(cost-fromBoundery < 0.2 * scale)
            return 2;
        if(cost-fromBoundery < 0.3 * scale)
            return 3;
        if(cost-fromBoundery < 0.4 * scale)
            return 4;
        if(cost-fromBoundery < 0.5 * scale)
            return 5;
        if(cost-fromBoundery < 0.6 * scale)
            return 6;
        if(cost-fromBoundery < 0.7 * scale)
            return 7;
        if(cost-fromBoundery < 0.8 * scale)
            return 8;
        if(cost-fromBoundery < 0.9 * scale)
            return 9;
        return 10;
    }

    public Color getColor(double cost){
        return switch (getSwitch(cost)) {
            case 1 -> Color.BLUE;
            case 2 -> Color.CORNFLOWERBLUE;
            case 3 -> Color.DARKTURQUOISE;
            case 4 -> Color.MEDIUMAQUAMARINE;
            case 5 -> Color.LIME;
            case 6 -> Color.GREENYELLOW;
            case 7 -> Color.GOLD;
            case 8 -> Color.DARKORANGE;
            case 9 -> Color.INDIANRED;
            case 10 -> Color.CRIMSON;
            default -> Color.BLACK;
        };
    }

    public void cleanMessages(){
        messages.setText("");
    }

    public void cleanUp(){
        if(!myPane.getChildren().isEmpty()) {
            messages.appendText("Wyczyszczono\n");
            ifRoadPrinted = false;
            unselectNodes();
            myPane.getChildren().removeAll();
            myPane.getChildren().clear();
            setToDefaultView();
        }
    }

    public void printGraph(Container container) {

        int i =0;
        for (int l = 1; l <= rowNum; l++) {
            for (int k = 1; k <= colNum; k++) {
                i++;
               Circle circle = new Circle(vX, vY, size);
               circle.setStrokeWidth(0.05*size);
               circle.setStroke(Color.BLACK);
               circle.setFill(Color.LIGHTSLATEGRAY);
                myPane.getChildren().add(circle);
                vX += 4 * size;

                if (container.Graph.get(i).containsKey(i + 1))
                    addHorizontallyRectangle(i, (Double) container.Graph.get(i).get(i + 1));
                if(container.Graph.get(i).containsKey(i+colNum)) {
                    addVerticalRectangle(i, (Double) container.Graph.get(i).get(i + colNum));
                }
            }
            vX = size;
            vY += 4 * size;
        }
        myPane.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
    }

    private void setToDefaultView(){
         vX=size;
         vY=size;
    }

    private boolean isBetween(double number, double from, double to ){
        if(number > from && number < to)
            return true;
        else
            return false;
    }

    public int whichNmbNodeChosen(MouseEvent e) {
        String target = e.getTarget().getClass().getSimpleName();
        if(!target.equals("Circle")){
            System.out.println("Nie koło");
            return -1;
        }

        double x = e.getX();
        double y = e.getY();

        int rowN=1;
        int colN=1;

        int i=1;
        while(!isBetween(x,(i-1)*4*size,((i-1)*4*size + 2*size))) {
            colN++;
            i++;
        }

        i=1;
        while(!isBetween(y,(i-1)*4*size,((i-1)*4*size + 2*size))) {
            rowN++;
            i++;
        }

        return colN + (rowN-1)*colNum;
    }

    private int firstNode;
    private int secondNode;
    private Circle circleFirst;
    private Circle circleSecond;
    private boolean ifFirstSelected = false;
    private boolean ifSecondSelected = false;

    public void unselectNodes(){
        if(myPane.getChildren().contains(circleFirst)) {
            messages.appendText("Odznaczono\n");
            myPane.getChildren().remove(circleFirst);
            ifFirstSelected = false;
            firstNode = 0;
        }
        if(myPane.getChildren().contains(circleSecond)) {
            myPane.getChildren().remove(circleSecond);
            ifSecondSelected = false;
            ifRoadPrinted = false;
            secondNode = 0;
        }
    }

    private void switchToSelectedNodeFirst(int node){
        messages.appendText("Zaznaczono pierwszy wierzchołek\n");
            circleFirst = new Circle();
            circleFirst.setFill(Color.CYAN);
            circleFirst.setRadius(size);
            circleFirst.setCenterX(getX(node)-size);
            circleFirst.setCenterY(getY(node)+0.15*size);
            myPane.getChildren().add(circleFirst);

    }

    private void switchToSelectedNodeSecond(int node){
        messages.appendText("Zaznaczono drugi wierzchołek\n");
        circleSecond = new Circle();
        circleSecond.setFill(Color.ROYALBLUE);
        circleSecond.setRadius(size);
        circleSecond.setCenterX(getX(node)-size);
        circleSecond.setCenterY(getY(node)+0.15*size);
        myPane.getChildren().add(circleSecond);
    }

    private void handleEvent(MouseEvent e) {
        int node = whichNmbNodeChosen(e);
        if(node != -1) {
            if (!ifFirstSelected) {
                ifFirstSelected = true;
                firstNode = node;
                switchToSelectedNodeFirst(node);
                return;
            }
            if (!ifSecondSelected && ifFirstSelected && node != firstNode) {
                ifSecondSelected = true;
                secondNode = node;
                switchToSelectedNodeSecond(node);

                if(ifRead.isSelected()) {
                    dijkstra = new Dijkstra(reader.container, rowNum, colNum);
                    dijkstra.initDijkstra();
                    if(firstNode < secondNode) {
                        if (dijkstra.doDijkstra(firstNode, secondNode)) {
                            dijkstra.showPathDij(firstNode, secondNode);
                            printRoad(firstNode, secondNode);
                            System.out.println(dijkstra.doDijkstra(firstNode,secondNode));
                        }
                    } else {
                        if(dijkstra.doDijkstra(secondNode, firstNode)) {
                            dijkstra.showPathDij(secondNode, firstNode);
                            printRoad(secondNode, firstNode);
                        }
                    }
                }
                if(ifGenerate.isSelected()){
                    dijkstra = new Dijkstra(graphGenerator.container, rowNum, colNum);
                    dijkstra.initDijkstra();
                    if(firstNode < secondNode) {
                        if(dijkstra.doDijkstra(firstNode, secondNode)) {
                            dijkstra.showPathDij(firstNode, secondNode);
                            printRoad(firstNode, secondNode);
                        }
                    } else {
                        if(dijkstra.doDijkstra(secondNode, firstNode)) {
                            dijkstra.showPathDij(secondNode, firstNode);
                            printRoad(secondNode, firstNode);
                        }
                    }
                }
            }
        }
    }

    public void printRoad(int firstNode, int secondNode){
        if(!ifRoadPrinted){
            ifRoadPrinted = true;
            int temp = secondNode;
                while(firstNode != secondNode){
                    if(dijkstra.path.containsKey(secondNode)) {
                        Circle circleRoad = new Circle();
                        circleRoad.setCenterX(getX(dijkstra.path.get(secondNode)) - size);
                        circleRoad.setCenterY(getY(dijkstra.path.get(secondNode)) + 0.15 * size);
                        circleRoad.setFill(Color.BLACK);
                        circleRoad.setRadius(size);
                        secondNode = dijkstra.path.get(secondNode);
                        myPane.getChildren().add(circleRoad);
                    }
                    else {
                        messages.appendText("Nie udało się odnaleźć drogi.\n");
                        Costs.setText("brak");
                        return;
                    }
                }
                messages.appendText("Narysowano drogę\n");
                Costs.setText(Double.toString(roundTo3DecimalPlace(dijkstra.cost.get(temp))));
                Circle circleRoad = new Circle();
                circleRoad.setCenterX(getX(temp) - size);
                circleRoad.setCenterY(getY(temp) + 0.15 * size);
                circleRoad.setFill(Color.BLACK);
                circleRoad.setRadius(size);
                myPane.getChildren().add(circleRoad);
        }
    }

    public void redrawActualGraph(){
        if(ifGenerate.isSelected() && !FileNameGenWrong && !RowNumWrong && !ColNumWrong && !FromBounderyWrong && !ToBounderyWrong){
            cleanUp();
            messages.appendText("Zresetowano\n");
            printGraph(graphGenerator.container);
        }
        if(ifRead.isSelected() && !FileNameReadWrong){
            cleanUp();
            messages.appendText("Zresetowano\n");
            printGraph(reader.container);
        }
    }

    private void setSize(){
        double colCalculateSize;
        double rowCalculateSize;

        double width = myPane.getWidth();
        double height = myPane.getHeight();

        System.out.println(myPane.getWidth());
        System.out.println(myPane.getHeight());
        colCalculateSize = width/(2*(colNum + colNum-1));
        rowCalculateSize = height/(2*(rowNum + rowNum-1));

        size = Math.min(colCalculateSize, rowCalculateSize);

        System.out.println("size: "+size);
        if(size > 20){
            size = 20;
        }
        System.out.println("size: "+size);

    }


    public void setIfGenerate(ActionEvent event) {
        if(ifGenerate.isSelected()){
            ifRead.setDisable(true);

            FileNameGen.setEditable(true);
            FileNameGen.setStyle("-fx-background-insets-color: #fff;");
            RowNum.setEditable(true);
            RowNum.setStyle("-fx-background-insets-color: #fff;");
            ColNum.setEditable(true);
            ColNum.setStyle("-fx-background-insets-color: #fff;");
            ToBoundery.setEditable(true);
            ToBoundery.setStyle("-fx-background-insets-color: #fff;");
            FromBoundery.setEditable(true);
            FromBoundery.setStyle("-fx-background-insets-color: #fff;");
            IfConst.setDisable(false);
            IfSave.setDisable(false);
            HowMuchConnections.setDisable(false);
            HowMuchConnections.setValue(50);
            howMuchConnections = 50;
            HowMuch.setText(howMuchConnections +"%");
        }
        else {
            ifRead.setDisable(false);
            IfSave.setDisable(true);
            IfSave.setSelected(false);
            FileNameGen.setEditable(false);
            FileNameGen.setStyle("-fx-background-color: #fff;");
            FileNameGen.setText("");
            RowNum.setEditable(false);
            RowNum.setStyle("-fx-background-color: #fff;");
            RowNum.setText("");
            ColNum.setEditable(false);
            ColNum.setStyle("-fx-background-color: #fff;");
            ColNum.setText("");
            ToBoundery.setEditable(false);
            ToBoundery.setStyle("-fx-background-color: #fff;");
            ToBoundery.setText("");
            FromBoundery.setEditable(false);
            FromBoundery.setStyle("-fx-background-color: #fff;");
            FromBoundery.setText("");
            IfConst.setDisable(true);
            IfConst.setSelected(false);
            HowMuchConnections.setValue(50);
            HowMuchConnections.setDisable(true);
            HowMuch.setText("");

        }
    }
    public void setIfRead(ActionEvent event) {
        if(ifRead.isSelected()){
            ifGenerate.setDisable(true);
            FileNameRead.setEditable(true);
            FileNameRead.setStyle("-fx-background-insets-color: #fff;");


        }
        else {
            ifGenerate.setDisable(false);
            FileNameRead.setEditable(false);
            FileNameRead.setStyle("-fx-background-color: #fff;");
            FileNameRead.setText("");
        }
    }
    public void setIfConst(){
        if(IfConst.isSelected()){
            howMuchConnections = 100;
            HowMuchConnections.setDisable(true);
            HowMuchConnections.setValue(100);
            HowMuch.setText(howMuchConnections +"%");
        } else{
            HowMuchConnections.setDisable(false);
            HowMuchConnections.setValue(50);
        }
    }

    public void setIfBFS(ActionEvent event) {
        if(ifBFS.isSelected()){
            BFS = true;
            BFSresult.setDisable(false);
        }
        else {
            BFS = false;
            BFSresult.setSelected(false);
            BFSresult.setDisable(true);
        }
    }

    private double roundTo3DecimalPlace(double value) {
        return Math.round(value * 1000.0) / 1000.0;
    }

    public double getHowMuchConnections(){
        return howMuchConnections;
    }


    public void clickFiledFileNameGenWhenWrong(){
        if(FileNameGenWrong) {
            FileNameGenWrong = false;
            returnToNotWrongView(FileNameGen);
        }
    }

    public void clickFiledRowNumWhenWrong(){
        if(RowNumWrong) {
            RowNumWrong = false;
            returnToNotWrongView(RowNum);
        }
    }

    public void clickFiledColNumWhenWrong(){
        if(ColNumWrong) {
            ColNumWrong = false;
            returnToNotWrongView(ColNum);
        }
    }

    public void clickFiledFromBounderyWhenWrong(){
        if(FromBounderyWrong) {
            FromBounderyWrong = false;
            returnToNotWrongView(FromBoundery);
        }
    }

    public void clickFiledToBounderyWhenWrong(){
        if(ToBounderyWrong) {
            ToBounderyWrong = false;
            returnToNotWrongView(ToBoundery);
        }
    }

    public void clickFiledFileNameReadWhenWrong(){
        if(FileNameReadWrong) {
            FileNameReadWrong = false;
            returnToNotWrongView(FileNameRead);
        }
    }

    private void returnToNotWrongView(TextField n){
        n.setStyle("-fx-text-fill: black;");
        n.setStyle("-fx-background-color: white;");
        n.setStyle("-fx-background-insets-color: #fff;");
    }

    private void setToWrongView(TextField n){
        n.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
    }

    public void onClickButtonStart(ActionEvent event) throws IOException {
        unselectNodes();
        cleanUp();
        cleanMessages();

        if (ifGenerate.isSelected()){
            if(FileNameGen.getLength() > 0 && FileNameGen.getText().matches("[A-Za-z0-9]+")){
                fileNameGen = FileNameGen.getText();
            }
            else {
                messages.appendText("[nazwa pliku]: Powinna składać się z liter A-Z, a-z, oraz cyfr 1-9, rozszerzenie pomijamy \n");
                setToWrongView(FileNameGen);
                FileNameGenWrong = true;
            }
            if(RowNum.getLength() > 0 && RowNum.getText().matches("[0-9]+") ) {
                    rowNum = Integer.parseInt(RowNum.getText());
                } else {
                    messages.appendText("[liczba wierszy]: Należy wprowadzić liczbę nieujemną\n");
                    setToWrongView(RowNum);
                    RowNumWrong = true;
                }
            if(rowNum == 1){
                messages.appendText("[liczba wierszy]: powinna być większa od jedności\n");
                setToWrongView(RowNum);
                RowNumWrong = true;
            }

            if(ColNum.getLength() > 0 && ColNum.getText().matches("[0-9]+")){
                    colNum = Integer.parseInt(ColNum.getText());
            } else {
                messages.appendText("[liczba kolumn]: Należy wprowadzić liczbę nieujemną \n");
                setToWrongView(ColNum);
                ColNumWrong = true;
            }
            if(colNum == 1){
                messages.appendText("[liczba kolumn]: powinna być większa od jedności\n");
                setToWrongView(ColNum);
                ColNumWrong = true;
            }

            if(FromBoundery.getLength() > 0 && FromBoundery.getText().matches("^(-?)(0|([1-9][0-9]*))(\\.[0-9]+)?$")){
                fromBoundery = Double.parseDouble(FromBoundery.getText());
            } else {
                messages.appendText("[od]: Należy wprowadzić liczbę nieujemną \n");
                setToWrongView(FromBoundery);
                FromBounderyWrong = true;
            }
            //[0-9]+
            if(ToBoundery.getLength() > 0 && ToBoundery.getText().matches("^(-?)(0|([1-9][0-9]*))(\\.[0-9]+)?$")){
                toBoundery = Double.parseDouble(ToBoundery.getText());
            } else {
                messages.appendText("[do]: Należy wprowadzić liczbę nieujemną\n");
                setToWrongView(ToBoundery);
                ToBounderyWrong = true;
            }

            if(!ToBounderyWrong && !FromBounderyWrong && ToBoundery.getLength() > 0 && FromBoundery.getLength() > 0){
                if(toBoundery < fromBoundery) {
                    messages.appendText("Górna granica powinna być większa niż dolna\n");
                    setToWrongView(ToBoundery);
                    ToBounderyWrong = true;
                }
                if(toBoundery < 0){
                    messages.appendText("Górna granica powinna być nieujemna\n");
                    setToWrongView(ToBoundery);
                    ToBounderyWrong = true;
                }
                if(fromBoundery < 0){
                    messages.appendText("Dolna granica powinna być nieujemna\n");
                    setToWrongView(FromBoundery);
                    ToBounderyWrong = true;
                }
            }

            if(!FileNameGenWrong && !RowNumWrong && !ColNumWrong && !FromBounderyWrong && !ToBounderyWrong){
                graphGenerator = new GraphGenerator(rowNum,colNum,fromBoundery,toBoundery,fileNameGen,howMuchConnections/100);
                graphGenerator.graphGen();
                rowNum = graphGenerator.getRowNum();
                colNum = graphGenerator.getColNum();
                setSize();
                fromBoundery = graphGenerator.getFromBound();
                toBoundery = graphGenerator.getToBound();

                if(!myPane.getChildren().isEmpty()) {
                    myPane.getChildren().removeAll();
                    myPane.getChildren().clear();
                    setToDefaultView();
                }
                if(myPane.getChildren().isEmpty()) {
                    setToDefaultView();
                    Weights.setText("<"+roundTo3DecimalPlace(fromBoundery)+","+roundTo3DecimalPlace(toBoundery)+">");
                    printGraph(graphGenerator.container);
                }

                if(IfSave.isSelected())
                graphGenerator.saveToFile();

                if(BFS){
                    BFS bfs = new BFS(graphGenerator.container, rowNum,colNum);
                    boolean BFSresGen = bfs.doBFS();
                    BFSresult.setSelected(BFSresGen);
                }
            }
        }

        else if (ifRead.isSelected()){
            if(FileNameRead.getLength()>0 && FileNameRead.getText().matches("[A-Za-z0-9]+")){
                fileNameRead = FileNameRead.getText();
            }else {
                messages.appendText("[nazwa pliku]: Powinna składać się z liter A-Z, a-z, oraz cyfr 1-9, rozszerzenie pomijamy \n");
                setToWrongView(FileNameRead);
                FileNameReadWrong = true;
            }

            if(!FileNameReadWrong){
                File file = new File("data/"+fileNameRead+".txt");
                if(file.exists()) {
                    reader = new Reader(fileNameRead);
                    reader.readFromFile();
                    rowNum = reader.getRowNum();
                    colNum = reader.getColNum();
                    setSize();
                    fromBoundery = reader.getFromBoundery();
                    toBoundery = reader.getToBoundery();

                    if(!myPane.getChildren().isEmpty()) {
                        myPane.getChildren().removeAll();
                        myPane.getChildren().clear();
                        setToDefaultView();
                    }
                    if(myPane.getChildren().isEmpty()) {
                        setToDefaultView();
                        if(fromBoundery != -1 && toBoundery != -1)
                            Weights.setText("<"+roundTo3DecimalPlace(fromBoundery)+","+roundTo3DecimalPlace(toBoundery)+">");
                        else
                            Weights.setText("Brak");
                        printGraph(reader.container);
                    }
                    if (BFS) {
                        BFS bfs = new BFS(reader.container, rowNum, colNum);
                        boolean BFSresRead = bfs.doBFS();
                        BFSresult.setSelected(BFSresRead);
                    }
                } else {
                    messages.appendText("[nazwa pliku]: podany plik nie występuje w katalogu [data]\n");
                    setToWrongView(FileNameRead);
                    FileNameReadWrong = true;
                }
            }
        } else
        messages.appendText("Zaznacz odpowiednią opcję pragramu [generowanie / wczytywanie]\n");
    }
}