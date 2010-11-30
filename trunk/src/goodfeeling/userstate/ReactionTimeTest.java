package goodfeeling.userstate;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.util.Date;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Test mierzący czas reakcji użytkownika na bodźce.
 * Test ten należy do zestawu testów mierzących sprawność intelektualną
 * użytkownika.
 */
public class ReactionTimeTest extends JPanel {

	public ReactionTimeTest() {
		addListeners();
		reset();
	}

	private void reset() {
		lastBlink = null;
		lastClick = null;
		testEnded = false;
		text = "";
		setBackground(Color.BLACK);
		setForeground(Color.WHITE);
		repaint();
		blinker = new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(2000 + new Random().nextInt(1000));
				} catch (InterruptedException e) {
				}
				if (!testEnded) {
					blink();
				}
			}
		});
		blinker.start();
	}

	private void addListeners() {
		MouseListener mouseListener = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (!testEnded) {
					if (lastBlink == null) {
						tooSoon();
					} else {
						reaction();
					}
				} else {
					reset();
				}
			}
		};
		addMouseListener(mouseListener);
	}

	private void blink() {
		setForeground(Color.RED);
		lastBlink = new Date();
	}

	private void reaction() {
		setForeground(Color.WHITE);
		lastClick = new Date();
		long reactionTime = lastClick.getTime() - lastBlink.getTime();
		text = "Czas reakcji: " + reactionTime + " ms.";
		testEnded = true;
	}

	private void tooSoon() {
		setForeground(Color.WHITE);
		text = "Skucha!";
		testEnded = true;
		blinker.interrupt();
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		clear(g);
		Graphics2D g2d = (Graphics2D)g;
		if (!testEnded) {
			g2d.fill(circle);
		} else {
			g2d.drawString(text, 10, 25);
		}
	}

	private void clear(Graphics g) {
		super.paintComponent(g);
	}

	public static void main(String[] args) {
		final ReactionTimeTest test = new ReactionTimeTest();
		Runnable runner = new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Test czasu reakcji");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(450, 500);
				frame.setContentPane(test);
				frame.setVisible(true);
			}
		};
		EventQueue.invokeLater(runner);
	}

	private final Ellipse2D.Double circle =
		new Ellipse2D.Double(170, 170, 100, 100);
	private Date lastBlink;
	private Date lastClick;
	private boolean testEnded;
	private String text = "";
	private Thread blinker;
}
