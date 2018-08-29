import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
/**
 *  Provides a dialog box for selecting a character.
*/
public class PlayerSelect extends JDialog implements ActionListener {
 
	private static final long serialVersionUID = 1L;
/**
	 * 
	 */
	ButtonGroup peopleGroup;
public PlayerSelect(JFrame parent, String title) {
    super(parent, title, true);
    JPanel optionsPane = new JPanel();
    peopleGroup = new ButtonGroup();
    JRadioButton greenRadio = new JRadioButton("Mr Green");
    peopleGroup.add(greenRadio);
    JRadioButton plumRadio = new JRadioButton("Professor Plum");
    peopleGroup.add(plumRadio);
    JRadioButton whiteRadio = new JRadioButton("Mrs White");
    peopleGroup.add(whiteRadio);
    JRadioButton peaRadio = new JRadioButton("Mrs Peacock");
    peopleGroup.add(peaRadio);
    JRadioButton scarRadio = new JRadioButton("Miss Scarlet");
    peopleGroup.add(scarRadio);
    JRadioButton mustRadio = new JRadioButton("Colonel Mustard");
    peopleGroup.add(mustRadio);
    optionsPane.add(greenRadio);
    optionsPane.add(plumRadio);
    optionsPane.add(whiteRadio);
    optionsPane.add(peaRadio);
    optionsPane.add(scarRadio);
    optionsPane.add(mustRadio);
    getContentPane().add(optionsPane);
    JPanel buttonPane = new JPanel();
    JButton button = new JButton("OK"); 
    buttonPane.add(button); 
    button.addActionListener(this);
    getContentPane().add(buttonPane, BorderLayout.SOUTH);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    pack(); 
    setVisible(true);
  }
  
  public String getPerson() {
	  for (Enumeration<AbstractButton> buttons = peopleGroup.getElements(); buttons.hasMoreElements();) {
          AbstractButton button = buttons.nextElement();

          if (button.isSelected()) {
              return button.getText();
          }
      }
	return null;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
	setVisible(false); 
    dispose();
  }
}