package es.upm.eacs.pruebas;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import org.hamcrest.Matchers;
import static org.hamcrest.CoreMatchers.hasItems;

import org.junit.Before;
import org.junit.Test;

import es.upm.eacs.pruebas.TicTacToeGame.Cell;
import es.upm.eacs.pruebas.TicTacToeGame.EventType;

public class TicTacToeGameTest {
	
	private TicTacToeGame game;

	@Before
	public void setup() {
		game = new TicTacToeGame();
	}

	@Test
	public void test() {

		Connection conn1 = mock(Connection.class);
		game.addConnection(conn1);
		Connection conn2 = mock(Connection.class);
		game.addConnection(conn2);
		
		Player px = new Player(0, "X", "PlayerX");
		game.addPlayer(px);
		Player po = new Player(0, "O", "PlayerO");
		game.addPlayer(po);
		
		reset(conn1);
		//verify(conn1).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(px, po)));

	}

}