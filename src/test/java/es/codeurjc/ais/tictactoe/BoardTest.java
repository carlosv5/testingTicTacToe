package es.codeurjc.ais.tictactoe;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;

import es.codeurjc.ais.tictactoe.Board;
import es.codeurjc.ais.tictactoe.TicTacToeGame.Cell;

public class BoardTest {
	
	private Board board;
	private final int numberOfCells = 9;
	
	@Before
	public void setup() {
		board = new Board();
	}

	@Test
	public void GivenABoardWithCellsFilledWithaDraw_WhenCheckDraw_ThenTheResultIsYes() {

		// When
		board.enableAll();
		for (int i = 0; i < 9; i++) {
			Cell cell = board.getCell(i);
			cell.value = "X";
		}

		// Then
		assertTrue(board.checkDraw());
	}

	@Test
	public void GivenABoardWithCellsFilledWitouthaDraw_WhenCheckDraw_ThenTheResultIsNo() {

		// When
		board.enableAll();
		for (int i = 0; i < 3; i++) {
			Cell cell = board.getCell(i);
			cell.value = "O";
		}

		// Then
		assertFalse(board.checkDraw());
	}

	@Test
	public void GivenWinningPositions_WhenFillingThisPositionsInTheBoard_ThenGetCellsIfWinnerItReturnsTheWinningPositions() {

		// Given
		int[] winPositions = { 0, 1, 2 };

		// When
		board.enableAll();
		for (int i = 0; i < winPositions.length; i++) {
			Cell cell = board.getCell(winPositions[i]);
			cell.value = "X";
		}
		int[] result = board.getCellsIfWinner("X");

		// Then
		for (int i = 0; i < 3; i++) {
			assertThat(result[i], is(winPositions[i]));
		}
	}

	@Test
	public void GivenNotWinningPositions_WhenFillingThisPositionsInTheBoard_ThenGetCellsIfWinnerItReturnsNull() {

		// Given
		int[] winPositions = { 0, 1, 5 };

		// When
		board.enableAll();
		for (int i = 0; i < winPositions.length; i++) {
			Cell cell = board.getCell(winPositions[i]);
			cell.value = "X";
		}
		int[] result = board.getCellsIfWinner("X");

		// Then
		assertNull(result);

	}

	@Test
	public void GivenWinningPositions_WhenFillingThisPositionsInTheBoardWithX_ThenGetCellsIfWinnerOfOReturnsNull() {

		// Given
		int[] winPositions = { 0, 1, 2 };

		// When
		board.enableAll();

		for (int i = 0; i < winPositions.length; i++) {
			Cell cell = board.getCell(winPositions[i]);
			cell.value = "X";
		}
		int[] result = board.getCellsIfWinner("O");

		// Then
		assertNull(result);

	}

}
