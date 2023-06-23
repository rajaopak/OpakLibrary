package dev.rajaopak.opaklibrary.libs;

import dev.rajaopak.opaklibrary.OpakLibrary;

import java.util.logging.Level;

public class Debug {

  private static boolean ENABLED = false;

  public static void enable() {
    ENABLED = true;
  }

  public static void disable() {
    ENABLED = false;
  }

    public static void info(String message) {
        if (ENABLED) {
            OpakLibrary.getInstance().getLogger().info(message);
        }
    }

    public static void info(String message, boolean force) {
        if (ENABLED || force) {
            OpakLibrary.getInstance().getLogger().info(message);
        }
    }

    public static void warn(String message) {
        if (ENABLED) {
            OpakLibrary.getInstance().getLogger().log(Level.WARNING, message);
        }
    }

    public static void warn(String message, boolean force) {
        if (ENABLED || force) {
            OpakLibrary.getInstance().getLogger().log(Level.WARNING, message);
        }
    }

    public static void warn(String message, Throwable e) {
        if (ENABLED) {
            OpakLibrary.getInstance().getLogger().log(Level.WARNING, message, e);
        }
    }

    public static void warn(String message, Throwable e, boolean force) {
        if (ENABLED || force) {
            OpakLibrary.getInstance().getLogger().log(Level.WARNING, message, e);
        }
    }

    public static void error(String message) {
        if (ENABLED) {
            OpakLibrary.getInstance().getLogger().log(Level.SEVERE, message);
        }
    }

    public static void error(String message, boolean force) {
        if (ENABLED || force) {
            OpakLibrary.getInstance().getLogger().log(Level.SEVERE, message);
        }
    }

    public static void error(String message, Throwable e) {
        if (ENABLED) {
            OpakLibrary.getInstance().getLogger().log(Level.SEVERE, message, e);
        }
    }

    public static void error(String message, Throwable e, boolean force) {
        if (ENABLED || force) {
            OpakLibrary.getInstance().getLogger().log(Level.SEVERE, message, e);
        }
    }
}
