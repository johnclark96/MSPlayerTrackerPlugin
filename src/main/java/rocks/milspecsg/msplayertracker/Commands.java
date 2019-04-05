package rocks.milspecsg.msplayertracker;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.*;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;


import java.util.List;
import java.util.function.Supplier;

public class Commands   implements Supplier<CommandCallable>
    {

        List<String> worlds;
        List<String> msgWorlds = listWorlds();

        private List<String> listWorlds()
        {
            for(World world : Sponge.getServer().getWorlds())
            {
                if(!worlds.contains(world.getName())) {
                    worlds.add(world.getName());
                }
            }
            return worlds;
        }


        private MSPlayerTracker plugin;

        private final CommandCallable worldListCommand;


        public static String tag = TextColors.BLUE + "Online Player List";
        public static Text linetag = Text.of(tag);
        public static Text line = Text.of(TextColors.GOLD+"==============================================");

        public Commands(MSPlayerTracker plugin) {

            this.plugin = plugin;

            this.worldListCommand = CommandSpec.builder()
                    .description(Text.of("usage /pworld"))
                    .arguments(GenericArguments.onlyOne(GenericArguments.none()))
                    .executor((CommandSource src, CommandContext args) ->
                    {
                       //Entity player = (Entity) src;
                        if (src.hasPermission("playertracker.admin")) {

                            src.sendMessage(Text.of(tag + line + msgWorlds));
                            plugin.logger.debug("Error");
                            return CommandResult.success();
                        } else {
                            src.sendMessage(Text.of("You do not have permission to use that command!"));
                            return CommandResult.success();
                        }
                    }).build();
        }

    public void init()
    {
        CommandManager commandManager = Sponge.getCommandManager();
        commandManager.register(this.plugin, this.get(), "pworld", "pw");
    }
    @Override
    public CommandCallable get()
    {
        return CommandSpec.builder()
                .child(this.worldListCommand, "list")
                .build();
    }
}
