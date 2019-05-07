package sample.Bouncers;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Bouncer extends Group
{
	// bouncer_velocity specifies the number of pixels the object
	// will be moved in a single movement operation.
	double bouncer_velocity   =  4.0 ;
	
	// bouncer_direction is an angle in radians. This angle specifies
	// the direction where the bouncer will be moved next.
	double bouncer_direction  =  Math.random() * Math.PI * 2 ;
	
	Circle bouncer_background ;
	
	Rectangle bouncing_area ;
	
	double last_movement_x, last_movement_y ;
	
	public Bouncer( Point2D given_position,
	                Color given_color,
	                Rectangle given_bouncing_area )
	{
		bouncer_background = new Circle( given_position.getX(),
				given_position.getY(),
				32, given_color ) ;
		
		bouncer_background.setStroke( Color.BLACK ) ;
		
		bouncing_area  =  given_bouncing_area ;
		
		getChildren().add( bouncer_background ) ;
	}
	
	
	public void shrink()
	{
		//  The if-construct ensures that the ball does not become
		//  too small.
		
		if ( bouncer_background.getRadius()  > 5 )
		{
			bouncer_background.setRadius( bouncer_background.getRadius() - 3 ) ;
		}
	}
	
	
	public void enlarge()
	{
		bouncer_background.setRadius( bouncer_background.getRadius() + 3 ) ;
	}
	
	
	// With the following method it is possible to check whether the given
	// point is within the ball area, in the host coordinate system.
	
	public boolean contains_point( double given_point_x, double given_point_y )
	{
		//  Here we use the Pythagorean theorem to calculate the distance
		//  from the given point to the center point of the ball.
		
		double  distance_from_given_point_to_ball_center  =
				
				Math.sqrt(
						
						Math.pow( bouncer_background.getCenterX()  -  given_point_x, 2 )  +
								Math.pow( bouncer_background.getCenterY()  -  given_point_y, 2 )  ) ;
		
		return ( distance_from_given_point_to_ball_center  <=
				         bouncer_background.getRadius() ) ;
	}
	
	
	public void move()
	{
		//  In the following statement a minus sign is needed when the
		//  y coordinate is calculated. The reason for this is that the
		//  y direction in the graphical coordinate system is 'upside down'.
		
		last_movement_x = bouncer_velocity * Math.cos(bouncer_direction ) ;
		last_movement_y = - bouncer_velocity * Math.sin(bouncer_direction ) ;
		
		bouncer_background.setCenterX(
				bouncer_background.getCenterX() + last_movement_x ) ;
		bouncer_background.setCenterY(
				bouncer_background.getCenterY() + last_movement_y ) ;
		
		//  Now, after we have moved this bouncer, we start finding out whether
		//  or not it has hit a wall or some other obstacle. If a hit occurs,
		//  a new direction for the bouncer must be calculated.
		
		//  The following four if constructs must be four separate ifs.
		//  If they are replaced with an if - else if - else if - else if
		//  construct, the program will not work when the bouncer enters
		//  a corner in an angle of 45 degrees (i.e. Math.PI / 4).
		
		if ( bouncer_background.getCenterY() - bouncer_background.getRadius()
				     <=  bouncing_area.getY() )
		{
			//  The bouncer has hit the northern 'wall' of the bouncing area.
			
			bouncer_direction = 2 * Math.PI - bouncer_direction ;
		}
		
		if ( bouncer_background.getCenterX() - bouncer_background.getRadius()
				     <=  bouncing_area.getX() )
		{
			//  The western wall has been reached.
			
			bouncer_direction = Math.PI - bouncer_direction ;
		}
		
		if ( ( bouncer_background.getCenterY()  +  bouncer_background.getRadius() )
				     >= ( bouncing_area.getY()  +  bouncing_area.getHeight() ) )
		{
			//  Southern wall has been reached.
			
			bouncer_direction = 2 * Math.PI - bouncer_direction ;
		}
		
		if ( ( bouncer_background.getCenterX()  +  bouncer_background.getRadius() )
				     >= ( bouncing_area.getX()  +  bouncing_area.getWidth() ) )
		{
			//  Eastern wall reached.
			
			bouncer_direction = Math.PI - bouncer_direction ;
		}
	}
	
}
