package me.hulipvp.celestial.storage.backend;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import me.hulipvp.celestial.factions.Faction;
import me.hulipvp.celestial.factions.FactionManager;
import me.hulipvp.celestial.profile.Profile;
import me.hulipvp.celestial.api.object.CelestialObject;
import org.bson.Document;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.eq;

public class MongoBackend implements IBackend<CelestialObject> {

    private final JavaPlugin plugin;

    private MongoClient mongoClient;
    private MongoCollection<Document> factions, profiles;

    private Map<Class<? extends CelestialObject>, MongoCollection<Document>> collections;

    public MongoBackend(final JavaPlugin plugin) {
        this.plugin = plugin;

        if(!load(plugin))
            plugin.getServer().getPluginManager().disablePlugin(plugin);
    }

    @Override
    public boolean load(final JavaPlugin plugin) {
        try {
            final MongoInformation information = new MongoInformation(plugin.getConfig().getConfigurationSection("database"));
            final ServerAddress serverAddress = new ServerAddress(information.getAddress(), information.getPort());

            if(information.isAuthEnabled())
                mongoClient = new MongoClient(serverAddress, information.getMongoCredentials(), MongoClientOptions.builder().build()); // Just keep the default options for now
            else
                mongoClient = new MongoClient(serverAddress);

            final MongoDatabase database = mongoClient.getDatabase(information.getDbName());
            factions = database.getCollection("factions");
            profiles = database.getCollection("profiles");

            collections = new HashMap<>();
            collections.put(Profile.class, factions);
            collections.put(Faction.class, profiles);

            return true;
        } catch(final Exception ex) {
            plugin.getLogger().severe("Unable to establish a Mongo connection: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public void close() {
        if(mongoClient != null)
            mongoClient.close();
    }

    @Override
    public void create(final CelestialObject obj) {
        runAsync(obj, object -> {
            getCollection(object).insertOne(object.toDocument());
        });
    }

    @Override
    public void save(final CelestialObject obj) {
        getCollection(obj).findOneAndReplace(eq("uuid", obj.getUuidString()), obj.toDocument());
    }

    @Override
    public void saveAsync(final CelestialObject obj) {
        runAsync(obj, this::save);
    }

    @Override
    public void delete(final CelestialObject obj) {
        runAsync(obj, object -> {
            getCollection(object).findOneAndDelete(eq("uuid", object.getUuidString()));
        });
    }

    @Override
    public void load(final CelestialObject obj) {
        final Document document = getCollection(obj).find(eq("uuid", obj.getUuidString())).first();
        if(document != null)
            obj.fromDocument(document);
        else
            create(obj);
    }

    @Override
    public void loadFactions() {
        for(final Document document : collections.get(Faction.class).find())
            FactionManager.get().load(document);
    }

    private MongoCollection<Document> getCollection(final CelestialObject obj) {
        MongoCollection<Document> collection = collections.get(obj.getClass());
        if(collection == null)
            collection = collections.get(obj.getClass().getSuperclass()); // In case of a PlayerFaction or any of that type

        return collection;
    }

    private void runAsync(final CelestialObject obj, final Consumer<CelestialObject> consumer) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            consumer.accept(obj);
        });
    }

    /**
     *
     * Our class that will be used to collect the Mongo login information
     * from the plugin's configuration
     *
     */
    private class MongoInformation {

        @Getter private String dbName;
        @Getter private String address;
        @Getter private int port;

        @Getter private boolean authEnabled;
        @Getter private String authDb;
        @Getter private String authUser;
        @Getter private char[] authPass;

        public MongoInformation(final ConfigurationSection section) {
            dbName = section.getString("name");
            address = section.getString("address");
            port = section.getInt("port");

            authEnabled = section.getBoolean("auth.enabled");
            authDb = section.getString("auth.authDb");
            authUser = section.getString("auth.username");
            authPass = section.getString("auth.password").toCharArray();
        }

        public MongoCredential getMongoCredentials() {
            return MongoCredential.createCredential(authUser, authDb, authPass);
        }
    }
}
