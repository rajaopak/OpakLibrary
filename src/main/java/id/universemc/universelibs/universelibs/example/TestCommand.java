package id.universemc.universelibs.universelibs.example;

import id.universemc.universelibs.universelibs.UniverseLib;
import id.universemc.universelibs.universelibs.commands.BaseCommand;
import id.universemc.universelibs.universelibs.inventory.SimpleInventory;
import id.universemc.universelibs.universelibs.libs.Common;
import id.universemc.universelibs.universelibs.libs.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class TestCommand extends BaseCommand {

    public TestCommand() {
        super(UniverseLib.getInstance(), "unltestcmd", Arrays.asList("unltc", "unltcmd"), "unilibs.testcmd2",
                sender -> {
                    SimpleInventory inv = new SimpleInventory(27, "Test GUI");
                    ItemBuilder iBuilder = new ItemBuilder(Material.GREEN_WOOL);
                    AtomicInteger amount = new AtomicInteger(1);
                    AtomicInteger slot = new AtomicInteger(13);
                    iBuilder.setName("&aHello!").setLore("&eClick me!");
                    inv.addClickHandler(event -> {
                        if (event.getSlot() == 13) {
                            if (amount.get() == 64) amount.set(0);
                            iBuilder.setAmount(amount.incrementAndGet());
                            /*Common.sendMessage(event.getWhoClicked(), "&aClicked!");*/
                            inv.setItem(slot.get(), iBuilder.build());
                        }
                    });
                    inv.setItem(13, iBuilder.build());
                    inv.setFilterItem(new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
                    if (sender instanceof Player) inv.open((Player) sender);
                    else Common.sendMessage(sender, "&cThis command can only be used by players!");
                },
                sender -> Common.sendMessage(sender, "&cYou don't have permission to use this command!"),
                sender -> Common.sendMessage(sender, "&cPlease specify correct Subcommand!"),
                new TestSubCommand());
        this.register();
    }

}
