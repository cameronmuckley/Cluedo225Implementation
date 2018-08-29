import java.awt.BorderLayout;import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;

public abstract class GUI {
	/**
	 * The GUI class, which is extended and used by the main game class.	
	 * This is a chopped up version of supplied GUI code from another course.
	 *
	 */
	public enum Direction {
		UP, DOWN, RIGHT, LEFT
	};
	
// abstract methods implemented by the main class.
	protected abstract void redraw(Graphics g);

	protected abstract void onClick(MouseEvent e);

	protected abstract void makeMove(Board.Direction d);
	
	protected abstract void onInput();
	
	protected abstract void rollDice();

	public JTextArea getTextOutputArea() {
		return textOutputArea;
	}
	
	public JTextField getSearchBox() {
		return search;
	}

	public Dimension getDrawingAreaDimension() {
		return drawing.getSize();
	}

	public void redraw() {
		frame.repaint();
	}

	private static final int DEFAULT_DRAWING_HEIGHT = 400;
	private static final int DEFAULT_DRAWING_WIDTH = 400;
	private static final int TEXT_OUTPUT_ROWS = 5;

	private JFrame frame;
	
	private JMenuBar menubar;
	private JPanel controls;
	private JComponent drawing; // we customise this to make it a drawing pane.
	private JTextArea textOutputArea;
	private JTextField search;
	
	public GUI() {
		initialise();
	}

	@SuppressWarnings("serial")
	private void initialise() {
		
		JButton quit = new JButton("Quit");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				System.exit(0); // cleanly end the program.
			}
		});

		JButton west = new JButton("\u2190");
		west.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				makeMove(Board.Direction.UP);
				redraw();
			}
		});

		JButton east = new JButton("\u2192");
		east.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				makeMove(Board.Direction.DOWN);
				redraw();
			}
		});

		JButton north = new JButton("\u2191");
		north.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				makeMove(Board.Direction.LEFT);
				redraw();
			}
		});

		JButton south = new JButton("\u2193");
		south.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				makeMove(Board.Direction.RIGHT);
				redraw();
			}
		});
			
		search = new JTextField(15);
		search.setMaximumSize(new Dimension(0, 25));
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onInput();
				redraw();
			}
		});

		controls = new JPanel();
		controls.setLayout(new BoxLayout(controls, BoxLayout.LINE_AXIS));

		// make an empty border so the components aren't right up against the
		// frame edge.
		Border edge = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		controls.setBorder(edge);

		JPanel loadquit = new JPanel();
		loadquit.setLayout(new GridLayout(2, 1));
		// manually set a fixed size for the panel containing the load and quit
		// buttons (doesn't change with window resize).
		loadquit.setMaximumSize(new Dimension(50, 100));
		loadquit.add(quit);
		controls.add(loadquit);
		// rigid areas are invisible components that can be used to space
		// components out.
		controls.add(Box.createRigidArea(new Dimension(15, 0)));

		JPanel navigation = new JPanel();
		navigation.setMaximumSize(new Dimension(150, 60));
		navigation.setLayout(new GridLayout(2, 3));
		navigation.add(north);
		navigation.add(west);
		navigation.add(south);
		navigation.add(east);
		controls.add(navigation);
		controls.add(search);
		controls.add(Box.createRigidArea(new Dimension(15, 0)));
		// glue is another invisible component that grows to take up all the
		// space it can on resize.
		controls.add(Box.createHorizontalGlue());
		
		/*
		 * Dice Component
		 */		
		JButton diceRoll = new JButton("Roll");
		diceRoll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				System.out.println("Reached");
				rollDice();
			}
		});
		controls.add(diceRoll);
		/*
		 * then make the drawing canvas, which is really just a boring old
		 * JComponent with the paintComponent method overridden to paint
		 * whatever we like. this is the easiest way to do drawing.
		 */

		drawing = new JComponent() {
			protected void paintComponent(Graphics g) {
				redraw(g);
			}
		};
		drawing.setPreferredSize(new Dimension(DEFAULT_DRAWING_WIDTH,
				DEFAULT_DRAWING_HEIGHT));
		// this prevents a bug where the component won't be
		// drawn until it is resized.
		drawing.setVisible(true);

		drawing.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				onClick(e);
				redraw();
			}
		});

		drawing.addMouseWheelListener(new MouseAdapter() {
			public void mouseWheelMoved(MouseWheelEvent e) {
			}
		});

		/*
		 * then make the JTextArea that goes down the bottom. we put this in a
		 * JScrollPane to get scroll bars when necessary.
		 */

		textOutputArea = new JTextArea(TEXT_OUTPUT_ROWS, 0);
		textOutputArea.setLineWrap(true);
		textOutputArea.setWrapStyleWord(true); // pretty line wrap.
		textOutputArea.setEditable(false);
		JScrollPane scroll = new JScrollPane(textOutputArea);
		// these two lines make the JScrollPane always scroll to the bottom when
		// text is appended to the JTextArea.
		DefaultCaret caret = (DefaultCaret) textOutputArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		split.setDividerSize(5); // make the selectable area smaller
		split.setContinuousLayout(true); // make the panes resize nicely
		split.setResizeWeight(1); // always give extra space to drawings
		// JSplitPanes have a default border that makes an ugly row of pixels at
		// the top, remove it.
		split.setBorder(BorderFactory.createEmptyBorder());
		split.setTopComponent(drawing);
		split.setBottomComponent(scroll);

		frame = new JFrame("Cluedo");
		// this makes the program actually quit when the frame's close button is
		// pressed.
		//Placeholder, showing the potential use of a menu
		menubar = new JMenuBar();
				
		JMenu menuFile = new JMenu("File");
		JMenuItem menuOpen = new JMenuItem("Open");
		JMenuItem menuSave = new JMenuItem("Save");
		JMenuItem menuSaveAs = new JMenuItem("Save As");
		
		JMenu menuGame = new JMenu("Game");

		menuFile.add(menuOpen);
		menuFile.add(menuSave);
		menuFile.add(menuSaveAs);
		
		menubar.add(menuFile);
		menubar.add(menuGame);
		
		frame.setJMenuBar(menubar);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(controls, BorderLayout.NORTH);
		frame.add(split, BorderLayout.CENTER);

		// always do these two things last, in this order.
		frame.pack();
		frame.setVisible(true);
	}
}

