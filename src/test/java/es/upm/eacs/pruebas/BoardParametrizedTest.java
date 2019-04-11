package es.upm.eacs.pruebas;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

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
		WIN, LOOSE
	};

	@Parameters(name = "{index}: Cells[{0}, {1}, {2}] - Player {3} - Result:{4}")
	public static Collection<Object[]> data() {
	Object[][] values = {   { 0, 1, 2, "X", Type.WIN }, 
							{ 3, 4, 5, "X", Type.WIN },
							{ 6, 7, 8, "X", Type.WIN },
							{ 0, 3, 6, "X", Type.WIN },
							{ 1, 4, 7, "X", Type.WIN },
							{ 2, 5, 8, "X", Type.WIN },
							{ 0, 4, 8, "X", Type.WIN },
							{ 6, 4, 2, "X", Type.WIN },
							{ 0, 2, 3, "X", Type.LOOSE },
							{ 3, 1, 4, "X", Type.LOOSE }
		};
		return Arrays.asList(values);
	}

	@Parameter(0)
	public int cellId0;
	@Parameter(1)
	public int cellId1;
	@Parameter(2)
	public int cellId2;
	@Parameter(3)
	public String playerName;
	@Parameter(4)
	public Type type;

	@Before
	public void setup() {
		board = new Board();
	}

	@Test
	public void GivenBoardAndPositions_WhenFillingThisPositionsInTheBoard_ThenGetCellsIfWinnerItReturnsNullOrNotNull() {
		// When
		board.enableAll();
		board.getCell(cellId0).value = playerName;
		board.getCell(cellId1).value = playerName;
		board.getCell(cellId2).value = playerName;
		int[] result = board.getCellsIfWinner(playerName);

		// Then
		if (type == Type.WIN) {
			assertNotNull("El jugador " + playerName + " debería de haber ganado!", result);
		} else {
			assertNull("El jugador " + playerName + " debería de haber ganado!", result);
		}
	}

	@Test
	public void GivenBoardAndPositions_WhenFillingThisPositionsInTheBoardWithPlayer_ThenGetCellsIfWinnerOfOtherPlayerReturnsNull() {

		// When
		board.enableAll();
		board.getCell(cellId0).value = playerName;
		board.getCell(cellId1).value = playerName;
		board.getCell(cellId2).value = playerName;
		int[] result = board.getCellsIfWinner("other");

		// Then
		assertNull("El jugador other no debería de haber ganado!", result);
	}

}

