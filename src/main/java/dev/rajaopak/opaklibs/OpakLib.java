package dev.rajaopak.opaklibs;

import dev.rajaopak.opaklibs.example.ConfigFile;
import dev.rajaopak.opaklibs.example.ConfigValue;
import dev.rajaopak.opaklibs.example.MainCommand;
import dev.rajaopak.opaklibs.example.TestCommand;
import dev.rajaopak.opaklibs.libs.Common;
import org.bukkit.plugin.java.JavaPlugin;

public final class OpakLib extends JavaPlugin {

    private static OpakLib INSTANCE;

    public static OpakLib getInstance() {
        return INSTANCE;
    }

    public long uptime;

    @Override
    public void onEnable() {
        // Plugin startup logic
        uptime = System.currentTimeMillis();
        INSTANCE = this;
        OpakLibs.init(this);
        new MainCommand(this);
        new TestCommand();
        ConfigFile.init();
        ConfigValue.init(this.getConfig());
        Common.setPrefix(ConfigValue.PREFIX);
        /*new BaseCommand(this, "test", Arrays.asList("test123", "t"), "perm.test",
                sender -> sender.sendMessage("No Arguments"),
                sender -> sender.sendMessage("No Permission"),
                sender -> sender.sendMessage("No SubCommand"),
                new TestSubCommand()).register();*/
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public long getUpTime() {
        return (System.currentTimeMillis() - uptime) / 1000;
    }
}
