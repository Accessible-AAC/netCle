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
package lyricom.netCleConfig.solutions;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.ResourceBundle;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import lyricom.netCleConfig.ui.ScreenInfo;

/**
 *
 * @author Andrew
 */
public class Description extends JDialog {

    public Description(JDialog parent, String txt) {
       super(parent, false);
        setUndecorated(true);
        rootPane.setBorder(new LineBorder(Color.BLACK, 2));
        setLayout(new BorderLayout());
        
        add(showText(txt), BorderLayout.CENTER);
//        add(closeBtn(), BorderLayout.SOUTH);
        
        pack();
        // Center on screen
//        Dimension dim = new Dimension(200, 200);
//        setMinimumSize(dim);
        Point center = ScreenInfo.getCenter();
//        setLocation(center.x-dim.width/2, center.y-dim.height/2);
        Point loc = MouseInfo.getPointerInfo().getLocation();
        loc.x += 150;
        setLocation(loc);
        
        setVisible(true);
    }
    
    private JComponent showText(String txt) {
        JEditorPane pane = new JEditorPane();
        pane.setContentType("text/html");
        pane.setText(txt);
        pane.setBorder(new EmptyBorder(10,10,10,10));
        return pane;
    }
    
    private JPanel closeBtn() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton b = new JButton(SRes.getStr("SW_CLOSE_BTN"));
        final Description thisPanel = this;
        b.addActionListener(e -> {
            thisPanel.dispose();
        });
        p.add(b);
        return p;
    }
}
    
