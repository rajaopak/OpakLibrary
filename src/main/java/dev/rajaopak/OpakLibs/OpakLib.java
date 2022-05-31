package dev.rajaopak.OpakLibs;

import dev.rajaopak.OpakLibs.example.ConfigFile;
import dev.rajaopak.OpakLibs.example.ConfigValue;
import dev.rajaopak.OpakLibs.example.MainCommand;
import dev.rajaopak.OpakLibs.example.TestCommand;
import dev.rajaopak.OpakLibs.libs.Common;
import org.bukkit.plugin.java.JavaPlugin;

public final class OpakLib extends JavaPlugin {

    private static OpakLib INSTANCE;

    public static OpakLib getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        INSTANCE = this;
        OpakLibs.init(this);
        new MainCommand(this);
        new TestCommand();
        ConfigFile.init();
        ConfigValue.init(this.getConfig());
        Common.setPrefix(ConfigValue.PREFIX);
        /*new SpigotCommand(this, "test", Arrays.asList("test123", "t"), "perm.test",
                sender -> sender.sendMessage("No Arguments"),
                sender -> sender.sendMessage("No Permission"),
                sender -> sender.sendMessage("No SubCommand"),
                new TestSubCommand()).register();*/
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
