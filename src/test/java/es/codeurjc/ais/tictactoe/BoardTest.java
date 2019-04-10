package es.codeurjc.ais.tictactoe;
import static org.junit.Assert.*;


import org.junit.Test;

import es.codeurjc.ais.tictactoe.Board;
import es.codeurjc.ais.tictactoe.TicTacToeGame.Cell;

public class BoardTest {

	  @Test
	  public void GivenABoardWhenCellsFilledWithaDrawWhenCheckDrawTheResultIsYes() {
		  
	  //Given
	  Board board = new Board();
	  
		//When
	  board.enableAll();

	   for(int i = 0;  i<9; i++) {
		   if(i %2 == 0) {
				Cell cell = board.getCell(i);
				   cell.value = "X";     
		   } else {
				Cell cell = board.getCell(i);
				   cell.value = "O";    
		   }
	   }
		  
	  //Then
	  assertTrue(board.checkDraw());
	  }	
	  
	  
	  @Test
	  public void GivenABoardWhenCellsFilledWitouthaDrawWhenCheckDrawTheResultIsNo() {
		  
	  //Given
	  Board board = new Board();
	  
		//When
	  board.enableAll();

	   for(int i = 0;  i<3; i++) {
			Cell cell = board.getCell(i);
			   cell.value = "O"; 
	   }
	 
	  //Then
	  assertFalse(board.checkDraw());
	  }
	
}
