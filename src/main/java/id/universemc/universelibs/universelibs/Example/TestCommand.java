package id.universemc.universelibs.universelibs.Example;

import id.universemc.universelibs.universelibs.UniverseLib;
import id.universemc.universelibs.universelibs.commands.SpigotCommand;

import java.util.Arrays;

public class TestCommand extends SpigotCommand {

    public TestCommand() {
        super(UniverseLib.getInstance(), "test", Arrays.asList("test123", "t"), "perm.test",
                sender -> sender.sendMessage("No Arguments"),
                sender -> sender.sendMessage("No Permission"),
                sender -> sender.sendMessage("No SubCommand"),
                new TestSubCommand());
    }

}