package com.alankehoe.initializers;

import com.netflix.astyanax.AstyanaxContext;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.NodeDiscoveryType;
import com.netflix.astyanax.connectionpool.impl.ConnectionPoolConfigurationImpl;
import com.netflix.astyanax.connectionpool.impl.CountingConnectionPoolMonitor;
import com.netflix.astyanax.impl.AstyanaxConfigurationImpl;
import com.netflix.astyanax.thrift.ThriftFamilyFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AstyanaxClient {

    private AstyanaxContext<Keyspace> context;

    @PostConstruct
    public void setupContext() {
        context = new AstyanaxContext.Builder()
                .forCluster("Test Cluster")
                .forKeyspace("application")
                .withAstyanaxConfiguration(new AstyanaxConfigurationImpl()
                                .setDiscoveryType(NodeDiscoveryType.RING_DESCRIBE)
                                .setTargetCassandraVersion("1.2")
                                .setCqlVersion("3.0.0")
                )
                .withConnectionPoolConfiguration(new ConnectionPoolConfigurationImpl("MyConnectionPool")
                                .setPort(9160)
                                .setMaxConnsPerHost(1)
                                .setSeeds("127.0.0.1:9160")
                )
                .withConnectionPoolMonitor(new CountingConnectionPoolMonitor())
                .buildKeyspace(ThriftFamilyFactory.getInstance());

        context.start();
    }

    public Keyspace getAstyanaxClient() {
        return context.getClient();
    }
}
