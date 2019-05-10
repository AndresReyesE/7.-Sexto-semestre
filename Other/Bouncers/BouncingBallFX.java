package sample.Bouncers;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class BouncingBallFX extends Application
{
	static final int SCENE_WIDTH   =  800 ;
	static final int SCENE_HEIGHT  =  680 ;
	
	AnimationTimer animation_timer ;
	
	public void start( Stage stage )
	{
		Group group_for_balls = new Group() ;
		
		stage.setTitle( "sample.Bouncers.BouncingBallFX.java" ) ;
		
		Scene scene = new Scene( group_for_balls, SCENE_WIDTH, SCENE_HEIGHT ) ;
		
		scene.setFill( Color.LIGHTYELLOW ) ;
		
		Rectangle bouncing_area  =  new Rectangle( 0, 0, SCENE_WIDTH, SCENE_HEIGHT ) ;
		
		ExplodingBouncer ball_on_screen = new ExplodingBouncer( new Point2D( SCENE_WIDTH / 2,
				SCENE_HEIGHT / 2 ),
				Color.LIME,
				bouncing_area ) ;
		
		group_for_balls.getChildren().add( ball_on_screen ) ;
		
		scene.setOnKeyPressed( ( KeyEvent event ) ->
		{
			if ( event.getCode()  ==  KeyCode.ESCAPE )
			{
				ball_on_screen.explode_ball() ;
			}
		} ) ;
		
		stage.setScene( scene ) ;
		stage.show() ;
		
		
		animation_timer = new AnimationTimer()
		{
			public void handle( long timestamp_of_current_frame )
			{
				ball_on_screen.move() ;
			}
		} ;
		
		
		animation_timer.start() ;
	}
	
	public static void main( String[] command_line_parameters )
	{
		launch( command_line_parameters ) ;
	}
}

