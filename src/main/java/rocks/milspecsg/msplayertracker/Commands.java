package rocks.milspecsg.msplayertracker;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.*;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;
import java.util.function.Supplier;

public class Commands   implements Supplier<CommandCallable>
    {

        // GETS ALL WORLDS
        public String[] getLoadedWorldNames() {
            return Sponge.getServer().getWorlds()
                    .stream()
                    .map(World::getName)
                    .toArray(String[]::new);
        }

        // OUTPUTS WORLDS INTO A STRING AND SEPERATES BY LINE
        String[] worldNames = getLoadedWorldNames();
        String worldList = String.join("\n", worldNames);

        private MSPlayerTracker plugin;

        private final CommandCallable worldListCommand;
        private final CommandCallable worldWorldCommand;

        private static String listtag = TextColors.BLUE + "PlayerTracker World List";
        private static Text line = Text.of(TextColors.GOLD+"==============================================");

        public Commands(MSPlayerTracker plugin) {

            this.plugin = plugin;



            this.worldListCommand = CommandSpec.builder()
                    .description(Text.of("usage /pworld"))
                    .arguments(GenericArguments.onlyOne(GenericArguments.none()))
                    .executor((CommandSource src, CommandContext args) ->
                    {
                        if (src.hasPermission("playertracker.admin")) {

                            src.sendMessage(Text.of(listtag));
                            src.sendMessage(Text.of(line));
                            src.sendMessage(Text.of(worldList));
                            return CommandResult.success();
                        } else {
                            src.sendMessage(Text.of("You do not have permission to use that command!"));
                            return CommandResult.success();
                        }
                    }).build();

            this.worldWorldCommand = CommandSpec.builder()
                    .description(Text.of("usage /pworld world"))
                    .arguments(GenericArguments.onlyOne(GenericArguments.none()))
                    .executor((CommandSource src, CommandContext args) ->
                    {
                        if (src.hasPermission("playertracker.admin")) {
                            for (Player onlinePlayer : Sponge.getServer().getOnlinePlayers()){
                                src.sendMessage(Text.of(onlinePlayer.getName()));
                            }
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
                .child(this.worldWorldCommand, "world")
                .build();
    }
}
