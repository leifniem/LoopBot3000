package views;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.transform.Rotate;

public class CircularSlider extends Region {
	private final static double DEFAULT_MIN_VALUE = 0;
	private final static double DEFAULT_MAX_VALUE = 100;
	private final static double MIN_ANGLE = -20;
	private final static double MAX_ANGLE = 200;
	private final static double PREF_KNOB_SIZE = 25;

	private Region knob = new Region();
	private Rotate rotate = new Rotate();

	private final DoubleProperty value = new SimpleDoubleProperty(this, "value", 0);
	private final DoubleProperty min = new SimpleDoubleProperty(this, "min", DEFAULT_MIN_VALUE);
	private final DoubleProperty max = new SimpleDoubleProperty(this, "max", DEFAULT_MAX_VALUE);

	public CircularSlider() {
		super();
		getStyleClass().add("circular_slider");
		
		knob.setPrefSize(PREF_KNOB_SIZE, PREF_KNOB_SIZE);
		knob.setId("knob");
		knob.getStyleClass().add("knob");
		knob.getTransforms().add(rotate);

		setOnMouseDragged();
		getChildren().add(knob);

		valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				requestLayout();
			}
		});
		
		minProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				requestLayout();
			}
		});
		
		maxProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				requestLayout();
			}
		});
	}

	private void setOnMouseDragged() {
		setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				double x = event.getX();
				double y = event.getY();
				double centerX = getWidth() / 2.0;
				double centerY = getHeight() / 2.0;
				double theta = Math.atan2((y - centerY), (x - centerX));
				double angle = Math.toDegrees(theta);
				if (angle > 0.0) {
					angle = 180 + (180 - angle);
				} else {
					angle = 180 - (180 - Math.abs(angle));
				}
				if (angle >= 270) {
					angle = angle - 360;
				}
				double value = angleToValue(angle);
				setValue(value);
			}
		});
	}

	@Override
	protected void layoutChildren() {
		super.layoutChildren();
		double knobX = (getWidth() - knob.getPrefWidth()) / 2.0;
		double knobY = 0; 
		knob.setLayoutX(knobX);
		knob.setLayoutY(knobY);
		double angle = valueToAngle(getValue());

		if (MIN_ANGLE <= angle && angle <= MAX_ANGLE) {
			rotate.setPivotX(knob.getWidth() / 2.0);
			rotate.setPivotY(knob.getHeight() / 2.0);
			rotate.setAngle(-angle);
		}
	}

	private double valueToAngle(double value) {
		double maxValue = getMax();
		double minValue = getMin();
		double revertedValue = (value - (minValue + maxValue)) * (-1);
		double angle = MIN_ANGLE + (MAX_ANGLE - MIN_ANGLE) * (revertedValue - minValue) / (maxValue - minValue);
		return angle;
	}

	private double angleToValue(double angle) {
		double maxValue = getMax();
		double minValue = getMin();
		double value = minValue + (maxValue - minValue) * (angle - MIN_ANGLE) / (MAX_ANGLE - MIN_ANGLE);
		value = Math.max(minValue, value);
		value = Math.min(maxValue, value);
		double revertedValue = (value - (minValue + maxValue)) * (-1);
		return revertedValue;
	}

	public final void setValue(double v) {
		value.set(v);
	}

	public final double getValue() {
		return value.get();
	}

	public final DoubleProperty valueProperty() {
		return value;
	}

	public final void setMin(double v) {
		min.set(v);
	}

	public final double getMin() {
		return min.get();
	}

	public final DoubleProperty minProperty() {
		return min;
	}

	public final void setMax(double v) {
		max.set(v);
	}

	public final double getMax() {
		return max.get();
	}

	public final DoubleProperty maxProperty() {
		return max;
	}
}