package dev.rajaopak.OpakLibs.example;

import dev.rajaopak.OpakLibs.commands.BaseCommand;
import dev.rajaopak.OpakLibs.inventory.SimpleInventory;
import dev.rajaopak.OpakLibs.libs.Common;
import dev.rajaopak.OpakLibs.OpakLib;
import dev.rajaopak.OpakLibs.libs.ItemBuilder;
import dev.rajaopak.OpakLibs.libs.Task;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class TestCommand extends BaseCommand {

    public TestCommand() {
        super(OpakLib.getInstance(), "unltestcmd", Arrays.asList("unltc", "unltcmd"), "universelibs.testcmd2",
                sender -> {
                    SimpleInventory inv = new SimpleInventory(27, "Test GUI");
                    ItemBuilder iBuilder = new ItemBuilder(Material.GREEN_WOOL);
                    AtomicInteger amount = new AtomicInteger(1);
                    iBuilder.setName("&aHello!").setLore("&eClick me!").setGlowing();
                    inv.addClickHandler(event -> {
                        if (event.getSlot() == 13) {
                            if (amount.get() == 64) amount.set(0);
                            iBuilder.setAmount(amount.incrementAndGet());
                            /*Common.sendMessage(event.getWhoClicked(), "&aClicked!");*/
                            inv.setItem(13, iBuilder.build());
                        }

                        if (event.getSlot() == 26)
                            inv.close(event.getWhoClicked());
                    });
                    inv.addOpenHandler(event -> {
                        Task.asyncLater(100L, () -> {
                            iBuilder.setLore(Common.color(Arrays.asList("&aGIGI", "&aGIGI", "&aGIGI", "&aGIGI", "&aGIGI", "&aGIGI", "&aGIGI", "&aGIGI")));
                            inv.setItem(13, iBuilder.build());
                        });
                    });
                    inv.setItem(11,
                            ItemBuilder.from(Material.NETHERITE_SWORD)
                                    .setName("&aAnjay aku bohong")
                                    .setLore("&eKamu bohong", "&eSusu yang bohong")
                                    .setGlowing()
                                    .setUnbreakable()
                                    .build()
                    );
                    inv.setItem(13, iBuilder.build());
                    inv.setItem(15,
                            ItemBuilder.from(Material.NETHERITE_PICKAXE)
                                    .setName("&aAnjay aku bohong")
                                    .setLore("&eKamu bohong", "&eSusu yang bohong")
                                    .setGlowing()
                                    .setUnbreakable()
                                    .build()
                    );
                    inv.setItem(26,
                            ItemBuilder.from(Material.BARRIER)
                                    .setName("&cClose")
                                    .setLore("&bGIGI", "&bGIGI", "&bGIGI")
                                    .setGlowing()
                                    .setUnbreakable()
                                    .build()
                    );
                    inv.setFilterItem(new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
                    if (sender instanceof Player) inv.open((Player) sender);
                    else Common.sendMessage(sender, "&cThis command can only be used by players!");
                },
                sender -> Common.sendMessage(sender, "&cYou don't have permission to use this command!"),
                sender -> Common.sendMessage(sender, "&cPlease specify correct Subcommand!", true),
                null, new TestSubCommand());
        this.register();
    }

}
