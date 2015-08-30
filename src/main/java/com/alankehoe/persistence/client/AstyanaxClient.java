package com.alankehoe.persistence.client;

import com.netflix.astyanax.AstyanaxContext;
import com.netflix.astyanax.Cluster;
import com.netflix.astyanax.connectionpool.NodeDiscoveryType;
import com.netflix.astyanax.connectionpool.impl.ConnectionPoolConfigurationImpl;
import com.netflix.astyanax.connectionpool.impl.ConnectionPoolType;
import com.netflix.astyanax.connectionpool.impl.CountingConnectionPoolMonitor;
import com.netflix.astyanax.impl.AstyanaxConfigurationImpl;
import com.netflix.astyanax.thrift.ThriftFamilyFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

public class AstyanaxClient {

    private static Cluster cluster;

    static {
        ConnectionPoolConfigurationImpl poolConfig = getConnectionPoolConfiguration();
        
        AstyanaxContext<Cluster> clusterContext = getAstyanaxClusterContext(poolConfig);

        clusterContext.start();

        cluster = clusterContext.getClient();
    }

    public static Cluster getCluster() {
        return cluster;
    }

    private static ConnectionPoolConfigurationImpl getConnectionPoolConfiguration() {
        return new ConnectionPoolConfigurationImpl("MyConnectionPool")
                .setPort(9160)
                .setMaxConnsPerHost(1)
                .setSeeds("127.0.0.1:9160")
                .setLatencyAwareUpdateInterval(10000)
                .setLatencyAwareResetInterval(0)
                .setLatencyAwareBadnessThreshold(2)
                .setLatencyAwareWindowSize(100);
    }

    private static AstyanaxContext<Cluster> getAstyanaxClusterContext(ConnectionPoolConfigurationImpl poolConfig) {
        AstyanaxContext.Builder builder = new AstyanaxContext.Builder();

        return builder
                .forCluster("Test Cluster")
                .withAstyanaxConfiguration(new AstyanaxConfigurationImpl()
                                .setDiscoveryType(NodeDiscoveryType.RING_DESCRIBE)
                                .setConnectionPoolType(ConnectionPoolType.TOKEN_AWARE)
                )
                .withConnectionPoolConfiguration(poolConfig)
                .withConnectionPoolMonitor(new CountingConnectionPoolMonitor())
                .buildCluster(ThriftFamilyFactory.getInstance());
    }
}
