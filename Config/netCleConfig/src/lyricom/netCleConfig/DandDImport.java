package lyricom.netCleConfig;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.TransferHandler;
import lyricom.netCleConfig.model.IOError;

/**
 *
 * @author Andrew
 */
public class DandDImport extends TransferHandler {
    private static final ResourceBundle RES = ResourceBundle.getBundle("strings");

    private QuickLoad parent;
    public DandDImport(QuickLoad parent) {
        this.parent = parent;
    }
    
    @Override
    public boolean canImport(TransferSupport supp) {
        // Check for String flavor or file list.
        if (supp.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return true;
        }
        if (supp.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            return true;
        }
        return false;
        // Fetch the drop location
        //DropLocation loc = supp.getDropLocation();

        // Return whether we accept the location
        //return shouldAcceptDropLocation(loc);
    }

    @Override
    public boolean importData(TransferSupport supp) {
        if (!canImport(supp)) {
            return false;
        }
        
        try {
            if (supp.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                // Fetch the Transferable and its data
                Transferable t = supp.getTransferable();
                String data = (String) t.getTransferData(DataFlavor.stringFlavor);

                parent.loadFromString(data);

                return false;   // If you return true it may be treated as move 
                                // and original data will be deleted.

            } else if (supp.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                Transferable t = supp.getTransferable();
                ArrayList<File> theList = new ArrayList<>();

                theList.addAll((Collection<? extends File>) t.getTransferData(DataFlavor.javaFileListFlavor));

                if (!theList.isEmpty()) {
                    parent.loadFromFile(theList.get(0));
                }
                return false;   // If you return true it may be treated as move 
                                // and original data will be deleted.
            } 
            
        } catch (UnsupportedFlavorException | IOException ex) {
            parent.setText("Drop failed.");            
         }
        return false;
    }
    
    @Override
    public int getSourceActions(JComponent c) {
        return COPY;
    }

    // * routines for drag out - not used.
    /*
    @Override
    protected Transferable createTransferable(JComponent c) {
        return new StringSelection("Hello World.");
    }

    @Override
    protected void exportDone(JComponent c, Transferable t, int action) {
        // No action needed.
    }
    */
}
