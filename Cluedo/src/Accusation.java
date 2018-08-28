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

public class Accusation extends JDialog implements ActionListener {
  /**
	 * 
	 */
	ButtonGroup weaponsGroup;
	ButtonGroup peopleGroup;
	private static final long serialVersionUID = 1L;
public Accusation(JFrame parent, String title) {
    super(parent, title, true);
    JPanel optionsPane = new JPanel();
    //Accusation Content
    weaponsGroup = new ButtonGroup();
    JRadioButton daggerRadio = new JRadioButton("Dagger");
    weaponsGroup.add(daggerRadio);
    JRadioButton revolverRadio = new JRadioButton("Revolver");
    weaponsGroup.add(revolverRadio);
    JRadioButton leadpipeRadio = new JRadioButton("Lead Pipe");
    weaponsGroup.add(leadpipeRadio);
    JRadioButton ropeRadio = new JRadioButton("Rope");
    weaponsGroup.add(ropeRadio);
    JRadioButton spannerRadio = new JRadioButton("Spanner");
    weaponsGroup.add(spannerRadio);
    optionsPane.add(daggerRadio);
    optionsPane.add(revolverRadio);
    optionsPane.add(leadpipeRadio);
    optionsPane.add(ropeRadio);
    optionsPane.add(spannerRadio);
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

  public String[] getAccusations() {
	  String[] s = new String[2];
	  s[0] = getWeapon();
	  s[1] = getPerson();
	  return s;
  }
  
  private String getWeapon() {
	for (Enumeration<AbstractButton> buttons = weaponsGroup.getElements(); buttons.hasMoreElements();) {
          AbstractButton button = buttons.nextElement();

          if (button.isSelected()) {
              return button.getText();
          }
      }
	return null;
  }
  
  private String getPerson() {
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