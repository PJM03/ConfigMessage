import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.github.pjm03.messageutil.TestPlugin;
import com.github.pjm03.messageutil.config.json.JsonConfig;
import com.github.pjm03.messageutil.config.yaml.YamlConfig;
import org.bukkit.plugin.Plugin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.IOException;

public class Test {
    private Plugin plugin;
    private ServerMock server;

    @BeforeEach
    void setup() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(TestPlugin.class);
    }

    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }

    @org.junit.jupiter.api.Test
    void test() throws IOException {
        PlayerMock player = server.addPlayer("Test");

        JsonConfig jsonConfig = new JsonConfig(new File("./example_message.json"), "message", "message-chain");
        YamlConfig yamlConfig = new YamlConfig(new File("./example_config.yml"), "message", "message-chain");

//        System.out.println(jsonConfig.parseMessage("chat"));
//        System.out.println(jsonConfig.parseMessage("actionbar"));
//        System.out.println(jsonConfig.parseMessage("title"));
//
//        System.out.println();
//
//        System.out.println(yamlConfig.parseMessage("chat"));
//        System.out.println(yamlConfig.parseMessage("actionbar"));
//        System.out.println(yamlConfig.parseMessage("title"));
//
//        System.out.println();

    }
}
