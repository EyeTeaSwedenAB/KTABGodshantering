package com.peter.exceptions;

import java.io.File;

/**
 * Created by andreajacobsson on 2016-09-23.
 */
public class WrongFilenameFormatException extends Exception {

    File wrongFile;

    public WrongFilenameFormatException(String cause) {
        super(cause);
    }

    public File getWrongFile() {
        return wrongFile;
    }

    public void setWrongFile(File wrongFile) {
        this.wrongFile = wrongFile;
    }
}
