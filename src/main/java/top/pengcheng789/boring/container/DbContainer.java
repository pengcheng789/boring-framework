package top.pengcheng789.boring.container;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.pengcheng789.boring.config.BoringConfig;
import top.pengcheng789.boring.exception.NotSpecifiedDbNameException;

import java.util.Arrays;

/**
 * @author Cai Pengcheng
 * Create Date: 2018-01-23
 */
public class DbContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbContainer.class);

    private static MongoClient mongoClient;

    static {
        if (BoringConfig.isMongodbEnable()) {
            LOGGER.info("Mongodb is enable. Creating mongodb client ...");
            try {
                mongoClient = createClient();
            } catch (NotSpecifiedDbNameException e) {
                LOGGER.error("Create mongodb client failure.");
                throw new RuntimeException(e);
            }
            LOGGER.info("Created mongodb client.");
        } else {
            LOGGER.info("Mongodb is not enable.");
        }
    }

    /**
     * Create a Mongodb client.
     */
    private static MongoClient createClient() throws NotSpecifiedDbNameException {
        String host = BoringConfig.getAppMongoHost();
        int port = BoringConfig.getAppMongoPort();
        String database = BoringConfig.getAppMongoDatabase();
        String username = BoringConfig.getAppMongoUsername();
        String password = BoringConfig.getAppMongoPassword();

        if ("".equals(database)) {
            throw new NotSpecifiedDbNameException("Couldn't find the property of \'database\'");
        }

        if ("".equals(username) || "".equals(password)) {
            return new MongoClient(host, port);
        }

        MongoCredential credential = MongoCredential
            .createCredential(username, database, password.toCharArray());
        MongoClientOptions options = MongoClientOptions.builder().sslEnabled(true).build();

        return new MongoClient(new ServerAddress(host, port), credential, options);
    }
}
