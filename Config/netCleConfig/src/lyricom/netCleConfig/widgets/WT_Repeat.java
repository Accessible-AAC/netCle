/* * * * * * * * * * * * * * * * * * * * * * * * * * * * 
    Copyright (C) 2019 Andrew Hodgson

    This file is part of the netClé Configuration software.

    netClé Configuration software is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    netClé Configuration software is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this netClé configuration software.  
    If not, see <https://www.gnu.org/licenses/>.   
 * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package lyricom.netCleConfig.widgets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import lyricom.netCleConfig.model.Trigger;

/**
 *
 * @author Andrew
 */
public class WT_Repeat extends W_Base implements ActionListener {

    private final Trigger theTrigger;
    private final JCheckBox repeatBox;
    
    public WT_Repeat(String label, Trigger t) {
        super();
        theTrigger = t;
        repeatBox = new JCheckBox();
        add(new JLabel(label));
        add(repeatBox);        
        update();
        repeatBox.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        theTrigger.setRepeat(repeatBox.isSelected());
/*        if (theTrigger.isRepeat()) {
            System.out.println("Repeat is TRUE");
        } else {
            System.out.println("Repeat is FALSE");
        } */
    }
    
    @Override
    public void update() {
        repeatBox.setSelected(theTrigger.isRepeat());        
    }
}
