package application;
	
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			// Sets up an initial sudoku board 
			int[][] board = new int[][]
			{
					{ 3, 0, 6, 5, 0, 8, 4, 0, 0 },
					{ 5, 2, 0, 0, 0, 0, 0, 0, 0 },
					{ 0, 8, 7, 0, 0, 0, 0, 3, 1 },
					{ 0, 0, 3, 0, 1, 0, 0, 8, 0 },
					{ 9, 0, 0, 8, 6, 3, 0, 0, 5 },
					{ 0, 5, 0, 0, 9, 0, 6, 0, 0 },
					{ 1, 3, 0, 0, 0, 0, 2, 5, 0 },
					{ 0, 0, 0, 0, 0, 0, 0, 7, 4 },
					{ 0, 0, 5, 2, 0, 6, 3, 0, 0 } };
			int[][] oldBoard = new int[9][9];
			
			Scanner sc = new Scanner(new File("SudokuPuzzles.txt"));
			ArrayList<String> words = new ArrayList<String>();
			ArrayList<int[][]> puzzles = new ArrayList<int[][]>();

			// Runs through the attached txt file and adds the sudoku puzzles it contains to 
			// an ArrayList to be stored if the user wishes to play more than one game
			for (int i = 1; i < 501; i++)
			{
				if (i % 10 != 1)
				{
					words.add(sc.next());
				}
				if (sc.hasNextLine())
				{
					sc.nextLine();
				}
			}
			
			// Goes through and turns the strings in the words ArrayList into
			// 2D arrays that represents sudoku boards
			for (int i = 0; i < 50; i++)
			{
				int[][] newPuzzle = new int[9][9];
				for (int j = 0; j < 9; j++)
				{
					for (int k = 0; k < 9; k++)
					{
						newPuzzle[j][k] = Integer.valueOf(Character.toString(words.get(j + (i * 9)).charAt(k)));
					}
				}
				puzzles.add(newPuzzle);
			}
			// This nested loop deep copied the sudoku board so that one represents the 
			// solved board and and the other the unsolved board
			for (int i = 0; i < oldBoard.length; i++)
			{
				for (int j = 0; j < oldBoard.length; j++)
				{
					oldBoard[i][j] = board[i][j];
				}
			}

			// Solves the board
			solve(board);
			
			// Makes a 2d array of TextFiedls to display to the user representing 
			// the sudoku board
			TextField[][] grid = new TextField[9][9];
			for (int i = 0; i < grid.length; i++)
			{
				for (int j = 0; j < grid.length; j++)
				{
					grid[i][j] = new TextField();
					grid[i][j].setMaxWidth(25);
					grid[i][j].setOnAction(e ->
					{
						
					});
				}
			}
	
			// Sets up the root VBox and an array of HBoxes to represent rows
			VBox root = new VBox(5);
			HBox[] rows = new HBox[12];
			
	        // Initializes and positions HBoxes to represent all the rows of the GUI
			for (int i = 0; i < rows.length; i++)
			{
				if (i == rows.length - 1)
				{
					rows[i] = new HBox(20);
					rows[i].setAlignment(Pos.CENTER);
					
				} else
				{
					rows[i] = new HBox(5);
					rows[i].setAlignment(Pos.CENTER);
			
				}
			}

			// Adds all the TextFields into the rows to display the sudoku board
			for (int i = 0; i < grid.length; i++)
			{
				for (int j = 0; j < grid.length; j++)
				{
					rows[i].getChildren().add(grid[i][j]);
				}

			}
			
			
			Button checkButton = new Button();
			Button solveButton = new Button();
			Button hintButton = new Button();
			Button newGameButton = new Button();
			checkButton.setText("Check");
			solveButton.setText("Solve");
			hintButton.setText("Hint");
			newGameButton.setText("New Game");
	
			
			rows[11].getChildren().addAll(checkButton, solveButton, hintButton, newGameButton);
			
			
			for (int i = 0; i < rows.length; i++)
			{
				root.getChildren().add(rows[i]);
			}
			
			// Searches for all pre-given numbers on the sudoku board and makes them blue to 
			// show that they are in the correct spot and bans the user from being able to edit them
			for (int i = 0; i < oldBoard.length; i++)
			{
				for (int j = 0; j < oldBoard.length; j++)
				{
					if (oldBoard[i][j] != 0)
					{
						grid[i][j].setText(Integer.toString(oldBoard[i][j]));
						grid[i][j].setEditable(false);
						grid[i][j].setStyle("-fx-text-inner-color: #101ee8;");
						
					}
				}
			}

			// When the user clicks the check button the grid is searched to see if 
			// any of its contents match the solution board and if it does turns thoes numbers blue
			// and bans the user from changing them
			checkButton.setOnAction(e ->
			{
				for (int i = 0; i < grid.length; i++)
				{
					for (int j = 0; j < grid.length; j++)
					{
						try
						{
							if (Integer.parseInt(grid[i][j].getText()) == board[i][j])
							{
								grid[i][j].setEditable(false);
								grid[i][j].setStyle("-fx-text-inner-color: #101ee8;");
							} else
							{
								grid[i][j].setStyle("-fx-text-inner-color: #e81010;");
							}
						} catch (NumberFormatException ex)
						{

						}
					}
				}
			});
			
			// When the user clicks the solve button the grid is changed to the solution board 
			// and all the numbers are changed to blue and prevent further editing 
			solveButton.setOnAction(e -> 
			{
				
				for (int i = 0; i < grid.length; i++)
				{
					for (int j = 0; j < grid.length; j++)
					{
						grid[i][j].setText(Integer.toString(board[i][j]));
						grid[i][j].setEditable(false);
						grid[i][j].setStyle("-fx-text-inner-color: #101ee8;");
					}
				}
				
			});
		
			// When the user clicks the hint Button one spot is randomly chosen to be given to the user
			hintButton.setOnAction( e -> 
			{
				boolean gaveHint = true;
				for (int i = 0; i < board.length; i++)
				{
					for (int j = 0; j < board.length; j++)
					{
						try 
						{
							if (Integer.parseInt(grid[i][j].getText()) != board[i][j])
							{
								gaveHint = false;
							}
							
						} catch (NumberFormatException ex)
						{
							gaveHint = false;
						}
					
					}
				}
				
				while (!gaveHint)
				{
					Random rand = new Random();
					int rand_row = rand.nextInt(9);
					int rand_col = rand.nextInt(9);
					if (grid[rand_row][rand_col].isEditable())
					{
						grid[rand_row][rand_col].setText(Integer.toString(board[rand_row][rand_col]));
						grid[rand_row][rand_col].setEditable(false);
						grid[rand_row][rand_col].setStyle("-fx-text-inner-color: #101ee8;");
						gaveHint = true;
					}
				}
				
				

			});
			
			// When new game button is clicked the board is reset and changed to a random new board
			// picked from the puzzles ArrayList
			newGameButton.setOnAction(e -> 
			{
				
				
				getNewPuzzle(oldBoard, board, puzzles);
				
				for (int i = 0; i < oldBoard.length; i++)
				{
					for (int j = 0; j < oldBoard.length; j++)
					{
						if (oldBoard[i][j] != 0)
						{
							grid[i][j].setText(Integer.toString(oldBoard[i][j]));
							grid[i][j].setEditable(false);
							grid[i][j].setStyle("-fx-text-inner-color: #101ee8;");
							
						}
						else 
						{
							grid[i][j].setText("");
							grid[i][j].setEditable(true);
						}
					}
				}
				
			});
			
			Scene scene = new Scene(root,400,400);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Sudoku");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method randomly picks a puzzle from the puzzles ArrayList and deep copies it to oldBoard
	 * and makes Board to the solution
	 * 
	 * @param oldBoard - The unsolved original board
	 * @param board - The solved board
	 * @param puzzles - An ArrayList of sudoku puzzles
	 */
	public static void getNewPuzzle(int[][] oldBoard, int[][] board, ArrayList<int[][]> puzzles)
	{
		
		Random rand = new Random();
		int rando = rand.nextInt(50);
		int[][] tempBoard = puzzles.get(rando); 
		
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board.length; j++)
			{
				oldBoard[i][j] = tempBoard[i][j];
				board[i][j] = tempBoard[i][j];
				
			}
		}
		
		solve(board);
		
		
	}
	
	/**
	 * This method checks to see if the new number being placed into 
	 * board[row][col] is valid or not.
	 * 
	 * @param board - The current board
	 * @param row - The row of the board being looked at
	 * @param col - The column of the board being looked at 
	 * @param newNum - The potential new Number that will be added
	 * @return - False if not a valid change and True if it is
	 */
	public static boolean validChange(int[][] board, int row, int col, int newNum)
	{
		for (int i = 0; i < board.length; i++)
		{
			if (board[row][i] == newNum)
			{
				return false;
			}
		}

		for (int i = 0; i < board.length; i++)
		{

			if (board[i][col] == newNum)
			{
				return false;
			}
		}

		int sqrt = (int) Math.sqrt(board.length);
		int boxRowStart = row - row % sqrt;
		int boxColStart = col - col % sqrt;

		for (int i = boxRowStart; i < boxRowStart + sqrt; i++)
		{
			for (int j = boxColStart; j < boxColStart + sqrt; j++)
			{
				if (board[i][j] == newNum)
				{
					return false;
				}
			}
		}

		return true;
		
	}
	
	/**
	 * This method solves the given sudoku board as long as a solution exists.
	 * 
	 * @param board - The sudoku board that needs to be solved
	 * @return - True if the board has been solved and false if it has not
	 */
	public static boolean solve(int[][] board)
	{
		int row = -1;
		int col = -1;
		boolean fullBoard = true;

		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board.length; j++)
			{
				if (board[i][j] == 0)
				{
					row = i;
					col = j;

					fullBoard = false;
					break;
				}
			}
			if (!fullBoard)
			{
				break;
			}
		}

		if (fullBoard)
		{
			return true;
		}

		for (int i = 1; i <= board.length; i++)
		{
			if (validChange(board, row, col, i))
			{
				board[row][col] = i;
				if (solve(board))
				{
					return true;
				} else
				{
					board[row][col] = 0;
				}
			}
		}
		
		return false;

	}

	
	public static void main(String[] args) {
		
		launch(args);
	}
}
