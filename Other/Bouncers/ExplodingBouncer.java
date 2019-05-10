package sample.Bouncers;

/*
   sample.Bouncers.BouncingBallFX.java Copyright (c) Kari Laitinen

   http://www.naturalprogramming.com/

   2014-12-18 File created.
   2016-09-29 Last modification.


    This program shows a ball, or a bouncer, that moves
    on the screen. The ball bounces when it hits a 'wall'.
    The bouncer will explode when the Escape key is pressed.

    This program has the following hierarchy of classes:

    Bouncers extends Group

       - represents a ball object that can move and bounce
         inside a given rectangular area
       - Group is the base class because in lower classes
         the bouncer consists of several 'layers'

    RotatingBouncer extends Bouncers

       - represents a bouncer that rotates while it is moving

    ExplodingBouncer extends RotatingBouncer

       - a rotating bouncer that can be made to explode
         and disappear

*/


import javafx.scene.paint.Color;
import javafx.scene.shape.*;  // Arc, Circle, etc.
import javafx.geometry.* ; // Point2D, etc.


public class ExplodingBouncer extends RotatingBouncer
{
	static final int BALL_ALIVE_AND_WELL  =  0 ;
	static final int EXPLOSION_REQUESTED  =  1 ;
	static final int BALL_EXPLODING       =  2 ;
	static final int BALL_EXPLODED        =  3 ;
	
	int ball_state  =  BALL_ALIVE_AND_WELL ;
	
	double explosion_color_alpha_value = 0.0 ;
	
	Circle explosion_layer ;
	
	public ExplodingBouncer( Point2D   given_position,
	                         Color     given_color,
	                         Rectangle given_bouncing_area )
	{
		super( given_position, given_color, given_bouncing_area ) ;
	}
	
	public void explode_ball()
	{
		// With the following if construct we ensure that the
		// ball can be exploded only once.
		
		if ( ball_state == BALL_ALIVE_AND_WELL )
		{
			ball_state = EXPLOSION_REQUESTED ;
		}
	}
	
	
	public void move()
	{
		//  The ball will not move if it is exploding or exploded.
		
		if ( ball_state == BALL_ALIVE_AND_WELL )
		{
			super.move() ; // move the ball with the superclass method
		}
		else if ( ball_state == EXPLOSION_REQUESTED )
		{
			ball_state = BALL_EXPLODING ;
			enlarge() ; // make the ball somewhat larger in explosion
			enlarge() ;
			
			// The color for the explosion layer will be fully transparent
			// yellow when the 'explosion' starts.
			Color initial_explosion_color =
					new Color( 1.0, 1.0, 0.0, explosion_color_alpha_value ) ;
			
			explosion_layer = new Circle( bouncer_background.getCenterX(),
					bouncer_background.getCenterY(),
					bouncer_background.getRadius() + 6,
					initial_explosion_color ) ;
			
			getChildren().add( explosion_layer ) ;
		}
		else if ( ball_state == BALL_EXPLODING )
		{
			if ( explosion_color_alpha_value > 0.98 )
			{
				ball_state = BALL_EXPLODED ;
				
				getChildren().clear() ; // This removes all ball layers.
			}
			else
			{
				// The ball will be 'exploded' by having a transparent
				// yellow ball over the original ball.
				// As the opaqueness of the yellow color gradually increases,
				// the ball becomes ultimately completely yellow in
				// the final stage of the explosion.
				
				explosion_color_alpha_value += 0.02 ; // decrease transparency
				
				explosion_layer.setFill(
						new Color( 1.0, 1.0, 0.0, explosion_color_alpha_value ) ) ;
				
			}
		}
	}
}


