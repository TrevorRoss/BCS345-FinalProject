import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class GameController 
{
	double Scene_X, Scene_Y;
	double Translate_X, Translate_Y;
	String[] User_Answers = { "0", "1", "2", "3", "4", "5", "6", "7" };

	@FXML
	private ImageView ans1, ans2, ans3, ans4, ans5, ans6, ans7; //answer boxes

	@FXML
	private ImageView  left, right, down, up ; // arrows

	@FXML
	private ImageView character; //character

	@FXML
	private ImageView welcome, level1, level2, level3, level4, level5, level6, level7, level8, level9; //levels

	@FXML
	private Pane Congratulate, Sorry; // Overlay screens

	@FXML 
	void clear(MouseEvent event) //this will clear the input from userAns array and then resets to the starting value
	{
		ans1.setImage(null);
		ans2.setImage(null);
		ans3.setImage(null);
		ans4.setImage(null);
		ans5.setImage(null);
		ans6.setImage(null);
		ans7.setImage(null);
		Sorry.setVisible(false);
		Congratulate.setVisible(false);

		for (int i = 0; i < User_Answers.length; i++) 
		{
			User_Answers[i] = "1";
		}
	}

	@FXML 
	void click(MouseEvent event) //this gets the values that will be used in the move method
	{ 
		Scene_X = event.getSceneX();
		Scene_Y = event.getSceneY();
		Translate_X = ((Node) (event.getSource())).getTranslateX();
		Translate_Y = ((Node) (event.getSource())).getTranslateY();

	}

	@FXML 
	void copy(MouseEvent event) //this is to set the answers onto the screen and put into an array then gets copied onto the screen

	{ 
		
		//this checks the ImageView to see if it is loaded allowing it to be written to
		if (ans1.getImage() == null) 
		{
			if (checkBounds(event, ans1)) 
			{
				//this sets the ImageView depending on the image that is being dragged
				ans1.setImage(assignsImage(event.getPickResult().getIntersectedNode().getId()));
				User_Answers[0] = event.getPickResult().getIntersectedNode().getId();
			}
		}
		else if (ans2.getImage() == null) 
		{
			if (checkBounds(event, ans2)) 
			{
				ans2.setImage(assignsImage(event.getPickResult().getIntersectedNode().getId()));
				User_Answers[1] = event.getPickResult().getIntersectedNode().getId();
			}
		} 
		else if (ans3.getImage() == null) 
		{
			if (checkBounds(event, ans3)) 
			{
				ans3.setImage(assignsImage(event.getPickResult().getIntersectedNode().getId()));
				User_Answers[2] = event.getPickResult().getIntersectedNode().getId();
			}
		} 
		else if (ans4.getImage() == null) 
		{
			if (checkBounds(event, ans4)) 
			{
				ans4.setImage(assignsImage(event.getPickResult().getIntersectedNode().getId()));
				User_Answers[3] = event.getPickResult().getIntersectedNode().getId();
			}
		} 
		else if (ans5.getImage() == null) 
		{ 
			if (checkBounds(event, ans5)) 
			{
				ans5.setImage(assignsImage(event.getPickResult().getIntersectedNode().getId()));
				User_Answers[4] = event.getPickResult().getIntersectedNode().getId();
			}
		} 
		else if (ans6.getImage() == null) 
		{
			if (checkBounds(event, ans6)) 
			{
				ans6.setImage(assignsImage(event.getPickResult().getIntersectedNode().getId()));
				User_Answers[5] = event.getPickResult().getIntersectedNode().getId();
			}
		} 
		else if (ans7.getImage() == null) 
		{
			if (checkBounds(event, ans7)) 
			{
				ans7.setImage(assignsImage(event.getPickResult().getIntersectedNode().getId()));
				User_Answers[6] = event.getPickResult().getIntersectedNode().getId();
			}
		}
		//this will return the arrows when the button is released
		resetArrows();
	}
	

	@FXML 
	void move(MouseEvent event) 	 //this will allow clicked objects to be movable
	{ 
		double offsetX = event.getSceneX() - Scene_X;
		double offsetY = event.getSceneY() - Scene_Y;
		double newTranslateX = Translate_X + offsetX;
		double newTranslateY = Translate_Y + offsetY;

		((Node) (event.getSource())).setTranslateX(newTranslateX);
		((Node) (event.getSource())).setTranslateY(newTranslateY);
	}

	@FXML 
	void nextLevel(MouseEvent event) throws IOException //this loads the fxml file depending on which level is being played
	{ 
		//this will check to see which level has a value, the other levels should not have a value
		if (welcome != null) 
		{
			levelSelect("1", welcome);
		} 
		else if (level1 != null) 
		{
			levelSelect("2");
		} 
		else if (level2 != null) 
		{
			levelSelect("3");
		} 
		else if (level3 != null) 
		{
			levelSelect("4");
		} 
		else if (level4 != null) 
		{
			levelSelect("5");
		} 
		else if (level5 != null) 
		{
			levelSelect("6");
		} 
		else if (level6 != null) 
		{
			levelSelect("7");
		} 
		else if (level7 != null) 
		{
			levelSelect("8");
		} 
		else if (level8 != null) 
		{
			levelSelect("9");
		} 
		else if (level9 != null) 
		{
			levelSelect("1");
		}
	}

	@FXML 
	void play(MouseEvent event) throws IOException { //this creates parallel transitions for the character to rotate 
													//and translate then it is put into a sequential transition
		
		//this will check for the current level
		if (level1 != null) 
		{
			String levelAns[] = { "right", "up", "right" };
			//this will check to see if levelAns is equal to userAns
			if (checkAns(levelAns)) 
			{
				SequentialTransition lvl = new SequentialTransition(setParMove(2, 0, 2), setParMove(0, -1, 1),
						setParMove(6, 0, 6));
				lvl.play();
				//if it is true, the “congratulations” pane will pop up which will allow the user to proceed to the next level
				lvl.setOnFinished(e -> Congratulate.setVisible(true));
			} else 
			{
				//if it is false, the “sorry” pane will pop up which will allow the user to either reset the level or quit the game
				Sorry.setVisible(true);
			}
		} 
		else if (level2 != null) 
		{
			String levelAns[] = { "right", "up", "right", "up", "right", "down", "right" };
			if (checkAns(levelAns)) 
			{
				SequentialTransition lvl = new SequentialTransition(setParMove(1, 0, 1), setParMove(0, -1, 1),
						setParMove(1, 0, 1), setParMove(0, -1, 1), setParMove(3, 0, 3), setParMove(0, 2, 2),
						setParMove(3, 0, 3));
				lvl.play();
				lvl.setOnFinished(e -> Congratulate.setVisible(true));
			}else 
			{
				Sorry.setVisible(true);
			}
		} 
		else if (level3 != null) 
		{
			String levelAns[] = { "up", "right", "up", "right", "down", "right" };
			if (checkAns(levelAns)) 
			{
				SequentialTransition lvl = new SequentialTransition(setParMove(0, -2, 2), setParMove(1, 0, 1),
						setParMove(0, -1, 1), setParMove(2, 0, 2), setParMove(0, 3, 3), setParMove(5, 0, 5));
				lvl.play();
				lvl.setOnFinished(e -> Congratulate.setVisible(true));
			} else 
			{
				Sorry.setVisible(true);
			}
		} 
		else if (level4 != null) 
		{
			String levelAns[] = { "down", "right", "up", "right" };
			if (checkAns(levelAns)) 
			{
				SequentialTransition lvl = new SequentialTransition(setParMove(0, 2, 2), setParMove(4, 0, 4),
						setParMove(0, -1, 1), setParMove(4, 0, 4));
				lvl.play();
				lvl.setOnFinished(e -> Congratulate.setVisible(true));
			} else 
			{
				Sorry.setVisible(true);
			}
		} 
		else if (level5 != null) 
		{
			String levelAns[] = { "right", "down", "right", "up", "right" };
			if (checkAns(levelAns)) 
			{
				SequentialTransition lvl = new SequentialTransition(setParMove(2, 0, 2), setParMove(0, 1, 1),
						setParMove(3, 0, 3), setParMove(0, -3, 3), setParMove(3, 0, 3));
				lvl.play();
				lvl.setOnFinished(e -> Congratulate.setVisible(true));
			} else 
			{
				Sorry.setVisible(true);
			}
		} 
		else if (level6 != null) 
		{
			String levelAns[] = { "right", "up", "right", "up", "right" };
			if (checkAns(levelAns)) 
			{
				SequentialTransition lvl = new SequentialTransition(setParMove(1, 0, 1), setParMove(0, -2, 2),
						setParMove(4, 0, 4), setParMove(0, -1, 1), setParMove(3, 0, 3));
				lvl.play();
				lvl.setOnFinished(e -> Congratulate.setVisible(true));
			} else 
			{
				Sorry.setVisible(true);
			}
		} 
		else if (level7 != null) 
		{
			String levelAns[] = { "right", "up", "right", "down", "right" };
			if (checkAns(levelAns)) 
			{
				SequentialTransition lvl = new SequentialTransition(setParMove(2, 0, 2), setParMove(0, -2, 2),
						setParMove(2, 0, 2), setParMove(0, 2, 2), setParMove(4, 0, 4));
				lvl.play();
				lvl.setOnFinished(e -> Congratulate.setVisible(true));
			} else {
				Sorry.setVisible(true);
			}
		} 
		else if (level8 != null) 
		{
			String levelAns[] = { "right", "down", "right", "down", "right", "down", "right" };
			if (checkAns(levelAns)) 
			{
				SequentialTransition lvl = new SequentialTransition(setParMove(1, 0, 1), setParMove(0, 1, 1),
						setParMove(1, 0, 1), setParMove(0, 1, 1), setParMove(3, 0, 3), setParMove(0, 1, 1),
						setParMove(3, 0, 3));
				lvl.play();
				lvl.setOnFinished(e -> Congratulate.setVisible(true));
			} else 
			{
				Sorry.setVisible(true);
			}
		}
		else if (level9 != null) 
		{
			String levelAns[] = { "right", "down", "right", "down", "right", "up", "right" };
			if (checkAns(levelAns)) 
			{
				SequentialTransition lvl = new SequentialTransition(setParMove(2, 0, 2), setParMove(0, 2, 2),
						setParMove(1, 0, 1), setParMove(0, 1, 1), setParMove(2, 0, 2), setParMove(0, -2, 2),
						setParMove(3, 0, 3));
				lvl.play();
				lvl.setOnFinished(e -> Congratulate.setVisible(true));
			} else {
				Sorry.setVisible(true);
			}
		}
	}

	@FXML 
	void quit(MouseEvent event) //this will allow the user to quit the game
	{ 
		
		System.exit(0);
	}

	
	ParallelTransition setParMove(int x, int y, int r) //this will set the individual Parallel Transition to allow the fuzzy to move
	{
		Duration duration = Duration.seconds(1);
		TranslateTransition move = new TranslateTransition(duration, character);
		RotateTransition rotate = new RotateTransition(duration, character);
		move.setByX(138 * x);
		move.setByY(138 * y);
		rotate.setByAngle(360 * r);
		return new ParallelTransition(move, rotate);
	}


	Image assignsImage(String direction) //this will set the image into the users answers depending on the variable being passed
	{
		switch (direction) 
		{
		case "up":
			return up.getImage();
		case "left":
			return left.getImage();
		case "right":
			return right.getImage();
		case "down":
			return down.getImage();
		default:
			break;
		}
		return null;
	}

	
	boolean checkAns(String array[]) //this will compare the passed array to the userAns array, if it doesn’t match then it will return false
	{
		for (int i = 0; i < array.length; i++) 
		{
			if (!User_Answers[i].equals(array[i])) 
			{
				return false;
			}
		}
		return true;
	}

	
	Boolean checkBounds(MouseEvent event, ImageView box) //the ImageView passed into the method will be checked if the MouseEvent 
	{														//is within the ImageView
		if (event.getSceneX() <= box.getLayoutX() + box.getFitWidth() && event.getSceneX() >= box.getLayoutX()
				&& event.getSceneY() <= box.getLayoutY() + box.getFitHeight()
				&& event.getSceneY() >= box.getLayoutY()) 
		{
			return true;
		}
		return false;
	}

	
	void levelSelect(String level) throws IOException //if it exists, this will take the passed variable, load, and display the fxml file
	{

		Parent par = FXMLLoader.load(getClass().getResource(level + ".fxml"));
		Scene scn = new Scene(par);
		Stage stg = (Stage) ((Node) character).getScene().getWindow();
		stg.setTitle("Kodable " + level);
		stg.setScene(scn);
		stg.show();
	}


	void levelSelect(String level, ImageView img) throws IOException //if it exists, this will take the passed variable, load, 
	{																	//and display the fxml file then gets the for img.
	
		Parent par = FXMLLoader.load(getClass().getResource(level + ".fxml"));
		Scene scn = new Scene(par);
		Stage stg = (Stage) ((Node) img).getScene().getWindow();
		stg.setTitle("Kodable " + level);
		stg.setScene(scn);
		stg.show();
	}

	void resetArrows() //this will move the arrows back to its set location and the drop shadow is removed
	{

		left.setTranslateX(-left.getX());
		left.setTranslateY(-left.getY());

		right.setTranslateX(-right.getX());
		right.setTranslateY(-right.getY());

		up.setTranslateX(-up.getX());
		up.setTranslateY(-up.getY());

		down.setTranslateX(-down.getX());
		down.setTranslateY(-down.getY());
		Congratulate.toFront();
		Sorry.toFront();
	}
}