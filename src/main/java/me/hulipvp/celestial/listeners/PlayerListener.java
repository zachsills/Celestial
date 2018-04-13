package me.hulipvp.celestial.listeners;

import me.hulipvp.celestial.profile.Profile;
import me.hulipvp.celestial.profile.ProfileManager;
import me.hulipvp.celestial.profile.data.type.StatisticType;
import me.hulipvp.celestial.util.Locale;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onLogin(final AsyncPlayerPreLoginEvent event) {
        final Profile profile = new Profile(event.getUniqueId());

        if(profile.getDeathban() != 0L && profile.getDeathban() > System.currentTimeMillis()) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, Locale.DEATHBAN_MESSAGE.toString());
        } else {
            profile.setDeathban(0L);
        }
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        handleLogout(event.getPlayer());
    }

    @EventHandler
    public void onKick(final PlayerKickEvent event) {
        handleLogout(event.getPlayer());
    }

    private void handleLogout(final Player player) {
        final Profile profile = ProfileManager.get().remove(player.getUniqueId());
        if(profile == null)
            return;

        profile.save();
    }

    @EventHandler
    public void onBreak(final BlockBreakEvent event) {
        if(!event.getBlock().getType().name().split("_")[1].equals("ORE"))
            return;

        final Material material = event.getBlock().getType();
        final StatisticType type = StatisticType.getByMaterial(material);

        final Profile profile = ProfileManager.get().get(event.getPlayer());
        profile.getStatistic(type).increment();
    }
}
