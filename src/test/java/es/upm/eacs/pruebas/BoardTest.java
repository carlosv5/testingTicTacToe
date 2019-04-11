package es.upm.eacs.pruebas;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import es.upm.eacs.pruebas.Board;
import es.upm.eacs.pruebas.TicTacToeGame.Cell;

import static org.hamcrest.CoreMatchers.is;

public class BoardTest {

	private Board board;
	private final int numberOfCells = 9;

	@Before
	public void setup() {
		board = new Board();
	}

	@Test
	public void GivenBoardWithCellsFilledWithaDraw_WhenCheckDraw_ThenTheResultIsYes() {

		// When
		board.enableAll();
		for (int i = 0; i < numberOfCells; i++) {
			Cell cell = board.getCell(i);
			cell.value = "X";
		}

		// Then
		assertTrue(board.checkDraw());
	}

	@Test
	public void GivenBoardWithoutCellsFilled_WhenCheckDraw_ThenTheResultIsNo() {

		// When
		board.enableAll();

		// Then
		assertFalse(board.checkDraw());
	}

	@Test
	public void GivenBoardAndWinningPositions_WhenFillingThisPositionsInTheBoard_ThenGetCellsIfWinnerItReturnsTheWinningPositions() {

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
	public void GivenBoardAndNotWinningPositions_WhenFillingThisPositionsInTheBoard_ThenGetCellsIfWinnerItReturnsNull() {

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
	public void GivenBoardAndWinningPositions_WhenFillingThisPositionsInTheBoardWithX_ThenGetCellsIfWinnerOfOReturnsNull() {

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

	@Test
	public void GivenBoard_whenEnableAllCells_thenAllCellsEnabled() {

		// When
		board.enableAll();

		// Then
		for (int i = 0; i < numberOfCells; i++) {
			assertTrue(board.getCell(i).active);
		}
	}

	@Test
	public void GivenBoard_whenDisableAllCells_thenAllCellsDisabled() {

		// When
		board.disableAll();

		// Then
		for (int i = 0; i < numberOfCells; i++) {
			assertFalse(board.getCell(i).active);
		}
	}

}
