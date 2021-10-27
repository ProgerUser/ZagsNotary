package ru.psv.mj.www.pl.jsolve;

import java.io.Closeable;
import java.io.IOException;

public final class Resources {

    public static void closeStream(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            throw new ResourceException(e);
        }
    }
}