package me.hulipvp.celestial.listeners;

import me.hulipvp.celestial.profile.Profile;
import me.hulipvp.celestial.profile.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(final AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        final Profile profile = ProfileManager.get().get(player);
        final String message = event.getMessage();

        switch(profile.getChatType()) {
            case PUBLIC:
                sendGlobalMessage(player, message);
                break;
            case ALLY:
                break;
            case FACTION:
                break;
        }
    }

    private void sendGlobalMessage(final Player player, final String message) {
        if(player.hasPermission("celestial.staff"))
            Bukkit.broadcastMessage(message);
        else
            ProfileManager.get().sendAll(message);
    }
}
