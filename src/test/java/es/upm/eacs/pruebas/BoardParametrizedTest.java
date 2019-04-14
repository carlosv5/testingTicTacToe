package es.upm.eacs.pruebas;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class BoardParametrizedTest {

	private Board board;

	enum Type {
		WINX, WINO, DRAW
	};

	@Parameters(name = "{index}: TypeTest: {9} - CellsX[{0},{1},{2},{6},{8}] - CellsO[{3},{4},{5},{7}] - Player {3} - Result:{4}")
	public static Collection<Object[]> data() {
		Object[][] values = {   { 0, 3, 1, 4, 2, 6, 9, 9, 9, Type.WINX, "X", "O" }, 
								{ 0, 1, 4, 2, 8, 5, 9, 9, 9, Type.WINX, "X", "O" },
								{ 2, 3, 5, 4, 8, 7, 9, 9, 9, Type.WINX, "X", "O" }, 
								{ 1, 0, 4, 3, 5, 6, 9, 9, 9, Type.WINO, "X", "O" },
								{ 0, 3, 1, 4, 8, 5, 9, 9, 9, Type.WINO, "X", "O" }, 
								{ 4, 0, 6, 1, 5, 2, 9, 9, 9, Type.WINO, "X", "O" },
								{ 0, 1, 4, 2, 5, 3, 6, 8, 7, Type.DRAW, "X", "O" }, 
								{ 0, 2, 1, 3, 5, 4, 6, 7, 8, Type.DRAW, "X", "O" },
								{ 0, 1, 3, 8, 4, 5, 7, 6, 2, Type.DRAW, "X", "O" } 
								};

		// 0 1 2
		// 3 4 5
		// 6 7 8

		return Arrays.asList(values);
	}

	@Parameter(0)
	public int cellId0;
	@Parameter(1)
	public int cellId1;
	@Parameter(2)
	public int cellId2;
	@Parameter(3)
	public int cellId3;
	@Parameter(4)
	public int cellId4;
	@Parameter(5)
	public int cellId5;
	@Parameter(6)
	public int cellId6;
	@Parameter(7)
	public int cellId7;
	@Parameter(8)
	public int cellId8;
	@Parameter(9)
	public Type type;
	@Parameter(10)
	public String p0;
	@Parameter(11)
	public String p1;

	@Before
	public void setup() {
		board = new Board();
		board.enableAll();
	}

	private void fillBoard() {
		int[] cells = { cellId0, cellId1, cellId2, cellId3, cellId4, cellId5, cellId6, cellId7, cellId8 };
		for (int i = 0; i < cells.length; i++) {
			if (cells[i] != 9) {
				if (i % 2 == 0) {
					board.getCell(cells[i]).value = p0;
				} else {
					board.getCell(cells[i]).value = p1;
				}
			}
		}
	}

	@Test
	public void GivenBoardAndPositions_WhenAsummingWINXTypeAndFillingThisPositionsInTheBoard_ThenFirstOneWins() {
		// When
		Assume.assumeTrue(type == Type.WINX);
		fillBoard();
		int[] result = board.getCellsIfWinner(p0);

		// Then
		assertNotNull(result);
		assertFalse(board.checkDraw());
	}

	@Test
	public void GivenBoardAndPositions_WhenAsummingWINOTypeAndFillingThisPositionsInTheBoard_ThenSecondOneWins() {
		// When
		Assume.assumeTrue(type == Type.WINO);
		fillBoard();
		int[] result = board.getCellsIfWinner(p1);

		// Then
		assertNotNull(result);
		assertFalse(board.checkDraw());
	}

	@Test
	public void GivenBoardAndPositions_WhenAsummingDRAWypeAndFillingThisPositionsInTheBoard_ThenTheyDraw() {
        // When 
		Assume.assumeTrue(type == Type.DRAW);
		fillBoard();

		// Then
		assertTrue(board.checkDraw());
	}

}
