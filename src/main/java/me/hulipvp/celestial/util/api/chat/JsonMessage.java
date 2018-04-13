package me.hulipvp.celestial.util.api.chat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author JustisR
 * 
 */

public class JsonMessage {

	private String msg;

	/**
	 * Create a new json message!
	 */
	public JsonMessage() {
		msg = "[{\"text\":\"\",\"extra\":[{\"text\": \"\"}";
	}

	private static Class<?> getNmsClass(final String nmsClassName) throws ClassNotFoundException {
		return Class.forName("net.minecraft.server." + getServerVersion() + "." + nmsClassName);
	}

	private static String getServerVersion() {
		return Bukkit.getServer().getClass().getPackage().getName().substring(23);
	}

	/**
	 * Send the json string to all players on the server.
	 */
	public void send() {
		final List<Object> players = new ArrayList<>();
		for(final Player p : Bukkit.getServer().getOnlinePlayers())
			players.add(p);

		send(players.toArray(new Player[players.size()]));
	}

	/**
	 * Send the json string to specified player(s)
	 * @param player to send the message to.
	 */
	public void send(final Player... player) {
		String nmsClass = ((!getServerVersion().startsWith("v1_7_R")) ? "IChatBaseComponent$" : "") + "ChatSerializer";
		for(final Player p : player) {
			try {
                final Object comp = getNmsClass(nmsClass).getMethod("a", String.class).invoke(null, msg + "]}]");
                final Object packet = getNmsClass("PacketPlayOutChat").getConstructor(getNmsClass("IChatBaseComponent")).newInstance(comp);
                final Object handle = p.getClass().getMethod("getHandle").invoke(p);
                final Object playerConnection = handle.getClass().getField("playerConnection").get(handle);

				playerConnection.getClass().getMethod("sendPacket",getNmsClass("Packet")).invoke(playerConnection, packet);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Append text to the json message.
	 * @param text to be appended
	 * @return json string builder
	 */
	public JsonStringBuilder append(final String text) {
		return new JsonStringBuilder(this, esc(text));
	}
	
	private static String esc(final String s) {
		return JSONObject.escape(s);
	}
	/**
	 * 
	 * @author JustisR
	 *
	 */
	public static class JsonStringBuilder {

		private final JsonMessage message;
		private final String string;
		private String hover = "", click = "";

		/**
		 * Settings for the json message's text
		 * @param msg the original message
		 * @param text the text to be appended to the message.
		 */
		private JsonStringBuilder(final JsonMessage msg, final String text) {
			message = msg;
			string = ",{\"text\":\"" + text + "\"";
		}

		/**
		 * Set the hover event's action as showing a tooltip with the given text
		 * @param lore the text to be displayed in the tooltip
		 * @return the json string builder to which you are applying settings
		 */
		public JsonStringBuilder setHoverAsTooltip(final String... lore) {
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < lore.length; i++)
				if (i + 1 == lore.length) builder.append(lore[i]);
				else builder.append(lore[i] + "\n");
			hover = ",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + esc(builder.toString()) + "\"}";
			return this;
		}
		
		/**
		 * Set the hover event's action as showing an achievement
		 * @param ach the achievement to be displayed.
		 * 	for list of achievements, visit <a href="http://minecraft.gamepedia.com/Achievements">here</a>
		 * @return the json string builder to which you are applying settings
		 */
		public JsonStringBuilder setHoverAsAchievement(final String ach) {
			hover = ",\"hoverEvent\":{\"action\":\"show_achievement\",\"value\":\"achievement." + esc(ach) + "\"}";
			return this;
		}

		/**
		 * Set the click event's action as redirecting to a URL
		 * @param link to redirect to
		 * @return the json string builder to which you are applying settings.
		 */
		public JsonStringBuilder setClickAsURL(final String link) {
			click = ",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"" + esc(link) + "\"}";
			return this;
		}

		/**
		 * Set the click event's action as suggesting a command
		 * @param cmd to suggest
		 * @return the json string builder to which you are applying settings;
		 */
		public JsonStringBuilder setClickAsSuggestCmd(final String cmd) {
			click = ",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"" + esc(cmd) + "\"}";
			return this;
		}

		/**
		 * Set the click event's action as executing a command
		 * @param cmd
		 * @return
		 */
		public JsonStringBuilder setClickAsExecuteCmd(final String cmd) {
			click = ",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + esc(cmd) + "\"}";
			return this;
		}

		/**
		 * Finalize the appending of the text, with settings.
		 * @return
		 */
		public JsonMessage save() {
			message.msg += string + hover + click + "}";
			return message;
		}
	}

}