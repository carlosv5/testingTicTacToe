package es.upm.eacs.pruebas;

import static org.mockito.Mockito.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import es.upm.eacs.pruebas.TicTacToeGame.EventType;
import es.upm.eacs.pruebas.TicTacToeGame.WinnerValue;

public class TicTacToeGameTest {

	private TicTacToeGame game;
	private Connection conn1;
	private Connection conn2;
	private Player px;
	private Player po;

	@Before
	public void setup() {
		game = new TicTacToeGame();
		conn1 = mock(Connection.class);
		conn2 = mock(Connection.class);

		game.addConnection(conn1);
		game.addConnection(conn2);

		px = new Player(0, "X", "PlayerX");
		po = new Player(1, "O", "PlayerO");

		game.addPlayer(px);
		verify(conn1, times(1)).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItem(px)));
		verify(conn2, times(1)).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItem(px)));

		reset(conn1);
		reset(conn2);

		game.addPlayer(po);
		verify(conn1, times(1)).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(px, po)));
		verify(conn2, times(1)).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(px, po)));
	}

	private void fillBoard(int[] cells) {
		for (int i = 0; i < cells.length; i++) {
			game.mark(cells[i]);
		}
	}

	@Test
	public void testWinX() {
		int[] cells = { 0, 1, 3, 4, 6 };
		fillBoard(cells);
		verify(conn1, times(3)).sendEvent(eq(EventType.SET_TURN), argThat(is(px)));
		verify(conn1, times(2)).sendEvent(eq(EventType.SET_TURN), argThat(is(po)));

		ArgumentCaptor<WinnerValue> argument = ArgumentCaptor.forClass(WinnerValue.class);
		verify(conn1).sendEvent(eq(EventType.GAME_OVER), argument.capture());
		verify(conn1).sendEvent(eq(EventType.GAME_OVER), argument.capture());
		WinnerValue winnerValueResult = argument.getValue();

		WinnerValue winnerValue = new WinnerValue();
		int[] array = { 0, 3, 6 };
		winnerValue.pos = array;
		winnerValue.player = px;

		assertThat(px.getLabel(), is(winnerValueResult.player.getLabel()));
		// assertArrayEquals(array, winnerValueResult );
	}

	@Test
	public void testWinO() {
		int[] cells = { 4, 0, 1, 3, 2, 6 };
		fillBoard(cells);
		verify(conn1, times(3)).sendEvent(eq(EventType.SET_TURN), argThat(is(px)));
		verify(conn1, times(3)).sendEvent(eq(EventType.SET_TURN), argThat(is(po)));

		ArgumentCaptor<WinnerValue> argument = ArgumentCaptor.forClass(WinnerValue.class);
		verify(conn1).sendEvent(eq(EventType.GAME_OVER), argument.capture());
		verify(conn1).sendEvent(eq(EventType.GAME_OVER), argument.capture());
		WinnerValue winnerValueResult = argument.getValue();

		WinnerValue winnerValue = new WinnerValue();
		int[] array = { 0, 3, 6 };
		winnerValue.pos = array;
		winnerValue.player = px;

		assertThat(po.getLabel(), is(winnerValueResult.player.getLabel()));
		// assertArrayEquals(array, winnerValueResult );
	}

	@Test
	public void testDraw() {
		int[] cells = { 0, 2, 1, 3, 4, 7, 5, 8, 6 };
		fillBoard(cells);
		verify(conn1, times(5)).sendEvent(eq(EventType.SET_TURN), argThat(is(px)));
		verify(conn2, times(4)).sendEvent(eq(EventType.SET_TURN), argThat(is(po)));
		verify(conn2, times(1)).sendEvent(eq(EventType.GAME_OVER), argThat(nullValue()));
	}
}