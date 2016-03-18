package gui.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import parser.csv.Parser;
import cep.main.CEPMain;
import cep.main.event.ETLContainer;
import cep.main.event.EqualTick;
import cep.main.event.Lion;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;

public class GuiMain extends Application implements MapComponentInitializedListener {

	private static final double SCREEN_X = 1280;
	private static final double SCREEN_Y = 900;
	private Map<String, List<Lion>> lionMap;
	private List<String> keySet;

	private Group equalTickGroup;

	private GoogleMapView mapView;
	private GoogleMap map;

	@Override
	public void mapInitialized() {
		MapOptions mapOptions = new MapOptions();

		mapOptions.center(new LatLong(-3.8396436, 38.9237614)).mapType(MapTypeIdEnum.ROADMAP).overviewMapControl(false).panControl(false).rotateControl(false)
				.scaleControl(false).streetViewControl(false).zoomControl(false).zoom(11);

		map = mapView.createMap(mapOptions);
		Group root = new Group();
		mapView.getChildren().add(root);
		equalTickGroup = new Group();
		mapView.addMapReadyListener(() -> {
			initEnv(root);
		});
	}

	private void initEnv(Group root) {

		root.getChildren().add(equalTickGroup);

		Group lineGroup = new Group();
		Group lineGroupRed = drawLineGroup(lionMap.get(keySet.get(0)), Color.RED);
		Group lineGroupBlue = drawLineGroup(lionMap.get(keySet.get(1)), Color.BLUE);
		Group lineGroupOrange = drawLineGroup(lionMap.get(keySet.get(2)), Color.ORANGE);
		Group lineGroupBlack = drawLineGroup(lionMap.get(keySet.get(3)), Color.BLACK);
		lineGroup.getChildren().add(lineGroupRed);
		lineGroup.getChildren().add(lineGroupBlue);
		lineGroup.getChildren().add(lineGroupBlack);
		lineGroup.getChildren().add(lineGroupOrange);
		lineGroup.setVisible(false);

		root.getChildren().add(lineGroup);

		CheckBox cbRed = createCheckBox("cbRed", "RED / " + keySet.get(0), 5, 60, true, false);
		CheckBox cbBlue = createCheckBox("cbBlue", "BLUE / " + keySet.get(1), 5, 80, true, false);
		CheckBox cbOrange = createCheckBox("cbOrange", "ORANGE / " + keySet.get(2), 5, 100, true, false);
		CheckBox cbBlack = createCheckBox("cbBlack", "BLACK / " + keySet.get(3), 5, 120, true, false);
		equalTickGroup.setVisible(false);

		cbRed.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (cbRed.isSelected()) {
					lineGroupRed.setVisible(true);
				} else {
					lineGroupRed.setVisible(false);
				}
			}
		});

		cbBlue.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (cbBlue.isSelected()) {
					lineGroupBlue.setVisible(true);
				} else {
					lineGroupBlue.setVisible(false);
				}
			}
		});

		cbOrange.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (cbOrange.isSelected()) {
					lineGroupOrange.setVisible(true);
				} else {
					lineGroupOrange.setVisible(false);
				}
			}
		});

		cbBlack.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (cbBlack.isSelected()) {
					lineGroupBlack.setVisible(true);
				} else {
					lineGroupBlack.setVisible(false);
				}
			}
		});

		addToRoot(root, cbRed, cbBlue, cbOrange, cbBlack);
		root.getChildren().add(createButton("Line Map", 110, 12, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				lineGroup.setVisible(true);
				setCheckBoxVisibility(true, root);
				equalTickGroup.setVisible(false);
			}
		}));
		root.getChildren().add(createButton("Run Test", 180, 12, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				equalTickGroup.setVisible(true);
			}
		}));
		createETView();
	}

	private void createETView() {
		int counter = 0;
		for (EqualTick et : ETLContainer.getInstance().getETL()) {
			final Point2D p = getPointFromLatLong(et.getLocLatA(), et.getLocLongA());
			equalTickGroup.getChildren().add(createCircle("cET" + counter, p.getX(), p.getY(), 10, Color.GREEN, true, null));
		}
	}

	private void setCheckBoxVisibility(boolean visible, Group root) {
		((CheckBox) findNodeWithId("cbRed", root)).setVisible(visible);
		((CheckBox) findNodeWithId("cbBlue", root)).setVisible(visible);
		((CheckBox) findNodeWithId("cbOrange", root)).setVisible(visible);
		((CheckBox) findNodeWithId("cbBlack", root)).setVisible(visible);
	}

	private void addToRoot(Group root, Node... nodes) {
		for (int i = 0; i < nodes.length; i++)
			root.getChildren().add(nodes[i]);
	}

	private CheckBox createCheckBox(String id, String text, double x, double y, boolean selected, boolean visible) {
		CheckBox cb = new CheckBox(text);
		cb.setId(id);
		cb.setLayoutX(x);
		cb.setLayoutY(y);
		cb.setSelected(selected);
		cb.setVisible(visible);
		return cb;
	}

	private Group drawLineGroup(List<Lion> lions, Color color) {
		Group lineGroup = new Group();
		for (int i = 1; i < lions.size(); i++) {
			Point2D start = getPointFromLatLong(lions.get(i - 1).getLocLat(), lions.get(i - 1).getLocLong());
			Point2D end = getPointFromLatLong(lions.get(i).getLocLat(), lions.get(i).getLocLong());
			lineGroup.getChildren().add(createLine("line" + color.toString() + i, start.getX(), start.getY(), end.getX(), end.getY(), color, 0.5));
		}
		return lineGroup;
	}

	private Line createLine(String id, double startX, double startY, double endX, double endY, Color color, double strokeWidth) {
		Line line = new Line(startX, startY, endX, endY);
		line.setFill(null);
		line.setStroke(color);
		line.setStrokeWidth(strokeWidth);
		return line;
	}

	private Button createButton(String text, double x, double y, EventHandler<ActionEvent> event) {
		Button button = new Button(text);
		button.setLayoutX(x);
		button.setLayoutY(y);
		button.setOnAction(event);
		return button;
	}

	private Circle createCircle(String id, double x, double y, double radius, Color color, boolean visible) {
		Circle circle = new Circle(x, y, radius, color);
		circle.setId(id);
		circle.setVisible(visible);
		return circle;
	}

	private Circle createCircle(String id, double x, double y, double radius, Color color, boolean visible, Color fill) {
		Circle circle = createCircle(id, x, y, radius, color, visible);
		circle.setFill(fill);
		circle.setStroke(color);
		return circle;
	}

	private Node findNodeWithId(String id, Group root) {
		for (Node node : root.getChildren()) {
			if (id.equals(node.getId()))
				return node;
		}
		return null;
	}

	private Point2D getPointFromLatLong(double locLat, double locLong) {
		return map.fromLatLngToPoint(new LatLong(locLat, locLong));
	}

	@Override
	public void start(Stage primaryStage) {
		CEPMain em = new CEPMain();
		em.runTest();
		if (lionMap == null) {
			Parser parser = new Parser(",");
			lionMap = parser.parseCSV();
			keySet = new ArrayList<>(lionMap.keySet());
		}
		mapView = new GoogleMapView();
		mapView.addMapInializedListener(this);

		Scene scene = new Scene(mapView, SCREEN_X, SCREEN_Y);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
