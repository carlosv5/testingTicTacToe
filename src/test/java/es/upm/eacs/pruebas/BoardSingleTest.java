package es.upm.eacs.pruebas;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import es.upm.eacs.pruebas.Board;
import es.upm.eacs.pruebas.TicTacToeGame.Cell;

import static org.hamcrest.CoreMatchers.is;

public class BoardSingleTest {

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
		assertTrue("Debería haber empate", board.checkDraw());
	}

	@Test
	public void GivenBoardWithoutCellsFilled_WhenCheckDraw_ThenTheResultIsNo() {

		// When
		board.enableAll();

		// Then
		assertFalse("No debería haber empate", board.checkDraw());
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
			assertThat("Las celdas ganadoras deberían ser las mismas", result[i], is(winPositions[i]));
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
		assertNull("El jugador X no debería de haber ganado!", result);

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
		assertNull("El jugador O no debería de haber ganado!", result);

	}

	@Test
	public void GivenBoard_whenEnableAllCells_thenAllCellsEnabled() {

		// When
		board.enableAll();

		// Then
		for (int i = 0; i < numberOfCells; i++) {
			assertTrue("Las celdas deberían estar habilitadas", board.getCell(i).active);
		}
	}

	@Test
	public void GivenBoard_whenDisableAllCells_thenAllCellsDisabled() {

		// When
		board.disableAll();

		// Then
		for (int i = 0; i < numberOfCells; i++) {
			assertFalse("Las celdas no deberían estar habilitadas",board.getCell(i).active);
		}
	}

}
