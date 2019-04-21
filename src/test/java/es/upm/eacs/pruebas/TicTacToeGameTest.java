package es.upm.eacs.pruebas;

import static org.mockito.Mockito.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import es.upm.eacs.pruebas.TicTacToeGame.EventType;
import es.upm.eacs.pruebas.TicTacToeGame.WinnerValue;

public class TicTacToeGameTest {

	private TicTacToeGame game;
	private Connection conn1;
	private Connection conn2;
	private Player playerX;
	private Player playerO;

	@Before
	public void setup() {
		game = new TicTacToeGame();
		conn1 = mock(Connection.class);
		conn2 = mock(Connection.class);

		game.addConnection(conn1);
		game.addConnection(conn2);

		playerX = new Player(0, "X", "PlayerX");
		playerO = new Player(1, "O", "PlayerO");

		game.addPlayer(playerX);
		verify(conn1, times(1)).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItem(playerX)));
		verify(conn2, times(1)).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItem(playerX)));

		reset(conn1);
		reset(conn2);

		game.addPlayer(playerO);
		verify(conn1, times(1)).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(playerX, playerO)));
		verify(conn2, times(1)).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(playerX, playerO)));
	}

	private void fillBoard(int[] cells) {
		for (int i = 0; i < cells.length; i++) {
			game.mark(cells[i]);
		}
	}
	
	private void checkWinner(Player player, int[] winnerCells) {
		ArgumentCaptor<WinnerValue> argument = ArgumentCaptor.forClass(WinnerValue.class);
		verify(conn1).sendEvent(eq(EventType.GAME_OVER), argument.capture());
		verify(conn1).sendEvent(eq(EventType.GAME_OVER), argument.capture());
		WinnerValue winnerValueResult = argument.getValue();

		WinnerValue winnerValue = new WinnerValue();
		winnerValue.pos = winnerCells;
		winnerValue.player = player;

		assertThat(player.getLabel(), is(winnerValueResult.player.getLabel()));
		assertEquals(winnerCells[0], winnerValue.pos[0]);
		assertEquals(winnerCells[1], winnerValue.pos[1]);
		assertEquals(winnerCells[2], winnerValue.pos[2]);
	}

	@Test
	public void GivenTwoPlayers_WhenFillingTheCells_ThenFirstPlayerWins() {
		int[] cells = { 0, 1, 3, 4, 6 };
		fillBoard(cells);
		verify(conn1, times(3)).sendEvent(eq(EventType.SET_TURN), argThat(is(playerX)));
		verify(conn2, times(2)).sendEvent(eq(EventType.SET_TURN), argThat(is(playerO)));
		
		int[] winnerCells = {cells[0], cells[2], cells[4]};
		checkWinner(playerX, winnerCells);
	}

	@Test
	public void GivenTwoPlayers_WhenFillingTheCells_ThenSecondPlayerWins() {
		int[] cells = { 4, 0, 1, 3, 2, 6 };
		fillBoard(cells);
		verify(conn1, times(3)).sendEvent(eq(EventType.SET_TURN), argThat(is(playerX)));
		verify(conn2, times(3)).sendEvent(eq(EventType.SET_TURN), argThat(is(playerO)));
		
		int[] winnerCells = {cells[1], cells[3], cells[5]};
		checkWinner(playerO, winnerCells);
	}

	@Test
	public void GivenTwoPlayers_WhenFillingTheCells_ThenPlayersDraw() {
		int[] cells = { 0, 2, 1, 3, 4, 7, 5, 8, 6 };
		fillBoard(cells);
		verify(conn1, times(5)).sendEvent(eq(EventType.SET_TURN), argThat(is(playerX)));
		verify(conn2, times(4)).sendEvent(eq(EventType.SET_TURN), argThat(is(playerO)));
		verify(conn1, times(1)).sendEvent(eq(EventType.GAME_OVER), argThat(nullValue()));
		verify(conn2, times(1)).sendEvent(eq(EventType.GAME_OVER), argThat(nullValue()));
	}
}