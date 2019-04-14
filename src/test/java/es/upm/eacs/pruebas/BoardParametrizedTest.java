package es.upm.eacs.pruebas;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
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

import es.upm.eacs.pruebas.TicTacToeGame.Cell;

@RunWith(Parameterized.class)
public class BoardParametrizedTest {

	private Board board;
	private String px = "X";
	private String po = "O";
	enum Type {
		WINX, WINO, DRAW
	};

	@Parameters(name = "{index}: TypeTest: {9} - CellsX[{0},{1},{2},{6},{8}] - CellsO[{3},{4},{5},{7}] - Player {3} - Result:{4}")
	public static Collection<Object[]> data() {
	Object[][] values = { { 0, 3, 1, 4, 2, 6, 9, 9, 9, Type.WINX },
						  { 0, 1, 4, 2, 8, 5, 9, 9, 9, Type.WINX },
						  { 2, 3, 5, 4, 8, 7, 9, 9, 9, Type.WINX }, 
						  { 1, 0, 4, 3, 5, 6, 9, 9, 9, Type.WINO },
						  { 0, 3, 1, 4, 8, 5, 9, 9, 9, Type.WINO },
						  { 4, 0, 6, 1, 5, 2, 9, 9, 9, Type.WINO },
						  { 0, 1, 4, 2, 5, 3, 6, 8, 7, Type.DRAW },
						  { 0, 2, 1, 3, 5, 4, 6, 7, 8, Type.DRAW },
						  { 0, 1, 3, 8, 4, 5, 7, 6, 2, Type.DRAW }
		};
	
	// 0  1  2
	// 3  4  5
	// 6  7  8	
	
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

	@Before
	public void setup() {
		board = new Board();
	}

	@Test
	public void GivenBoardAndPositions_WhenAsummingWINXTypeAndFillingThisPositionsInTheBoard_ThenCheckBoardMethods() {
		// When
		Assume.assumeTrue(type == Type.WINX);
		board.enableAll();
			
		board.getCell(cellId0).value = px;
		board.getCell(cellId1).value = po;
		board.getCell(cellId2).value = px;
		board.getCell(cellId3).value = po;
		board.getCell(cellId4).value = px;
		board.getCell(cellId5).value = po;
		int[] resultX = board.getCellsIfWinner(px);
		int[] resultO = board.getCellsIfWinner(po);
		
		// Then
		assertNull(resultO);
		assertFalse(board.checkDraw());
		assertThat(resultX[0], is(cellId0));
		assertThat(resultX[1], is(cellId2));
		assertThat(resultX[2], is(cellId4));
	}
	
	@Test
	public void GivenBoardAndPositions_WhenAsummingWINOTypeAndFillingThisPositionsInTheBoard_ThenCheckBoardMethods() {
		// When
		Assume.assumeTrue(type == Type.WINO);
		board.enableAll();
			
		board.getCell(cellId0).value = px;
		board.getCell(cellId1).value = po;
		board.getCell(cellId2).value = px;
		board.getCell(cellId3).value = po;
		board.getCell(cellId4).value = px;
		board.getCell(cellId5).value = po;
		int[] resultX = board.getCellsIfWinner(px);
		int[] resultO = board.getCellsIfWinner(po);
		
		// Then
		assertNull(resultX);
		assertFalse(board.checkDraw());
		assertThat(resultO[0], is(cellId1));
		assertThat(resultO[1], is(cellId3));
		assertThat(resultO[2], is(cellId5));
	}
	
	@Test
	public void GivenBoardAndPositions_WhenAsummingDRAWypeAndFillingThisPositionsInTheBoard_ThenCheckBoardMethods() {
		// When
		Assume.assumeTrue(type == Type.DRAW);
		board.enableAll();
			
		board.getCell(cellId0).value = px;
		board.getCell(cellId1).value = po;
		board.getCell(cellId2).value = px;
		board.getCell(cellId3).value = po;
		board.getCell(cellId4).value = px;
		board.getCell(cellId5).value = po;
		board.getCell(cellId6).value = px;
		board.getCell(cellId7).value = po;
		board.getCell(cellId8).value = px;
		int[] resultX = board.getCellsIfWinner(px);
		int[] resultO = board.getCellsIfWinner(po);
		
		// Then
		assertNull(resultX);
		assertNull(resultO);
		assertTrue(board.checkDraw());
	}
}

