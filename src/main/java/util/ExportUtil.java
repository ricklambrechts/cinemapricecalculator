package util;

import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ExportUtil {

    static final Logger logger = LogManager.getLogManager().getLogger(ExportUtil.class.getName());

    public static void CloseFileWriterQuietly( FileWriter fileWriter ) {
        try {
            if (fileWriter != null) {
                fileWriter.close();
            }
        } catch( Exception ex ) {
            logger.log(Level.WARNING, "Exception during Resource.close()", ex);
        }
    }

}
