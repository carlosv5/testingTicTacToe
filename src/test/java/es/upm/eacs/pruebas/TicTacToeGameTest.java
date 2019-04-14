package es.upm.eacs.pruebas;

import static org.mockito.Mockito.*;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

import org.junit.Before;
import org.junit.Test;

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
		Connection conn2 = mock(Connection.class);
		game.addConnection(conn1);
		game.addConnection(conn2);
		Player px = new Player(0, "X", "PlayerX");
		Player po = new Player(0, "O", "PlayerO");
		game.addPlayer(px);
		//game.addPlayer(po);
		verify(conn1).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(px)));
		
	}

}