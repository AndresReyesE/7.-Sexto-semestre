package sample.Bouncers;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class RotatingBouncer extends Bouncer
{
	Shape rotating_layer ;
	
	public RotatingBouncer(  Point2D given_position,
	                         Color given_color,
	                         Rectangle given_bouncing_area )
	{
		super( given_position, given_color, given_bouncing_area ) ;
		
		// Here, we'll construct a rotating_layer which will be
		// rotated over the bouncer_background. The rotating_layer is
		// a union of two filled Arc objects.
		
		Arc north_east_arc = new Arc( bouncer_background.getCenterX(),
				bouncer_background.getCenterY(),
				bouncer_background.getRadius(),
				bouncer_background.getRadius(),
				0, 90 ) ;
		
		north_east_arc.setType( ArcType.ROUND ) ;
		
		Arc south_west_arc = new Arc( bouncer_background.getCenterX(),
				bouncer_background.getCenterY(),
				bouncer_background.getRadius(),
				bouncer_background.getRadius(),
				180, 90 ) ;
		
		south_west_arc.setType( ArcType.ROUND ) ;
		
		
		rotating_layer = Shape.union( north_east_arc, south_west_arc ) ;
		rotating_layer.setFill( Color.GREEN ) ;
		
		getChildren().add( rotating_layer ) ;
	}
	
	
	public void move()
	{
		super.move() ; // run the corresponding upper class method first
		
		// The rotating_layer must move so that it will be exactly over the
		// bouncer_background. We'll translate the coordinates according to how
		// the ball was moved by the upperclass method.
		
		rotating_layer.setTranslateX( rotating_layer.getTranslateX() + last_movement_x ) ;
		rotating_layer.setTranslateY( rotating_layer.getTranslateY() + last_movement_y ) ;
		
		rotating_layer.setRotate( rotating_layer.getRotate() + 2 ) ;
		
		if ( rotating_layer.getRotate() >= 360 )
		{
			rotating_layer.setRotate( 0 ) ;
		}
	}
}