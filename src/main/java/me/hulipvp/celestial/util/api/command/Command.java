package me.hulipvp.celestial.util.api.command;

import me.hulipvp.celestial.factions.FactionMember;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Command Framework - Command <br>
 * The command annotation used to designate methods as commands. All methods
 * should have a single CommandArgs argument
 *
 * @author minnymin3
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    /**
     * The name of the command. If it is a sub command then its values would be
     * separated by periods. ie. a command that would be a subcommand of test
     * would be 'test.subcommandname'
     *
     * @return
     */
    String name();

    /**
     * Gets the required permission of the command
     *
     * @return
     */
    String permission() default "";

    /**
     * The message sent to the player when they do not have permission to
     * execute it
     *
     * @return
     */
    String noPerm() default "You do not have permission to perform that action";

    /**
     * A list of alternate names that the command is executed under. See
     * name() for details on how names work
     *
     * @return
     */
    String[] aliases() default {};

    /**
     * The description that will appear in /help of the command
     *
     * @return
     */
    String description() default "";

    /**
     * The usage that will appear in /help (commandname)
     *
     * @return
     */
    String usage() default "";

    /**
     * Whether or not the command is available to players only
     *
     * @return
     */
    boolean playerOnly() default false;

    /**
     * Whether or not this command requires a Faction or not
     *
     * @return
     */
    boolean requiresFaction() default false;

    /**
     * Whether or not this command has a required rank in order to perform
     * this command
     * <br>
     * Note, requiresFaction() must be true in order for this to be checked
     *
     * @return
     */
    FactionMember.Rank requiredRank() default FactionMember.Rank.MEMBER;
}
